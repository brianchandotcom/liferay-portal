AUI.add(
	'liferay-document-library',
	function(A) {
		var Lang = A.Lang;

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var STR_ROW_IDS_FILE_ENTRY_CHECKBOX = 'rowIdsFileEntry';

		var STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX = 'rowIdsDLFileShortcut';

		var STR_ROW_IDS_FOLDER_CHECKBOX = 'rowIdsFolder';

		var WIN = A.config.win;

		var HTML5_UPLOAD = (WIN && WIN.File && WIN.FormData && WIN.XMLHttpRequest);

		var DocumentLibrary = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'documentlibrary',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var documentLibraryContainer = instance.byId('documentLibraryContainer');

						instance._documentLibraryContainer = documentLibraryContainer;

						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventOpenDocument = instance.ns('openDocument');
						instance._entriesContainer = instance.byId('entriesContainer');

						var checkBoxesId = [
							instance.ns(STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX),
							instance.ns(STR_ROW_IDS_FOLDER_CHECKBOX),
							instance.ns(STR_ROW_IDS_FILE_ENTRY_CHECKBOX)
						];

						var displayStyle = config.displayStyle;

						var displayStyleCSSClass = 'entry-display-style';

						var displayStyleToolbar = instance.byId(DISPLAY_STYLE_TOOLBAR);

						var namespace = instance.NS;

						var portletContainerId = instance.ns('documentLibraryContainer');

						var selectConfig = config.select;

						selectConfig.checkBoxesId = checkBoxesId;
						selectConfig.displayStyle = displayStyle;
						selectConfig.displayStyleCSSClass = displayStyleCSSClass;
						selectConfig.displayStyleToolbar = displayStyleToolbar;
						selectConfig.namespace = namespace;
						selectConfig.portletContainerId = portletContainerId;
						selectConfig.selector = 'entry-selector';
						selectConfig.toggleSelector = 'click-selector';

						instance._appViewSelect = new Liferay.AppViewSelect(selectConfig);

						var moveConfig = config.move;

						moveConfig.processEntryIds = {
							checkBoxesIds: checkBoxesId,
							entryIds: [
								instance.ns('fileShortcutIds'),
								instance.ns('folderIds'),
								instance.ns('fileEntryIds')
							]
						};

						moveConfig.displayStyleCSSClass = displayStyleCSSClass;
						moveConfig.draggableCSSClass = '.entry-link';
						moveConfig.moveToTrashActionName = 'move_to_trash';
						moveConfig.namespace = namespace;
						moveConfig.portletContainerId = portletContainerId;
						moveConfig.portletGroup = 'document-library';

						instance._appViewMove = new Liferay.AppViewMove(moveConfig);

						var foldersConfig = config.folders;

						instance._folderId = foldersConfig.defaultParentFolderId;

						var eventHandles = [
							Liferay.on(instance._eventOpenDocument, instance._openDocument, instance)
						];

						instance._config = config;

						instance._toggleTrashAction();

						var hasPermission = (themeDisplay.isSignedIn() && instance.one('#addButtonContainer'));

						if (HTML5_UPLOAD && hasPermission && instance._entriesContainer.inDoc()) {
							config.appViewEntryTemplates = instance.byId('appViewEntryTemplates');

							eventHandles.push(A.getDoc().once('dragenter', instance._plugUpload, instance, config));
						}

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._appViewMove.destroy();
						instance._appViewSelect.destroy();

						instance._documentLibraryContainer.purge(true);
					},

					getFolderId: function() {
						var instance = this;

						return instance._folderId;
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