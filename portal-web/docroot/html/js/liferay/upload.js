AUI.add(
	'liferay-upload',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;
		var UploaderQueue = A.Uploader.Queue;

		var UPLOADER_TYPE = A.Uploader.TYPE || 'none';

		var TPL_FILE_LIST = [
			'<tpl for=".">',
				'<tpl if="!values.error">',
					'<li class="upload-file {[ values.temp ? "upload-complete pending-file selectable" : "" ]}" data-fileId="{id}" data-fileName="{name}" id="{id}">',
						'<input class="{[ !values.temp ? "aui-helper-hidden" : "" ]} select-file" data-fileName="{name}" id="{id}checkbox" name="{$ns}selectUploadedFileCheckbox" type="checkbox" value="{name}" />',
						'<span class="file-title" title="{name}">{name}</span>',
						'<span class="progress-bar">',
							'<span class="progress" id="{id}progress"></span>',
						'</span>',
						'<a class="lfr-button cancel-button" href="javascript:;" id="{id}cancelButton">{[ this.cancelFileText ]}</a>',
						'<a class="lfr-button delete-button" href="javascript:;" id="{id}deleteButton">{[ this.deleteFileText ]}</a>',
					'</li>',
				'</tpl>',
				'<tpl if="values.error">',
					'<li class="upload-file upload-error" data-fileId="{id}" id="{id}">',
						'<span class="file-title" title="{name}">{name}</span>',
						'<span class="error-message" title="{error}">{error}</span>',
					'</li>',
				'</tpl>',
			'</tpl>'
		];

		var TPL_UPLOAD = [
			'<div class="upload-target" id="{$ns}uploader">',
				'<div class="drag-drop-area" id="{$ns}uploaderContent">',
					'<h4 class="drop-file-text {["', UPLOADER_TYPE, '" == "html5" ? "" : "aui-helper-hidden"]}">{[ this.dropFileText ]}<span>{[ this.orText ]}</span></h4>',
							'<span class="aui-button" id="{$ns}selectFilesButton">',
								'<span class="aui-button-content">',
									'<input class="aui-button-input" type="button" value="{[ this.selectFilesText ]}" />',
								'</span>',
							'</span>',
						'</div>',
					'</div>',
				'</div>',
			'</div>',

			'<div class="aui-helper-hidden upload-list-info" id="{$ns}listInfo">',
				'<h4>{[ this.uploadsCompleteText ]}</h4>',
			'</div>',

			'<div class="pending-files-info portlet-msg-alert aui-helper-hidden">{[ this.pendingFileText ]}</div>',

			'<div class="aui-helper-hidden float-container manage-upload-target" id="{$ns}manageUploadTarget" style="position: relative;">',
				'<span class="aui-field aui-field-choice select-files aui-state-default">',
					'<span class="aui-field-content">',
						'<span class="aui-field-element">',
							'<input type="checkbox" name="{$ns}allRowIdsCheckbox" id="{$ns}allRowIdsCheckbox" class="aui-helper-hidden select-all-files" />',
						'</span>',
					'</span>',
				'</span>',

				'<a href="javascript:;" class="lfr-button cancel-uploads aui-helper-hidden">{[ this.cancelUploadsText ]}</a>',
				'<a href="javascript:;" class="lfr-button clear-uploads aui-helper-hidden">{[ this.clearRecentUploadsText ]}</a>',
			'</div>',

			'<div class="upload-list" id="{$ns}fileList">',
				'<ul class="lfr-component" id="{$ns}fileListContent"></ul>',
			'</div>'
		];

		/**
		 * OPTIONS
		 *
		 * Required
		 * allowedFileTypes {string}: A comma-separated list of allowable filetypes.
		 * container {string|object}: The container where the uploader will be placed.
		 * deleteFile {string}: The URL that will handle the deleting of the pending files.
		 * maxFileSize {number}: The maximum file size that can be uploaded.
		 * tempFileURL {string|object}: The URL or configuration of the service to retrieve the pending files.
		 * uploadFile {string}: The URL to where the file will be uploaded.
		 *
		 * Optional
		 * fallbackContainer {string|object}: A selector or DOM element of the container holding a fallback (in case flash is not supported).
		 * fileDescription {string}: A string describing what files can be uploaded.
		 * metadataContainer {string}: Metadata container.
		 * metadataExplanationContainer {string}: A container explaining how to save metadata.
		 * namespace {string}: A unique string so that the global callback methods don't collide.
		 *
		 * Callbacks
		 * onFileComplete {function}: Called whenever a file is completely uploaded.
		 * onUploadsComplete {function}: Called when all files are finished being uploaded, and is passed no arguments.
		 * onUploadProgress {function}: Called during upload, and is also passed in the number of bytes loaded as it's second argument.
		 * onUploadError {function}: Called when an error in the upload occurs. Gets passed the error number as it's only argument.
		 */

		var Upload = function(options) {
			var instance = this;

			options = options || {};

			var maxFileSize = parseInt(options.maxFileSize, 10) || 0;

			instance._namespaceId = options.namespace || '_liferay_pns_' + Liferay.Util.randomInt() + '_';

			instance._allowedFileTypes = options.allowedFileTypes;
			instance._deleteFile = options.deleteFile;
			instance._maxFileSize = maxFileSize;
			instance._maxFileSizeInKB = Math.floor(maxFileSize / 1024);

			instance._container = A.one(options.container);
			instance._fallbackContainer = A.one(options.fallbackContainer);
			instance._metadataContainer = A.one(options.metadataContainer);
			instance._metadataExplanationContainer = A.one(options.metadataExplanationContainer);

			instance._tempFileURL = options.tempFileURL;
			instance._uploadFile = options.uploadFile;

			instance._classicUploaderParam = 'uploader=classic';
			instance._newUploaderParam = 'uploader=new';

			// Check for an override via the query string

			var loc = location.href;

			if ((loc.indexOf(instance._classicUploaderParam) > -1) && instance._fallbackContainer) {
				instance._fallbackContainer.show();

				instance._setupIframe();

				return;
			}

			// Language keys

			instance._cancelUploadsText = Liferay.Language.get('cancel-all-uploads');
			instance._cancelFileText = Liferay.Language.get('cancel-upload');
			instance._duplicateFileText = Liferay.Language.get('please-enter-a-unique-document-name');
			instance._fileListPendingText = Liferay.Language.get('x-files-ready-to-be-uploaded');
			instance._filesSelectedText = Liferay.Language.get('x-files-selected');
			instance._fileTypesDescriptionText = options.fileDescription || instance._allowedFileTypes;
			instance._invalidFileExtensionText = Liferay.Language.get('document-names-must-end-with-one-of-the-following-extensions') + instance._allowedFileTypes;
			instance._invalidFileNameText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-name');
			instance._invalidFileSizeText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-size-no-larger-than-x');
			instance._noFilesSelectedText = Liferay.Language.get('no-files-selected');
			instance._unexpectedDeleteErrorText = Liferay.Language.get('an-unexpected-error-occurred-while-deleting-the-file');
			instance._unexpectedUploadErrorText = Liferay.Language.get('an-unexpected-error-occurred-while-uploading-your-file');
			instance._uploadsCompleteText = Liferay.Language.get('all-files-ready-to-be-saved');
			instance._uploadStatusText = Liferay.Language.get('uploading-file-x-of-x');
			instance._zeroByteFileText = Liferay.Language.get('the-file-contains-no-data-and-cannot-be-uploaded.-please-use-the-classic-uploader');

			instance._errorMessages = {
				'490': instance._duplicateFileText,
				'491': instance._invalidFileExtensionText,
				'492': instance._invalidFileNameText,
				'493': instance._invalidFileSizeText
			};

			if (instance._fallbackContainer) {
				instance._useFallbackText = Liferay.Language.get('use-the-classic-uploader');
				instance._useNewUploaderText = Liferay.Language.get('use-the-new-uploader');
			}

			if (instance.flashVersion >= 9 && instance._fallbackContainer) {
				instance._fallbackContainer.show();

				instance._setupIframe();

				return;
			}

			instance._fileListBuffer = [];
			instance._renderFileListTask = A.debounce(instance._renderFileList, 10, instance);

			if (UPLOADER_TYPE != 'none') {
				instance._setupControls();

				instance._setupUploader();

				instance._setupCallbacks();

				instance._filesQueued = 0;
				instance._filesTotal = 0;
			}
			else {
				A.Template(
					'<tpl>',
						'<div class="upload-target" id="{$ns}uploader">',
							'<div class="drag-drop-area" id="{$ns}uploaderContent">',
								'<h4 class="drop-file-text">{notAvailableText}</h4>',
									'</div>',
								'</div>',
							'</div>',
						'</div>',

						'<div class="upload-list" id="{$ns}fileList">',
							'<ul class="lfr-component" id="{$ns}fileListContent"></ul>',
						'</div>',
					'</tpl>'
				).render(
					{
						$ns: instance._namespaceId,
						notAvailableText: Liferay.Language.get('multiple-uploading-not-available')
					}, 
					A.one('#' + instance._namespaceId + 'fileUpload')
				);
			}
		};

		Upload.prototype = {
			addFile: function(file) {
				var instance = this;

				instance._fileListBuffer.push(file);

				instance._renderFileListTask();

				instance._pendingFileInfo.hide();
				instance._cancelButton.show();
			},

			_afterFilesSaved: function(event) {
				var instance = this;

				instance._updateMetadataContainer();
				instance._updateManageUploadDisplay();
			},

			_cancelAllFiles: function() {
				var instance = this;

				var uploader = instance._uploader;

				var queue = uploader.queue;

				var fileList = queue.get('fileList');

				queue.cancelUpload();

				uploader.queue = null;

				instance._filesQueued = 0;
				instance._filesTotal = 0;

				A.each(
					fileList,
					function(item, index, collection) {
						var li = A.one('#' + item.id);

						if (li) {
							li.remove(true);
						}
					}
				);

				instance._cancelButton.hide();

				instance._updateList(0, instance._cancelUploadsText);
			},

			_clearUploads: function() {
				var instance = this;

				var completeUploads = instance._fileListContent.all('.file-saved,.upload-error');

				if (completeUploads) {
					completeUploads.remove(true);
				}

				instance._updateManageUploadDisplay();
			},

			_formatTempFiles: function(fileNames) {
				var instance = this;

				var allRowIdsCheckbox = instance._allRowIdsCheckbox;

				if (fileNames.length) {
					var fileListContent = instance._fileListContent;

					instance._pendingFileInfo.show();

					allRowIdsCheckbox.show();

					instance._manageUploadTarget.show();

					var metadataExplanationContainer = instance._metadataExplanationContainer;

					if (metadataExplanationContainer) {
						metadataExplanationContainer.show();
					}

					var files = AArray.map(
						fileNames,
						function(item, index, collection) {
							return {
								id: A.guid(),
								name: item,
								temp: true
							};
						}
					);

					instance._fileListTPL.render(files, fileListContent);
				}
				else {
					allRowIdsCheckbox.attr('checked', true);
				}
			},

			_getValidFiles: function(data) {
				var instance = this;

				var maxFileSize = instance._maxFileSize;
				var maxFileSizeInKB = instance._maxFileSizeInKB;

				return AArray.filter(
					data,
					function(item, index, collection) {

						var id = item.get('id') || A.guid();
						var name = item.get('name');
						var size = item.get('size') || 0;

						var error;
						var file;

						if (maxFileSizeInKB > 0 && (size > maxFileSize)) {
							error = Lang.sub(instance._invalidFileSizeText, [maxFileSizeInKB]);
						}
						else if (name.length > 240) {
							error = instance._invalidFileNameText;
						}
						else if (size === 0) {
							error = instance._zeroByteFileText;
						}

						if (error) {
							item.error = error;
						}
						else {
							file = item;
						}

						item.id = id;
						item.name = name;
						item.size = size;

						instance.addFile(item);

						return file;
					}
				);
			},

			_handleDeleteResponse: function(json, li) {
				var instance = this;

				if (!json.deleted) {
					var errorHTML = instance._fileListTPL.parse(
						[
							{
								error: json.errorMessage,
								id: li.attr('data-fileId'),
								name: li.attr('data-fileName')
							}
						]
					);

					li.replace(errorHTML);
				}

				li.remove(true);

				instance._updateManageUploadDisplay();
				instance._updateMetadataContainer();
				instance._updatePendingInfoContainer();
			},

			_handleDrop: function(event) {
				var instance = this;

				event.halt();

				var uploaderBoundingBox = instance._uploaderBoundingBox;

				var target = event.target;

				var uploader = instance._uploader;

				var dataTransfer = event._event.dataTransfer;

				var dragDropFiles = dataTransfer && dataTransfer.files;

				if (dragDropFiles && (target === uploaderBoundingBox || uploaderBoundingBox.contains(target))) {
					event.fileList = A.map(
						dragDropFiles,
						function(item, index, collection) {
							return new A.FileHTML5(item);
						}
					);

					uploader.fire('fileselect', event);
				}

				if (UPLOADER_TYPE === 'flash') {
					var selectFilesButton = uploader.get('selectFilesButton');

					selectFilesButton.removeClass('yui3-button-selected');
				}
			},

			_handleFileClick: function(event) {
				var instance = this;

				var currentTarget = event.currentTarget;

				if (currentTarget.hasClass('select-file')) {
					instance._onSelectFileClick(currentTarget);
				}
				else if (currentTarget.hasClass('delete-button')) {
					instance._onDeleteFileClick(currentTarget);
				}
				else if (currentTarget.hasClass('cancel-button')) {
					instance._onCancelFileClick(currentTarget);
				}
			},

			_isUploading: function() {
				var instance = this;

				var queue = instance._uploader.queue;

				var uploading = !!(queue && (queue.queuedFiles.length > 0 || queue.numberOfUploads > 0 || !A.Object.isEmpty(queue.currentFiles)) && queue._currentState === UploaderQueue.UPLOADING);

				return uploading;
			},

			_markSelected: function(node) {
				var instance = this;

				var fileItem = node.ancestor('.upload-file.selectable');

				fileItem.toggleClass('selected');
			},

			_namespace: function(txt) {
				var instance = this;

				txt = txt || '';

				return instance._namespaceId + txt;
			},

			_onAllUploadsComplete: function(event) {
				var instance = this;

				var uploader = instance._uploader;

				instance._filesQueued = 0;
				instance._filesTotal = 0;

				uploader.set('enabled', true);

				uploader.set('fileList', []);

				instance._cancelButton.hide();

				instance._clearUploadsButton.toggle(!!instance._fileListContent.one('.file-saved,.upload-error'));

				instance._updateList(0, instance._uploadsCompleteText);

				Liferay.fire('allUploadsComplete');
			},

			_onCancelFileClick: function(currentTarget) {
				var instance = this;

				var uploader = instance._uploader;

				var queue = uploader.queue;

				var li = currentTarget.ancestor('li');

				if (li) {
					if (queue) {
						var fileId = li.attr('data-fileId');

						var file = queue.currentFiles[fileId] || AArray.find(
							queue.queuedFiles,
							function(item, index, collection) {
								return item.id === fileId;
							}
						);

						if (file) {
							queue.cancelUpload(file);

							instance._updateList(0, instance._cancelFileText);
						}

						if (queue.queuedFiles.length === 0 && queue.numberOfUploads <= 0) {
							uploader.queue = null;
						}
					}

					li.remove(true);
				}
			},

			_onDeleteFileClick: function(currentTarget) {
				var instance = this;

				var li = currentTarget.ancestor('li');

				A.io.request(
					instance._deleteFile,
					{
						data: {
							fileName : li.attr('data-fileName')
						},
						dataType: 'json',
						on: {
							success: function(event, id, obj) {
								instance._handleDeleteResponse(this.get('responseData'), li);
							},
							failure: function(event, id, obj) {
								instance._handleDeleteResponse(
									{
										errorMessage: instance._unexpectedDeleteErrorText
									},
									li
								);
							}
						}
					}
				);
			},

			_onSelectFileClick: function(currentTarget) {
				var instance = this;

				Liferay.Util.checkAllBox(
					instance._fileListSelector,
					instance._selectUploadedFileCheckboxId,
					instance._allRowIdsCheckboxSelector
				);

				instance._markSelected(currentTarget);

				instance._updateMetadataContainer();
			},

			_onBeforeUnload: function(event) {
				var instance = this;

				if (instance._isUploading()) {
					event.preventDefault();
				}
			},

			_onUploadComplete: function(event) {
				var instance = this;

				var file = event.file;

				var fileId = file.id;

				var li = A.one('#' + fileId);

				var data = String(event.data);

				// Check for an HTTP error code in the 490's
				if (data.indexOf('49') === 0) {;
					var errorMessage;

					if (data === '493') {
						errorMessage = Lang.sub(instance._invalidFileSizeText, [instance._maxFileSizeInKB]);
					}
					else {
						errorMessage = instance._errorMessages[data];
					}

					file.error = errorMessage;

					instance._fileListContent.append(instance._fileListTPL.parse([file]));

					if (li) {
						li.remove(true);
					}
				}
				else {
					if (li) {
						li.replaceClass('file-uploading', 'pending-file upload-complete selectable selected');

						var input = li.one('input');

						if (input) {
							input.attr('checked', true);

							input.show();
						}

						instance._updateManageUploadDisplay();
					}

					instance._updateMetadataContainer();
				}
			},

			_onUploadProgress: function(event) {
				var instance = this;

				var progress = A.one('#' + event.file.id + 'progress');

				if (progress) {
					var percentLoaded = Math.ceil(event.percentLoaded / 3) * 3;

					progress.setStyle('width', percentLoaded + '%');
				}
			},

			_onUploadStart: function(event) {
				var instance = this;

				var filesQueued = (instance._filesQueued -= 1);

				var filesTotal = instance._filesTotal;

				var position = filesTotal - filesQueued;

				var currentListText = Lang.sub(instance._uploadStatusText, [position, filesTotal]);

				var fileIdSelector = '#' + event.file.id;

				A.on(
					'available',
					function() {
						A.one(fileIdSelector).addClass('file-uploading');
					},
					fileIdSelector
				);

				instance._updateList(0, currentListText);
			},

			_queueUpload: function(event) {
				var instance = this;

				var fileList = event.fileList;

				var validFiles = instance._getValidFiles(fileList);

				var validFilesLength = validFiles.length;

				if (validFilesLength > 0) {
					var uploader = instance._uploader;

					uploader.set('fileList', validFiles);

					instance._filesTotal += validFilesLength;
					instance._filesQueued += validFilesLength;

					if (instance._isUploading()) {
						var uploadQueue = uploader.queue;

						A.each(
							validFiles,
							function(item, index, collection) {
								uploadQueue.addToQueueBottom(item);
							}
						);
					}
					else {
						uploader.uploadAll();
					}
				}
			},

			_renderFileList: function() {
				var instance = this;

				var fileListBuffer = instance._fileListBuffer;

				var fileListHTML = instance._fileListTPL.parse(fileListBuffer);

				var fileListContent = instance._fileListContent;

				var firstLi = fileListContent.one('li.upload-complete');

				if (firstLi) {
					firstLi.placeBefore(fileListHTML);
				}
				else {
					fileListContent.append(fileListHTML);
				}

				fileListBuffer.length = 0;
			},

			_setupCallbacks: function() {
				var instance = this;

				var uploader = instance._uploader;

				uploader.after('fileselect', instance._queueUpload, instance);

				uploader.on('alluploadscomplete', instance._onAllUploadsComplete, instance);
				uploader.on('fileuploadstart', instance._onUploadStart, instance);
				uploader.on('uploadcomplete', instance._onUploadComplete, instance);
				uploader.on('uploadprogress', instance._onUploadProgress, instance);

				var docElement = A.one(A.config.doc.documentElement);

				docElement.on('drop', instance._handleDrop, instance);

				var uploaderBoundingBox = instance._uploaderBoundingBox;

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

							var dropEffect = 'none';

							var inDropArea = target.compareTo(uploaderBoundingBox) || uploaderBoundingBox.contains(target);

							if (inDropArea) {
								dropEffect = 'copy';
							}

							docElement.toggleClass('upload-drop-active', inDropArea);

							dataTransfer.dropEffect = dropEffect;
						}

						removeCssClassTask();
					}
				);

				A.getWin().on('beforeunload', instance._onBeforeUnload, instance);
			},

			_setupControls: function() {
				var instance = this;

				var templateConfig = {
					$ns: instance._namespaceId,
					cancelFileText: instance._cancelFileText,
					deleteFileText: Liferay.Language.get('delete-file'),
					clearRecentUploadsText: Liferay.Language.get('clear-documents-already-saved'),
					cancelUploadsText: instance._cancelUploadsText,
					dropFileText: Liferay.Language.get('drop-files-here-to-upload'),
					orText: Liferay.Language.get('or'),
					pendingFileText: Liferay.Language.get('these-files-have-been-previously-uploaded-but-not-actually-saved.-please-save-or-delete-them-before-they-are-removed'),
					selectFilesText: Liferay.Language.get('select-files'),
					uploadsCompleteText: instance._uploadsCompleteText
				};

				instance._fileListTPL = new A.Template(TPL_FILE_LIST, templateConfig);

				if (!instance._hasControls) {
					instance._selectUploadedFileCheckboxId = instance._namespace('selectUploadedFileCheckbox');

					instance._fileListSelector = '#' + instance._namespace('fileList');
					instance._allRowIdsCheckboxSelector = '#' + instance._namespace('allRowIdsCheckbox');

					var uploadFragment = new A.Template(TPL_UPLOAD, templateConfig).render();

					instance._allRowIdsCheckbox = uploadFragment.one(instance._allRowIdsCheckboxSelector);

					instance._manageUploadTarget = uploadFragment.one('#' + instance._namespace('manageUploadTarget'));

					instance._manageUploadTarget.setStyle('position', 'relative');

					instance._cancelButton = uploadFragment.one('.cancel-uploads');
					instance._clearUploadsButton = uploadFragment.one('.clear-uploads');

					instance._fileList = uploadFragment.one(instance._fileListSelector);
					instance._fileListContent = uploadFragment.one('#' + instance._namespace('fileListContent'));
					instance._listInfo = uploadFragment.one('#' + instance._namespace('listInfo'));
					instance._pendingFileInfo = uploadFragment.one('.pending-files-info');
					instance._selectFilesButton = uploadFragment.one('#' + instance._namespace('selectFilesButton'));

					instance._uploaderBoundingBox = uploadFragment.one('#' + instance._namespace('uploader'));
					instance._uploaderContentBox = uploadFragment.one('#' + instance._namespace('uploaderContent'));

					Liferay.after('filesSaved', instance._afterFilesSaved, instance);

					var selectAllCheckbox = instance._manageUploadTarget.one('.select-all-files');

					selectAllCheckbox.on(
						'click',
						function() {
							Liferay.Util.checkAll(
								instance._fileListSelector,
								instance._selectUploadedFileCheckboxId,
								instance._allRowIdsCheckboxSelector
							);

							var uploadedFiles = instance._fileListContent.all('.upload-file.upload-complete');

							uploadedFiles.toggleClass('selected', instance._allRowIdsCheckbox.attr('checked'));

							instance._updateMetadataContainer();
						}
					);

					instance._fileList.delegate('click', instance._handleFileClick, '.select-file, li .delete-button, li .cancel-button', instance);

					instance._cancelButton.on('click', instance._cancelAllFiles, instance);

					var tempFileURL = instance._tempFileURL;

					if (Lang.isString(tempFileURL)) {
						A.io.request(
							tempFileURL,
							{
								after: {
									success: function(event) {
										instance._formatTempFiles(this.get('responseData'));
									}
								},
								dataType: 'json'
							}
						);
					}
					else {
						tempFileURL.method(tempFileURL.params, A.bind('_formatTempFiles', instance));
					}

					var container = instance._container;

					instance._uploadFragment = uploadFragment;

					instance._clearUploadsButton.on('click', instance._clearUploads, instance);

					instance._cancelButton.hide();

					if (instance._fallbackContainer) {
						instance._useFallbackButton = A.Node.create('<a class="use-fallback using-new-uploader" href="javascript:;">' + instance._useFallbackText + '</a>');
						instance._fallbackContainer.placeAfter(instance._useFallbackButton);

						instance._useFallbackButton.on(
							'click',
							function(event) {
								var fallback = event.currentTarget;
								var newUploaderClass = 'using-new-uploader';
								var fallbackClass = 'using-classic-uploader';

								var metadataContainer = instance._metadataContainer;
								var metadataExplanationContainer = instance._metadataExplanationContainer;

								if (fallback && fallback.hasClass(newUploaderClass)) {
									if (metadataContainer && metadataExplanationContainer) {
										metadataContainer.hide();
										metadataExplanationContainer.hide();
									}

									container.hide();

									instance._fallbackContainer.show();

									fallback.text(instance._useNewUploaderText);
									fallback.replaceClass(newUploaderClass, fallbackClass);

									instance._setupIframe();

									var classicUploaderUrl = '';

									if (location.hash.length) {
										classicUploaderUrl = '&';
									}

									location.hash += classicUploaderUrl + instance._classicUploaderParam;
								}
								else {
									if (metadataContainer && metadataExplanationContainer) {
										var totalFiles = instance._fileList.all('li input[name=' + instance._selectUploadedFileCheckboxId + ']');

										var selectedFiles = totalFiles.filter(':checked');

										var selectedFilesCount = selectedFiles.size();

										if (selectedFilesCount > 0) {
											metadataContainer.show();
										}
										else {
											metadataExplanationContainer.show();
										}
									}

									container.show();

									instance._fallbackContainer.hide();

									fallback.text(instance._useFallbackText);
									fallback.replaceClass(fallbackClass, newUploaderClass);

									location.hash = location.hash.replace(instance._classicUploaderParam, instance._newUploaderParam);
								}
							}
						);
					}

					instance._hasControls = true;
				}
			},

			_setupIframe: function() {
				var instance = this;

				if (!instance._fallbackIframe) {
					instance._fallbackIframe = instance._fallbackContainer.all('iframe[id$=-iframe]');

					if (instance._fallbackIframe.size()) {
						var portletLayout = instance._fallbackIframe.one('#main-content');

						var frameHeight = 250;

						if (portletLayout) {
							frameHeight = portletLayout.get('offsetHeight') || frameHeight;
						}

						instance._fallbackIframe.setStyle('height', frameHeight + 150);
					}
				}
			},

			_setupUploader: function() {
				var instance = this;

				var uploader = new A.Uploader(
					{
						fileFieldName: 'file',
						dragAndDropArea: A.Node.create('<div />'),
						multipleFiles: true,
						on: {
							render: function(event) {
								instance._container.setContent(instance._uploadFragment);
							}
						},
						selectFilesButton: instance._selectFilesButton,
						boundingBox: instance._uploaderBoundingBox,
						contentBox: instance._uploaderContentBox,
						simLimit: 2,
						swfURL: Liferay.Util.addParams('ts=' + A.Lang.now(), themeDisplay.getPathContext() + '/html/js/aui/uploader/assets/flashuploader.swf'),
						uploadURL: Liferay.Util.addParams('ts=' + A.Lang.now(), instance._uploadFile)
					}
				);

				instance._uploader = uploader;

				uploader.render();
			},

			_updateList: function(listLength, message) {
				var instance = this;

				var infoTitle = instance._listInfo.one('h4');

				if (infoTitle) {
					var listText = message || Lang.sub(instance._fileListPendingText, [listLength]);

					infoTitle.html(listText);
				}
			},

			_updateManageUploadDisplay: function() {
				var instance = this;

				var fileListContent = instance._fileListContent;

				var hasUploadedFiles = !!fileListContent.one('.upload-complete');
				var hasSavedFiles = !!fileListContent.one('.file-saved,.upload-error');

				instance._allRowIdsCheckbox.toggle(hasUploadedFiles);

				instance._clearUploadsButton.toggle(hasSavedFiles);
				instance._manageUploadTarget.toggle(hasUploadedFiles);

				instance._listInfo.toggle(!!fileListContent.one('li'));
			},

			_updateMetadataContainer: function() {
				var instance = this;

				var metadataContainer = instance._metadataContainer;
				var metadataExplanationContainer = instance._metadataExplanationContainer;

				if (metadataContainer && metadataExplanationContainer) {
					var totalFiles = instance._fileList.all('li input[name=' + instance._selectUploadedFileCheckboxId + ']');

					var totalFilesCount = totalFiles.size();

					var selectedFiles = totalFiles.filter(':checked');

					var selectedFilesCount = selectedFiles.size();

					var selectedFileName = '';

					if (selectedFilesCount > 0) {
						selectedFileName = selectedFiles.item(0).ancestor().attr('data-fileName');
					}

					if (metadataContainer) {
						metadataContainer.toggle((selectedFilesCount > 0));

						var selectedFilesText = instance._noFilesSelectedText;

						if (selectedFilesCount == 1) {
							selectedFilesText = selectedFileName;
						}
						else if (selectedFilesCount == totalFilesCount) {
							selectedFilesText = Liferay.Language.get('all-files-selected');
						}
						else if (selectedFilesCount > 1) {
							selectedFilesText = Lang.sub(instance._filesSelectedText, [selectedFilesCount]);
						}

						var selectedFilesCountContainer = metadataContainer.one('.selected-files-count');

						if (selectedFilesCountContainer) {
							selectedFilesCountContainer.setContent(selectedFilesText);

							selectedFilesCountContainer.attr('title', selectedFilesText);
						}
					}

					if (metadataExplanationContainer) {
						metadataExplanationContainer.toggle((!selectedFilesCount) && (totalFilesCount > 0));
					}
				}
			},

			_updatePendingInfoContainer: function() {
				var instance = this;

				var totalFiles = instance._fileList.all('li input[name=' + instance._selectUploadedFileCheckboxId + ']');

				if (!totalFiles.size()) {
					instance._pendingFileInfo.hide();
				}
			}
		};

		Liferay.Upload = Upload;
	},
	'',
	{
		requires: ['aui-io-request', 'aui-template', 'collection', 'swfupload', 'uploader', 'uploader-flash', 'uploader-html5']
	}
);