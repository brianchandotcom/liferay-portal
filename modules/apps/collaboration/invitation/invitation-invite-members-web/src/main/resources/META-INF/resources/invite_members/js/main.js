AUI.add(
	'liferay-portlet-invite-members',
	function(A) {
		var Lang = A.Lang;

		var Language = Liferay.Language;

		var Util = Liferay.Util;

		var KEY_ENTER = 13;

		var STR_AVAILABLE_USERS_URL = 'availableUsersURL'

		var STR_CLICK = 'click';

		var STR_KEYPRESS = 'keypress';

		var TPL_EMAIL_ROW =
			'<div class="user" data-emailAddress="{emailAddress}">' +
				'<span class="email">{emailAddress}</span>' +
			'</div>';

		var TPL_MORE_RESULTS =
			'<div class="more-results">' +
				'<a href="javascript:;" data-end="{end}">{message}</a>' +
			'</div>';

		var TPL_NO_USERS_MESSAGE = '<small class="text-capitalize text-muted">{message}</small>';

		var TPL_USER =
			'<div class="{cssClass}" data-userId="{userId}">' +
			'<span class="name">{userFullName}</span>'+
			'<span class="email">{userEmailAddress}</span>' +
			'</div>';

		var InviteMembers = A.Component.create(
			{
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
					initializer: function(params) {
						var instance = this;

						if (!instance.rootNode) {
							return;
						}

						instance._form = instance.get('form').node;
						instance._invitedMembersList = instance.one('#invitedMembersList');
						instance._inviteUserSearch = instance.one('#inviteUserSearch');
						instance._membersList = instance.one('#membersList');

						instance._inviteMembersList = new InviteMembersList(
							{
								inputNode: instance._inviteUserSearch,
								listNode: instance._membersList,
								minQueryLength: 0,
								requestTemplate: function(query) {
									return {
										end: instance.get('pageDelta'),
										keywords: query,
										start: 0
									}
								},
								resultTextLocator: function(response) {
									var result = '';

									if (typeof response.toString != 'undefined') {
										result = response.toString();
									}
									else if (typeof response.responseText != 'undefined') {
										result = response.responseText;
									}

									return result;
								},
								source: instance._createDataSource(instance.get(STR_AVAILABLE_USERS_URL))
							}
						);

						instance._inviteMembersList.sendRequest();

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_addMemberEmail: function(event) {
						var instance = this;

						var emailInput = instance.one('#emailAddress');

						var emailAddress = Lang.trim(emailInput.val());

						if (emailAddress) {
							var emailRow = A.Lang.sub(
								TPL_EMAIL_ROW,
								{
									emailAddress: emailAddress
								}
							);

							var invitedEmailList = instance.one('#invitedEmailList');

							invitedEmailList.append(emailRow);
						}

						emailInput.val('');

						Util.focusFormField(emailInput.getDOM());
					},

					_addMemberInvite: function(user) {
						var instance = this;

						user.addClass('invited').cloneNode(true).appendTo(instance._invitedMembersList);
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.one('#emailButton').on(STR_CLICK, instance._addMemberEmail, instance),
							instance._inviteMembersList.on('results', instance._onInviteMembersListResults, instance),
							instance._form.on('submit', instance._syncFields, instance),
							instance._membersList.delegate(STR_CLICK, instance._onMemberListClick, '.more-results a', instance),
							instance.rootNode.delegate(STR_CLICK, instance._handleInvite, '.user', instance),
							instance.rootNode.on(STR_KEYPRESS, instance._onEmailKeypress, instance)
						];
					},

					_createDataSource: function(url) {
						var instance = this;

						return new A.DataSource.IO(
							{
								ioConfig: {
									method: "post"
								},
								on: {
									request: function(event) {
										var data = event.request;

										event.cfg.data = instance.ns(
											{
												end: data.end || instance.get('pageDelta'),
												keywords: data.keywords || '',
												start: data.start || 0
											}
										)
									}
								},
								source: url
							}
						);
					},

					_getByName: function(form, name) {
						var instance = this;

						return instance.one('[name=' + instance.ns(name) + ']', form);
					},

					_handleInvite: function(event) {
						var instance = this;

						var user = event.currentTarget;

						var userId = user.attr('data-userId');

						if (userId) {
							if (user.hasClass('invited')) {
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

					_onEmailKeypress: function(event) {
						var instance = this;

						if (event.keyCode == KEY_ENTER) {
							instance._addMemberEmail();

							event.preventDefault();
						}
					},

					_onInviteMembersListResults: function(event) {
						var instance = this;

						var responseData = A.JSON.parse(event.data.responseText);

						instance._membersList.html(instance._renderResults(responseData).join(''));
					},

					_onMemberListClick: function(event) {
						var instance = this;

						var node = event.currentTarget;

						var start = A.DataType.Number.parse(node.getAttribute('data-end'));

						var pageDelta = instance.get('pageDelta');

						var end = start + pageDelta;

						A.io.request(
							instance.get(STR_AVAILABLE_USERS_URL),
							{
								after: {
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										var moreResults = instance._membersList.one('.more-results');

										moreResults.remove();

										instance._membersList.append(instance._renderResults(responseData).join(''));
									}
								},
								data: instance.ns(
									{
										end: end,
										keywords: instance._inviteUserSearch.get('value'),
										start: start
									}
								),
								dataType: 'json'
							}
						);
					},

					_removeEmailInvite: function(user) {
						user.remove();
					},

					_removeMemberInvite: function(user, userId) {
						var instance = this;

						userId = userId || user.getAttribute('data-userId');

						var membersList = instance.one('#membersList');

						var memberListUser = membersList.one('[data-userId="' + userId + '"]');

						if (memberListUser) {
							memberListUser.removeClass('invited');
						}

						var invitedMembersList = instance.one('#invitedMembersList');


						var invitedUser = invitedMembersList.one('[data-userId="' + userId + '"]');

						invitedUser.remove();
					},

					_renderResults: function(responseData) {
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
										message: Language.get('there-are-no-users-to-invite')
									}
								);

								buffer.push(noUsersMessage);
							}
						}
						else {
							buffer.push(
								A.Array.map(
									results,
									function(result) {
										var invited = instance._invitedMembersList.one('[data-userId="' + result.userId + '"]');

										return Lang.sub(
											TPL_USER,
											{
												cssClass: result.hasPendingMemberRequest ? "pending-member-request user" : (invited ? "invited user" : "user"),
												userEmailAddress: result.userEmailAddress,
												userFullName: result.userFullName,
												userId: result.userId
											}
										);
									}
								).join('')
							);

							if (count > results.length) {
								var moreResults = Lang.Sub(
									TPL_MORE_RESULTS,
									{
										end : options.end,
										message: Language.get('view-more')
									}
								);

								buffer.push(moreResults);
							}
						}

						return buffer;
					},

					_syncFields: function(form) {
						var instance = this;

						instance._syncInvitedRoleIdField(form);

						instance._syncInvitedTeamIdField(form);

						instance._syncReceiverUserIdsField(form);

						instance._syncReceiverEmailAddressesField(form);
					},

					_syncInvitedRoleIdField : function() {
						var instance = this;

						var form = instance._form;

						var invitedRoleId = instance._getByName(form, 'invitedRoleId');

						var roleId = instance._getByName(form, 'roleId')

						invitedRoleId.val(roleId ? roleId.val() : 0);
					},

					_syncInvitedTeamIdField : function(form) {
						var instance = this;

						var invitedTeamId = instance._getByName(form, 'invitedTeamId');

						var teamId = instance._getByName(form, 'teamId')

						invitedTeamId.val(teamId ? teamId.val() : 0);
					},

					_syncReceiverEmailAddressesField : function(form) {
						var instance = this;

						var receiverEmailAddresses = instance._getByName(form, 'receiverEmailAddresses');

						var emailAddresses = [];

						var invitedEmailList = instance.one('#invitedEmailList');

						invitedEmailList.all('.user').each(
							function (item, index) {
								emailAddresses.push(item.attr('data-emailAddress'));
							}
						);

						receiverEmailAddresses.val(emailAddresses.join());
					},

					_syncReceiverUserIdsField :function(form) {
						var instance = this;

						var receiverUserIds = instance._getByName(form, 'receiverUserIds');

						var userIds = [];

						var invitedMembersList = instance.one('#invitedMembersList');

						invitedMembersList.all('.user').each(
							function (item, index) {
								userIds.push(item.attr('data-userId'));
							}
						);

						receiverUserIds.val(userIds.join());
					}
				}
			}
		);

		var InviteMembersList = A.Component.create(
			{
				EXTENDS: A.Base,

				AUGMENTS: [A.AutoCompleteBase],

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._listNode = A.one(config.listNode);

						instance._bindUIACBase();
						instance._syncUIACBase();
					}
				}
			}
		);

		Liferay.Portlet.InviteMembers = InviteMembers;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'datasource-io', 'datatype-number', 'liferay-portlet-base', 'liferay-util-window', 'node-core']
	}
);