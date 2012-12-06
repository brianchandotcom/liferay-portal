AUI.add(
	'document-library-upload',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;
		var HistoryManager = Liferay.HistoryManager;
		var UploaderQueue = A.Uploader.Queue;

		var UA = A.UA;

		var LString = Lang.String;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_APP_VIEW_ENTRY = 'app-view-entry-taglib';

		var CSS_COLUMN_CONTENT = '.aui-column-content';

		var CSS_DRAG_HIGHLIGHT = 'drag-highlight';

		var CSS_ENTRY_DISPLAY_STYLE = 'entry-display-style';

		var CSS_ENTRY_ROW_CLASS = CSS_APP_VIEW_ENTRY + ' ' + CSS_ENTRY_DISPLAY_STYLE + ' results-row ';

		var CSS_ICON = 'icon';

		var CSS_PORTLET_SECTION_BODY = 'portlet-section-body';

		var CSS_PORTLET_SECTION_ALT = 'alt portlet-section-alternate';

		var SELECTOR_OVERLAY_MASK = '.aui-overlaymask';

		var CSS_TAGLIB_ICON = 'taglib-icon';

		var CSS_TAGLIB_TEXT = 'taglib-text';

		var CSS_UPLOAD_ERROR = 'upload-error';

		var CSS_UPLOAD_SUCCESS = 'upload-success';

		var PATH_THEME_IMAGES = themeDisplay.getPathThemeImages();

		var REGEX_AUDIO = /\.(aac|auif|bwf|flac|mp3|mp4|m4a|wav|wma)$/i;

		var REGEX_COMPRESSED = /\.(dmg|gz|tar|tgz|zip)$/i;

		var REGEX_IMAGE = /\.(bmp|gif|jpeg|jpg|png|tiff)$/i;

		var REGEX_VIDEO = /\.(avi|flv|mpe|mpg|mpeg|mov|m4v|ogg|wmv)$/i;

		var SELECTOR_DATA_FOLDER = '[data-folder="true"]';

		var SELECTOR_DATA_FOLDER_DATA_TITLE = '[data-folder="true"][data-title]';

		var SELECTOR_DISPLAY_DESCRIPTIVE = '.display-descriptive';

		var SELECTOR_DISPLAY_ICON = '.display-icon';

		var SELECTOR_DOCUMENT_CONTAINER = '.document-container';

		var SELECTOR_DOCUMENT_ENTRIES_PAGINATOR = '.document-entries-paginator';

		var SELECTOR_ENTRIES_EMPTY = '.entries-empty';

		var SELECTOR_ENTRY_LINK = '.entry-link';

		var SELECTOR_ENTRY_THUMBNAIL = '.entry-thumbnail';

		var SELECTOR_ENTRY_TITLE = '.entry-title';

		var SELECTOR_HEADER_ROW = '.lfr-header-row';

		var SELECTOR_HIDDEN = '.aui-helper-hidden';

		var SELECTOR_IMAGE_ICON = 'img.icon';

		var SELECTOR_TEMPLATE_ROW = 'tr.lfr-template';

		var SELECTOR_SEARCH_CONTAINER = '.aui-searchcontainer';

		var SELECTOR_ENTRY_DISPLAY_STYLE = '.' + CSS_ENTRY_DISPLAY_STYLE;

		var SELECTOR_TAGLIB_ICON = '.' + CSS_TAGLIB_ICON;

		var SIZE_DENOMINATOR = 1024;

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_DATA_FOLDER_ID = 'data-folder-id';

		var STR_EXTENSION_PDF = '.pdf';

		var STR_FIRST = 'first';

		var STR_LIST = 'list';

		var STR_NAVIGATION_OVERLAY_BACKGROUND = '#FFF';

		var STR_SIZE_SUFFIX_KB = 'k';

		var STR_SIZE_SUFFIX_MB = 'MB';

		var STR_SIZE_SUFFIX_GB = 'GB';

		var STR_THUMBNAIL_EXTENSION = '.png';

		var STR_THUMBNAIL_DEFAULT = 'default' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_PDF = 'pdf' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_AUDIO = 'music' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_COMPRESSED = 'compressed' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_VIDEO = 'video' + STR_THUMBNAIL_EXTENSION;

		var STR_THUMBNAIL_PATH = PATH_THEME_IMAGES + '/file_system/large/';

		var UPLOADER_TYPE = A.Uploader.TYPE || 'none';

		var UPLOADING = A.Uploader.Queue.UPLOADING;

		var TPL_ENTRY_ROW_TITLE = '<span class="' + CSS_APP_VIEW_ENTRY + ' ' + CSS_ENTRY_DISPLAY_STYLE + '">' +
			'<a class="' + CSS_TAGLIB_ICON + '">' +
				'<img alt="" class="' + CSS_ICON + '" src="' + PATH_THEME_IMAGES + '/file_system/small/page.png" />' +
				'<span class="' + CSS_TAGLIB_TEXT + '">{0}</span>' +
			'</a>' +
		'</span>';

		var TPL_ERROR_FOLDER = new A.Template(
			'<ul class="lfr-component">',
				'<tpl for=".">',
					'<li><b>{name}</b>: {errorMessage}</li>',
				'</tpl>',
			'</ul>'
		);

		var TPL_IMAGE_THUMBNAIL = themeDisplay.getPathContext() + '/documents/' + themeDisplay.getScopeGroupId() + '/{0}/{1}';

		DocumentLibraryUpload = function() {
		};

		DocumentLibraryUpload.NAME = 'documentlibraryupload';

		DocumentLibraryUpload.prototype = {
			destructor: function() {
				var instance = this;

				var uploader = instance._uploader;

				if (uploader) {
					instance._dataset.destroy();
					instance._overlayManager.destroy();

					uploader.destroy();
				}

				instance._detachSubscriptions();
			},

			_attachSubscriptions: function(data) {
				var instance = this;

				var displayStyle = instance._getDisplayStyle();

				var handles = instance._handles;
				var uploader = instance._uploader;

				if (data.isFolderUpload) {
					handles.push(
						uploader.on('uploadcomplete', instance._detectFolderUploadError, instance, data),
						uploader.on('uploadstart', instance._showFolderUploadStarting, instance, data),
						uploader.on('totaluploadprogress', instance._showFolderUploadProgress, instance, data),
						uploader.on('alluploadscomplete', instance._showFolderUploadComplete, instance, data, displayStyle)
					);
				}
				else {
					handles.push(
						uploader.on('uploadprogress', instance._showEntryUploadProgress, instance),
						uploader.on('uploadcomplete', instance._showEntryUploadComplete, instance, displayStyle),
						uploader.after('fileuploadstart', instance._showEntryUploadStarting, instance)
					);
				}
			},

			_combineFileLists: function(fileList, queuedFiles) {
				var instance = this;

				A.each(
					queuedFiles,
					function(item, index, collection) {
						fileList.push(item);
					}
				);
			},

			_createEntries: function(files) {
				var instance = this;

				var displayStyle = instance._getDisplayStyle();

				var overlayManager = instance._overlayManager;

				var addEntry = function(file) {
					var title = file.name;

					var entryNode = instance._spawnEntryNode(displayStyle, file);

					var errorMessage = file.errorMessage;

					if (errorMessage) {
						instance._displayEntryError(entryNode, errorMessage, displayStyle);
					}
					else {
						var overlay = instance._createOverlay(entryNode);
						var progressBar = instance._createProgressBar();

						overlayManager.register(overlay);

						overlay.show();

						file.overlay = overlay;
						file.progressBar = progressBar;
					}

					file.entryNode = entryNode;
				};

				AArray.each(files.validFiles, addEntry);
				AArray.each(files.invalidFiles, addEntry);
			},

			_createEntryRow: function(file) {
				var instance = this;

				var fileSize = instance._formatStorageSize(file.size);

				var searchContainerNode = instance._entriesContainer.one(SELECTOR_SEARCH_CONTAINER);

				var searchContainer = Liferay.SearchContainer.get(searchContainerNode.attr('id'));

				var columnValues = AArray.map(
					instance._columnNames,
					function(item, index, collection) {
						var value = '';

						if (item == 'name') {
							value = Lang.sub(TPL_ENTRY_ROW_TITLE, [file.name]);
						}
						else if (item == 'size') {
							value = fileSize;
						}
						else if (item == 'downloads') {
							value = '0';
						}

						return value;
					}
				);

				var row = searchContainer.addRow(columnValues, A.guid());

				var rowCssClass = CSS_PORTLET_SECTION_ALT;

				if (searchContainer._ids.length % 2) {
					rowCssClass = CSS_PORTLET_SECTION_BODY;
				}

				row.addClass(rowCssClass);

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
								this.set('label', 'complete!');
							},
							valueChange: function(event) {
								this.set('label', event.newVal + '%');
							}
						},
						width: width
					}
				);
			},

			_detachSubscriptions: function() {
				var instance = this;

				var handles = instance._handles;

				AArray.invoke(handles, 'detach');
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

				if (displayStyle === STR_LIST) {
					var imageIcon = node.one(SELECTOR_IMAGE_ICON);

					imageIcon.attr('src', PATH_THEME_IMAGES + '/common/close.png');
				}
				else {
					node.one(SELECTOR_ENTRY_THUMBNAIL).addClass(CSS_UPLOAD_ERROR);
				}

				new A.Tooltip(
					{
						constrain: true,
						bodyContent: message,
						trigger: node
					}
				).render();
			},

			_displayFolderError: function(folderEntry, invalidFiles) {
				var instance = this;

				var bodyContent = TPL_ERROR_FOLDER.parse(invalidFiles);

				var errorNode = folderEntry.errorNode;

				if (!errorNode) {
					errorNode = A.Node.create('<span class="folder-error-icon"></span>');

					folderEntry.one(SELECTOR_ENTRY_TITLE).prepend(errorNode);

					folderEntry.errorNode = errorNode;
				}

				var tooltip = folderEntry.tooltip;

				if (!tooltip) {
					tooltip = new A.Tooltip(
						{
							align: {
								points: ['bc', 'tc']
							},
							constrain: true,
							cssClass: 'portlet-document-library-folder-error',
							bodyContent: bodyContent,
							showArrow: false,
							trigger: errorNode
						}
					).render();

					folderEntry.tooltip = tooltip;
				}

				tooltip.set('bodyContent', bodyContent);

				tooltip.show();
			},

			_formatStorageSize: function(size) {
				var instance = this;

				var suffix = STR_SIZE_SUFFIX_KB;

				size /= SIZE_DENOMINATOR;

				if (size > SIZE_DENOMINATOR) {
					suffix = STR_SIZE_SUFFIX_MB;

					size /= SIZE_DENOMINATOR;
				}

				if (size > SIZE_DENOMINATOR) {
					suffix = STR_SIZE_SUFFIX_GB;

					size /= SIZE_DENOMINATOR;
				}

				var precision = 1;

				if (suffix == STR_SIZE_SUFFIX_KB) {
					precision = 0;
				}

				return LString.round(size, precision) + suffix;
			},

			_getCurrentUploadData: function() {
				var instance = this;

				var dataset = instance._dataset;

				var currentUploadData = dataset.get(STR_FIRST);

				return currentUploadData;
			},

			_getDisplayStyle: function(style) {
				var instance = this;

				var displayStyleNamespace = instance.ns('displayStyle');

				var displayStyle = HistoryManager.get(displayStyleNamespace) || instance._config.displayStyle;

				if (style) {
					displayStyle = (style == displayStyle);
				}

				return displayStyle;
			},

			_getFolderEntry: function(target) {
				var instance = this;

				var overlayBoundingBox = target.ancestor(SELECTOR_OVERLAY_MASK);

				var folderEntry = overlayBoundingBox && overlayBoundingBox.folderEntry;

				if (!folderEntry) {
					if (target.attr('data-folder') == 'true') {
						folderEntry = target;
					}

					if (!folderEntry) {
						folderEntry = target.ancestor(SELECTOR_ENTRY_LINK + SELECTOR_DATA_FOLDER);
					}

					if (!folderEntry) {
						folderEntry = target.ancestor(SELECTOR_DATA_FOLDER_DATA_TITLE);
					}

					if (folderEntry) {
						var displayIcon = target.ancestor(SELECTOR_ENTRY_DISPLAY_STYLE);

						var overlay = folderEntry.overlay;

						if (!overlay) {
							overlay = instance._createOverlay(displayIcon);

							overlay.get(STR_BOUNDING_BOX).folderEntry = folderEntry;

							folderEntry.overlay = overlay;
							folderEntry.progressBar = instance._createProgressBar();
						}

						displayIcon.removeClass(CSS_ACTIVE_AREA);

						overlay.show();
					}
				}

				return folderEntry;
			},

			_getMediaThumbnail: function(fileName) {
				var instance = this;

				var thumbnailName = STR_THUMBNAIL_DEFAULT;

				if (REGEX_IMAGE.test(fileName)) {
					thumbnailName = Lang.sub(TPL_IMAGE_THUMBNAIL, [instance._folderId, fileName]);
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
					var container = instance._entriesContainer;

					var columnContent = container.ancestor(CSS_COLUMN_CONTENT);

					var documentEntriesPaginator = A.one(SELECTOR_DOCUMENT_ENTRIES_PAGINATOR);

					var documentEntriesPaginatorOverlay = instance._createOverlay(documentEntriesPaginator, STR_NAVIGATION_OVERLAY_BACKGROUND);

					var headerRow = columnContent.one(SELECTOR_HEADER_ROW);

					var headerRowOverlay = instance._createOverlay(headerRow, STR_NAVIGATION_OVERLAY_BACKGROUND);

					var navigationPane = instance.byId('listViewContainer');

					var navigationPaneOverlay = instance._createOverlay(navigationPane, STR_NAVIGATION_OVERLAY_BACKGROUND);

					navigationOverlays = [documentEntriesPaginatorOverlay, headerRowOverlay, navigationPaneOverlay];

					var uploader = instance._uploader;

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
						}
					);

					instance._navigationOverlays = navigationOverlays;
				}

				return navigationOverlays;
			},

			_getUploadResponse: function(responseData) {
				var instance = this;

				var error = (Lang.isString(responseData) && responseData.indexOf('49') === 0);

				var message;

				if (!error) {
					try {
						responseData = A.JSON.parse(responseData);
					}
					catch (err) {
					}

					if (Lang.isObject(responseData)) {
						message = instance.ns('fileEntryId=') + responseData.fileEntryId;
					}
				}
				else {
					message = instance._errorMessages[Lang.trim(responseData)];
				}

				return {
					error: error,
					message: message
				};
			},

			_getUploadURL: function(folderId) {
				var instance = this;

				var uploadURL = instance._uploadURL;

				if (!uploadURL) {
					var config = instance._config;

					var redirect = config.redirect;

					uploadURL = unescape(config.uploadURL);

					instance._uploadURL = Liferay.Util.addParams(
						{
							redirect: redirect,
							ts: Lang.now()
						},
						uploadURL
					);
				}

				return Lang.sub(
					uploadURL,
					{
						folderId: folderId
					}
				);
			},

			_initDLUpload: function() {
				var instance = this;

				if (UPLOADER_TYPE == 'html5' && !UA.touch) {
					var config = instance._config;

					var maxFileSize = config.maxFileSize;

					var foldersConfig = config.folders;

					instance._folderId = foldersConfig.defaultParentFolderId;

					instance._initUploader();

					var columnNames = config.columnNames;

					columnNames.push('');
					columnNames.unshift('');

					instance._columnNames = columnNames;

					instance._dataset = new A.DataSet();

					instance._dimensions = foldersConfig.dimensions;

					instance._handles = [];

					var appViewEntryTemplates = instance.byId('appViewEntryTemplates');

					instance._invisibleDescriptiveEntry = appViewEntryTemplates.one(SELECTOR_ENTRY_DISPLAY_STYLE + SELECTOR_DISPLAY_DESCRIPTIVE);
					instance._invisibleIconEntry = appViewEntryTemplates.one(SELECTOR_ENTRY_DISPLAY_STYLE + SELECTOR_DISPLAY_ICON);

					instance._maxFileSize = maxFileSize;

					instance._overlayManager = new A.OverlayManager();

					instance._viewFileEntryUrl = config.viewFileEntryUrl;

					// Upload Error Messages

					instance._invalidFileSizeText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-size-no-larger-than-x');
					instance._zeroByteFileText = Liferay.Language.get('the-file-contains-no-data-and-cannot-be-uploaded.-please-use-the-classic-uploader');

					instance._errorMessages = {
						'490': Liferay.Language.get('please-enter-a-unique-document-name'),
						'491': Liferay.Language.get('document-names-must-end-with-one-of-the-following-extensions') + instance._allowedFileTypes,
						'492': Liferay.Language.get('please-enter-a-file-with-a-valid-file-name'),
						'493': Lang.sub(instance._invalidFileSizeText, [Math.floor(maxFileSize / SIZE_DENOMINATOR)])
					};
				}
			},

			_initUploader: function() {
				var instance = this;

				var folderId = instance._folderId;

				var uploadURL = instance._getUploadURL(folderId);

				var uploader = new A.Uploader(
					{
						appendNewFiles: false,
						fileFieldName: 'file',
						multipleFiles: true,
						simLimit: 2
					}
				);

				uploader.get('boundingBox').hide();

				uploader.render();

				instance._uploader = uploader;

				instance._initUploaderCallbacks();

				return uploader;
			},

			_initUploaderCallbacks: function() {
				var instance = this;

				var uploader = instance._uploader;

				var docElement = A.one(document.documentElement);

				var entriesContainer = instance._entriesContainer;

				A.getWin().on('beforeunload', instance._onBeforeUnload, instance);

				Liferay.on(
					instance.ns('dataRequest'),
					function(event) {
						if (instance._isUploading()) {
							event.halt();
						}
					},
					instance
				);

				Liferay.after(
					'liferay-app-view-folders:dataRequest',
					function(event) {
						var requestParams = event.requestParams;

						instance._folderId = requestParams[instance.ns('folderId')];
					},
					instance
				);

				var removeCssClassTask = A.debounce(
					function() {
						docElement.removeClass('upload-drop-intent');
						docElement.removeClass('upload-drop-active');
					},
					500
				);

				docElement.on(
					'dragover',
					function(event) {
						var originalEvent = event._event;

						var dataTransfer = originalEvent.dataTransfer;

						if (dataTransfer && AArray.indexOf(dataTransfer.types, 'Files') > -1) {
							event.halt();

							docElement.addClass('upload-drop-intent');

							var target = event.target;

							docElement.toggleClass('upload-drop-active', (target.compareTo(entriesContainer) || entriesContainer.contains(target)));

							dataTransfer.dropEffect = 'copy';
						}

						removeCssClassTask();
					}
				);

				docElement.delegate(
					'drop',
					function(event) {
						var originalEvent = event._event;

						var dataTransfer = originalEvent.dataTransfer;

						var dragDropFiles = dataTransfer && AArray(dataTransfer.files);

						if (AArray.indexOf(dataTransfer.types, 'Files') > -1) {
							event.halt();

							instance._getNavigationOverlays();

							event.fileList = AArray.map(
								dragDropFiles,
								function(item, index, collection) {
									return new A.FileHTML5(item);
								}
							);

							uploader.fire('fileselect', event);
						}
					},
					'body, .document-container, .aui-overlaymask, .aui-progressbar, [data-folder="true"]'
				);

				entriesContainer.delegate(
					['dragleave', 'dragover'],
					function(event) {
						var parentElement = event.target.ancestor(SELECTOR_ENTRY_DISPLAY_STYLE);

						parentElement.toggleClass(CSS_ACTIVE_AREA, event.type == 'dragover');
					},
					SELECTOR_DATA_FOLDER
				);

				uploader.after('alluploadscomplete', instance._startNextUpload, instance);
				uploader.after('fileselect', instance._onFileSelect, instance);
			},

			_isUploading: function() {
				var instance = this;

				var queue = instance._uploader.queue;

				return !!(queue && (queue.queuedFiles.length > 0 || queue.numberOfUploads > 0 || !A.Object.isEmpty(queue.currentFiles)) && queue._currentState === UploaderQueue.UPLOADING);
			},

			_onBeforeUnload: function(event) {
				var instance = this;

				if (instance._isUploading()) {
					event.preventDefault();
				}
			},

			_onFileSelect: function(event) {
				var instance = this;

				var folderId;
				var overlay;
				var progressBar;

				var originalEvent = event.details[0];

				var fileList = event.fileList;

				var files = instance._validateFiles(fileList);

				var folderEntry = instance._getFolderEntry(originalEvent.target);

				var uploader = instance._uploader;

				var queue = uploader.queue;

				var invalidFiles = files.invalidFiles;
				var validFiles = files.validFiles;

				var isFolderUpload = (folderEntry !== null);

				if (isFolderUpload) {
					folderId = folderEntry.attr(STR_DATA_FOLDER_ID);

					overlay = folderEntry.overlay;

					progressBar = folderEntry.progressBar;

					var uploadData = instance._getCurrentUploadData();

					if (uploadData && invalidFiles.length > 0) {
						uploadData.invalidFiles = uploadData.invalidFiles.concat(invalidFiles);
					}
				}
				else {
					instance._createEntries(files);

					folderId = instance._folderId;
				}

				var dataset = instance._dataset;

				var pendingUploadData = dataset.item(String(folderId));

				if (pendingUploadData) {
					if (pendingUploadData.folderId == folderId) {
						AArray.each(
							validFiles,
							function(item, index, collection) {
								queue.addToQueueBottom(item);
							}
						);
					}
					else {
						instance._combineFileLists(pendingUploadData.fileList, validFiles);

						dataset.replace(folderId, pendingUploadData);
					}
				}
				else {
					dataset.add(
						folderId,
						{
							fileList: validFiles,
							folderEntry: folderEntry,
							folderId: folderId,
							invalidFiles: invalidFiles,
							isFolderUpload : isFolderUpload,
							overlay: overlay,
							progressBar: progressBar
						}
					);
				}

				if (!instance._isUploading()) {
					var emptyMessage = instance._entriesContainer.one(SELECTOR_ENTRIES_EMPTY);

					if (emptyMessage) {
						emptyMessage.hide();
					}

					instance._startUpload();
				}
			},

			_showEntryUploadComplete: function(event, displayStyle) {
				var instance = this;

				var file = event.file;

				var entryNode = file.entryNode;

				var response = instance._getUploadResponse(event.data);

				if (response) {
					if (response.error) {
						instance._displayEntryError(entryNode, response.message, displayStyle);
					}
					else {
						var displayStyleList = (displayStyle === STR_LIST);

						if (!displayStyleList) {
							instance._updateThumbnail(entryNode, file.name);
						}

						var responseFileEntryId = response.message;

						var fileEntryId = instance.ns('fileEntryId=') + responseFileEntryId;

						instance._updateEntryLink(entryNode, fileEntryId, displayStyleList);
					}
				}

				file.overlay.hide();
			},

			_showEntryUploadProgress: function(event) {
				var instance = this;

				var file = event.file;
				var percentLoaded = event.percentLoaded;

				var progressBar = file.progressBar;

				progressBar.set('value', percentLoaded);
			},

			_showEntryUploadStarting: function(event) {
				var instance = this;

				var file = event.file;

				var overlay = file.overlay;
				var progressBar = file.progressBar;

				var overlayContentBox = overlay.get(STR_CONTENT_BOX);
				var overlayBoundingBox = overlay.get(STR_BOUNDING_BOX);

				var progressBarBoundingBox = progressBar.get(STR_BOUNDING_BOX);

				progressBar.render(overlayBoundingBox);

				progressBarBoundingBox.center(overlayContentBox);
			},

			_showFolderUploadComplete: function(event, uploadData, displayStyle) {
				var instance = this;

				var folderEntry = uploadData.folderEntry;

				var invalidFiles = uploadData.invalidFiles;

				var selector = SELECTOR_ENTRY_THUMBNAIL;

				if (displayStyle === STR_LIST) {
					selector = SELECTOR_TAGLIB_ICON;
				}

				var resultsNode = folderEntry.one(selector);

				var uploadResultClass;

				if (resultsNode) {
					if (invalidFiles.length) {
						uploadResultClass = CSS_UPLOAD_ERROR;

						instance._displayFolderError(folderEntry, invalidFiles);
					}
					else {
						uploadResultClass = CSS_UPLOAD_SUCCESS;
					}

					resultsNode.addClass(uploadResultClass);

					setTimeout(
						function() {
							resultsNode.removeClass(uploadResultClass);
						},
						5000
					);
				}

				folderEntry.overlay.hide();
			},

			_showFolderUploadProgress: function(event, uploadData) {
				var instance = this;

				var percentLoaded = Math.ceil(event.percentLoaded);

				var progressBar = uploadData.progressBar;

				progressBar.set('value', percentLoaded);
			},

			_showFolderUploadStarting: function(event, uploadData) {
				var instance = this;

				var overlay = uploadData.overlay;

				var overlayBoundingBox = overlay.get(STR_BOUNDING_BOX);
				var overlayContentBox = overlay.get(STR_CONTENT_BOX);

				var progressBar = uploadData.progressBar;

				var progressBarBoundingBox = progressBar.get(STR_BOUNDING_BOX);

				progressBar.render(overlayBoundingBox);

				progressBarBoundingBox.center(overlayContentBox);
			},

			_spawnEntryNode: function(displayStyle, file) {
				var instance = this;

				var entryNode;

				var entriesContainer;

				var title = file.name;

				if (displayStyle == STR_LIST) {
					var searchContainer = instance._entriesContainer.one(SELECTOR_SEARCH_CONTAINER);

					entriesContainer = searchContainer.one('tbody');

					entryNode = instance._createEntryRow(file);
				}
				else {
					var invisibleEntry = instance._invisibleDescriptiveEntry;

					if (displayStyle === CSS_ICON) {
						invisibleEntry = instance._invisibleIconEntry;
					}

					entryNode = invisibleEntry.clone();

					var entryLink = entryNode.one(SELECTOR_ENTRY_LINK);

					var entryTitle = entryLink.one(SELECTOR_ENTRY_TITLE);

					entriesContainer = instance._entriesContainer;

					entryLink.attr('title', title);

					entryTitle.setContent(title);
				}

				entryNode.attr(
					{
						'data-title': title,
						id: A.guid()
					}
				);

				entriesContainer.append(entryNode);

				entryNode.show();

				return entryNode;
			},

			_startNextUpload: function(event) {
				var instance = this;

				instance._detachSubscriptions();

				var dataset = instance._dataset;

				dataset.removeAt(0);

				if (dataset.length > 0) {
					instance._startUpload();
				}
			},

			_startUpload: function() {
				var instance = this;

				var uploadData = instance._getCurrentUploadData();

				var fileList = uploadData.fileList;

				var uploader = instance._uploader;

				instance._attachSubscriptions(uploadData);

				if (fileList.length > 0) {
					var uploadURL = instance._getUploadURL(uploadData.folderId);

					uploader.uploadThese(fileList, uploadURL);
				}
				else {
					uploader.fire('alluploadscomplete');
				}
			},

			_updateThumbnail: function(node, fileName) {
				var instance = this;

				var imageNode = node.one('img');

				var thumbnailPath = instance._getMediaThumbnail(fileName);

				imageNode.attr('src', thumbnailPath);
			},

			_updateEntryLink: function(node, id, displayStyleList) {
				var instance = this;

				var selector = SELECTOR_ENTRY_LINK;

				if (displayStyleList) {
					selector = SELECTOR_ENTRY_DISPLAY_STYLE + ' ' + SELECTOR_TAGLIB_ICON;
				}

				var link = node.one(selector);

				if (link) {
					link.attr('href', Liferay.Util.addParams(id, instance._viewFileEntryUrl));
				}
			},

			_validateFiles: function(data) {
				var instance = this;

				var invalidFiles = [];

				var maxFileSize = instance._maxFileSize;

				var validFiles = AArray.filter(
					data,
					function(item, index, collection) {
						var error;
						var file;

						var name = item.get('name');
						var size = item.get('size') || 0;

						if (maxFileSize !== 0 && size > maxFileSize) {
							error = instance._invalidFileSizeText;
						}
						else if (size === 0) {
							error = instance._zeroByteFileText;
						}

						if (error) {
							item.errorMessage = error;

							invalidFiles.push(item);
						}
						else {
							file = item;
						}

						item.name = name;
						item.size = size;

						return file;
					}
				);

				return {
					validFiles: validFiles,
					invalidFiles: invalidFiles
				};
			}
		};

		Liferay.DocumentLibraryUpload = DocumentLibraryUpload;
	},
	'',
	{
		requires: ['aui-data-set', 'aui-overlay-manager', 'aui-overlay-mask', 'aui-progressbar', 'aui-template', 'aui-tooltip', 'liferay-app-view-folders', 'liferay-app-view-move', 'liferay-app-view-paginator', 'liferay-app-view-select', 'liferay-search-container', 'uploader']
	}
);