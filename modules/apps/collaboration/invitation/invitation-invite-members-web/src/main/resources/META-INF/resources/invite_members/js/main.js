AUI.add(
	'liferay-so-invite-members',
	function(A) {
		var Lang = A.Lang;
		var Util = Liferay.Util;

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

						var rootNode = instance.rootNode;

						if (!rootNode) {
							return;
						}

						var membersList = instance.one('#membersList');

						var form = instance.get('form').node;

						form.on(
							'submit',
							function(event) {
								instance._syncFields(form);
							}
						);

						instance.one('#emailButton').on(
							'click',
							function(event) {
								instance._addMemberEmail();

								var emailInput = instance.one('#emailAddress');

								Util.focusFormField(emailInput.getDOM());
							}
						);

						rootNode.delegate(
							'click',
							function(event) {
								var user = event.currentTarget;

								var userEmail = user.attr('data-emailAddress');
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
							'.user'
						);

						rootNode.delegate(
							'keyup',
							function(event) {
								if (event.keyCode == 13) {
									instance._addMemberEmail();
								}
							},
							'.controls'
						);
					},

					_addMemberEmail: function() {
						var instance = this;

						var emailInput = instance.one('#emailAddress');

						var emailAddress = Lang.trim(emailInput.val());

						if (emailAddress) {
							var html = '<div class="user" data-emailAddress="' + emailAddress + '"><span class="email">' + emailAddress + '</span></div>';

							var invitedEmailList = instance.one('#invitedEmailList');

							invitedEmailList.append(html);
						}

						emailInput.val('');
					},

					_addMemberInvite: function(user) {
						var instance = this;

						var invitedMembersList = instance.one('#invitedMembersList');

						user.addClass('invited').cloneNode(true).appendTo(invitedMembersList);
					},

					_getByName: function(form, name) {
						var instance = this;

						return instance.one('[name=' + instance.ns(name) + ']', form);
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

					_syncInvitedRoleIdField : function(form) {
						var instance = this;

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

		Liferay.InviteMembers = InviteMembers;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base', 'liferay-util-window']
	}
);

AUI.add(
	'liferay-so-invite-members-list',
	function(A) {
		var InviteMembersList = A.Base.create(
			'inviteMembersList',
			A.Base,
			[A.AutoCompleteBase],
			{
				initializer: function(config) {
					this._listNode = A.one(config.listNode);

					this._bindUIACBase();
					this._syncUIACBase();
				}
			}
		);

		Liferay.InviteMembersList = InviteMembersList;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'node-core']
	}
);