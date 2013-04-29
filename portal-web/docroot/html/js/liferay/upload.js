AUI.add(
	'liferay-upload',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;
		var UploaderQueue = A.Uploader.Queue;

		var formatSelectorNS = A.Node.formatSelectorNS;

		var STR_BLANK = '';

		var STR_PARAM_FALLBACK = 'uploader=fallback';

		var TPL_ERROR_MESSAGE = '<div class="alert alert-error">{0}</div>';

		var TPL_FILE_LIST = [
			'<tpl for=".">',
				'<tpl if="!values.error">',
					'<li class="upload-file {[ values.temp ? "upload-complete pending-file selectable" : "" ]}" data-fileId="{id}" data-fileName="{name}" id="{id}">',
						'<input class="{[ !values.temp ? "hide" : "" ]} select-file" data-fileName="{name}" id="{id}checkbox" name="{$ns}selectUploadedFileCheckbox" type="checkbox" value="{name}" />',
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
					'<tpl if="this.uploaderType == \'html5\'">',
						'<h4 class="drop-file-text">{[ this.dropFileText ]}<span>{[ this.orText ]}</span></h4>',
					'</tpl>',
					'<button class="btn" id="{$ns}selectFilesButton">{[ this.selectFilesText ]}</button>',
				'</div>',
			'</div>',

			'<div class="hide upload-list-info" id="{$ns}listInfo">',
				'<h4>{[ this.uploadsCompleteText ]}</h4>',
			'</div>',

			'<div class="pending-files-info alert alert-block hide">{[ this.pendingFileText ]}</div>',

			'<div class="hide float-container manage-upload-target" id="{$ns}manageUploadTarget">',
				'<tpl if="multipleFiles">',
					'<span class="field field-choice select-files">',
						'<span class="field-content">',
							'<span class="field-element">',
								'<input class="select-all-files" id="{$ns}allRowIdsCheckbox" name="{$ns}allRowIdsCheckbox" type="checkbox" />',
							'</span>',
						'</span>',
					'</span>',
				'</tpl>',

				'<a href="javascript:;" class="lfr-button cancel-uploads hide">{[ this.cancelUploadsText ]}</a>',
				'<a href="javascript:;" class="lfr-button clear-uploads hide">{[ this.clearRecentUploadsText ]}</a>',
			'</div>',

			'<div class="upload-list" id="{$ns}fileList">',
				'<ul class="lfr-component" id="{$ns}fileListContent"></ul>',
			'</div>'
		];

		var UPLOADER_TYPE = A.Uploader.TYPE || 'none';

		var URL_SWF_UPLOADER = themeDisplay.getPathContext() + '/html/js/aui/uploader/assets/flashuploader.swf';

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
		 * metadataContainer {string}: Metadata container.
		 * metadataExplanationContainer {string}: A container explaining how to save metadata.
		 * namespace {string}: A unique string so that the global callback methods don't collide.
		 */

		var Upload = A.Component.create(
			{
				ATTRS: {
					allowedFileTypes: {
						value: STR_BLANK
					},
					deleteFile: {
						value: ''
					},
					fallback: {
						setter: A.one,
						value: null
					},
					maxFileSize: {
						setter: Lang.toInt,
						value: 0
					},
					metadataContainer: {
						setter: A.one,
						value: null
					},
					metadataExplanationContainer: {
						setter: A.one,
						value: null
					},
					multipleFiles: {
						value: true
					},
					render: {
						value: true
					},
					tempFileURL: {
						value: ''
					},
					uploadFile: {
						value: ''
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				NAME: 'liferayupload',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var fallback = instance.get('fallback');

						var useFallback = (location.hash.indexOf(STR_PARAM_FALLBACK) > -1) && fallback;

						if (useFallback ||
							UPLOADER_TYPE == 'none' ||
							(UPLOADER_TYPE == 'flash' && !A.SWFDetect.isFlashVersionAtLeast(10, 1))) {

							if (fallback) {
								fallback.show();
							}
							else {
								instance.one('#fileUpload').append(Lang.sub(TPL_ERROR_MESSAGE, [Liferay.Language.get('multiple-file-uploading-is-not-available')]));
							}

							instance._preventRenderHandle = instance.on(
								'render',
								function(event) {
									event.preventDefault();
								}
							);
						}
						else {
							var allowedFileTypes = instance.get('allowedFileTypes');

							var maxFileSizeKB = Math.floor(instance.get('maxFileSize') / 1024);

							instance._cancelUploadsText = Liferay.Language.get('cancel-all-uploads');
							instance._cancelFileText = Liferay.Language.get('cancel-upload');

							if (instance.get('multipleFiles')) {
								instance._selectFileText = Liferay.Language.get('select-files');
								instance._dropFileText = Liferay.Language.get('drop-files-here-to-upload');

							}
							else {
								instance._selectFileText = Liferay.Language.get('select-file');
								instance._dropFileText = Liferay.Language.get('drop-file-here-to-upload');
							}

							instance._invalidFileNameText = Liferay.Language.get('please-enter-a-file-with-a-valid-file-name');
							instance._invalidFileSizeText = Lang.sub(Liferay.Language.get('please-enter-a-file-with-a-valid-file-size-no-larger-than-x'), [maxFileSizeKB]);
							instance._uploadsCompleteText = Liferay.Language.get('all-files-ready-to-be-saved');

							instance._errorMessages = {
								'490': Liferay.Language.get('please-enter-a-unique-document-name'),
								'491': Liferay.Language.get('document-names-must-end-with-one-of-the-following-extensions') + ' ' + allowedFileTypes,
								'492': instance._invalidFileNameText,
								'493': instance._invalidFileSizeText
							};

							instance._metadataContainer = instance.get('metadataContainer');
							instance._metadataExplanationContainer = instance.get('metadataExplanationContainer');

							instance._fileListBuffer = [];
							instance._renderFileListTask = A.debounce(instance._renderFileList, 10, instance);
						}

						instance._fallback = fallback;
					},

					renderUI: function() {
						var instance = this;

						instance._renderControls();
						instance._renderUploader();
					},

					bindUI: function() {
						var instance = this;

						if (instance._allRowIdsCheckbox) {
							instance._allRowIdsCheckbox.on('click', instance._onAllRowIdsClick, instance);
						}

						instance._cancelButton.on('click', instance._cancelAllFiles, instance);
						instance._clearUploadsButton.on('click', instance._clearUploads, instance);

						A.getWin().on('beforeunload', instance._onBeforeUnload, instance);

						instance._fileList.delegate('click', instance._handleFileClick, '.select-file, li .delete-button, li .cancel-button', instance);

						Liferay.after('filesSaved', instance._afterFilesSaved, instance);

						var uploader = instance._uploader;

						uploader.after('fileselect', instance._onFileSelect, instance);

						uploader.on('alluploadscomplete', instance._onAllUploadsComplete, instance);
						uploader.on('fileuploadstart', instance._onUploadStart, instance);
						uploader.on('uploadcomplete', instance._onUploadComplete, instance);
						uploader.on('uploadprogress', instance._onUploadProgress, instance);

						var docElement = A.getDoc().get('documentElement');

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

									var inDropArea = target.compareTo(uploaderBoundingBox) || uploaderBoundingBox.contains(target);

									var dropEffect = 'none';

									if (inDropArea) {
										dropEffect = 'copy';
									}

									docElement.toggleClass('upload-drop-active', inDropArea);

									dataTransfer.dropEffect = dropEffect;
								}

								removeCssClassTask();
							}
						);
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

						queue.pauseUpload();

						A.each(
							queuedFiles,
							function(item, index, collection) {
								var li = A.one('#' + item.id);

								if (li && !li.hasClass('upload-complete')) {
									li.remove(true);
								}
							}
						);

						A.all('.file-uploading').remove(true);

						queue.cancelUpload();

						uploader.queue = null;

						instance._cancelButton.hide();

						instance._filesTotal = 0;

						instance._updateList(0, instance._cancelUploadsText);
					},

					_clearUploads: function() {
						var instance = this;

						instance._fileListContent.all('.file-saved,.upload-error').remove(true);

						instance._updateManageUploadDisplay();
					},

					_formatTempFiles: function(fileNames) {
						var instance = this;

						if (fileNames.length) {
							var fileListContent = instance._fileListContent;

							instance._pendingFileInfo.show();
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
						else if (instance._allRowIdsCheckbox) {
							instance._allRowIdsCheckbox.attr('checked', true);
						}
					},

					_getValidFiles: function(data) {
						var instance = this;

						var maxFileSize = instance.get('maxFileSize');

						var zeroByteSizeText = Liferay.Language.get('the-file-contains-no-data-and-cannot-be-uploaded.-please-use-the-classic-uploader');

						return AArray.filter(
							data,
							function(item, index, collection) {

								var id = item.get('id') || A.guid();
								var name = item.get('name');
								var size = item.get('size') || 0;

								var error;
								var file;

								if (size === 0) {
									error = zeroByteSizeText;
								}
								else if (name.length > 240) {
									error = instance._invalidFileNameText;
								}
								else if (maxFileSize > 0 && (size > maxFileSize)) {
									error = instance._invalidFileSizeText;
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

								instance._queueFile(item);

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

						var dragDropFiles = dataTransfer && AArray(dataTransfer.files);

						if (dragDropFiles && (target === uploaderBoundingBox || uploaderBoundingBox.contains(target))) {
							event.fileList = AArray.map(
								dragDropFiles,
								function(item, index, collection) {
									return new A.FileHTML5(item);
								}
							);

							uploader.fire('fileselect', event);
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

						return !!(queue && (queue.queuedFiles.length > 0 || queue.numberOfUploads > 0 || !A.Object.isEmpty(queue.currentFiles)) && queue._currentState === UploaderQueue.UPLOADING);
					},

					_markSelected: function(node) {
						var instance = this;

						var fileItem = node.ancestor('.upload-file.selectable');

						fileItem.toggleClass('selected');
					},

					_onAllRowIdsClick: function(event) {
						var instance = this;

						Liferay.Util.checkAll(
							instance._fileListSelector,
							instance._selectUploadedFileCheckboxId,
							instance._allRowIdsCheckboxSelector
						);

						var uploadedFiles = instance._fileListContent.all('.upload-file.upload-complete');

						uploadedFiles.toggleClass('selected', instance._allRowIdsCheckbox.attr('checked'));

						instance._updateMetadataContainer();
					},

					_onAllUploadsComplete: function(event) {
						var instance = this;

						var uploader = instance._uploader;

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

									instance._cancelButton.hide();
								}
							}

							li.remove(true);

							instance._filesTotal -= 1;
						}
					},

					_onDeleteFileClick: function(currentTarget) {
						var instance = this;

						var li = currentTarget.ancestor('li');

						li.hide();

						var failureResponse = {
							errorMessage: Liferay.Language.get('an-unexpected-error-occurred-while-deleting-the-file')
						};

						var deleteFile = instance.get('deleteFile');

						if (deleteFile) {
							A.io.request(
								deleteFile,
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
											li.show();

											instance._handleDeleteResponse(failureResponse, li);
										}
									}
								}
							);
						}
						else {
							instance._handleDeleteResponse(failureResponse, li);
						}
					},

					_onFileSelect: function(event) {
						var instance = this;

						var fileList = event.fileList;

						var validFiles = instance._getValidFiles(fileList);

						var validFilesLength = validFiles.length;

						if (validFilesLength) {
							var uploader = instance._uploader;

							uploader.set('fileList', validFiles);

							instance._filesTotal += validFilesLength;

							instance._cancelButton.show();

							if (instance._isUploading()) {
								var uploadQueue = uploader.queue;

								AArray.each(validFiles, uploadQueue.addToQueueBottom, uploadQueue);
							}
							else {
								uploader.uploadAll();
							}
						}

						instance._pendingFileInfo.hide();
					},

					_onSelectFileClick: function(currentTarget) {
						var instance = this;

						if (instance.get('multipleFiles')) {
							Liferay.Util.checkAllBox(
								instance._fileListSelector,
								instance._selectUploadedFileCheckboxId,
								instance._allRowIdsCheckboxSelector
							);
						}

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

						var data = Lang.trim(String(event.data));

						if (data.indexOf('49') === 0) {
							file.error = instance._errorMessages[data] || Liferay.Language.get('an-unexpected-error-occurred-while-uploading-your-file');

							var newLi = instance._fileListTPL.parse([file]);

							if (li) {
								li.placeBefore(newLi);

								li.remove(true);
							}
							else {
								instance._fileListContent.prepend(newLi);
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

						var progress = A.byIdNS(event.file.id, 'progress');

						if (progress) {
							var percentLoaded = Math.min(Math.ceil(event.percentLoaded / 3) * 3, 100);

							progress.setStyle('width', percentLoaded + '%');
						}
					},

					_onUploadStart: function(event) {
						var instance = this;

						var uploader = instance._uploader;

						var queue = uploader.queue;

						var filesQueued = queue ? queue.queuedFiles.length : 0;

						var filesTotal = instance._filesTotal;

						var position = filesTotal - filesQueued;

						var currentListText = Lang.sub(Liferay.Language.get('uploading-file-x-of-x'), [position, filesTotal]);

						var fileIdSelector = '#' + event.file.id;

						A.on(
							'available',
							function() {
								A.one(fileIdSelector).addClass('file-uploading');
							},
							fileIdSelector
						);

						instance._listInfo.show();

						instance._updateList(0, currentListText);
					},

					_queueFile: function(file) {
						var instance = this;

						instance._fileListBuffer.push(file);

						instance._renderFileListTask();
					},

					_renderControls: function() {
						var instance = this;

						var templateConfig = {
							$ns: instance.NS,
							cancelFileText: instance._cancelFileText,
							cancelUploadsText: instance._cancelUploadsText,
							clearRecentUploadsText: Liferay.Language.get('clear-documents-already-saved'),
							deleteFileText: Liferay.Language.get('delete-file'),
							dropFileText: instance._dropFileText,
							orText: Liferay.Language.get('or'),
							pendingFileText: Liferay.Language.get('these-files-have-been-previously-uploaded-but-not-actually-saved.-please-save-or-delete-them-before-they-are-removed'),
							selectFilesText: instance._selectFileText,
							uploadsCompleteText: instance._uploadsCompleteText,
							uploaderType: UPLOADER_TYPE
						};

						instance._fileListTPL = new A.Template(TPL_FILE_LIST, templateConfig);

						instance._selectUploadedFileCheckboxId = instance.ns('selectUploadedFileCheckbox');

						var NS = instance.NS;

						instance._fileListSelector = formatSelectorNS(NS, '#fileList');
						instance._allRowIdsCheckboxSelector = formatSelectorNS(NS, '#allRowIdsCheckbox');

						var uploadFragment = new A.Template(TPL_UPLOAD, templateConfig).render(
							{
								multipleFiles: instance.get('multipleFiles')
							}
						);

						instance._allRowIdsCheckbox = uploadFragment.one(instance._allRowIdsCheckboxSelector);

						instance._manageUploadTarget = uploadFragment.oneNS(NS, '#manageUploadTarget');

						instance._cancelButton = uploadFragment.one('.cancel-uploads');
						instance._clearUploadsButton = uploadFragment.one('.clear-uploads');

						instance._fileList = uploadFragment.one(instance._fileListSelector);
						instance._fileListContent = uploadFragment.oneNS(NS, '#fileListContent');
						instance._listInfo = uploadFragment.oneNS(NS, '#listInfo');
						instance._pendingFileInfo = uploadFragment.one('.pending-files-info');
						instance._selectFilesButton = uploadFragment.oneNS(NS, '#selectFilesButton');

						instance._uploaderBoundingBox = uploadFragment.oneNS(NS, '#uploader');
						instance._uploaderContentBox = uploadFragment.oneNS(NS, '#uploaderContent');

						var tempFileURL = instance.get('tempFileURL');

						if (tempFileURL) {
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
						}

						instance._uploadFragment = uploadFragment;

						instance._cancelButton.hide();
					},

					_renderFileList: function() {
						var instance = this;

						var fileListBuffer = instance._fileListBuffer;
						var fileListContent = instance._fileListContent;

						var fileListHTML = instance._fileListTPL.parse(fileListBuffer);

						var firstLi = fileListContent.one('li.upload-complete');

						if (firstLi) {
							firstLi.placeBefore(fileListHTML);
						}
						else {
							fileListContent.append(fileListHTML);
						}

						fileListBuffer.length = 0;
					},

					_renderUploader: function() {
						var instance = this;

						var timestampParam = '_LFR_UPLOADER_TS=' + Lang.now();

						var uploader = new A.Uploader(
							{
								boundingBox: instance._uploaderBoundingBox,
								contentBox: instance._uploaderContentBox,
								fileFieldName: 'file',
								multipleFiles: instance.get('multipleFiles'),
								on: {
									render: function(event) {
										instance.get('boundingBox').setContent(instance._uploadFragment);
									}
								},
								selectFilesButton: instance._selectFilesButton,
								simLimit: 2,
								swfURL: Liferay.Util.addParams(timestampParam, URL_SWF_UPLOADER),
								uploadURL: Liferay.Util.addParams(timestampParam, instance.get('uploadFile'))
							}
						).render();

						uploader.addTarget(instance);

						instance._uploader = uploader;
					},

					_updateList: function(listLength, message) {
						var instance = this;

						var infoTitle = instance._listInfo.one('h4');

						if (infoTitle) {
							var listText = message || Lang.sub(Liferay.Language.get('x-files-ready-to-be-uploaded'), [listLength]);

							infoTitle.html(listText);
						}
					},

					_updateManageUploadDisplay: function() {
						var instance = this;

						var fileListContent = instance._fileListContent;

						var hasUploadedFiles = !!fileListContent.one('.upload-complete');
						var hasSavedFiles = !!fileListContent.one('.file-saved,.upload-error');

						if (instance._allRowIdsCheckbox) {
							instance._allRowIdsCheckbox.toggle(hasUploadedFiles);
						}

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

							var selectedFileName = STR_BLANK;

							var hasSelectedFiles = (selectedFilesCount > 0);

							if (hasSelectedFiles) {
								selectedFileName = selectedFiles.item(0).ancestor().attr('data-fileName');
							}

							if (metadataContainer) {
								metadataContainer.toggle(hasSelectedFiles);

								var selectedFilesText = Liferay.Language.get('no-files-selected');

								if (hasSelectedFiles) {
									if (selectedFilesCount == 1) {
										selectedFilesText = selectedFileName;
									}
									else if (selectedFilesCount == totalFilesCount) {
										selectedFilesText = Liferay.Language.get('all-files-selected');
									}
									else if (selectedFilesCount > 1) {
										selectedFilesText = Lang.sub(Liferay.Language.get('x-files-selected'), [selectedFilesCount]);
									}
								}

								var selectedFilesCountContainer = metadataContainer.one('.selected-files-count');

								if (selectedFilesCountContainer) {
									selectedFilesCountContainer.html(selectedFilesText);

									selectedFilesCountContainer.attr('title', selectedFilesText);
								}
							}

							if (metadataExplanationContainer) {
								metadataExplanationContainer.toggle((!hasSelectedFiles) && (totalFilesCount > 0));
							}
						}
					},

					_updatePendingInfoContainer: function() {
						var instance = this;

						var totalFiles = instance._fileList.all('li input[name=' + instance._selectUploadedFileCheckboxId + ']');

						if (!totalFiles.size()) {
							instance._pendingFileInfo.hide();
						}
					},

					_filesTotal: 0
				}
			}
		);

		Liferay.Upload = Upload;
	},
	'',
	{
		requires: ['aui-io-request', 'aui-template-deprecated', 'collection', 'liferay-portlet-base', 'uploader']
	}
);