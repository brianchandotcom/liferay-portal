/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-portlet-invite-members',
	(A) => {
		const Lang = A.Lang;

		const Language = Liferay.Language;

		const Util = Liferay.Util;

		const CSS_INVITED = 'invited';

		const KEY_ENTER = 13;

		const STR_AVAILABLE_USERS_URL = 'availableUsersURL';

		const STR_BLANK = '';

		const STR_CLICK = 'click';

		const STR_KEYPRESS = 'keypress';

		const STR_SPACE = ' ';

		const TPL_EMAIL_ROW =
			'<div class="user" data-emailAddress="{emailAddress}">' +
			'<span class="email">{emailAddress}</span>' +
			'</div>';

		const TPL_MORE_RESULTS =
			'<div class="more-results">' +
			'<a href="javascript:void(0);" data-end="{end}">{message}</a>' +
			'</div>';

		const TPL_NO_USERS_MESSAGE =
			'<small class="text-capitalize text-muted">{message}</small>';

		const TPL_USER =
			'<div class="{cssClass}" data-userId="{userId}">' +
			'<span class="name">{userFullName}</span>' +
			'<span class="email">{userEmailAddress}</span>' +
			'</div>';

		const InviteMembersList = A.Component.create({
			AUGMENTS: [A.AutoCompleteBase],

			EXTENDS: A.Base,

			prototype: {
				initializer(config) {
					const instance = this;

					instance._listNode = A.one(config.listNode);

					instance._bindUIACBase();
					instance._syncUIACBase();
				},
			},
		});

		const InviteMembers = A.Component.create({
			ATTRS: {
				availableUsersURL: {
					validator: Lang.isString,
				},

				form: {
					validator: Lang.isObject,
				},

				pageDelta: {
					validator: Lang.isInteger,
					value: 50,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'soinvitemembers',

			prototype: {
				_addMemberEmail() {
					const instance = this;

					const emailInput = instance.one('#emailAddress');

					const emailAddress = Lang.trim(emailInput.val());

					if (emailAddress) {
						const emailRow = Lang.sub(TPL_EMAIL_ROW, {
							emailAddress,
						});

						const invitedEmailList = instance.one(
							'#invitedEmailList'
						);

						invitedEmailList.append(emailRow);
					}

					emailInput.val(STR_BLANK);

					Util.focusFormField(emailInput.getDOM());
				},

				_addMemberInvite(user) {
					const instance = this;

					user.addClass(CSS_INVITED)
						.cloneNode(true)
						.appendTo(instance._invitedMembersList);
				},

				_bindUI() {
					const instance = this;

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
						),
					];
				},

				_createDataSource(url) {
					const instance = this;

					return new A.DataSource.IO({
						ioConfig: {
							method: 'post',
						},
						on: {
							request(event) {
								const data = event.request;

								event.cfg.data = instance.ns({
									end: data.end || instance.get('pageDelta'),
									keywords: data.keywords || STR_BLANK,
									start: data.start || 0,
								});
							},
						},
						source: url,
					});
				},

				_getByName(form, name) {
					const instance = this;

					return instance.one(
						'[name=' + instance.ns(name) + ']',
						form
					);
				},

				_handleInvite(event) {
					const instance = this;

					const user = event.currentTarget;

					const userId = user.attr('data-userId');

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
					const instance = this;

					if (Number(event.keyCode) === KEY_ENTER) {
						instance._addMemberEmail();

						event.preventDefault();
					}
				},

				_onInviteMembersListResults(event) {
					const instance = this;

					const responseData = JSON.parse(event.data.responseText);

					instance._membersList.html(
						instance._renderResults(responseData).join(STR_BLANK)
					);
				},

				_onMemberListClick(event) {
					const instance = this;

					const node = event.currentTarget;

					const start = A.DataType.Number.parse(node.dataset.end);

					const end = start + instance.get('pageDelta');

					const body = new URLSearchParams(
						instance.ns({
							end,
							keywords: instance._inviteUserSearch.get('value'),
							start,
						})
					);

					Liferay.Util.fetch(instance.get(STR_AVAILABLE_USERS_URL), {
						body,
						method: 'POST',
					})
						.then((response) => {
							return response.json();
						})
						.then((responseData) => {
							const moreResults = instance._membersList.one(
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
					const instance = this;

					userId = userId || user.dataset.userId;

					const membersList = instance.one('#membersList');

					const memberListUser = membersList.one(
						'[data-userId="' + userId + '"]'
					);

					if (memberListUser) {
						memberListUser.removeClass(CSS_INVITED);
					}

					const invitedUser = instance._invitedMembersList.one(
						'[data-userId="' + userId + '"]'
					);

					invitedUser.remove();
				},

				_renderResults(responseData) {
					const instance = this;

					const count = responseData.count;
					const options = responseData.options;
					const results = responseData.users;

					const buffer = [];

					if (!results.length) {
						if (Number(options.start) === 0) {
							const noUsersMessage = A.Lang.sub(
								TPL_NO_USERS_MESSAGE,
								{
									message: Language.get(
										'there-are-no-users-to-invite'
									),
								}
							);

							buffer.push(noUsersMessage);
						}
					}
					else {
						buffer.push(
							A.Array.map(results, (result) => {
								let cssClass = 'user';

								if (result.hasPendingMemberRequest) {
									cssClass +=
										STR_SPACE + 'pending-member-request';
								}

								const invited = instance._invitedMembersList.one(
									'[data-userId="' + result.userId + '"]'
								);

								if (invited) {
									cssClass += CSS_INVITED;
								}

								return Lang.sub(TPL_USER, {
									cssClass,
									userEmailAddress: Liferay.Util.escapeHTML(
										result.userEmailAddress
									),
									userFullName: Liferay.Util.escapeHTML(
										result.userFullName
									),
									userId: result.userId,
								});
							}).join(STR_BLANK)
						);

						if (count > results.length) {
							const moreResults = Lang.sub(TPL_MORE_RESULTS, {
								end: options.end,
								message: Language.get('view-more'),
							});

							buffer.push(moreResults);
						}
					}

					return buffer;
				},

				_syncFields(form) {
					const instance = this;

					instance._syncInvitedRoleIdField(form);

					instance._syncInvitedTeamIdField(form);

					instance._syncReceiverUserIdsField(form);

					instance._syncReceiverEmailAddressesField(form);
				},

				_syncInvitedRoleIdField() {
					const instance = this;

					const form = instance._form;

					const invitedRoleId = instance._getByName(
						form,
						'invitedRoleId'
					);

					const roleId = instance._getByName(form, 'roleId');

					invitedRoleId.val(roleId ? roleId.val() : 0);
				},

				_syncInvitedTeamIdField(form) {
					const instance = this;

					const invitedTeamId = instance._getByName(
						form,
						'invitedTeamId'
					);

					const teamId = instance._getByName(form, 'teamId');

					invitedTeamId.val(teamId ? teamId.val() : 0);
				},

				_syncReceiverEmailAddressesField(form) {
					const instance = this;

					const receiverEmailAddresses = instance._getByName(
						form,
						'receiverEmailAddresses'
					);

					const emailAddresses = [];

					const invitedEmailList = instance.one('#invitedEmailList');

					invitedEmailList.all('.user').each((item) => {
						emailAddresses.push(item.attr('data-emailAddress'));
					});

					receiverEmailAddresses.val(emailAddresses.join());
				},

				_syncReceiverUserIdsField(form) {
					const instance = this;

					const receiverUserIds = instance._getByName(
						form,
						'receiverUserIds'
					);

					const userIds = [];

					instance._invitedMembersList.all('.user').each((item) => {
						userIds.push(item.attr('data-userId'));
					});

					receiverUserIds.val(userIds.join());
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					if (!instance.rootNode) {
						return;
					}

					const form = instance.get('form');

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
								start: 0,
							};
						},
						resultTextLocator(response) {
							let result = STR_BLANK;

							if (typeof response.toString !== 'undefined') {
								result = response.toString();
							}
							else if (
								typeof response.responseText !== 'undefined'
							) {
								result = response.responseText;
							}

							return result;
						},
						source: instance._createDataSource(
							instance.get(STR_AVAILABLE_USERS_URL)
						),
					});

					instance._inviteMembersList.sendRequest();

					instance._bindUI();
				},
			},
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
			'node-core',
		],
	}
);
