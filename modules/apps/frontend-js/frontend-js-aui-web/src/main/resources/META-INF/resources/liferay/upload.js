/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-upload',
	A => {
		var AArray = A.Array;
		var Lang = A.Lang;
		var UploaderQueue = A.Uploader.Queue;

		var STATUS_CODE = Liferay.STATUS_CODE;
		var STRINGS = 'strings';

		var STR_PARAM_FALLBACK = 'uploader=fallback';

		var TPL_ERROR_MESSAGE = '<div class="alert alert-danger">{0}</div>';

		var TPL_FILE_LIST = [
			'<tpl for=".">',
			'<tpl if="!values.error">',
			'<li class="upload-file {[ values.temp ? "upload-complete pending-file selectable" : "" ]} {[ values.selected ? "selected" : "" ]}" data-fileId="{id}" data-fileName="{[ LString.escapeHTML(values.name) ]}" data-title="{[ LString.escapeHTML(values.title ? values.title : values.name) ]}" id="{id}">',
			'<div class="form-check form-check-card form-check-middle-left">',
			'<div class="custom-checkbox custom-control">',
			'<label>',
			'<input class="custom-control-input entry-selector select-file" data-fileName="{[ LString.escapeHTML(values.name) ]}" data-title="{[ LString.escapeHTML(values.title ? values.title : values.name) ]}" {[ !values.temp ? "disabled" : "" ]} id="{id}checkbox" name="{$ns}selectUploadedFile" type="{[ this.multipleFiles ? "checkbox" : "hidden" ]}" value="{[ LString.escapeHTML(values.name) ]}" />',
			'<span class="custom-control-label"></span>',
			'<div class="card card-horizontal">',
			'<div class="card-body">',
			'<div class="card-row">',
			'<div class="autofit-col">',
			'<span class="sticker sticker-rounded">',
			'<span class="sticker-overlay inline-item">',
			Liferay.Util.getLexiconIconTpl('document'),
			'</span>',
			'</span>',
			'</div>',
			'<div class="autofit-col autofit-col-expand autofit-col-gutters clamp-horizontal">',
			'<div class="clamp-container">',
			'<span class="file-title text-truncate" title="{[ LString.escapeHTML(values.title ? values.title : values.name) ]}">{[ LString.escapeHTML(values.title ? values.title : values.name) ]}</span>',
			'</div>',
			'<span class="progress">',
			'<span class="progress-bar progress-bar-animated" id="{id}progress"></span>',
			'</span>',
			'</div>',
			'<div class="autofit-col delete-button-col">',
			'<a class="delete-button lfr-button" href="javascript:;" id="{id}deleteButton" title="{[ this.strings.deleteFileText ]}">',
			Liferay.Util.getLexiconIconTpl('times'),
			'</a>',
			'</div>',

			'<a class="cancel-button lfr-button" href="javascript:;" id="{id}cancelButton">',
			Liferay.Util.getLexiconIconTpl('times'),
			'<span class="cancel-button-text">{[ this.strings.cancelFileText ]}</span>',
			'</a>',
			'</div>',
			'</div>',
			'</div>',
			'</label>',
			'</div>',
			'</div>',
			'</li>',
			'</tpl>',

			'<tpl if="values.error && this.multipleFiles">',
			'<li class="upload-error upload-file" data-fileId="{id}" id="{id}">',
			'<span class="file-title" title="{[ LString.escapeHTML(values.name) ]}">{[ LString.escapeHTML(values.name) ]}</span>',

			'<span class="error-message" title="{[ LString.escapeHTML(values.error) ]}">{[ LString.escapeHTML(values.error) ]}</span>',

			'<tpl if="values.messageListItems && (values.messageListItems.length > 0)">',
			'<ul class="error-list-items">',
			'<tpl for="messageListItems">',
			'<li>{[ LString.escapeHTML(values.type) ]}: <strong>{[ LString.escapeHTML(values.name) ]}</strong>',
			'<tpl if="info">',
			'<span class="error-info"">({[ LString.escapeHTML(values.info) ]})</span>',
			'</tpl>',
			'</li>',
			'</tpl>',
			'</ul>',
			'</tpl>',
			'</li>',
			'</tpl>',

			'<tpl if="values.error && !this.multipleFiles">',
			'<li class="alert alert-danger upload-error" data-fileId="{id}" id="{id}">',
			'<h4 class="upload-error-message">{[ Lang.sub(this.strings.fileCannotBeSavedText, [LString.escapeHTML(values.name)]) ]}</h4>',

			'<span class="error-message" title="{[ LString.escapeHTML(values.error) ]}">{[ LString.escapeHTML(values.error) ]}</span>',

			'<tpl if="values.messageListItems && (values.messageListItems.length > 0)">',
			'<ul class="error-list-items">',
			'<tpl for="messageListItems">',
			'<li>{[ LString.escapeHTML(values.type) ]}: <strong>{[ LString.escapeHTML(values.name) ]}</strong>',
			'<tpl if="info">',
			'<span class="error-info"">({[ LString.escapeHTML(values.info) ]})</span>',
			'</tpl>',
			'</li>',
			'</tpl>',
			'</ul>',
			'</tpl>',
			'</li>',
			'</tpl>',

			'<tpl if="values.warningMessages && (values.warningMessages.length > 0)">',
			'<li class="alert upload-error" data-fileId="{id}" id="{id}">',
			'<span class="error-message" title="{[ LString.escapeHTML(values.error ? this.strings.warningFailureText : this.strings.warningText) ]}">{[ values.error ? this.strings.warningFailureText : this.strings.warningText ]}</span>',

			'<ul class="error-list-items">',
			'<tpl for="warningMessages">',
			'<li>{[ LString.escapeHTML(values.type) ]} <strong>({size})</strong>:',
			'<tpl if="info">',
			'<span class="error-info"">{[ LString.escapeHTML(values.info) ]}</span>',
			'</tpl>',
			'</li>',
			'</tpl>',
			'</ul>',
			'</li>',
			'</tpl>',
			'</tpl>'
		];

		var TPL_UPLOAD = [
			'<div class="upload-target" id="{$ns}uploader">',
			'<div class="drag-drop-area" id="{$ns}uploaderContent">',
			'<tpl if="this.uploaderType == \'html5\'">',
			'<h4 class="drop-file-text">{[ this.dropFileText ]}<span class="or-text">{[ this.strings.orText ]}</span></h4>',
			'</tpl>',

			'<span class="select-files-container" id="{$ns}selectFilesButton">',
			'<button class="btn btn-secondary" type="button">{[ this.selectFilesText ]}</button>',
			'</span>',
			'</div>',
			'</div>',

			'<div class="hide upload-list-info" id="{$ns}listInfo">',
			'<h4>{[ this.strings.uploadsCompleteText ]}</h4>',
			'</div>',

			'<div class="alert alert-warning hide pending-files-info" role="alert">',
			'<span class="alert-indicator">',
			Liferay.Util.getLexiconIconTpl('warning'),
			'</span>',
			'<strong class="lead">{[ this.strings.warningTitle ]}</strong>{[ this.strings.pendingFileText ]}',
			'</div>',

			'<div class="float-container hide manage-upload-target" id="{$ns}manageUploadTarget">',
			'<tpl if="multipleFiles">',
			'<span class="field field-choice select-files">',
			'<span class="field-content">',
			'<span class="field-element">',
			'<input class="select-all-files" id="{$ns}allRowIds" name="{$ns}allRowIds" type="checkbox" />',
			'</span>',
			'</span>',
			'</span>',
			'</tpl>',

			'<a class="cancel-uploads hide lfr-button" href="javascript:;">{[ this.cancelUploadsText ]}</a>',
			'<a class="clear-uploads hide lfr-button" href="javascript:;">{[ this.strings.clearRecentUploadsText ]}</a>',
			'</div>',

			'<div class="upload-list" id="{$ns}fileList">',
			'<ul class="list-unstyled {[ this.multipleFiles ? "multiple-files" : "single-file" ]}" id="{$ns}fileListContent"></ul>',
			'</div>'
		];

		var UPLOADER_TYPE = A.Uploader.TYPE || 'none';

		var URL_SWF_UPLOADER =
			themeDisplay.getPathContext() +
			'/aui/uploader/assets/flashuploader.swf';

		/**
		 * OPTIONS
		 *
		 * Required
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

		var Upload = A.Component.create({
			ATTRS: {
				deleteFile: {
					value: ''
				},

				fallback: {
					setter: A.one,
					value: null
				},

				maxFileSize: {
					setter: Lang.toInt,
					value:
						Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE
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
					validator: Lang.isBoolean,
					value: true
				},

				removeOnComplete: {
					validator: Lang.isBoolean,
					value: false
				},

				render: {
					value: true
				},

				restoreState: {
					validator: Lang.isBoolean,
					value: true
				},

				rootElement: {
					setter: A.one,
					value: null
				},

				simultaneousUploads: {
					validator: Lang.isNumber,
					value: 2
				},

				strings: {
					value: {
						allFilesSelectedText: Liferay.Language.get(
							'all-files-selected'
						),
						cancelFileText: Liferay.Language.get('cancel-upload'),
						cancelUploadsText: Liferay.Language.get(
							'cancel-all-uploads'
						),
						clearRecentUploadsText: Liferay.Language.get(
							'clear-documents-already-saved'
						),
						deleteFileText: Liferay.Language.get('delete-file'),
						dropFileText: Liferay.Language.get(
							'drop-file-here-to-upload'
						),
						dropFilesText: Liferay.Language.get(
							'drop-files-here-to-upload'
						),
						fileCannotBeSavedText: Liferay.Language.get(
							'the-file-x-cannot-be-saved'
						),
						invalidFileNameText: Liferay.Language.get(
							'please-enter-a-file-with-a-valid-file-name'
						),
						invalidFileSizeText: Liferay.Language.get(
							'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
						),
						invalidUploadRequestSizeText: Liferay.Language.get(
							'request-is-larger-than-x-and-could-not-be-processed'
						),
						noFilesSelectedText: Liferay.Language.get(
							'no-files-selected'
						),
						notAvailableText: Liferay.Language.get(
							'multiple-file-uploading-is-not-available'
						),
						orText: Liferay.Language.get('or'),
						pendingFileText: Liferay.Language.get(
							'these-files-have-been-previously-uploaded-but-not-actually-saved.-please-save-or-delete-them-before-they-are-removed'
						),
						selectFileText: Liferay.Language.get('select-file'),
						selectFilesText: Liferay.Language.get('select-files'),
						unexpectedErrorOnDeleteText: Liferay.Language.get(
							'an-unexpected-error-occurred-while-deleting-the-file'
						),
						unexpectedErrorOnUploadText: Liferay.Language.get(
							'an-unexpected-error-occurred-while-uploading-your-file'
						),
						uploadingFileXofXText: Liferay.Language.get(
							'uploading-file-x-of-x'
						),
						uploadingText: Liferay.Language.get('uploading'),
						uploadsCompleteText: Liferay.Language.get(
							'all-files-ready-to-be-saved'
						),
						warningFailureText: Liferay.Language.get(
							'consider-that-the-following-data-would-not-have-been-imported-either'
						),
						warningText: Liferay.Language.get(
							'the-following-data-will-not-be-imported'
						),
						warningTitle: Liferay.Language.get('warning-colon'),
						xFilesReadyText: Liferay.Language.get(
							'x-files-ready-to-be-uploaded'
						),
						xFilesSelectedText: Liferay.Language.get(
							'x-files-selected'
						),
						zeroByteSizeText: Liferay.Language.get(
							'the-file-contains-no-data-and-cannot-be-uploaded.-please-use-the-classic-uploader'
						)
					}
				},

				tempFileURL: {
					value: ''
				},

				tempRandomSuffix: {
					validator: Lang.isString,
					value: null
				},

				uploadFile: {
					value: ''
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			NAME: 'liferayupload',

			prototype: {
				_afterFilesSaved() {
					var instance = this;

					instance._updateMetadataContainer();
					instance._updateManageUploadDisplay();
				},

				_cancelAllFiles() {
					var instance = this;

					var strings = instance.get(STRINGS);

					var uploader = instance._uploader;

					var queue = uploader.queue;

					queue.pauseUpload();

					queue.queuedFiles.forEach(item => {
						var li = A.one('#' + item.id);

						if (li && !li.hasClass('upload-complete')) {
							li.remove(true);
						}
					});

					A.all('.file-uploading').remove(true);

					queue.cancelUpload();

					uploader.queue = null;

					instance._cancelButton.hide();

					instance._filesTotal = 0;

					var cancelText = instance.get('multipleFiles')
						? strings.cancelUploadsText
						: strings.cancelFileText;

					instance._updateList(0, cancelText);
				},

				_clearUploads() {
					var instance = this;

					instance._fileListContent
						.all('.file-saved,.upload-error')
						.remove(true);

					instance._updateManageUploadDisplay();
				},

				_filesTotal: 0,

				_formatTempFiles(fileNames) {
					var instance = this;

					if (Array.isArray(fileNames) && fileNames.length) {
						var fileListContent = instance._fileListContent;

						instance._pendingFileInfo.show();
						instance._manageUploadTarget.show();

						var metadataExplanationContainer =
							instance._metadataExplanationContainer;

						if (metadataExplanationContainer) {
							metadataExplanationContainer.show();
						}

						var files = fileNames.map(item => {
							var title = item;

							var tempTitle = title;

							var tempRandomSuffix = instance.get(
								'tempRandomSuffix'
							);

							if (tempRandomSuffix) {
								var lastIndexOfPeriod = title.lastIndexOf('.');
								var posTempRandomSuffix = title.indexOf(
									tempRandomSuffix
								);

								if (posTempRandomSuffix != -1) {
									tempTitle = title.substr(
										0,
										posTempRandomSuffix
									);

									if (lastIndexOfPeriod > 0) {
										tempTitle += title.substr(
											lastIndexOfPeriod
										);
									}
								}
							}

							return {
								id: A.guid(),
								name: item,
								temp: true,
								title: tempTitle
							};
						});

						instance._fileListTPL.render(files, fileListContent);
					}
					else if (instance._allRowIdsCheckbox) {
						instance._allRowIdsCheckbox.attr('checked', true);
					}
				},

				_getValidFiles(data) {
					var instance = this;

					var strings = instance.get(STRINGS);

					var maxFileSize = instance.get('maxFileSize');
					var maxUploadRequestSize =
						Liferay.PropsValues
							.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

					return data.filter(item => {
						var id = item.get('id') || A.guid();
						var name = item.get('name');
						var size = item.get('size') || 0;

						var error;
						var file;

						if (size === 0) {
							error = strings.zeroByteSizeText;
						}
						else if (name.length > 240) {
							error = strings.invalidFileNameText;
						}
						else if (maxFileSize > 0 && size > maxFileSize) {
							error = instance._invalidFileSizeText;
						}
						else if (
							maxUploadRequestSize > 0 &&
							size > maxUploadRequestSize
						) {
							error = instance._invalidUploadRequestSize;
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
					});
				},

				_handleDeleteResponse(json, li) {
					var instance = this;

					if (!json.deleted) {
						var errorHTML = instance._fileListTPL.parse([
							{
								error: json.errorMessage,
								id: li.attr('data-fileId'),
								name: li.attr('data-fileName')
							}
						]);

						li.replace(errorHTML);
					}

					li.remove(true);

					instance._updateManageUploadDisplay();
					instance._updateMetadataContainer();
					instance._updatePendingInfoContainer();
					instance._updateWarningContainer();

					Liferay.fire('tempFileRemoved');

					instance.fire('tempFileRemoved');
				},

				_handleDrop(event) {
					var instance = this;

					event.halt();

					var uploaderBoundingBox = instance._uploaderBoundingBox;

					var target = event.target;

					var uploader = instance._uploader;

					var dataTransfer = event._event.dataTransfer;

					var dragDropFiles =
						dataTransfer && AArray(dataTransfer.files);

					if (
						dragDropFiles &&
						(target === uploaderBoundingBox ||
							uploaderBoundingBox.contains(target))
					) {
						event.fileList = dragDropFiles.map(item => {
							return new A.FileHTML5(item);
						});

						uploader.fire('fileselect', event);
					}
				},

				_handleFileClick(event) {
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

				_isUploading() {
					var instance = this;

					var queue = instance._uploader.queue;

					return !!(
						queue &&
						(queue.queuedFiles.length > 0 ||
							queue.numberOfUploads > 0 ||
							!A.Object.isEmpty(queue.currentFiles)) &&
						queue._currentState === UploaderQueue.UPLOADING
					);
				},

				_markSelected(node) {
					var fileItem = node.ancestor('.upload-file.selectable');

					fileItem.toggleClass('selected');
				},

				_onAllRowIdsClick() {
					var instance = this;

					Liferay.Util.checkAll(
						instance._fileListSelector,
						instance._selectUploadedFileCheckboxId,
						instance._allRowIdsCheckboxSelector
					);

					var uploadedFiles = instance._fileListContent.all(
						'.upload-file.upload-complete'
					);

					uploadedFiles.toggleClass(
						'selected',
						instance._allRowIdsCheckbox.attr('checked')
					);

					instance._updateMetadataContainer();
				},

				_onAllUploadsComplete() {
					var instance = this;

					var strings = instance.get(STRINGS);

					var uploader = instance._uploader;

					instance._filesTotal = 0;

					uploader.set('enabled', true);

					uploader.set('fileList', []);

					instance._cancelButton.hide();

					if (instance.get('multipleFiles')) {
						instance._clearUploadsButton.toggle(
							!!instance._fileListContent.one(
								'.file-saved,.upload-error'
							)
						);
					}

					var uploadsCompleteText;

					if (
						instance._fileListContent.one(
							'.upload-file.upload-complete'
						) &&
						instance.get('multipleFiles')
					) {
						uploadsCompleteText = strings.uploadsCompleteText;
					}

					instance._updateList(0, uploadsCompleteText);

					var removeOnComplete = instance.get('removeOnComplete');

					if (removeOnComplete) {
						instance._listInfo.one('h4').hide();

						instance._allRowIdsCheckbox.hide();
					}

					Liferay.fire('allUploadsComplete');
				},

				_onBeforeUnload(event) {
					var instance = this;

					if (instance._isUploading()) {
						event.preventDefault();
					}
				},

				_onCancelFileClick(currentTarget) {
					var instance = this;

					var strings = instance.get(STRINGS);

					var uploader = instance._uploader;

					var queue = uploader.queue;

					var li = currentTarget.ancestor('li');

					if (li) {
						if (queue) {
							var fileId = li.attr('data-fileId');

							var file =
								queue.currentFiles[fileId] ||
								AArray.find(queue.queuedFiles, item => {
									return item.id === fileId;
								});

							if (file) {
								queue.cancelUpload(file);

								instance._updateList(0, strings.cancelFileText);
							}

							if (
								queue.queuedFiles.length === 0 &&
								queue.numberOfUploads <= 0
							) {
								uploader.queue = null;

								instance._cancelButton.hide();
							}
						}

						li.remove(true);

						instance._filesTotal -= 1;
					}
				},

				_onDeleteFileClick(currentTarget) {
					var instance = this;

					var strings = instance.get(STRINGS);

					var li = currentTarget.ancestor('li');

					li.hide();

					var failureResponse = {
						errorMessage: strings.unexpectedErrorOnDeleteText
					};

					var deleteFile = instance.get('deleteFile');

					if (deleteFile) {
						var data = {
							fileName: li.attr('data-fileName')
						};

						Liferay.Util.fetch(deleteFile, {
							body: Liferay.Util.objectToFormData(
								instance.ns(data)
							),
							method: 'POST'
						})
							.then(response => response.json())
							.then(response => {
								instance._handleDeleteResponse(response, li);
							})
							.catch(() => {
								li.show();

								instance._handleDeleteResponse(
									failureResponse,
									li
								);
							});
					}
					else {
						instance._handleDeleteResponse(failureResponse, li);
					}
				},

				_onFileSelect(event) {
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

							validFiles.forEach(
								uploadQueue.addToQueueBottom,
								uploadQueue
							);
						}
						else {
							uploader.uploadAll();
						}
					}

					instance._pendingFileInfo.hide();
				},

				_onSelectFileClick(currentTarget) {
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

				_onUploadComplete(event) {
					var instance = this;

					var strings = instance.get(STRINGS);

					var file = event.file;

					var fileId = file.id;

					var li = A.one('#' + fileId);

					var data = event.data;

					var input;

					var newLiNode;

					try {
						data = JSON.parse(data);
					}
					catch (e) {}

					if (
						data.status &&
						data.status >=
							STATUS_CODE.SC_DUPLICATE_FILE_EXCEPTION &&
						data.status < STATUS_CODE.INTERNAL_SERVER_ERROR
					) {
						file.error =
							data.message || strings.unexpectedErrorOnUploadText;

						file.messageListItems = data.messageListItems;
						file.warningMessages = data.warningMessages;

						newLiNode = instance._fileListTPL.parse([file]);

						if (li) {
							li.placeBefore(newLiNode);

							li.remove(true);
						}
						else {
							instance._fileListContent.prepend(newLiNode);
						}
					}
					else {
						if (li) {
							if (data.warningMessages) {
								file.selected = true;
								file.temp = true;
								file.warningMessages = data.warningMessages;

								newLiNode = instance._fileListTPL.parse([file]);

								li.placeBefore(newLiNode);

								li.remove(true);
							}
							else if (data.name) {
								file.selected = true;
								file.temp = true;
								file.name = data.name;
								file.title = data.title;

								newLiNode = A.Node.create(
									instance._fileListTPL.parse([file])
								);

								input = newLiNode.one('input');

								if (input) {
									input.attr('checked', true);
									input.attr('disabled', false);

									input.show();
								}

								li.placeBefore(newLiNode);

								li.remove(true);
							}
							else {
								li.replaceClass(
									'file-uploading',
									'pending-file upload-complete selectable selected'
								);

								input = li.one('input');

								if (input) {
									input.attr('checked', true);

									input.show();
								}
							}

							instance._updateManageUploadDisplay();
						}

						instance._updateMetadataContainer();
					}

					var removeOnComplete = instance.get('removeOnComplete');

					if (removeOnComplete) {
						li.remove(true);
					}

					instance.fire('uploadComplete', file);
				},

				_onUploadProgress(event) {
					var progress = A.one('#' + event.file.id + 'progress');

					if (progress) {
						var percentLoaded = Math.min(
							Math.ceil(event.percentLoaded / 3) * 3,
							100
						);

						progress.setStyle('width', percentLoaded + '%');
						progress.setAttribute('aria-valuenow', percentLoaded);
					}
				},

				_onUploadStart(event) {
					var instance = this;

					var strings = instance.get(STRINGS);

					var uploader = instance._uploader;

					var queue = uploader.queue;

					var filesQueued = queue ? queue.queuedFiles.length : 0;

					var filesTotal = instance._filesTotal;

					var position = filesTotal - filesQueued;

					var currentListText;

					if (instance.get('multipleFiles')) {
						currentListText = Lang.sub(
							strings.uploadingFileXofXText,
							[position, filesTotal]
						);
					}
					else {
						currentListText = strings.uploadingText;

						instance._fileListContent
							.all('.pending-file,.upload-error')
							.remove(true);
					}

					var fileIdSelector = '#' + event.file.id;

					A.on(
						'available',
						() => {
							A.one(fileIdSelector).addClass('file-uploading');
						},
						fileIdSelector
					);

					instance._listInfo.show();

					instance._updateList(0, currentListText);
				},

				_queueFile(file) {
					var instance = this;

					instance._fileListBuffer.push(file);

					instance._renderFileListTask();
				},

				_renderControls() {
					var instance = this;

					var multipleFiles = instance.get('multipleFiles');
					var strings = instance.get(STRINGS);

					var templateConfig = {
						$ns: instance.NS,
						cancelUploadsText: multipleFiles
							? strings.cancelUploadsText
							: strings.cancelFileText,
						dropFileText: multipleFiles
							? strings.dropFilesText
							: strings.dropFileText,
						multipleFiles,
						selectFilesText: multipleFiles
							? strings.selectFilesText
							: strings.selectFileText,
						strings,
						uploaderType: UPLOADER_TYPE
					};

					instance._fileListTPL = new A.Template(
						TPL_FILE_LIST,
						templateConfig
					);

					// LPS-102399

					if (A.UA.ie) {
						instance._fileListTPL.tpls = instance._fileListTPL.tpls.map(
							tpl => {
								if (tpl.tplFn) {
									var tplBodyRegex = /function anonymous\(values,parent\s*\) \{\s*(.*)\s*\}/;
									var tplFn = tpl.tplFn.toString();

									if (tplBodyRegex.test(tplFn)) {
										var tplBody = tplBodyRegex
											.exec(tplFn)[1]
											.replace(/values/g, 'parts');

										tpl.tplFn = new Function(
											'parts, parent',
											tplBody
										);
									}
								}

								return tpl;
							}
						);
					}

					instance._selectUploadedFileCheckboxId = instance.ns(
						'selectUploadedFile'
					);

					var idNS = '#' + instance.NS;

					instance._allRowIdsCheckboxSelector = idNS + 'allRowIds';
					instance._fileListSelector = idNS + 'fileList';

					var uploadFragment = new A.Template(
						TPL_UPLOAD,
						templateConfig
					).render({
						multipleFiles
					});

					instance._allRowIdsCheckbox = uploadFragment.one(
						instance._allRowIdsCheckboxSelector
					);
					instance._manageUploadTarget = uploadFragment.one(
						idNS + 'manageUploadTarget'
					);
					instance._cancelButton = uploadFragment.one(
						'.cancel-uploads'
					);
					instance._clearUploadsButton = uploadFragment.one(
						'.clear-uploads'
					);
					instance._fileList = uploadFragment.one(
						instance._fileListSelector
					);
					instance._fileListContent = uploadFragment.one(
						idNS + 'fileListContent'
					);
					instance._listInfo = uploadFragment.one(idNS + 'listInfo');
					instance._pendingFileInfo = uploadFragment.one(
						'.pending-files-info'
					);
					instance._selectFilesButton = uploadFragment.one(
						idNS + 'selectFilesButton'
					);
					instance._uploaderBoundingBox = uploadFragment.one(
						idNS + 'uploader'
					);
					instance._uploaderContentBox = uploadFragment.one(
						idNS + 'uploaderContent'
					);

					var tempFileURL = instance.get('tempFileURL');

					if (tempFileURL && instance.get('restoreState')) {
						if (Lang.isString(tempFileURL)) {
							Liferay.Util.fetch(tempFileURL)
								.then(response => response.json())
								.then(response =>
									instance._formatTempFiles(response)
								);
						}
						else {
							tempFileURL.method(
								tempFileURL.params,
								A.bind('_formatTempFiles', instance)
							);
						}
					}

					instance._uploadFragment = uploadFragment;

					instance._cancelButton.hide();
				},

				_renderFileList() {
					var instance = this;

					var fileListBuffer = instance._fileListBuffer;
					var fileListContent = instance._fileListContent;

					var fileListHTML = instance._fileListTPL.parse(
						fileListBuffer
					);

					var firstLi = fileListContent.one('li.upload-complete');

					if (firstLi) {
						firstLi.placeBefore(fileListHTML);
					}
					else {
						fileListContent.append(fileListHTML);
					}

					fileListBuffer.length = 0;
				},

				_renderUploader() {
					var instance = this;

					var timestampParam = '_LFR_UPLOADER_TS=' + Date.now();

					var uploader = new A.Uploader({
						boundingBox: instance._uploaderBoundingBox,
						contentBox: instance._uploaderContentBox,
						fileFieldName: 'file',
						multipleFiles: instance.get('multipleFiles'),
						on: {
							render() {
								instance
									.get('boundingBox')
									.setContent(instance._uploadFragment);
							}
						},
						selectFilesButton: instance._selectFilesButton,
						simLimit: instance.get('simultaneousUploads'),
						swfURL: Liferay.Util.addParams(
							timestampParam,
							URL_SWF_UPLOADER
						),
						uploadURL: Liferay.Util.addParams(
							timestampParam,
							instance.get('uploadFile')
						)
					}).render();

					uploader.addTarget(instance);

					instance._uploader = uploader;
				},

				_updateList(listLength, message) {
					var instance = this;

					var strings = instance.get(STRINGS);

					var infoTitle = instance._listInfo.one('h4');

					if (!instance.get('multipleFiles')) {
						infoTitle.html('');
					}
					else if (infoTitle) {
						var listText =
							message ||
							Lang.sub(strings.xFilesReadyText, [listLength]);

						infoTitle.html(listText);
					}
				},

				_updateManageUploadDisplay() {
					var instance = this;

					var fileListContent = instance._fileListContent;

					var hasSavedFiles = !!fileListContent.one(
						'.file-saved,.upload-error'
					);
					var hasUploadedFiles = !!fileListContent.one(
						'.upload-complete'
					);

					if (instance._allRowIdsCheckbox) {
						instance._allRowIdsCheckbox.toggle(hasUploadedFiles);
					}

					if (instance.get('multipleFiles')) {
						instance._clearUploadsButton.toggle(hasSavedFiles);
					}

					instance._manageUploadTarget.toggle(hasUploadedFiles);

					instance._listInfo.toggle(!!fileListContent.one('li'));
				},

				_updateMetadataContainer() {
					var instance = this;

					var strings = instance.get(STRINGS);

					var metadataContainer = instance._metadataContainer;
					var metadataExplanationContainer =
						instance._metadataExplanationContainer;

					if (metadataContainer && metadataExplanationContainer) {
						var totalFiles = instance._fileList.all(
							'li input[name=' +
								instance._selectUploadedFileCheckboxId +
								']'
						);

						var totalFilesCount = totalFiles.size();

						var selectedFiles = totalFiles.filter(':checked');

						var selectedFilesCount = selectedFiles.size();

						var hasSelectedFiles = selectedFilesCount > 0;

						if (metadataContainer) {
							metadataContainer.toggle(hasSelectedFiles);

							var selectedFilesText = strings.noFilesSelectedText;

							if (hasSelectedFiles) {
								if (selectedFilesCount === 1) {
									var titleNode = selectedFiles
										.item(0)
										.ancestor('[data-title]');

									if (titleNode) {
										selectedFilesText = titleNode.attr(
											'data-title'
										);
									}
								}
								else if (
									selectedFilesCount === totalFilesCount
								) {
									selectedFilesText =
										strings.allFilesSelectedText;
								}
								else if (selectedFilesCount > 1) {
									selectedFilesText = Lang.sub(
										strings.xFilesSelectedText,
										[selectedFilesCount]
									);
								}
							}

							var selectedFilesCountContainer = metadataContainer.one(
								'.selected-files-count'
							);

							if (selectedFilesCountContainer) {
								selectedFilesCountContainer.html(
									selectedFilesText
								);

								selectedFilesCountContainer.attr(
									'title',
									selectedFilesText
								);
							}
						}

						if (metadataExplanationContainer) {
							metadataExplanationContainer.toggle(
								!hasSelectedFiles && totalFilesCount > 0
							);
						}
					}
				},

				_updatePendingInfoContainer() {
					var instance = this;

					var totalFiles = instance._fileList.all(
						'li input[name=' +
							instance._selectUploadedFileCheckboxId +
							']'
					);

					if (!totalFiles.size()) {
						instance._pendingFileInfo.hide();
					}
				},

				_updateWarningContainer() {
					var instance = this;

					var totalFiles = instance._fileList.all(
						'li input[name=' +
							instance._selectUploadedFileCheckboxId +
							']'
					);

					var warningContainer = instance._fileList.one(
						'.upload-error'
					);

					if (!totalFiles.size() && warningContainer) {
						warningContainer.hide();
					}
				},

				bindUI() {
					var instance = this;

					if (instance._allRowIdsCheckbox) {
						instance._allRowIdsCheckbox.on(
							'click',
							instance._onAllRowIdsClick,
							instance
						);
					}

					instance._cancelButton.on(
						'click',
						instance._cancelAllFiles,
						instance
					);
					instance._clearUploadsButton.on(
						'click',
						instance._clearUploads,
						instance
					);

					A.getWin().on(
						'beforeunload',
						instance._onBeforeUnload,
						instance
					);

					instance._fileList.delegate(
						'click',
						instance._handleFileClick,
						'.select-file, li .delete-button, li .cancel-button',
						instance
					);

					Liferay.after(
						'filesSaved',
						instance._afterFilesSaved,
						instance
					);

					var uploader = instance._uploader;

					uploader.after(
						'fileselect',
						instance._onFileSelect,
						instance
					);

					uploader.on(
						'alluploadscomplete',
						instance._onAllUploadsComplete,
						instance
					);
					uploader.on(
						'fileuploadstart',
						instance._onUploadStart,
						instance
					);
					uploader.on(
						'uploadcomplete',
						instance._onUploadComplete,
						instance
					);
					uploader.on(
						'uploadprogress',
						instance._onUploadProgress,
						instance
					);

					var rootElement = instance.get('rootElement');

					var docElement = rootElement
						? rootElement
						: A.getDoc().get('documentElement');

					docElement.on('drop', instance._handleDrop, instance);

					var uploaderBoundingBox = instance._uploaderBoundingBox;

					var removeCssClassTask = A.debounce(() => {
						docElement.removeClass('upload-drop-intent');
						docElement.removeClass('upload-drop-active');
					}, 500);

					docElement.on('dragover', event => {
						var originalEvent = event._event;

						var dataTransfer = originalEvent.dataTransfer;

						if (
							dataTransfer &&
							AArray.indexOf(dataTransfer.types, 'Files') > -1
						) {
							event.halt();

							docElement.addClass('upload-drop-intent');

							var target = event.target;

							var inDropArea =
								target.compareTo(uploaderBoundingBox) ||
								uploaderBoundingBox.contains(target);

							var dropEffect = 'none';

							if (inDropArea) {
								dropEffect = 'copy';
							}

							docElement.toggleClass(
								'upload-drop-active',
								inDropArea
							);

							dataTransfer.dropEffect = dropEffect;
						}

						removeCssClassTask();
					});
				},

				initializer() {
					var instance = this;

					var strings = instance.get(STRINGS);

					var fallback = instance.get('fallback');

					var useFallback =
						location.hash.indexOf(STR_PARAM_FALLBACK) > -1 &&
						fallback;

					if (
						useFallback ||
						UPLOADER_TYPE == 'none' ||
						(UPLOADER_TYPE == 'flash' &&
							!A.SWFDetect.isFlashVersionAtLeast(10, 1))
					) {
						if (fallback) {
							fallback.show();
						}
						else {
							instance
								.one('#fileUpload')
								.append(
									Lang.sub(TPL_ERROR_MESSAGE, [
										strings.notAvailableText
									])
								);
						}

						instance._preventRenderHandle = instance.on(
							'render',
							event => {
								event.preventDefault();
							}
						);
					}
					else {
						var maxFileSize = Liferay.Util.formatStorage(
							instance.get('maxFileSize')
						);

						var maxUploadRequestSize = Liferay.Util.formatStorage(
							Liferay.PropsValues
								.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE
						);

						instance._invalidFileSizeText = Lang.sub(
							strings.invalidFileSizeText,
							[maxFileSize]
						);
						instance._invalidUploadRequestSize = Lang.sub(
							strings.invalidUploadRequestSizeText,
							[maxUploadRequestSize]
						);

						instance._metadataContainer = instance.get(
							'metadataContainer'
						);
						instance._metadataExplanationContainer = instance.get(
							'metadataExplanationContainer'
						);

						instance._fileListBuffer = [];
						instance._renderFileListTask = A.debounce(
							instance._renderFileList,
							10,
							instance
						);
					}

					instance._fallback = fallback;
				},

				renderUI() {
					var instance = this;

					instance._renderControls();
					instance._renderUploader();
				}
			}
		});

		Liferay.Upload = Upload;
	},
	'',
	{
		requires: [
			'aui-template-deprecated',
			'collection',
			'liferay-portlet-base',
			'uploader'
		]
	}
);
