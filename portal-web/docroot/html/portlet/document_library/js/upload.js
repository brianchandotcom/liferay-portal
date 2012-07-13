AUI.add(
	'document-library-upload',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;
		var HistoryManager = Liferay.HistoryManager;
		var UA = A.UA;

		var BOUNDING_BOX = 'boundingBox';

		var CONTENT_BOX = 'contentBox';

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_APP_VIEW_ENTRY = 'app-view-entry-taglib';

		var CSS_COLUMN_CONTENT = '.aui-column-content';

		var CSS_DATA_FOLDER_DATA_TITLE = '[data-folder="true][data-title]';

		var CSS_DATA_FOLDER = '[data-folder="true]';

		var CSS_DISPLAY_DESCRIPTIVE = '.display-descriptive';

		var CSS_DISPLAY_ICON = '.display-icon';

		var CSS_DOCUMENT_CONTAINER = '.document-container';

		var CSS_DOCUMENT_ENTRIES_PAGINATOR = '.document-entries-paginator';

		var CSS_DOT_ENTRY_DISPLAY_STYLE = '.entry-display-style';

		var CSS_DOT_TAGLIB_ICON = '.taglib-icon';

		var CSS_DRAG_HIGHLIGHT = 'drag-highlight';

		var CSS_ENTRIES_EMPTY = '.entries-empty';

		var CSS_ENTRY_DISPLAY_STYLE = 'entry-display-style';

		var CSS_ENTRY_LINK = '.entry-link';

		var CSS_ENTRY_THUMBNAIL = '.entry-thumbnail';

		var CSS_ENTRY_TITLE = '.entry-title';

		var CSS_ENTRY_ROW_CLASS = CSS_APP_VIEW_ENTRY + ' ' + CSS_ENTRY_DISPLAY_STYLE + ' results-row ';

		var CSS_HEADER_ROW = '.lfr-header-row';

		var CSS_HIDDEN = '.aui-helper-hidden';

		var CSS_ICON = 'icon';

		var CSS_IMAGE_ICON = 'img.icon';

		var CSS_PORTLET_SECTION_HOVER = 'portlet-section-hover';

		var CSS_PORTLET_SECTION_HOVER_ALT = 'alt portlet-section-alternate-hover';

		var CSS_OVERLAY_MASK = '.aui-overlaymask';

		var CSS_TAGLIB_ICON = 'taglib-icon';

		var CSS_TAGLIB_TEXT = 'taglib-text';

		var CSS_TEMPLATE_ROW = 'tr.lfr-template';

		var CSS_SEARCH_CONTAINER = '.aui-searchcontainer';

		var CSS_UPLOAD_ERROR = 'upload-error';

		var CSS_UPLOAD_SUCCESS = 'upload-success';

		var DATA_FOLDER_ID = 'data-folder-id';

		var FIRST = 'first';

		var LIST = 'list';

		var NAVIGATION_OVERLAY_BACKGROUND = '#FFF';

		var TPL_ENTRY_ROW = new A.Template(
			'<tpl>',
				'<tpl if="column == \'title\'">',
					'<span class="' + CSS_APP_VIEW_ENTRY + ' ' + CSS_ENTRY_DISPLAY_STYLE + '">',
						'<a class="' + CSS_TAGLIB_ICON + '">',
							'<img alt="" class="' + CSS_ICON + '" src="/html/themes/classic/images/file_system/small/page.png" />',
							'<span class="' + CSS_TAGLIB_TEXT + '" id="' + A.guid() + '">{name}</span>',
						'</a>',
					'</span>',
				'</tpl>',
				'<tpl if="column == \'size\'">',
					'{size}MB',
				'</tpl>',
				'<tpl if="column == \'downloads\'">',
					'0',
				'</tpl>',
			'</tpl>'
		);

		var UPLOADER_TYPE = A.Uploader.TYPE || 'none';

		var UPLOADING = A.Uploader.Queue.UPLOADING;

		DocumentLibraryUpload = function() {};

		DocumentLibraryUpload.NAME = 'documentlibraryupload';

		DocumentLibraryUpload.prototype = {
			destructor: function() {
				var instance = this;

				if (instance._uploader) {
					instance._dataset.destroy();

					instance._overlayManager.destroy();

					instance._uploader.destroy();
				}

				AArray.invoke(instance._subscriptions, 'detach');
			},

			_attachSubscriptions: function(data) {
				var instance = this;

				var displayStyle = instance._getDisplayStyle();

				var subscriptions = instance._subscriptions;

				var uploader = instance._uploader;

				if (data.isFolderUpload) {
					var detectFolderUploadError = uploader.on('uploadcomplete', instance._detectFolderUploadError, instance, data);
					var showFolderUploadStarting = uploader.on('uploadstart', instance._showFolderUploadStarting, instance, data);
					var showFolderUploadProgress = uploader.on('totaluploadprogress', instance._showFolderUploadProgress, instance, data);
					var showFolderUploadComplete = uploader.on('alluploadscomplete', instance._showFolderUploadComplete, instance, data, displayStyle);

					subscriptions.push(detectFolderUploadError, showFolderUploadStarting, showFolderUploadProgress, showFolderUploadComplete);
				}
				else {
					var showEntryUploadStarting = uploader.after('fileuploadstart', instance._showEntryUploadStarting, instance);
					var showEntryUploadProgress = uploader.on('uploadprogress', instance._showEntryUploadProgress, instance);
					var showEntryUploadComplete = uploader.on('uploadcomplete', instance._showEntryUploadComplete, instance, displayStyle);

					subscriptions.push(showEntryUploadStarting, showEntryUploadProgress, showEntryUploadComplete);
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

					if (!!errorMessage) {
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

				AArray.map(
					files.validFiles,
					function(item, index, collection) {
						addEntry(item);
					}
				);

				AArray.map(
					files.invalidFiles,
					function(item, index, collection) {
						addEntry(item);
					}
				);
			},

			_createEntryRow: function(file) {
				var instance = this;

				var entriesCount = instance._entriesCount += 1;

				var fileSizeInMB = Math.round(file.size / Math.pow(1024, 2) * 10 ) / 10;

				var hoverCSS = (entriesCount % 2) ? CSS_PORTLET_SECTION_HOVER_ALT : CSS_PORTLET_SECTION_HOVER;

				var searchContainer = A.one(CSS_SEARCH_CONTAINER);

				var templateRow = searchContainer.one(CSS_TEMPLATE_ROW);

				var row = templateRow.clone();

				var column = row.one('td');

				var columnNames = instance._columnNames;

				A.each(
					columnNames,
					function(item, index, collection) {
						var columnName = item;

						if (columnName === 'name') {
							columnName = 'title';
						}

						column = column.next();

						column.attr(
							{
								colspan: 1,
								headers: instance.ns('ocerSearchContainer_col-' + columnName),
								id: instance.ns('ocerSearchContainer_col-' + columnName + '_row-' + entriesCount)
							}
						);

						column.addClass('align-left col-' + columnName + ' valign-middle');

						TPL_ENTRY_ROW.render(
							{
								column: columnName,
								name: file.name,
								size: fileSizeInMB
							},
							column
						);
					}
				);

				row.attr(
					{
						'className': CSS_ENTRY_ROW_CLASS + hoverCSS,
						'data-draggable': true
					}
				);

				var tableBody = searchContainer.one('tbody');

				tableBody.append(row);

				return row;
			},

			_createNavigationOverlays: function() {
				var instance = this;

				var container = instance._entriesContainer;

				var columnContent = container.ancestor(CSS_COLUMN_CONTENT);

				var documentEntriesPaginator = A.one(CSS_DOCUMENT_ENTRIES_PAGINATOR);

				var documentEntriesPaginatorOverlay = instance._createOverlay(documentEntriesPaginator, NAVIGATION_OVERLAY_BACKGROUND);

				var headerRow = columnContent.one(CSS_HEADER_ROW);

				var headerRowOverlay = instance._createOverlay(headerRow, NAVIGATION_OVERLAY_BACKGROUND);

				var navigationPane = instance.byId('listViewContainer');

				var navigationPaneOverlay = instance._createOverlay(navigationPane, NAVIGATION_OVERLAY_BACKGROUND);

				var navigationOverlays = [documentEntriesPaginatorOverlay, headerRowOverlay, navigationPaneOverlay];

				var uploader = instance._uploader;

				uploader.before(
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

				var subscriptions = instance._subscriptions;

				AArray.invoke(subscriptions, 'detach');
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

				var displayStyleIsList = (displayStyle === LIST);

				if (displayStyleIsList) {
					var imageIcon = node.one(CSS_IMAGE_ICON);

					imageIcon.attr('src', '/html/themes/classic/images/common/close.png');
				}
				else {
					node.one(CSS_ENTRY_THUMBNAIL).addClass(CSS_UPLOAD_ERROR);
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

				var tooltip = folderEntry.tooltip;

				var tooltipBodyContent = '';

				AArray.map(
					invalidFiles,
					function(item, index, collection) {
						tooltipBodyContent += '<p>' + item.name + ': ' + item.errorMessage + '</p>';
					}
				);

				if (tooltip) {
					tooltip.set('bodyContent', tooltipBodyContent);
				}
				else {
					tooltip = new A.Tooltip(
						{
							constrain: true,
							bodyContent: tooltipBodyContent,
							trigger: folderEntry
						}
					);

					folderEntry.tooltip = tooltip;
				}

				tooltip.render();
			},

			_getCurrentUploadData: function() {
				var instance = this;

				var dataset = instance._dataset;

				var currentUploadData = dataset.get(FIRST);

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

			_getDragDropFiles: function(files) {
				var instance = this;

				var fileList = [];

				A.each(
					files,
					function (item, index, collection) {
						var newFile = new A.FileHTML5(item);

						fileList.push(newFile);
					}
				);

				return fileList;
			},

			_getFolderEntry: function(target) {
				var instance = this;

				var getOverlayFolderEntry = function() {

					var overlayBoundingBox = target.ancestor(CSS_OVERLAY_MASK);

					var overlayFolderEntry = overlayBoundingBox ? overlayBoundingBox.folderEntry : null;

					return overlayFolderEntry;
				};

				var getTargetFolderEntry = function() {

					var entryLink = target.ancestor('.entry-link' + CSS_DATA_FOLDER);

					var folderEntry;

					var resultsRow = target.ancestor(CSS_DATA_FOLDER_DATA_TITLE);

					var targetIsDataFolder = (target.attr('data-folder') === "true");

					if (entryLink) {
						folderEntry = entryLink;
					}
					else if (resultsRow) {
						folderEntry = resultsRow;
					}
					else if (targetIsDataFolder) {
						folderEntry = target;
					}
					else {
						folderEntry = null;
					}

					if (folderEntry) {
						var displayIcon = target.ancestor(CSS_DOT_ENTRY_DISPLAY_STYLE);

						var overlay = folderEntry.overlay;

						if (!overlay) {
							overlay = instance._createOverlay(displayIcon);

							overlay.get(BOUNDING_BOX).folderEntry = folderEntry;

							folderEntry.overlay = overlay;
							folderEntry.progressBar = instance._createProgressBar();
						}

						displayIcon.removeClass(CSS_ACTIVE_AREA);

						overlay.show();
					}

					return folderEntry;
				};

				return getOverlayFolderEntry() || getTargetFolderEntry();
			},

			_getMediaThumbnail: function(fileName) {
				var instance = this;

				var extension = fileName.substring(fileName.length, fileName.lastIndexOf('.'));

				var extensionPath;

				var lowerCaseExtension = extension.toLowerCase();

				var imagePath;

				if ((/\.(bmp|gif|jpeg|jpg|png|tiff)$/i).test(lowerCaseExtension)) {
					imagePath = '/documents/' + themeDisplay.getScopeGroupId() + '/' + instance._folderId + '/' + fileName;
				}
				else if (lowerCaseExtension === '.pdf') {
					extensionPath = 'pdf.png';
				}
				else if ((/\.(aac|auif|bwf|flac|mp3|mp4|m4a|wav|wma)$/i).test(lowerCaseExtension)) {
					extensionPath = 'music.png';
				}
				else if ((/\.(avi|flv|mpe|mpg|mpeg|mov|m4v|ogg|wmv)$/i).test(lowerCaseExtension)) {
					extensionPath = 'video.png';
				}
				else if ((/\.(dmg|gz|tar|tgz|zip)$/i).test(lowerCaseExtension)) {
					extensionPath = 'compressed.png';
				}
				else {
					extensionPath = 'default.png';
				}

				return imagePath || '/html/themes/classic/images/file_system/large/' + extensionPath;
			},

			_getUploadResponse: function(response) {
				var instance = this;

				var parsedResponseData;

				try {
					parsedResponseData = A.JSON.parse(response);
				}
				catch(err) {
				}

				if (!!parsedResponseData) {
					var errorCode = String(parsedResponseData);

					var errorDetected = (errorCode.indexOf('49') === 0);

					var errorMessage;

					if (errorDetected) {
						errorMessage = instance._errorMessages[parsedResponseData];
					}
					else {
						errorMessage = instance.ns('fileEntryId=') + parsedResponseData.fileEntryId;
					}

					return {
						message: errorMessage,
						error: errorDetected
					};
				}
			},

			_getUploadURL: function(folderId) {
				var instance = this;

				var uploadURL = instance._uploadURL;

				if (!instance._uploadURL) {
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

				return A.Lang.sub(uploadURL, {folderId: folderId});
			},

			_initDLUpload: function() {
				var instance = this;

				if (UPLOADER_TYPE && UPLOADER_TYPE === 'html5' && !UA.ios) {
					var config = instance._config;

					var documentContainer = A.one(CSS_DOCUMENT_CONTAINER);

					var maxFileSize = config.maxFileSize;
					var maxFileSizeInKB = Math.floor(maxFileSize / 1024);

					instance._folderId = config.folders.defaultParentFolderId;

					var uploader = instance._initUploader();

					instance._initUploaderCallbacks(uploader);

					instance._columnNames = config.columnNames;
					instance._dataset = new A.DataSet();
					instance._dimensions = config.folders.dimensions;
					instance._entriesContainer = documentContainer;
					instance._entriesCount = config.paginator.entriesTotal;
					instance._invisibleDescriptiveEntry = documentContainer.one(CSS_HIDDEN + CSS_DOT_ENTRY_DISPLAY_STYLE + CSS_DISPLAY_DESCRIPTIVE);
					instance._invisibleIconEntry = documentContainer.one(CSS_HIDDEN + CSS_DOT_ENTRY_DISPLAY_STYLE + CSS_DISPLAY_ICON);
					instance._maxFileSize = maxFileSize;
					instance._overlayManager = new A.OverlayManager();
					instance._subscriptions = [];
					instance._uploader = uploader;
					instance._viewFileEntryUrl = config.viewFileEntryUrl;

					// Upload Error Messages

					var duplicateFileText = Liferay.Language.get('please-enter-a-unique-document-name');
					var invalidFileExtensionText = Liferay.Language.get('document-names-must-end-with-one-of-the-following-extensions') + instance._allowedFileTypes;
					var invalidFileNameText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-name');

					instance._invalidFileSizeText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-size-no-larger-than-x');
					instance._zeroByteFileText = Liferay.Language.get('the-file-contains-no-data-and-cannot-be-uploaded.-please-use-the-classic-uploader');

					instance._errorMessages = {
						'490': duplicateFileText,
						'491': invalidFileExtensionText,
						'492': invalidFileNameText,
						'493': Lang.sub(instance._invalidFileSizeText, [maxFileSizeInKB])
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
						simLimit: 2,
						swfURL: Liferay.Util.addParams('ts=' + Lang.now(), themeDisplay.getPathContext() + '/html/js/aui/uploader/assets/flashuploader.swf')
					}
				).render(A.getBody());

				uploader.get('selectFilesButton').remove();

				return uploader;
			},

			_initUploaderCallbacks: function(uploader) {
				var instance = this;

				var docElement = A.one(document.documentElement);

				var entriesContainer = instance._entriesContainer;

				A.getWin().on('beforeunload', instance._preventPageUnload, instance);

				Liferay.before(
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

				docElement.on(
					'dragover',
					function(event) {
						var originalEvent = event._event;

						var dataTransfer = originalEvent.dataTransfer;

						if (dataTransfer) {
							event.halt();

							var target = event.target;

							var dropEffect = 'none';

							if (true) {
								dropEffect = 'copy';
							}

							dataTransfer.dropEffect = dropEffect;
						}

						entriesContainer.addClass(CSS_DRAG_HIGHLIGHT);
					}
				);

				docElement.on(
					'dragleave',
					function() {
						entriesContainer.removeClass(CSS_DRAG_HIGHLIGHT);
					}
				);

				docElement.delegate(
					'drop',
					function(event) {
						var originalEvent = event._event;

						var dataTransfer = originalEvent.dataTransfer;

						var files = originalEvent.dataTransfer.files;

						if (dataTransfer && AArray.indexOf(dataTransfer.types, 'Files') > -1) {
							event.halt();

							if (!instance._navigationOverlays) {
								instance._createNavigationOverlays();
							}

							event.fileList = instance._getDragDropFiles(files);

							uploader.fire('fileselect', {_EVT: event});
						}
					},
					'body, .document-container, .aui-overlaymask, .aui-progressbar, [data-folder="true"]'
				);

				entriesContainer.delegate(
					'dragover',
					function(event) {
						var parentElement = event.target.ancestor(CSS_DOT_ENTRY_DISPLAY_STYLE);

						parentElement.addClass(CSS_ACTIVE_AREA);
					},
					CSS_DATA_FOLDER
				);

				entriesContainer.delegate(
					'dragleave',
					function(event) {
						var parentElement = event.target.ancestor(CSS_DOT_ENTRY_DISPLAY_STYLE);

						parentElement.removeClass(CSS_ACTIVE_AREA);
					},
					CSS_DATA_FOLDER
				);

				uploader.after('alluploadscomplete', instance._startNextUpload, instance);
				uploader.after('fileselect', instance._queueUpload, instance);
			},

			_isUploading: function() {
				var instance = this;

				var queue = instance._uploader.queue;

				var isUploading = !!(queue && queue._currentState == UPLOADING);

				return isUploading;
			},

			_preventPageUnload: function(event) {
				var instance = this;

				if (instance._isUploading()) {
					event.preventDefault();
				}
			},

			_queueUpload: function(event) {
				var instance = this;

				var fileList = event._EVT.fileList;

				var files = instance._validateFiles(fileList);

				var folderEntry = instance._getFolderEntry(event._EVT.target);

				var folderId;

				var invalidFiles = files.invalidFiles;

				var isFolderUpload = (folderEntry !== null);

				var overlay;

				var progressBar;

				var uploadData = instance._getCurrentUploadData();

				if (isFolderUpload) {
					folderId = folderEntry.attr(DATA_FOLDER_ID);

					overlay = folderEntry.overlay;

					progressBar = folderEntry.progressBar;

					if (invalidFiles.length > 0) {
						uploadData.invalidFiles = uploadData.invalidFiles.concat(invalidFiles);
					}
				}
				else {
					instance._createEntries(files);

					folderId = instance._folderId;
				}

				var activeUpload = !!(uploadData && uploadData.folderId === folderId);

				var dataset = instance._dataset;

				var isUploading = instance._isUploading();

				var pendingUploadData = dataset.item(folderId + '');

				var uploader = instance._uploader;

				var validFiles = files.validFiles;

				var queue = uploader.queue;

				if (isUploading && activeUpload) {
					AArray.map(
						validFiles,
						function(item, index, collection) {
							queue.addToQueueBottom(item);
						}
					);
				}
				else if (isUploading && pendingUploadData) {
					instance._combineFileLists(pendingUploadData.fileList, validFiles);

					dataset.replace(folderId, pendingUploadData);
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

				if (!isUploading) {
					var entriesContainer = instance._entriesContainer;

					var emptyMessage = entriesContainer.one(CSS_ENTRIES_EMPTY);

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
						var displayStyleIsList = (displayStyle === LIST);

						if (!displayStyleIsList) {
							instance._updateThumbnail(entryNode, file.name);
						}

						var responseFileEntryId = response.message;

						var fileEntryId = instance.ns('fileEntryId=') + responseFileEntryId;

						instance._updateEntryLink(entryNode, fileEntryId, displayStyleIsList);
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

				var overlayContentBox = overlay.get(CONTENT_BOX);
				var overlayBoundingBox = overlay.get(BOUNDING_BOX);

				var progressBarBoundingBox = progressBar.get(BOUNDING_BOX);

				progressBar.render(overlayBoundingBox);

				progressBarBoundingBox.center(overlayContentBox);
			},

			_showFolderUploadComplete: function(event, uploadData, displayStyle) {
				var instance = this;

				var displayStyleIsList = (displayStyle === LIST);

				var folderEntry = uploadData.folderEntry;

				var invalidFiles = uploadData.invalidFiles;

				var selector = displayStyleIsList ? CSS_DOT_TAGLIB_ICON : CSS_ENTRY_THUMBNAIL;

				var resultsNode = folderEntry.one(selector);

				var uploadResultClass;

				if (resultsNode) {
					if (invalidFiles.length > 0) {
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

				var overlayBoundingBox = overlay.get(BOUNDING_BOX);
				var overlayContentBox = overlay.get(CONTENT_BOX);

				var progressBar = uploadData.progressBar;

				var progressBarBoundingBox = progressBar.get(BOUNDING_BOX);

				progressBar.render(overlayBoundingBox);

				progressBarBoundingBox.center(overlayContentBox);
			},

			_spawnEntryNode: function(displayStyle, file) {
				var instance = this;

				var entryNode;

				var entriesContainer;

				var title = file.name;

				if (displayStyle === LIST) {
					var searchContainer = A.one(CSS_SEARCH_CONTAINER);

					entriesContainer = searchContainer.one('tbody');

					entryNode = instance._createEntryRow(file);
				}
				else {
					var invisibleEntry = displayStyle === CSS_ICON ? instance._invisibleIconEntry : instance._invisibleDescriptiveEntry;

					entryNode = invisibleEntry.clone();

					var entryLink = entryNode.one(CSS_ENTRY_LINK);

					var entryTitle = entryLink.one(CSS_ENTRY_TITLE);

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

			_updateEntryLink: function(node, id, displayStyleIsList) {
				var instance = this;

				var link = displayStyleIsList ? node.one(CSS_DOT_ENTRY_DISPLAY_STYLE).one(CSS_DOT_TAGLIB_ICON) : node.one(CSS_ENTRY_LINK);

				var href = Liferay.Util.addParams(id, instance._viewFileEntryUrl);

				link.attr('href', href);
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

						if (size > maxFileSize) {
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
		requires: ['aui-data-set', 'aui-overlay-manager', 'aui-overlay-mask', 'aui-progressbar', 'aui-template', 'aui-tooltip', 'liferay-app-view-folders', 'liferay-app-view-move', 'liferay-app-view-paginator', 'liferay-app-view-select', 'uploader']
	}
);