AUI.add(
	'liferay-upload',
	function(A) {
		var Lang = A.Lang;

		var TPL_FILE_ERROR = '<li class="upload-file upload-error"><span class="file-title" title="{0}">{0}</span> <span class="error-message">{1}</span></li>';

		var TPL_FILE_PENDING = '<li class="upload-file upload-complete pending-file selectable">' +
			'<input class="select-file" data-fileName="{0}" name="{1}" type="checkbox" value="{0}" />' +
			'<span class="file-title" title="{0}">{0}</span>' +
			'<a class="lfr-button delete-button" href="javascript:;">{2}</a>' +
		'</li>';

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
		 * buttonHeight {number}: The buttons height.
		 * buttonText {string}: The text to be displayed on the upload button.
		 * buttonUrl {string}: A relative (to the flash) file that will be used as the background image of the button.
		 * buttonWidth {number}: The buttons width.
		 * fallbackContainer {string|object}: A selector or DOM element of the container holding a fallback (in case flash is not supported).
		 * fileDescription {string}: A string describing what files can be uploaded.
		 * metadataContainer {string}: Metadata container.
		 * metadataExplanationContainer {string}: A container explaining how to save metadata.
		 * namespace {string}: A unique string so that the global callback methods don't collide.
		 * overlayButton {boolean}: Whether the button is overlayed upon the HTML link.
		 *
		 * Callbacks
		 * onFileComplete {function}: Called whenever a file is completely uploaded.
		 * onUploadsComplete {function}: Called when all files are finished being uploaded, and is passed no arguments.
		 * onUploadProgress {function}: Called during upload, and is also passed in the number of bytes loaded as it's second argument.
		 * onUploadError {function}: Called when an error in the upload occurs. Gets passed the error number as it's only argument.
		 */

		var TEMP_FILE_NAME_CHARECTERS = 15;

		var MAX_FILE_NAME_SIZE = 255;

		var Upload = function(options) {
			var instance = this;

			options = options || {};

			instance._namespaceId = options.namespace || '_liferay_pns_' + Liferay.Util.randomInt() + '_';

			instance._allowedFileTypes = options.allowedFileTypes;
			instance._deleteFile = options.deleteFile;
			instance._maxFileSize = options.maxFileSize || 0;

			instance._container = A.one(options.container);
			instance._fallbackContainer = A.one(options.fallbackContainer);
			instance._metadataContainer = A.one(options.metadataContainer);
			instance._metadataExplanationContainer = A.one(options.metadataExplanationContainer);

			instance._tempFileURL = options.tempFileURL;
			instance._uploadFile = options.uploadFile;

			instance._buttonUrl = options.buttonUrl || '';
			instance._buttonWidth = options.buttonWidth || 500;
			instance._buttonHeight = options.buttonHeight || 30;
			instance._buttonText = options.buttonText || '';

			instance._buttonPlaceHolderId = instance._namespace('buttonHolder');
			instance._overlayButton = options.overlayButton || true;

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

			instance._allFilesSelectedText = Liferay.Language.get('all-files-selected');
			instance._browseText = Liferay.Language.get('browse-you-can-select-multiple-files');
			instance._cancelUploadsText = Liferay.Language.get('cancel-all-uploads');
			instance._cancelFileText = Liferay.Language.get('cancel-upload');
			instance._clearRecentUploadsText = Liferay.Language.get('clear-documents-already-saved');
			instance._deleteFileText = Liferay.Language.get('delete-file');
			instance._duplicateFileText = Liferay.Language.get('please-enter-a-unique-document-name');
			instance._fileListPendingText = Liferay.Language.get('x-files-ready-to-be-uploaded');
			instance._filesSelectedText = Liferay.Language.get('x-files-selected');
			instance._fileTypesDescriptionText = options.fileDescription || instance._allowedFileTypes;
			instance._invalidFileExtensionText = Liferay.Language.get('document-names-must-end-with-one-of-the-following-extensions') + instance._allowedFileTypes;
			instance._invalidFileNameText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-name');
			instance._invalidFileSizeText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-size-no-larger-than-x');
			instance._noFilesSelectedText = Liferay.Language.get('no-files-selected');
			instance._pendingFileText = Liferay.Language.get('these-files-have-been-previously-uploaded-but-not-actually-saved.-please-save-or-delete-them-before-they-are-removed');
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

			instance._setupUploader();
		};

		Upload.prototype = {
			addFile: function(file) {
				var instance = this;

				var fileId = instance._namespace(file.id);

				var fileName = file.name;

				var li = A.Node.create(
					'<li class="upload-file" id="' + fileId + '">' +
						'<input class="aui-helper-hidden select-file" data-fileName="' + fileName + '" id="' + fileId + 'checkbox" name="' + instance._namespace('selectUploadedFileCheckbox') + '" type="checkbox" value="' + fileName + '" />' +
						'<span class="file-title" title="' + fileName + '">' + fileName + '</span>' +
						'<span class="progress-bar">' +
							'<span class="progress" id="' + fileId + 'progress"></span>' +
						'</span>' +
						'<a class="lfr-button cancel-button" href="javascript:;" id="' + fileId + 'cancelButton">' + instance._cancelFileText + '</a>' +
						'<a class="lfr-button delete-button" href="javascript:;" id="' + fileId + 'deleteButton">' + instance._deleteFileText + '</a>' +
					'</li>');

				instance._pendingFileInfo.hide();

				instance._cancelButton.show();

				var cancelButton = li.all('.cancel-button');

				cancelButton.on(
					'click',
					function(event) {
						cancelButton.hide();

						var uploader = instance._uploader;
						var queue = uploader.queue || {};

						queue.cancelUpload(file);

						if (uploader.stats.files_queued === 1) {
							// The YUI Uploader Queue has a bug: if you upload and cancel a single file, subsequent uploads will not be handled properly in the queue

							uploader.queue = null;

							// Note: calling queue = null (without the uploader property) will not null the uploader's queue property
						}
						else {
							queue._startNextFile();

							file.set('bytesUploaded', 0);

							var cancelAllUploads = instance._cancelButton;

							cancelAllUploads.hide();
						}

						if (li) {
							li.remove(true);
						}

						uploader.stats.files_queued -= 1;

						instance._updateList(0, instance._cancelFileText);
					}
				);

				var listingFiles = instance._getFileListUl();

				var uploadedFiles = listingFiles.one('.upload-complete');

				if (uploadedFiles) {
					uploadedFiles.placeBefore(li);
				}
				else {
					listingFiles.append(li);
				}
			},

			_cancelAllFiles: function() {
				var instance = this;

				var uploader = instance._uploader;

				var queue = uploader.queue;

				var fileList = queue.get('fileList');

				queue.cancelUpload();

				// because of the bug where canceling a single file will not remove the queue. it will be cheaper to just null it then do a comparison every time.
				uploader.queue = null;

				uploader.stats.files_queued = 0;
				uploader.stats.files_total = 0;

				A.each(
					fileList,
					function(item, index, collection) {
						var fileId = instance._namespace(item.id);

						var li = A.one('#' + fileId);

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

				var completeUploads = instance._getFileListUl().all('.file-saved,.upload-error');

				if (completeUploads) {
					completeUploads.remove(true);
				}

				instance._updateManageUploadDisplay();
			},

			_formatTempFiles: function(fileNames) {
				var instance = this;

				var allRowIdsCheckbox = A.one('#' + instance._namespace('allRowIdsCheckbox'));

				if (fileNames.length) {
					var ul = instance._getFileListUl();

					instance._pendingFileInfo.show();

					allRowIdsCheckbox.show();

					instance._clearUploadsButton.show();
					instance._manageUploadTarget.show();

					if (instance._metadataExplanationContainer) {
						instance._metadataExplanationContainer.show();
					}

					var buffer = [];

					var dataBuffer = [
						null,
						instance._namespace('selectUploadedFileCheckbox'),
						instance._deleteFileText
					];

					A.each(
						fileNames,
						function(item, index, collection) {
							dataBuffer[0] = item;

							buffer.push(Lang.sub(TPL_FILE_PENDING, dataBuffer));
						}
					);

					ul.append(buffer.join(''));
				}
				else {
					allRowIdsCheckbox.attr('checked', true);
				}
			},

			_getFileListUl: function() {
				var instance = this;

				var listingFiles = instance._fileList;
				var listingUl = listingFiles.all('ul');

				if (!listingUl.size()) {
					instance._listInfo.append('<h4></h4>');

					listingUl = A.Node.create('<ul class="lfr-component"></ul>');

					listingFiles.append(listingUl);

					instance._manageUploadTarget.append(instance._clearUploadsButton);
					instance._clearUploadsButton.hide();

					instance._cancelButton.on('click', instance._cancelAllFiles, instance);

					instance._fileListUl = listingUl;
				}

				return instance._fileListUl;
			},

			_getValidFiles: function(data) {
				var instance = this;

				return A.Array.filter(
					data,
					function (item, index, collection) {
						var maxFileSizeInKB = Math.floor(instance._maxFileSize.replace(/\D/g,'') / 1024);

						item.id = A.guid();
						item.name = item.get('name');
						item.size = item.get("size") || 0;

						var ul = instance._getFileListUl();

						if (item.size > maxFileSizeInKB * 1024) {
							var errorMessage = [item.name, instance._invalidFileSizeText.replace('{0}', maxFileSizeInKB)];

							ul.append(Lang.sub(TPL_FILE_ERROR, errorMessage));

							return;
						}
						else if (item.name.length > 240) {
							var errorMessage = [item.name, instance._invalidFileNameText];

							ul.append(Lang.sub(TPL_FILE_ERROR, errorMessage));

							return;
						}
						else if (item.size === 0) {
							var errorMessage = [item.name, instance._zeroByteFileText];

							ul.append(Lang.sub(TPL_FILE_ERROR, errorMessage));

							return;
						}
						else {
							instance.addFile(item);
						}

						return item;
					}
				);
			},

			_handleDeleteResponse: function(json, li) {
				var instance = this;

				if (json.deleted) {
					li.remove(true);
				}
				else {
					var errorHTML = Lang.sub('<span class="error-message">{errorMessage}</span>', json);

					li.append(errorHTML);
				}

				instance._updateManageUploadDisplay();
				instance._updateMetadataContainer();
				instance._updatePendingInfoContainer();
			},

			_handleDrag: function(event) {
				var instance = this;

				var dragDropArea = instance._dragDropArea;

				var target = event.target;

				if (event.type === 'dragover') {
					if (target === dragDropArea || target.ancestor('.float-container') === dragDropArea) {
						dragDropArea.addClass('drag-highlight');
					}
				}
				else {
					dragDropArea.removeClass('drag-highlight');
				}
			},

			_handleDrop: function(event) {
				var instance = this;

				var dragDropArea = instance._dragDropArea;

				var target = event.target;

				var uploader = instance._uploader;

				if (target === dragDropArea || target.ancestor('.float-container') === dragDropArea) {
					var fileList = [];

					var dragDropFiles = event._event.dataTransfer.files;

					if (!!dragDropFiles) {
						A.each(
							dragDropFiles,
							function (item, index, collection) {
								fileList.push(new A.FileHTML5(item));
							}
						);

						event.fileList = fileList;

						dragDropArea.removeClass('drag-highlight');

						uploader.fire('fileselect', event);
					}

					event.preventDefault();
					event.stopPropagation();
				}

				var uploaderType = uploader.TYPE;

				if (uploaderType === 'html5') {
					dragDropArea.removeClass('drag-highlight');
				}
				else if (uploaderType === 'flash') {
					var selectFilesButton = uploader.get('selectFilesButton');

					selectFilesButton.removeClass('yui3-button-selected');
				}

				event.preventDefault();
				event.stopPropagation();
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

			_onAllUploadsComplete: function (event) {
				var instance = this;

				var uploader = instance._uploader;

				uploader.stats.files_queued = 0;
				uploader.stats.files_total = 0;
				uploader.stats.files_uploaded = 0;

				uploader.set('enabled', true);

				uploader.set('fileList', []);

				instance._cancelButton.hide();

				instance._clearUploadsButton.show();

				instance._updateList(0, instance._uploadsCompleteText);

				Liferay.fire('allUploadsComplete');
			},

			_onDeleteFileClick: function(currentTarget) {
				var instance = this;

				var li = currentTarget.ancestor();

				A.io.request(
					instance._deleteFile,
					{
						data: {
							fileName : li.one('.select-file').attr('data-fileName')
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

				Liferay.Util.checkAllBox('#' + instance._fileListId, instance._namespace('selectUploadedFileCheckbox'), '#' + instance._namespace('allRowIdsCheckbox'));

				instance._markSelected(currentTarget);

				instance._updateMetadataContainer();
			},

			_onUploadComplete: function (upload) {
				var instance = this;

				var file = upload.file;

				var fileId = instance._namespace(file.id);

				var li = A.one('#' + fileId);

				var uploader = instance._uploader;

				var queue = uploader.queue;

				uploader.stats.files_uploaded++;

				var data = upload.data;

				if (data === 'file_error::invalid characters in file name') {
					var ul = instance._getFileListUl();

					var dataBuffer = [file.name, instance._invalidFileNameText];

					ul.append(Lang.sub(TPL_FILE_ERROR, dataBuffer));

					if (li) {
						li.remove(true);
					}

					return;
				}

				if (li) {
					li.replaceClass('file-uploading', 'upload-complete selectable selected');

					var input = li.one('input');

					if (input) {
						input.attr('checked', true);

						input.show();
					}

					instance._updateManageUploadDisplay();
				}

				instance._updateMetadataContainer();
			},

			_queueUpload: function(upload) {
				var instance = this;

				var fileList = upload.fileList;

				var validFiles = instance._getValidFiles(fileList);

				var validFilesLength = validFiles.length;

				if (validFilesLength > 0) {
					var uploader = instance._uploader;

					uploader.set('fileList', validFiles);

					uploader.stats.files_total += validFilesLength;

					uploader.stats.files_queued += validFilesLength;

					var queueIsUploading = instance._queueIsUploading();

					if (queueIsUploading) {
						A.each(
							validFiles,
							function (item, index, collection){
								uploader.queue.addToQueueBottom(item);
							}
						);
					}
					else {
						uploader.uploadAll();
					}
				}
			},

			_queueIsUploading: function() {
				var instance = this;

				var uploader = instance._uploader;

				var queue = uploader.queue;

				return !!(queue && queue._currentState && queue._currentState === 'uploading');
			},

			_setupCallbacks: function() {
				var instance = this;

				var uploader = instance._uploader;

				var docElement = A.one(document.documentElement);

				uploader.after('fileselect', instance._queueUpload, instance);
				uploader.on('alluploadscomplete', instance._onAllUploadsComplete, instance);
				uploader.on('fileuploadstart', instance._updateUploadStarting, instance);
				uploader.on('uploadcomplete', instance._onUploadComplete, instance);
				uploader.on('uploadprogress', instance._updateUploadProgress, instance);

				docElement.on(['dragover', 'dragleave'], instance._handleDrag, instance);
				docElement.on('drop', instance._handleDrop, instance);

				docElement.on(
					'dragover',
					function(event) {
						event.stopPropagation();
						event.preventDefault();
						try {
							event._event.dataTransfer.dropEffect = 'copy';
						}
						catch (err) {
						}
					},
					false
				);

				A.getWin().on(
					'beforeunload',
					function(event) {
						var queueIsUploading = instance._queueIsUploading();

						if (queueIsUploading) {
							event.preventDefault();
						}
					}
				);
			},

			_setupControls: function() {
				var instance = this;

				if (!instance._hasControls) {
					instance._dragDropAreaId = instance._namespace('dragDropArea');
					instance._dragDropAreaContentId = instance._namespace('dragDropAreaContent');
					instance._fileListId = instance._namespace('fileList');
					instance._listInfoId = instance._namespace('listInfo');
					instance._manageUploadTargetId = instance._namespace('manageUploadTarget');
					instance._uploadTargetId = instance._namespace('uploadTarget');

					instance._dragDropArea = A.Node.create('<div id="' + instance._dragDropAreaId + '" class="float-container drag-drop-area">DROP FILES HERE TO UPLOAD<br/>OR</div>');
					instance._manageUploadTarget = A.Node.create('<div id="' + instance._manageUploadTargetId + '" class="aui-helper-hidden float-container manage-upload-target"><span class="aui-field aui-field-choice select-files aui-state-default"><span class="aui-field-content"><span class="aui-field-element"><input class="aui-helper-hidden select-all-files" id="' + instance._namespace('allRowIdsCheckbox') + '" name="' + instance._namespace('allRowIdsCheckbox') + '" type="checkbox"/></span></span></span></div>');
					instance._uploadTarget = A.Node.create('<div id="' + instance._uploadTargetId + '" class="float-container upload-target"></div>');

					instance._manageUploadTarget.setStyle('position', 'relative');
					instance._uploadTarget.setStyle('position', 'relative');

					instance._browseButton = A.Node.create('<div class="browse-button-container"><a class="lfr-button browse-button" href="javascript:;">' + instance._browseText + '</a></div>');
					instance._cancelButton = A.Node.create('<a class="lfr-button cancel-uploads" href="javascript:;">' + instance._cancelUploadsText + '</a>');
					instance._clearUploadsButton = A.Node.create('<a class="lfr-button clear-uploads" href="javascript:;">' + instance._clearRecentUploadsText + '</a>');
					instance._fileList = A.Node.create('<div id="' + instance._fileListId + '" class="upload-list"></div>');
					instance._listInfo = A.Node.create('<div id="' + instance._listInfoId + '" class="upload-list-info"></div>');
					instance._pendingFileInfo = A.Node.create('<div class="pending-files-info portlet-msg-alert aui-helper-hidden">' + instance._pendingFileText + '</div>');

					Liferay.on('filesSaved', instance._updateMetadataContainer, instance);

					var selectAllCheckbox = instance._manageUploadTarget.one('.select-all-files');

					selectAllCheckbox.on(
						'click',
						function() {
							Liferay.Util.checkAll('#' + instance._fileListId, instance._namespace('selectUploadedFileCheckbox'), '#' + instance._namespace('allRowIdsCheckbox'));

							var filesUploaded = A.all('.upload-file.upload-complete');

							var allRowIds = instance._manageUploadTarget.one('#' + instance._namespace('allRowIdsCheckbox'));

							filesUploaded.toggleClass('selected', allRowIds.attr('checked'));

							instance._updateMetadataContainer();
						}
					);

					instance._fileList.delegate(
						'click',
						function(event) {
							var currentTarget = event.currentTarget;

							if (currentTarget.hasClass('select-file')) {
								instance._onSelectFileClick(currentTarget);
							}
							else if (currentTarget.hasClass('delete-button')) {
								instance._onDeleteFileClick(currentTarget);
							}
						},
						'.select-file, li .delete-button'
					);

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
						tempFileURL['method'](tempFileURL['params'], A.bind('_formatTempFiles', instance));
					}

					var container = instance._container;
					var manageUploadTarget = instance._manageUploadTarget;
					var uploadTarget = instance._uploadTarget;
					var dragDropArea = instance._dragDropArea || {};

					container.append(uploadTarget);
					container.append(instance._listInfo);
					container.append(instance._pendingFileInfo);
					container.append(manageUploadTarget);
					container.append(instance._fileList);

					manageUploadTarget.append(instance._cancelButton);

					instance._clearUploadsButton.on(
						'click',
						function() {
							instance._clearUploads();
						}
					);

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

								var movieBoundingBox = instance._movieBoundingBox;

								var metadataContainer = instance._metadataContainer;
								var metadataExplanationContainer = instance._metadataExplanationContainer;

								if (fallback && fallback.hasClass(newUploaderClass)) {
									if (movieBoundingBox) {
										movieBoundingBox.hide();
									}

									if (metadataContainer && metadataExplanationContainer) {
										metadataContainer.hide();
										metadataExplanationContainer.hide();
									}

									instance._container.hide();
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
									if (movieBoundingBox) {
										movieBoundingBox.show();
									}

									if (metadataContainer && metadataExplanationContainer) {
										var totalFiles = instance._fileList.all('li input[name=' + instance._namespace('selectUploadedFileCheckbox') + ']');

										var selectedFiles = totalFiles.filter(':checked');

										var selectedFilesCount = selectedFiles.size();

										if (selectedFilesCount > 0) {
											metadataContainer.show();
										}
										else {
											metadataExplanationContainer.show();
										}
									}

									instance._container.show();
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

				instance._movieBoundingBox = A.Node.create('<div class="lfr-upload-movie"><div class="lfr-upload-movie-content"></div></div>');
				instance._movieContentBox = instance._movieBoundingBox.get('firstChild');

				var movieBoundingBox = instance._movieBoundingBox;
				var movieContentBox = instance._movieContentBox;

				A.getBody().prepend(movieBoundingBox);

				var uploadURL = Liferay.Util.addParams('ts=' + A.Lang.now() , instance._uploadFile);

				if (A.Uploader.TYPE && A.Uploader.TYPE != 'none' && !A.UA.ios) {
					var uploader = new A.Uploader(
						{
							fileFieldName: 'file',
							height: '35px',
							multipleFiles: true,
							selectButtonLabel: 'Select Files',
							simLimit: 2,
							swfURL: Liferay.Util.addParams('ts=' + A.Lang.now() , themeDisplay.getPathContext() + '/html/js/aui/uploader/assets/flashuploader.swf'),
							uploadURL: uploadURL
						}
					);

					uploader.TYPE = A.Uploader.TYPE;

					instance._uploader = uploader;

					if (A.Uploader.TYPE == "html5") {
						var docElement = A.one(document.documentElement);

						uploader.set("dragAndDropArea", instance._dragDropArea);

						var docElement = document.documentElement;
						var docElementNode = A.one(docElement);
					}

					instance._setupControls();

					instance._setupCallbacks();

					uploader.stats = {
						files_queued: 0,
						files_total: 0,
						files_uploaded: 0
					}

					var uploadTarget = instance._uploadTarget;

					if (uploader.TYPE === 'html5') {
						var dragDropArea = instance._dragDropArea;

						uploadTarget.append(dragDropArea);

						uploader.render(dragDropArea);

						uploader.set('height', '35px');
						uploader.set('width', '150px');
					}
					else if (uploader.TYPE === 'flash') {
						uploader.render(uploadTarget);

						uploader.set('height', '40px');
					}
				}
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

				var ul = instance._getFileListUl();

				var files = ul.all('li');

				var uploadedFiles = files.filter('.upload-complete');

				var allRowIdsCheckbox = A.one('#' + instance._namespace('allRowIdsCheckbox'));

				var hasUploadedFiles = (uploadedFiles.size() > 0);

				allRowIdsCheckbox.toggle(hasUploadedFiles);

				instance._clearUploadsButton.toggle(hasUploadedFiles);
				instance._manageUploadTarget.toggle(hasUploadedFiles);

				instance._listInfo.toggle(!!files.size());
			},

			_updateMetadataContainer: function() {
				var instance = this;

				var metadataContainer = instance._metadataContainer;
				var metadataExplanationContainer = instance._metadataExplanationContainer;

				if (metadataContainer && metadataExplanationContainer) {
					var totalFiles = instance._fileList.all('li input[name=' + instance._namespace('selectUploadedFileCheckbox') + ']');

					var totalFilesCount = totalFiles.size();

					var selectedFiles = totalFiles.filter(':checked');

					var selectedFilesCount = selectedFiles.size();

					var selectedFileName = '';

					if (selectedFilesCount > 0) {
						selectedFileName = selectedFiles.item(0).attr('data-fileName');
					}

					if (metadataContainer) {
						metadataContainer.toggle((selectedFilesCount > 0));

						var selectedFilesText = instance._noFilesSelectedText;

						if (selectedFilesCount == 1) {
							selectedFilesText = selectedFileName;
						}
						else if (selectedFilesCount == totalFilesCount) {
							selectedFilesText = instance._allFilesSelectedText;
						}
						else if (selectedFilesCount > 1) {
							selectedFilesText = instance._filesSelectedText.replace('{0}', selectedFilesCount);
						}

						var selectedFilesCountContainer = metadataContainer.one('.selected-files-count');

						if (selectedFilesCountContainer != null) {
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

				var totalFiles = instance._fileList.all('li input[name=' + instance._namespace('selectUploadedFileCheckbox') + ']');

				if (!totalFiles.size()) {
					instance._pendingFileInfo.hide();
				}
			},

			_updateUploadProgress: function (upload) {
				var instance = this;

				var file = upload.file;

				var fileId = instance._namespace(file.id);

				var li = A.one('#' + fileId);

				var progress = document.getElementById(fileId + 'progress');

				if (li && progress) {
					li.addClass('file-uploading');

					progress.style.width = upload.percentLoaded + '%';
				}
			},

			_updateUploadStarting: function(event) {
				var instance = this;

				var uploader = instance._uploader;

				var files_queued = --uploader.stats.files_queued;

				var files_total = uploader.stats.files_total;

				var position = files_total - files_queued;

				var currentListText = Lang.sub(instance._uploadStatusText, [position, files_total]);

				instance._updateList(0, currentListText);
			}
		};

		Liferay.Upload = Upload;
	},
	'',
	{
		requires: ['aui-io-request', 'collection', 'swfupload', 'uploader', 'uploader-flash', 'uploader-html5']
	}
);