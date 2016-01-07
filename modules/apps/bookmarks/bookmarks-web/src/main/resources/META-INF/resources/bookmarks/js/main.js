AUI.add(
	'liferay-bookmarks',
	function(A) {
		var Lang = A.Lang;

		var Bookmarks = A.Component.create(
			{
				ATTRS: {
					editEntryUrl: {
						validator: Lang.isString
					},

					form: {
						validator: Lang.isObject
					},

					moveEntryUrl: {
						validator: Lang.isString
					},

					searchContainerId: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'bookmarks',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var namespace = instance.NS;

						var searchContainer = Liferay.SearchContainer.get(namespace + instance.get('searchContainerId'));

						searchContainer.registerAction('move-to-folder', A.bind('_moveToFolder', instance));
						searchContainer.registerAction('move-to-trash', A.bind('_moveToTrash', instance));

						instance._searchContainer = searchContainer;
					},

					_moveToFolder: function(obj) {
						var instance = this;

						var namespace = instance.NS;

						var dropTarget = obj.targetItem;

						var selectedItems = obj.selectedItems;

						var folderId = dropTarget.attr('data-folder-id');

						if (folderId) {
							if (!instance._searchContainer.select ||
								selectedItems.indexOf(dropTarget.one('input[type=checkbox]'))
							) {
								var form = instance.get('form').node;

								form.get(namespace + 'newFolderId').val(folderId);

								instance._processAction('move', instance.get('moveEntryUrl'));
							}
						}
					},

					_moveToTrash: function() {
						var instance = this;

						instance._processAction('move_to_trash', instance.get('editEntryUrl'));
					},

					_processAction: function(action, url, redirectUrl) {
						var instance = this;

						var namespace = instance.NS;

						var form = instance.get('form').node;

						redirectUrl = redirectUrl || location.href;

						form.attr('method', instance.get('form').method);

						if (form.get(namespace + 'javax-portlet-action')) {
							form.get(namespace + 'javax-portlet-action').val(action);
						}
						else {
							form.get(namespace + 'cmd').val(action);
						}

						form.get(namespace + 'redirect').val(redirectUrl);

						submitForm(form, url);
					}
				}
			}
		);

		Liferay.Portlet.Bookmarks = Bookmarks;
	},
	'',
	{
		requires: ['liferay-portlet-base']
	}
);