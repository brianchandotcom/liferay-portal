AUI.add(
	'liferay-so-invite-members',
	function(A) {
		var Lang = A.Lang;
		var Util = Liferay.Util;

		var KEY_ENTER = 13;

		var STR_CLICK = 'click';

		var STR_KEYUP = 'keyup';

		var TPL_EMAIL_ROW = new A.Template(
			'<div class="user" data-emailAddress="{emailAddress}">',
				'<span class="email">{emailAddress}</span>',
			'</div>'
		);

		var InviteMembers = A.Component.create(
			{
				ATTRS: {
					form: {
						validator: Lang.isObject
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'soinvitemembers',

				prototype: {
					initializer: function(params) {
						var instance = this;

						instance._rootNode = instance.rootNode;

						if (!instance._rootNode) {
							return;
						}

						instance._form = instance.get('form').node;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_addMemberEmail: function() {
						var instance = this;

						var emailInput = instance.one('#emailAddress');

						var emailAddress = Lang.trim(emailInput.val());

						if (emailAddress) {
							var emailRow = TPL_EMAIL_ROW.render(
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

						var invitedMembersList = instance.one('#invitedMembersList');

						user.addClass('invited').cloneNode(true).appendTo(invitedMembersList);
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.one('#emailButton').on(STR_CLICK, instance._addMemberEmail, instance),
							instance._rootNode.delegate(STR_CLICK, instance._handleInvite, '.user', instance),
							instance._rootNode.delegate(STR_KEYUP, instance._onEmailKeyup, '.controls', instance),
							instance._form.on('submit', instance._syncFields, instance)
						];
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

					_onEmailKeyup: function(event) {
						var instance = this;

						if (event.keyCode == KEY_ENTER) {
							instance._addMemberEmail();
						}
					},

					_removeEmailInvite: function(user) {
						user.remove();
					},

					_removeMemberInvite: function(user, userId) {
						var instance = this;

						userId = userId || user.getAttribute('data-userId');

						var membersList = instance.one('#membersList');

						var user = membersList.one('[data-userId="' + userId + '"]');

						if (user) {
							user.removeClass('invited');
						}

						var invitedMembersList = instance.one('#invitedMembersList');


						var invitedUser = invitedMembersList.one('[data-userId="' + userId + '"]');

						invitedUser.remove();
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
					initializer: function (config) {
						var instance = this;

						instance._listNode = A.one(config.listNode);

						instance._bindUIACBase();
						instance._syncUIACBase();
					}
				}
			}
		);

		Liferay.InviteMembers = InviteMembers;
		Liferay.InviteMembersList = InviteMembersList;
	},
	'',
	{
		requires: ['aui-base', 'aui-template-deprecated', 'autocomplete-base', 'liferay-portlet-base', 'liferay-util-window', 'node-core']
	}
);