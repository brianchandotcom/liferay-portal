AUI.add(
	'document-library-upload',
	function(A) {
		var AArray = A.Array;
		var HistoryManager = Liferay.HistoryManager;
		var Lang = A.Lang;
		var LString = Lang.String;
		var UploaderQueue = A.Uploader.Queue;

		var isNumber = Lang.isNumber;
		var isString = Lang.isString;

		var sub = Lang.sub;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_APP_VIEW_ENTRY = 'app-view-entry-taglib';

		var CSS_DISPLAY_DESCRIPTIVE = 'display-descriptive';

		var CSS_DISPLAY_ICON = 'display-icon';

		var CSS_DOCUMENT_ENTRIES_PAGINATION = 'document-entries-pagination';

		var CSS_ENTRIES_EMPTY = 'entries-empty';

		var CSS_ENTRY_DISPLAY_STYLE = 'entry-display-style';

		var CSS_ENTRY_LINK = 'entry-link';

		var CSS_ENTRY_SELECTOR = 'entry-selector';

		var CSS_ENTRY_TITLE_TEXT = 'entry-title-text';

		var CSS_ICON = 'icon';

		var CSS_SEARCHCONTAINER = 'searchcontainer';

		var CSS_TAGLIB_ICON = 'taglib-icon';

		var CSS_TAGLIB_TEXT = 'taglib-text';

		var CSS_UPLOAD_ERROR = 'upload-error';

		var CSS_UPLOAD_SUCCESS = 'upload-success';

		var CSS_UPLOAD_WARNING = 'upload-warning';

		var DOC = A.config.doc;

		var ERROR_RESULTS_MIXED = 1;

		var PATH_THEME_IMAGES = themeDisplay.getPathThemeImages();

		var REGEX_AUDIO = /\.(aac|auif|bwf|flac|mp3|mp4|m4a|wav|wma)$/i;

		var REGEX_COMPRESSED = /\.(dmg|gz|tar|tgz|zip)$/i;

		var REGEX_IMAGE = /\.(bmp|gif|jpeg|jpg|png|tiff)$/i;

		var REGEX_VIDEO = /\.(avi|flv|mpe|mpg|mpeg|mov|m4v|ogg|wmv)$/i;

		var SELECTOR_DATA_FOLDER = '[data-folder="true"]';

		var SELECTOR_DATA_FOLDER_DATA_TITLE = '[data-folder="true"][data-title]';

		var STR_DOT = '.';

		var SELECTOR_DISPLAY_DESCRIPTIVE = STR_DOT + CSS_DISPLAY_DESCRIPTIVE;

		var SELECTOR_DISPLAY_ICON = STR_DOT + CSS_DISPLAY_ICON;

		var SELECTOR_DOCUMENT_ENTRIES_PAGINATION = STR_DOT + CSS_DOCUMENT_ENTRIES_PAGINATION;

		var SELECTOR_ENTRIES_EMPTY = STR_DOT + CSS_ENTRIES_EMPTY;

		var SELECTOR_ENTRY_DISPLAY_STYLE = STR_DOT + CSS_ENTRY_DISPLAY_STYLE;

		var SELECTOR_ENTRY_LINK = STR_DOT + CSS_ENTRY_LINK;

		var SELECTOR_ENTRY_SELECTOR = STR_DOT + CSS_ENTRY_SELECTOR;

		var SELECTOR_ENTRY_TITLE_TEXT = STR_DOT + CSS_ENTRY_TITLE_TEXT;

		var SELECTOR_IMAGE_ICON = 'img.icon';

		var SELECTOR_SEARCH_CONTAINER = STR_DOT + CSS_SEARCHCONTAINER;

		var SELECTOR_TAGLIB_ICON = STR_DOT + CSS_TAGLIB_ICON;

		var STR_BLANK = '';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_EXTENSION_PDF = '.pdf';

		var STR_FIRST = 'first';

		var STR_FOLDER_ID = 'folderId';

		var STR_HOST = 'host';

		var STR_LABEL = 'label';

		var STR_LIST = 'list';

		var STR_NAME = 'name';

		var STR_NAVIGATION_OVERLAY_BACKGROUND = '#FFF';

		var STR_SIZE = 'size';

		var STR_SPACE = ' ';

		var STR_THUMBNAIL_EXTENSION = '.png';

		var STR_THUMBNAIL_DEFAULT = 'default' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_PDF = 'pdf' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_AUDIO = 'music' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_COMPRESSED = 'compressed' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_VIDEO = 'video' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_PATH = PATH_THEME_IMAGES + '/file_system/large/';

		var TPL_ENTRY_ROW_TITLE = '<span class="' + CSS_APP_VIEW_ENTRY + STR_SPACE + CSS_ENTRY_DISPLAY_STYLE + '">' +
				'<a class="' + CSS_TAGLIB_ICON + '">' +
					'<img alt="" class="' + CSS_ICON + '" src="' + PATH_THEME_IMAGES + '/file_system/small/page.png" />' +
					'<span class="' + CSS_TAGLIB_TEXT + '">{0}</span>' +
				'</a>' +
			'</span>';

		var TPL_ERROR_FOLDER = new A.Template(
			'<span class="lfr-status-success-label">{validFilesLength}</span>',

			'<span class="lfr-status-error-label">{invalidFilesLength}</span>',

			'<ul class="list-unstyled">',
				'<tpl for="invalidFiles">',
					'<li><b>{name}</b>: {errorMessage}</li>',
				'</tpl>',
			'</ul>'
		);

		var TPL_HIDDEN_CHECK_BOX = '<input class="hide ' + CSS_ENTRY_SELECTOR + '" name="{0}" type="checkbox" value="">';

		var TPL_IMAGE_THUMBNAIL = themeDisplay.getPathContext() + '/documents/{0}/{1}/{2}';

		var DocumentLibraryUpload = A.Component.create(
			{
				ATTRS: {
					appViewEntryTemplates: {
						validator: A.one,
						value: {}
					},

					columnNames: {
						setter: function(val) {
							var instance = this;

							val.push(STR_BLANK);
							val.unshift(STR_BLANK);

							return val;
						},
						validator: Array.isArray,
						value: []
					},

					dimensions: {
						value: {}
					},

					displayStyle: {
						validator: isString,
						value: STR_BLANK
					},

					entriesContainer: {
						validator: A.one,
						value: {}
					},

					folderId: {
						getter: function() {
							var instance = this;

							return instance.get(STR_HOST).getFolderId();
						},
						readonly: true,
						setter: Lang.toInt,
						validator: isNumber || isString,
						value: null
					},

					listViewContainer: {
						validator: A.one,
						value: {}
					},

					maxFileSize: {
						validator: function(val) {
							return isNumber(val) && val > 0;
						},
						value: 0
					},

					redirect: {
						validator: isString,
						value: STR_BLANK
					},

					scopeGroupId: {
						validator: isNumber,
						value: null
					},

					uploadURL: {
						setter: '_decodeURI',
						validator: isString,
						value: STR_BLANK
					},

					viewFileEntryURL: {
						setter: '_decodeURI',
						validator: isString,
						value: STR_BLANK
					}
				},

				AUGMENTS: [Liferay.StorageFormatter],

				EXTENDS: A.Plugin.Base,

				NAME: 'documentlibraryupload',

				NS: 'upload',

				prototype: {
					initializer: function() {
						var instance = this;

						var appViewEntryTemplates = instance.get('appViewEntryTemplates');

						instance._columnNames = instance.get('columnNames');
						instance._dimensions = instance.get('dimensions');
						instance._displayStyle = instance.get('displayStyle');
						instance._entriesContainer = instance.get('entriesContainer');
						instance._maxFileSize = instance.get('maxFileSize');
						instance._scopeGroupId = instance.get('scopeGroupId');

						instance._handles = [];

						instance._invisibleDescriptiveEntry = appViewEntryTemplates.one(SELECTOR_ENTRY_DISPLAY_STYLE + SELECTOR_DISPLAY_DESCRIPTIVE);
						instance._invisibleIconEntry = appViewEntryTemplates.one(SELECTOR_ENTRY_DISPLAY_STYLE + SELECTOR_DISPLAY_ICON);

						instance._strings = {
							invalidFileSize: Liferay.Language.get('please-enter-a-file-with-a-valid-file-size-no-larger-than-x'),
							zeroByteFile: Liferay.Language.get('the-file-contains-no-data-and-cannot-be-uploaded.-please-use-the-classic-uploader')
						};

						instance._bindDragDropUI();
					},

					destructor: function() {
						var instance = this;

						if (instance._dataSet) {
							instance._dataSet.destroy();
						}

						if (instance._navigationOverlays) {
							AArray.invoke(instance._navigationOverlays, 'destroy');
						}

						if (instance._uploader) {
							instance._uploader.destroy();
						}

						if (instance._tooltipDelegate) {
							instance._tooltipDelegate.destroy();
						}

						instance._detachSubscriptions();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_addFilesToQueueBottom: function(files) {
						var instance = this;

						var queue = instance._getUploader().queue;

						files.forEach(
							function(item, index) {
								queue.addToQueueBottom(item);
							}
						);
					},

					_attachSubscriptions: function(data) {
						var instance = this;

						var handles = instance._handles;

						var displayStyle = instance._getDisplayStyle();
						var uploader = instance._getUploader();

						if (data.folder) {
							handles.push(
								uploader.on('alluploadscomplete', instance._showFolderUploadComplete, instance, data, displayStyle),
								uploader.on('totaluploadprogress', instance._showFolderUploadProgress, instance, data),
								uploader.on('uploadcomplete', instance._detectFolderUploadError, instance, data),
								uploader.on('uploadstart', instance._showFolderUploadStarting, instance, data)
							);
						}
						else {
							handles.push(
								uploader.after('fileuploadstart', instance._showFileUploadStarting, instance),
								uploader.on('uploadcomplete', instance._showFileUploadComplete, instance, displayStyle),
								uploader.on('uploadprogress', instance._showFileUploadProgress, instance)
							);
						}
					},

					_bindDragDropUI: function() {
						var instance = this;

						var docElement = A.one(DOC.documentElement);

						var entriesContainer = instance._entriesContainer;

						var host = instance.get(STR_HOST);

						A.getWin()._node.onbeforeunload = A.bind('_confirmUnload', instance);

						var onDataRequestHandle = Liferay.on(host.ns('dataRequest'), instance._onDataRequest, instance);

						var removeCssClassTask = A.debounce(
							function() {
								docElement.removeClass('upload-drop-intent');
								docElement.removeClass('upload-drop-active');
							},
							500
						);

						var onDragOverHandle = docElement.on(
							'dragover',
							function(event) {
								var dataTransfer = event._event.dataTransfer;

								if (dataTransfer && dataTransfer.types) {
									var dataTransferTypes = dataTransfer.types || [];

									if (AArray.indexOf(dataTransferTypes, 'Files') > -1 && AArray.indexOf(dataTransferTypes, 'text/html') === -1) {
										event.halt();

										dataTransfer.dropEffect = 'copy';

										docElement.addClass('upload-drop-intent');

										var target = event.target;

										docElement.toggleClass('upload-drop-active', target.compareTo(entriesContainer) || entriesContainer.contains(target));

										removeCssClassTask();
									}
								}
							}
						);

						var onDropHandle = docElement.delegate(
							'drop',
							function(event) {
								var dataTransfer = event._event.dataTransfer;

								if (dataTransfer) {
									var dataTransferTypes = dataTransfer.types || [];

									if (AArray.indexOf(dataTransferTypes, 'Files') > -1 && AArray.indexOf(dataTransferTypes, 'text/html') === -1) {
										event.halt();

										var dragDropFiles = AArray(dataTransfer.files);

										event.fileList = dragDropFiles.map(
											function(item, index) {
												return new A.FileHTML5(item);
											}
										);

										var uploader = instance._getUploader();

										uploader.fire('fileselect', event);
									}
								}
							},
							'body, .document-container, .overlaymask, .progressbar, [data-folder="true"]'
						);

						var entriesDragDelegateHandle = entriesContainer.delegate(
							['dragleave', 'dragover'],
							function(event) {
								var dataTransfer = event._event.dataTransfer;

								var dataTransferTypes = dataTransfer.types;

								if (AArray.indexOf(dataTransferTypes, 'Files') > -1 && AArray.indexOf(dataTransferTypes, 'text/html') === -1) {
									var parentElement = event.target.ancestor(SELECTOR_ENTRY_DISPLAY_STYLE);

									parentElement.toggleClass(CSS_ACTIVE_AREA, event.type == 'dragover');
								}
							},
							SELECTOR_DATA_FOLDER
						);

						var entriesClickDelegateHandle = entriesContainer.delegate(
							'click',
							function(event) {
								event.preventDefault();
							},
							STR_DOT + CSS_UPLOAD_ERROR + STR_SPACE + SELECTOR_ENTRY_LINK
						);

						instance._eventHandles = [
							onDataRequestHandle,
							onDragOverHandle,
							onDropHandle,
							entriesDragDelegateHandle,
							entriesClickDelegateHandle
						];
					},

					_combineFileLists: function(fileList, queuedFiles) {
						var instance = this;

						queuedFiles.forEach(
							function(item, index) {
								fileList.push(item);
							}
						);
					},

					_confirmUnload: function() {
						var instance = this;

						if (instance._isUploading()) {
							return Liferay.Language.get('uploads-are-in-progress-confirmation');
						}
					},

					_createEntryNode: function(name, size, displayStyle) {
						var instance = this;

						var entryNode;

						var entriesContainer = instance.get('entriesContainer');

						if (displayStyle == STR_LIST) {
							var searchContainer = entriesContainer.one(SELECTOR_SEARCH_CONTAINER);

							entriesContainer = searchContainer.one('tbody');

							entryNode = instance._createEntryRow(name, size);
						}
						else {
							var invisibleEntry = instance._invisibleDescriptiveEntry;

							var hiddenCheckbox = sub(TPL_HIDDEN_CHECK_BOX, [instance.get(STR_HOST).ns('rowIdsFileEntry')]);

							if (displayStyle == CSS_ICON) {
								invisibleEntry = instance._invisibleIconEntry;
							}

							entryNode = invisibleEntry.clone();

							entryNode.append(hiddenCheckbox);

							var entryLink = entryNode.one(SELECTOR_ENTRY_LINK);

							var entryTitle = entryLink.one(SELECTOR_ENTRY_TITLE_TEXT);

							entryLink.attr('title', name);

							entryTitle.setContent(name);
						}

						entryNode.attr(
							{
								'data-title': name,
								id: A.guid()
							}
						);

						entriesContainer.append(entryNode);

						entryNode.show().scrollIntoView();

						return entryNode;
					},

					_createEntryRow: function(name, size) {
						var instance = this;

						var searchContainerNode = instance._entriesContainer.one(SELECTOR_SEARCH_CONTAINER);

						var searchContainer = Liferay.SearchContainer.get(searchContainerNode.attr('id'));

						var columnValues = instance._columnNames.map(
							function(item, index) {
								var value = STR_BLANK;

								if (item == STR_NAME) {
									value = sub(TPL_ENTRY_ROW_TITLE, [name]);
								}
								else if (item == STR_SIZE) {
									value = instance.formatStorage(size);
								}
								else if (item == 'downloads') {
									value = '0';
								}
								else if (index === 0) {
									value = sub(TPL_HIDDEN_CHECK_BOX, [instance.get(STR_HOST).ns('rowIdsFileEntry')]);
								}

								return value;
							}
						);

						var row = searchContainer.addRow(columnValues, A.guid());

						row.attr('data-draggable', true);

						return row;
					},

					_createOverlay: function(target, background) {
						var instance = this;

						var overlay = new A.OverlayMask(
							{
								background: background || null,
								target: target
							}
						).render();

						overlay.get(STR_BOUNDING_BOX).addClass('portlet-document-library-upload-mask');

						return overlay;
					},

					_createProgressBar: function() {
						var instance = this;

						var dimensions = instance._dimensions;

						var height = dimensions.height / 5;

						var width = dimensions.width / 0.64;

						return new A.ProgressBar(
							{
								height: height,
								on: {
									complete: function(event) {
										this.set(STR_LABEL, 'complete!');
									},
									valueChange: function(event) {
										this.set(STR_LABEL, event.newVal + '%');
									}
								},
								width: width
							}
						);
					},

					_createUploadStatus: function(target, file) {
						var instance = this;

						var overlay = instance._createOverlay(target);
						var progressBar = instance._createProgressBar();

						overlay.show();

						if (file) {
							file.overlay = overlay;
							file.progressBar = progressBar;
							file.target = target;
						}
						else {
							target.overlay = overlay;
							target.progressBar = progressBar;
						}
					},

					_decodeURI: function(val) {
						return decodeURI(val);
					},

					_destroyEntry: function() {
						var instance = this;

						var currentUploadData = instance._getCurrentUploadData();

						var fileList = currentUploadData.fileList;

						if (!currentUploadData.folder) {
							fileList.forEach(
								function(item, index) {
									item.overlay.destroy();

									item.progressBar.destroy();
								}
							);
						}

						AArray.invoke(fileList, 'destroy');
					},

					_detachSubscriptions: function() {
						var instance = this;

						var handles = instance._handles;

						AArray.invoke(handles, 'detach');

						handles.length = 0;
					},

					_detectFolderUploadError: function(event, data) {
						var instance = this;

						var response = instance._getUploadResponse(event.data);

						if (response.error) {
							var file = event.file;

							file.errorMessage = response.message;

							data.invalidFiles.push(file);
						}
					},

					_displayEntryError: function(node, message, displayStyle) {
						var instance = this;

						if (displayStyle == STR_LIST) {
							var imageIcon = node.one(SELECTOR_IMAGE_ICON);

							imageIcon.attr('src', PATH_THEME_IMAGES + '/common/close.png');
						}
						else {
							node.addClass(CSS_UPLOAD_ERROR);
						}

						instance._displayError(node, message);
					},

					_displayError: function(node, message) {
						var instance = this;

						node.attr('data-message', message);

						var tooltipDelegate = instance._tooltipDelegate;

						if (!tooltipDelegate) {
							tooltipDelegate = new A.TooltipDelegate(
								{
									formatter: function(val) {
										return instance._formatTooltip(val, this);
									},
									trigger: '.app-view-entry.upload-error',
									visible: false
								}
							);

							instance._tooltipDelegate = tooltipDelegate;
						}

						return node;
					},

					_displayResult: function(node, displayStyle, error) {
						var instance = this;

						var resultsNode = node;

						if (resultsNode) {
							var uploadResultClass = CSS_UPLOAD_SUCCESS;

							if (error) {
								resultsNode.removeClass(CSS_UPLOAD_ERROR).removeClass(CSS_UPLOAD_WARNING);

								if (error === true) {
									uploadResultClass = CSS_UPLOAD_ERROR;
								}
								else if (error == ERROR_RESULTS_MIXED) {
									uploadResultClass = CSS_UPLOAD_WARNING;
								}
							}

							resultsNode.addClass(uploadResultClass);
							resultsNode.addClass(CSS_ENTRY_DISPLAY_STYLE);
						}
					},

					_formatTooltip: function(val, tooltip) {
						var instance = this;

						tooltip.set('zIndex', 2);

						var node = tooltip.get('trigger');

						return node.attr('data-message');
					},

					_getCurrentUploadData: function() {
						var instance = this;

						var dataSet = instance._getDataSet();

						return dataSet.get(STR_FIRST);
					},

					_getDataSet: function() {
						var instance = this;

						var dataSet = instance._dataSet;

						if (!dataSet) {
							dataSet = new A.DataSet();

							instance._dataSet = dataSet;
						}

						return dataSet;
					},

					_getDisplayStyle: function(style) {
						var instance = this;

						var displayStyleNamespace = instance.get(STR_HOST).ns('displayStyle');

						var displayStyle = HistoryManager.get(displayStyleNamespace) || instance._displayStyle;

						if (style) {
							displayStyle = style == displayStyle;
						}

						return displayStyle;
					},

					_getEmptyMessage: function() {
						var instance = this;

						var emptyMessage = instance._emptyMessage;

						if (!emptyMessage) {
							emptyMessage = instance._entriesContainer.one(SELECTOR_ENTRIES_EMPTY);

							instance._emptyMessage = emptyMessage;
						}

						return emptyMessage;
					},

					_getFolderEntryNode: function(target) {
						var instance = this;

						var folderEntry;

						var overlayContentBox = target.hasClass('overlay-content');

						if (overlayContentBox) {
							var overlay = A.Widget.getByNode(target);

							folderEntry = overlay._originalConfig.target;
						}
						else {
							if (target.attr('data-folder') === 'true') {
								folderEntry = target;
							}

							if (!folderEntry) {
								folderEntry = target.ancestor(SELECTOR_ENTRY_LINK + SELECTOR_DATA_FOLDER);
							}

							if (!folderEntry) {
								folderEntry = target.ancestor(SELECTOR_DATA_FOLDER_DATA_TITLE);
							}

							folderEntry = folderEntry && folderEntry.ancestor();
						}

						return folderEntry;
					},

					_getMediaThumbnail: function(fileName) {
						var instance = this;

						var thumbnailName = STR_THUMBNAIL_DEFAULT;

						if (REGEX_IMAGE.test(fileName)) {
							thumbnailName = sub(TPL_IMAGE_THUMBNAIL, [instance._scopeGroupId, instance.get(STR_FOLDER_ID), fileName]);
						}
						else {
							if (LString.endsWith(fileName.toLowerCase(), STR_EXTENSION_PDF)) {
								thumbnailName = STR_THUMBNAIL_PDF;
							}
							else if (REGEX_AUDIO.test(fileName)) {
								thumbnailName = STR_THUMBNAIL_AUDIO;
							}
							else if (REGEX_VIDEO.test(fileName)) {
								thumbnailName = STR_THUMBNAIL_VIDEO;
							}
							else if (REGEX_COMPRESSED.test(fileName)) {
								thumbnailName = STR_THUMBNAIL_COMPRESSED;
							}

							thumbnailName = STR_THUMBNAIL_PATH + thumbnailName;
						}

						return thumbnailName;
					},

					_getNavigationOverlays: function() {
						var instance = this;

						var navigationOverlays = instance._navigationOverlays;

						if (!navigationOverlays) {
							navigationOverlays = [];

							var createNavigationOverlay = function(target) {
								if (target) {
									var overlay = instance._createOverlay(target, STR_NAVIGATION_OVERLAY_BACKGROUND);

									navigationOverlays.push(overlay);
								}
							};

							var entriesContainer = instance.get('entriesContainer');

							createNavigationOverlay(entriesContainer.one(SELECTOR_DOCUMENT_ENTRIES_PAGINATION));
							createNavigationOverlay(entriesContainer.one('.app-view-taglib.lfr-header-row'));
							createNavigationOverlay(instance.get('listViewContainer'));

							instance._navigationOverlays = navigationOverlays;
						}

						return navigationOverlays;
					},

					_getTargetFolderId: function(target) {
						var instance = this;

						var folderEntry = instance._getFolderEntryNode(target);

						var dataFolder = folderEntry && folderEntry.one('[data-folder-id]');

						return dataFolder && Lang.toInt(dataFolder.attr('data-folder-id')) || instance.get(STR_FOLDER_ID);
					},

					_getUploader: function() {
						var instance = this;

						var uploader = instance._uploader;

						if (!uploader) {
							uploader = new A.Uploader(
								{
									appendNewFiles: false,
									fileFieldName: 'file',
									multipleFiles: true,
									simLimit: 1
								}
							);

							var navigationOverlays = instance._getNavigationOverlays();

							uploader.on(
								'uploadstart',
								function(event) {
									AArray.invoke(navigationOverlays, 'show');
								}
							);

							uploader.after(
								'alluploadscomplete',
								function(event) {
									AArray.invoke(navigationOverlays, 'hide');

									var emptyMessage = instance._getEmptyMessage();

									if (emptyMessage && !emptyMessage.hasClass('hide')) {
										emptyMessage.hide(true);
									}
								}
							);

							uploader.get(STR_BOUNDING_BOX).hide();

							uploader.render();

							uploader.after('alluploadscomplete', instance._startNextUpload, instance);
							uploader.after('fileselect', instance._onFileSelect, instance);

							instance._uploader = uploader;
						}

						return uploader;
					},

					_getUploadResponse: function(responseData) {
						var instance = this;

						var error;
						var message;

						try {
							responseData = JSON.parse(responseData);
						}
						catch (e) {
						}

						if (Lang.isObject(responseData)) {
							error = responseData.status && (responseData.status >= 490 && responseData.status < 500);

							if (error) {
								message = responseData.message;
							}
							else {
								message = instance.get(STR_HOST).ns('fileEntryId=') + responseData.fileEntryId;
							}
						}

						return {
							error: error,
							message: message
						};
					},

					_getUploadStatus: function(key) {
						var instance = this;

						var dataSet = instance._getDataSet();

						return dataSet.item(String(key));
					},

					_getUploadURL: function(folderId) {
						var instance = this;

						var uploadURL = instance._uploadURL;

						if (!uploadURL) {
							var redirect = instance.get('redirect');

							uploadURL = instance.get('uploadURL');

							instance._uploadURL = Liferay.Util.addParams(
								{
									redirect: redirect,
									ts: Date.now()
								},
								uploadURL
							);
						}

						return sub(
							uploadURL,
							{
								folderId: folderId
							}
						);
					},

					_isUploading: function() {
						var instance = this;

						var uploader = instance._uploader;

						var queue = uploader && uploader.queue;

						return !!queue && (queue.queuedFiles.length > 0 || queue.numberOfUploads > 0 || !A.Object.isEmpty(queue.currentFiles)) && queue._currentState == UploaderQueue.UPLOADING;
					},

					_onDataRequest: function(event) {
						var instance = this;

						if (instance._isUploading()) {
							event.halt();
						}
					},

					_onFileSelect: function(event) {
						var instance = this;

						var target = event.details[0].target;

						var filesPartition = instance._validateFiles(event.fileList);

						instance._updateStatusUI(target, filesPartition);

						instance._queueSelectedFiles(target, filesPartition);
					},

					_positionProgressBar: function(overlay, progressBar) {
						var instance = this;

						var progressBarBoundingBox = progressBar.get(STR_BOUNDING_BOX);

						progressBar.render(overlay.get(STR_BOUNDING_BOX));

						progressBarBoundingBox.center(overlay.get(STR_CONTENT_BOX));
					},

					_queueSelectedFiles: function(target, filesPartition) {
						var instance = this;

						var key = instance._getTargetFolderId(target);

						var keyData = instance._getUploadStatus(key);

						var validFiles = filesPartition.matches;

						if (keyData) {
							instance._updateDataSetEntry(key, keyData, validFiles);
						}
						else {
							var dataSet = instance._getDataSet();

							var folderNode = null;

							var folder = key !== instance.get(STR_FOLDER_ID);

							if (folder) {
								folderNode = instance._getFolderEntryNode(target);
							}

							dataSet.add(
								key,
								{
									fileList: validFiles,
									folder: folder,
									folderId: key,
									invalidFiles: filesPartition.rejects,
									target: folderNode
								}
							);
						}

						if (!instance._isUploading()) {
							instance._startUpload();
						}
					},

					_showFileUploadComplete: function(event, displayStyle) {
						var instance = this;

						var file = event.file;

						var fileNode = file.target;

						var response = instance._getUploadResponse(event.data);

						if (response) {
							var hasErrors = !!response.error;

							if (hasErrors) {
								instance._displayEntryError(fileNode, response.message, displayStyle);
							}
							else {
								var displayStyleList = displayStyle == STR_LIST;

								var fileEntryId = JSON.parse(event.data).fileEntryId;

								if (!displayStyleList) {
									instance._updateThumbnail(fileNode, file.name);
								}

								instance._updateFileLink(fileNode, response.message, displayStyleList);

								instance._updateFileHiddenInput(fileNode, fileEntryId);
							}

							instance._displayResult(fileNode, displayStyle, hasErrors);
						}

						file.overlay.hide();
					},

					_showFileUploadProgress: function(event) {
						var instance = this;

						instance._updateProgress(event.file.progressBar, event.percentLoaded);
					},

					_showFileUploadStarting: function(event) {
						var instance = this;

						var file = event.file;

						instance._positionProgressBar(file.overlay, file.progressBar);
					},

					_showFolderUploadComplete: function(event, uploadData, displayStyle) {
						var instance = this;

						var folderEntry = uploadData.target;
						var invalidFiles = uploadData.invalidFiles;
						var totalFilesLength = uploadData.fileList.length;

						var invalidFilesLength = invalidFiles.length;

						var hasErrors = invalidFilesLength !== 0;

						if (hasErrors && invalidFilesLength !== totalFilesLength) {
							hasErrors = ERROR_RESULTS_MIXED;
						}

						instance._displayResult(folderEntry, displayStyle, hasErrors);

						if (hasErrors) {
							instance._displayError(
								folderEntry,
								TPL_ERROR_FOLDER.parse(
									{
										invalidFiles: invalidFiles,
										invalidFilesLength: invalidFilesLength,
										validFilesLength: totalFilesLength - invalidFilesLength
									}
								)
							);
						}

						folderEntry.overlay.hide();
					},

					_showFolderUploadProgress: function(event, uploadData) {
						var instance = this;

						instance._updateProgress(uploadData.target.progressBar, event.percentLoaded);
					},

					_showFolderUploadStarting: function(event, uploadData) {
						var instance = this;

						var target = uploadData.target;

						instance._positionProgressBar(target.overlay, target.progressBar);
					},

					_startNextUpload: function(event) {
						var instance = this;

						instance._destroyEntry();

						var dataSet = instance._getDataSet();

						dataSet.removeAt(0);

						if (dataSet.length) {
							instance._startUpload();
						}
					},

					_startUpload: function() {
						var instance = this;

						var uploadData = instance._getCurrentUploadData();

						var fileList = uploadData.fileList;

						var uploader = instance._getUploader();

						if (fileList.length) {
							var uploadURL = instance._getUploadURL(uploadData.folderId);

							instance._attachSubscriptions(uploadData);

							uploader.uploadThese(fileList, uploadURL);
						}
						else {
							uploader.fire('alluploadscomplete');
						}
					},

					_updateDataSetEntry: function(key, data, unmergedData) {
						var instance = this;

						var currentUploadData = instance._getCurrentUploadData();

						if (currentUploadData.folderId === key) {
							instance._addFilesToQueueBottom(unmergedData);
						}
						else {
							instance._combineFileLists(data.fileList, unmergedData);

							var dataSet = instance._getDataSet();

							dataSet.replace(key, data);
						}
					},

					_updateFileHiddenInput: function(node, id) {
						var instance = this;

						var inputNode = node.one('input');

						if (inputNode) {
							inputNode.val(id);
						}
					},

					_updateFileLink: function(node, id, displayStyleList) {
						var instance = this;

						var selector = SELECTOR_ENTRY_LINK;

						if (displayStyleList) {
							selector = SELECTOR_ENTRY_DISPLAY_STYLE + STR_SPACE + SELECTOR_TAGLIB_ICON;
						}

						var link = node.one(selector);

						if (link) {
							link.attr('href', Liferay.Util.addParams(id, instance.get('viewFileEntryURL')));
						}
					},

					_updateProgress: function(progressBar, value) {
						var instance = this;

						progressBar.set('value', Math.ceil(value));
					},

					_updateStatusUI: function(target, filesPartition) {
						var instance = this;

						var folderId = instance._getTargetFolderId(target);

						var folder = folderId !== instance.get(STR_FOLDER_ID);

						if (folder) {
							var folderEntryNode = instance._getFolderEntryNode(target);

							var folderEntryNodeOverlay = folderEntryNode.overlay;

							if (folderEntryNodeOverlay) {
								folderEntryNodeOverlay.show();

								instance._updateProgress(folderEntryNode.progressBar, 0);
							}
							else {
								instance._createUploadStatus(folderEntryNode);
							}

							folderEntryNode.removeClass(CSS_ACTIVE_AREA);
						}
						else {
							var displayStyle = instance._getDisplayStyle();

							filesPartition.matches.map(
								function(file) {
									var entryNode = instance._createEntryNode(file.name, file.size, displayStyle);

									instance._createUploadStatus(entryNode, file);
								}
							);

							filesPartition.rejects.map(
								function(file) {
									var entryNode = instance._createEntryNode(file.name, file.size, displayStyle);

									instance._displayEntryError(entryNode, file.errorMessage, instance._getDisplayStyle());
								}
							);
						}
					},

					_updateThumbnail: function(node, fileName) {
						var instance = this;

						var imageNode = node.one('img');

						var thumbnailPath = instance._getMediaThumbnail(fileName);

						imageNode.attr('src', thumbnailPath);
					},

					_validateFiles: function(data) {
						var instance = this;

						var maxFileSize = instance._maxFileSize;

						return AArray.partition(
							data,
							function(item, index) {
								var errorMessage;

								var size = item.get(STR_SIZE) || 0;

								var strings = instance._strings;

								if (maxFileSize !== 0 && size > maxFileSize) {
									errorMessage = sub(strings.invalidFileSize, [instance.formatStorage(instance._maxFileSize)]);
								}
								else if (size === 0) {
									errorMessage = strings.zeroByteFile;
								}

								item.errorMessage = errorMessage;
								item.size = size;
								item.name = item.get(STR_NAME);

								return !errorMessage;
							}
						);
					}
				}
			}
		);

		Liferay.DocumentLibraryUpload = DocumentLibraryUpload;
	},
	'',
	{
		requires: ['aui-component', 'aui-data-set-deprecated', 'aui-overlay-manager-deprecated', 'aui-overlay-mask-deprecated', 'aui-parse-content', 'aui-progressbar', 'aui-template-deprecated', 'aui-tooltip', 'liferay-search-container', 'liferay-storage-formatter', 'querystring-parse-simple', 'uploader']
	}
);