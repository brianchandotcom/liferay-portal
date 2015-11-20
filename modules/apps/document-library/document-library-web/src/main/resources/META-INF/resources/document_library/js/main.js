AUI.add(
	'liferay-document-library',
	function(A) {
		var Lang = A.Lang;

		var WIN = A.config.win;

		var HTML5_UPLOAD = WIN && WIN.File && WIN.FormData && WIN.XMLHttpRequest;

		var DocumentLibrary = A.Component.create(
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

				NAME: 'documentlibrary',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var documentLibraryContainer = instance.byId('documentLibraryContainer');

						instance._documentLibraryContainer = documentLibraryContainer;

						instance._eventDataRequest = instance.ns('dataRequest');
						instance._entriesContainer = instance.byId('entriesContainer');

						var namespace = instance.NS;

						var searchContainer = Liferay.SearchContainer.get(namespace + instance.get('searchContainerId'));

						searchContainer.registerAction('move-to-folder', A.bind('_moveToFolder', instance));
						searchContainer.registerAction('move-to-trash', A.bind('_moveToTrash', instance));

						instance._searchContainer = searchContainer;

						var foldersConfig = config.folders;

						instance._folderId = foldersConfig.defaultParentFolderId;

						var eventHandles = [
							Liferay.on(instance.ns('editEntry'), instance._editEntry, instance),
							Liferay.on(instance.ns('openDocument'), instance._openDocument, instance)
						];

						instance._config = config;

						instance._toggleTrashAction();

						var hasPermission = themeDisplay.isSignedIn() && instance.one('#addButtonContainer');

						if (HTML5_UPLOAD && hasPermission && instance._entriesContainer.inDoc()) {
							config.appViewEntryTemplates = instance.byId('appViewEntryTemplates');

							eventHandles.push(A.getDoc().once('dragenter', instance._plugUpload, instance, config));
						}

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._documentLibraryContainer.purge(true);
					},

					getFolderId: function() {
						var instance = this;

						return instance._folderId;
					},

					_editEntry: function(event) {
						var instance = this;

						var action = event.action;

						var url = instance.get('editEntryUrl');

						if (action === 'move' || action === 'moveEntries') {
							url = instance.get('moveEntryUrl');
						}

						instance._processAction(action, url);
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

						instance._processAction('moveToTrash', instance.get('editEntryUrl'));
					},

					_openDocument: function(event) {
						var instance = this;

						Liferay.Util.openDocument(
							event.webDavUrl,
							null,
							function(exception) {
								var errorMessage = Lang.sub(
									Liferay.Language.get('cannot-open-the-requested-document-due-to-the-following-reason'),
									[exception.message]
								);

								var openMSOfficeError = instance.ns('openMSOfficeError');

								if (openMSOfficeError) {
									openMSOfficeError.setHTML(errorMessage);

									openMSOfficeError.removeClass('hide');
								}

							}
						);
					},

					_plugUpload: function(event, config) {
						var instance = this;

						instance.plug(
							Liferay.DocumentLibraryUpload,
							{
								appViewEntryTemplates: config.appViewEntryTemplates,
								appViewMove: instance._appViewMove,
								columnNames: config.columnNames,
								dimensions: config.folders.dimensions,
								displayStyle: config.displayStyle,
								entriesContainer: instance._entriesContainer,
								folderId: instance._folderId,
								listViewContainer: instance.byId('listViewContainer'),
								maxFileSize: config.maxFileSize,
								redirect: config.redirect,
								scopeGroupId: config.scopeGroupId,
								uploadURL: config.uploadURL,
								viewFileEntryURL: config.viewFileEntryURL
							}
						);
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
					},

					_toggleTrashAction: function() {
						var instance = this;

						var trashEnabled = instance._config.trashEnabled;

						instance.one('#deleteAction').toggle(!trashEnabled);

						instance.one('#moveToTrashAction').toggle(trashEnabled);
					}
				}
			}
		);

		Liferay.Portlet.DocumentLibrary = DocumentLibrary;
	},
	'',
	{
		requires: ['document-library-upload', 'liferay-app-view-move', 'liferay-message', 'liferay-portlet-base']
	}
);