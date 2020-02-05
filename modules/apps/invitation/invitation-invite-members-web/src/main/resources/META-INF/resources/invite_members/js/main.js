/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-portlet-invite-members',
	A => {
		var Lang = A.Lang;

		var Language = Liferay.Language;

		var LString = Lang.String;

		var Util = Liferay.Util;

		var CSS_INVITED = 'invited';

		var KEY_ENTER = 13;

		var STR_AVAILABLE_USERS_URL = 'availableUsersURL';

		var STR_BLANK = '';

		var STR_CLICK = 'click';

		var STR_KEYPRESS = 'keypress';

		var STR_SPACE = ' ';

		var TPL_EMAIL_ROW =
			'<div class="user" data-emailAddress="{emailAddress}">' +
			'<span class="email">{emailAddress}</span>' +
			'</div>';

		var TPL_MORE_RESULTS =
			'<div class="more-results">' +
			'<a href="javascript:;" data-end="{end}">{message}</a>' +
			'</div>';

		var TPL_NO_USERS_MESSAGE =
			'<small class="text-capitalize text-muted">{message}</small>';

		var TPL_USER =
			'<div class="{cssClass}" data-userId="{userId}">' +
			'<span class="name">{userFullName}</span>' +
			'<span class="email">{userEmailAddress}</span>' +
			'</div>';

		var InviteMembers = A.Component.create({
			ATTRS: {
				availableUsersURL: {
					validator: Lang.isString
				},

				form: {
					validator: Lang.isObject
				},

				pageDelta: {
					validator: Lang.isInteger,
					value: 50
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'soinvitemembers',

			prototype: {
				_addMemberEmail() {
					var instance = this;

					var emailInput = instance.one('#emailAddress');

					var emailAddress = Lang.trim(emailInput.val());

					if (emailAddress) {
						var emailRow = Lang.sub(TPL_EMAIL_ROW, {
							emailAddress
						});

						var invitedEmailList = instance.one(
							'#invitedEmailList'
						);

						invitedEmailList.append(emailRow);
					}

					emailInput.val(STR_BLANK);

					Util.focusFormField(emailInput.getDOM());
				},

				_addMemberInvite(user) {
					var instance = this;

					user.addClass(CSS_INVITED)
						.cloneNode(true)
						.appendTo(instance._invitedMembersList);
				},

				_bindUI() {
					var instance = this;

					instance._eventHandles = [
						instance
							.one('#emailButton')
							.on(STR_CLICK, instance._addMemberEmail, instance),
						instance._inviteMembersList.on(
							'results',
							instance._onInviteMembersListResults,
							instance
						),
						instance._form.on(
							'submit',
							instance._syncFields,
							instance
						),
						instance._membersList.delegate(
							STR_CLICK,
							instance._onMemberListClick,
							'.more-results a',
							instance
						),
						instance.rootNode.delegate(
							STR_CLICK,
							instance._handleInvite,
							'.user',
							instance
						),
						instance.rootNode.on(
							STR_KEYPRESS,
							instance._onEmailKeypress,
							instance
						)
					];
				},

				_createDataSource(url) {
					var instance = this;

					return new A.DataSource.IO({
						ioConfig: {
							method: 'post'
						},
						on: {
							request(event) {
								var data = event.request;

								event.cfg.data = instance.ns({
									end: data.end || instance.get('pageDelta'),
									keywords: data.keywords || STR_BLANK,
									start: data.start || 0
								});
							}
						},
						source: url
					});
				},

				_getByName(form, name) {
					var instance = this;

					return instance.one(
						'[name=' + instance.ns(name) + ']',
						form
					);
				},

				_handleInvite(event) {
					var instance = this;

					var user = event.currentTarget;

					var userId = user.attr('data-userId');

					if (userId) {
						if (user.hasClass(CSS_INVITED)) {
							instance._removeMemberInvite(user, userId);
						}
						else {
							instance._addMemberInvite(user);
						}
					}
					else {
						instance._removeEmailInvite(user);
					}
				},

				_onEmailKeypress(event) {
					var instance = this;

					if (event.keyCode == KEY_ENTER) {
						instance._addMemberEmail();

						event.preventDefault();
					}
				},

				_onInviteMembersListResults(event) {
					var instance = this;

					var responseData = A.JSON.parse(event.data.responseText);

					instance._membersList.html(
						instance._renderResults(responseData).join(STR_BLANK)
					);
				},

				_onMemberListClick(event) {
					var instance = this;

					var node = event.currentTarget;

					var start = A.DataType.Number.parse(
						node.getAttribute('data-end')
					);

					var end = start + instance.get('pageDelta');

					const body = new URLSearchParams(
						instance.ns({
							end,
							keywords: instance._inviteUserSearch.get('value'),
							start
						})
					);

					Liferay.Util.fetch(instance.get(STR_AVAILABLE_USERS_URL), {
						body,
						method: 'POST'
					})
						.then(response => {
							return response.json();
						})
						.then(responseData => {
							var moreResults = instance._membersList.one(
								'.more-results'
							);

							moreResults.remove();

							instance._membersList.append(
								instance
									._renderResults(responseData)
									.join(STR_BLANK)
							);
						});
				},

				_removeEmailInvite(user) {
					user.remove();
				},

				_removeMemberInvite(user, userId) {
					var instance = this;

					userId = userId || user.getAttribute('data-userId');

					var membersList = instance.one('#membersList');

					var memberListUser = membersList.one(
						'[data-userId="' + userId + '"]'
					);

					if (memberListUser) {
						memberListUser.removeClass(CSS_INVITED);
					}

					var invitedUser = instance._invitedMembersList.one(
						'[data-userId="' + userId + '"]'
					);

					invitedUser.remove();
				},

				_renderResults(responseData) {
					var instance = this;

					var count = responseData.count;
					var options = responseData.options;
					var results = responseData.users;

					var buffer = [];

					if (results.length == 0) {
						if (options.start == 0) {
							var noUsersMessage = A.Lang.sub(
								TPL_NO_USERS_MESSAGE,
								{
									message: Language.get(
										'there-are-no-users-to-invite'
									)
								}
							);

							buffer.push(noUsersMessage);
						}
					}
					else {
						buffer.push(
							A.Array.map(results, result => {
								var cssClass = 'user';

								if (result.hasPendingMemberRequest) {
									cssClass +=
										STR_SPACE + 'pending-member-request';
								}

								var invited = instance._invitedMembersList.one(
									'[data-userId="' + result.userId + '"]'
								);

								if (invited) {
									cssClass += CSS_INVITED;
								}

								return Lang.sub(TPL_USER, {
									cssClass,
									userEmailAddress: LString.escapeHTML(
										result.userEmailAddress
									),
									userFullName: LString.escapeHTML(
										result.userFullName
									),
									userId: result.userId
								});
							}).join(STR_BLANK)
						);

						if (count > results.length) {
							var moreResults = Lang.sub(TPL_MORE_RESULTS, {
								end: options.end,
								message: Language.get('view-more')
							});

							buffer.push(moreResults);
						}
					}

					return buffer;
				},

				_syncFields(form) {
					var instance = this;

					instance._syncInvitedRoleIdField(form);

					instance._syncInvitedTeamIdField(form);

					instance._syncReceiverUserIdsField(form);

					instance._syncReceiverEmailAddressesField(form);
				},

				_syncInvitedRoleIdField() {
					var instance = this;

					var form = instance._form;

					var invitedRoleId = instance._getByName(
						form,
						'invitedRoleId'
					);

					var roleId = instance._getByName(form, 'roleId');

					invitedRoleId.val(roleId ? roleId.val() : 0);
				},

				_syncInvitedTeamIdField(form) {
					var instance = this;

					var invitedTeamId = instance._getByName(
						form,
						'invitedTeamId'
					);

					var teamId = instance._getByName(form, 'teamId');

					invitedTeamId.val(teamId ? teamId.val() : 0);
				},

				_syncReceiverEmailAddressesField(form) {
					var instance = this;

					var receiverEmailAddresses = instance._getByName(
						form,
						'receiverEmailAddresses'
					);

					var emailAddresses = [];

					var invitedEmailList = instance.one('#invitedEmailList');

					invitedEmailList.all('.user').each(item => {
						emailAddresses.push(item.attr('data-emailAddress'));
					});

					receiverEmailAddresses.val(emailAddresses.join());
				},

				_syncReceiverUserIdsField(form) {
					var instance = this;

					var receiverUserIds = instance._getByName(
						form,
						'receiverUserIds'
					);

					var userIds = [];

					instance._invitedMembersList.all('.user').each(item => {
						userIds.push(item.attr('data-userId'));
					});

					receiverUserIds.val(userIds.join());
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					if (!instance.rootNode) {
						return;
					}

					var form = instance.get('form');

					instance._form = instance.one(form.node);

					instance._invitedMembersList = instance.one(
						'#invitedMembersList'
					);
					instance._inviteUserSearch = instance.one(
						'#inviteUserSearch'
					);
					instance._membersList = instance.one('#membersList');

					instance._inviteMembersList = new InviteMembersList({
						inputNode: instance._inviteUserSearch,
						listNode: instance._membersList,
						minQueryLength: 0,
						requestTemplate(query) {
							return {
								end: instance.get('pageDelta'),
								keywords: query,
								start: 0
							};
						},
						resultTextLocator(response) {
							var result = STR_BLANK;

							if (typeof response.toString != 'undefined') {
								result = response.toString();
							}
							else if (
								typeof response.responseText != 'undefined'
							) {
								result = response.responseText;
							}

							return result;
						},
						source: instance._createDataSource(
							instance.get(STR_AVAILABLE_USERS_URL)
						)
					});

					instance._inviteMembersList.sendRequest();

					instance._bindUI();
				}
			}
		});

		var InviteMembersList = A.Component.create({
			AUGMENTS: [A.AutoCompleteBase],

			EXTENDS: A.Base,

			prototype: {
				initializer(config) {
					var instance = this;

					instance._listNode = A.one(config.listNode);

					instance._bindUIACBase();
					instance._syncUIACBase();
				}
			}
		});

		Liferay.Portlet.InviteMembers = InviteMembers;
	},
	'',
	{
		requires: [
			'aui-base',
			'autocomplete-base',
			'datasource-io',
			'datatype-number',
			'liferay-portlet-base',
			'liferay-util-window',
			'node-core'
		]
	}
);
