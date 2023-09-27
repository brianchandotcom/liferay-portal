/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
AUI.add(
	'liferay-image-selector',
	(A) => {
		const Lang = A.Lang;

		const CHANGE_IMAGE_CONTROLS_DELAY = 5000;

		const CSS_CHECK_ACTIVE = 'check-active';

		const CSS_DROP_ACTIVE = 'drop-active';

		const CSS_PROGRESS_ACTIVE = 'progress-active';

		const PROGRESS_HEIGHT = '6';

		const STATUS_CODE = Liferay.STATUS_CODE;

		const STR_CLICK = 'click';

		const STR_DOT = '.';

		const STR_DRAG_START = 'dragstart';

		const STR_ERROR_MESSAGE = 'errorMessage';

		const STR_IMAGE_DATA = 'imageData';

		const STR_IMAGE_DELETED = 'coverImageDeleted';

		const STR_IMAGE_SELECTED = 'coverImageSelected';

		const STR_IMAGE_UPLOADED = 'coverImageUploaded';

		const STR_SPACE = ' ';

		const STR_VALUE = 'value';

		const TPL_FILE_NAME = '<strong>{name}</strong>.{extension}';

		const TPL_PROGRESS_DATA =
			'<strong>{loaded}</strong> {loadedUnit} of <strong>{total}</strong> {totalUnit}';

		const ImageSelector = A.Component.create({
			ATTRS: {
				errorNode: {
					validator: Lang.isString,
				},

				fileEntryImageNode: {
					validator: Lang.isString,
				},

				fileNameNode: {
					validator: Lang.isString,
					value: '.file-name',
				},

				itemSelectorEventName: {
					validator: Lang.isString,
				},

				itemSelectorURL: {
					validator: Lang.isString,
				},

				maxFileSize: {
					setter: Lang.toInt,
					value:
						Liferay.PropsValues
							.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
				},

				paramName: {
					validator: Lang.isString,
				},

				progressDataNode: {
					validator: Lang.isString,
					value: '.progress-data',
				},

				uploadURL: {
					validator: Lang.isString,
				},

				validExtensions: {
					validator: Lang.isString,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'imageselector',

			prototype: {
				_bindUI() {
					const instance = this;

					instance._updateImageDataFn = A.bind(
						'_updateImageData',
						instance
					);

					instance.publish(STR_ERROR_MESSAGE, {
						defaultFn: A.bind('_showErrorMessage', instance),
					});

					instance.publish(STR_IMAGE_DATA, {
						defaultFn: A.bind('_defImageDataFn', instance),
					});

					instance._eventHandles = [
						instance._fileEntryImageNode.on(
							'load',
							instance._onImageLoaded,
							instance
						),
						instance.rootNode.delegate(
							STR_CLICK,
							instance._onBrowseClick,
							'.browse-image',
							instance
						),
						instance.rootNode.delegate(
							STR_DRAG_START,
							instance._onContentDragStart,
							'.drag-drop-label',
							instance
						),
						instance.rootNode.delegate(
							STR_DRAG_START,
							instance._onContentDragStart,
							'.file-validation-info',
							instance
						),
						instance
							.one('#removeImage')
							.on(STR_CLICK, instance._onDeleteClick, instance),
						instance
							.one('#cancelUpload')
							.on(STR_CLICK, instance._cancelUpload, instance),
					];
				},

				_cancelTimer() {
					const instance = this;

					if (instance._timer) {
						instance._timer.cancel();

						instance._timer = null;
					}
				},

				_cancelUpload() {
					const instance = this;

					instance._uploader.queue.cancelUpload();

					instance._stopProgress();
				},

				_createProgressBar() {
					const instance = this;

					const progressBar = new A.ProgressBar({
						boundingBox: instance.one('.progressbar'),
						height: PROGRESS_HEIGHT,
					}).render();

					instance._progressBar = progressBar;
				},

				_defImageDataFn(event) {
					const instance = this;

					const fileEntryId = event.imageData.fileEntryId;
					const fileEntryUrl = event.imageData.url;

					const rootNode = instance.rootNode;

					const fileEntryIdNode = rootNode.one(
						'#' + instance.get('paramName') + 'Id'
					);

					fileEntryIdNode.val(fileEntryId);

					const fileEntryImageNode = instance._fileEntryImageNode;

					fileEntryImageNode.attr('src', fileEntryUrl);

					instance._fileEntryId = fileEntryId;

					const showImageControls =
						fileEntryId !== 0 && fileEntryUrl !== '';

					fileEntryImageNode.toggle(showImageControls);

					const browseImageControls = instance.one(
						'.browse-image-controls'
					);
					const changeImageControls = instance.one(
						'.change-image-controls'
					);

					rootNode.toggleClass('drop-enabled', !showImageControls);

					browseImageControls.toggle(!showImageControls);

					if (!showImageControls) {
						changeImageControls.toggle(showImageControls);
					}
				},

				_onBrowseClick() {
					const instance = this;

					Liferay.Util.openSelectionModal({
						onSelect: (selectedItem) => {
							if (selectedItem) {
								instance._updateImageData(
									JSON.parse(selectedItem.value)
								);

								Liferay.fire(STR_IMAGE_SELECTED);
							}
						},
						selectEventName: instance.get('itemSelectorEventName'),
						title: Liferay.Language.get('select-file'),
						url: instance.get('itemSelectorURL'),
					});

					instance._cancelTimer();
				},

				_onContentDragStart(event) {
					event.preventDefault();
				},

				_onDeleteClick(event) {
					const instance = this;

					Liferay.fire(STR_IMAGE_DELETED, {
						imageData: null,
					});

					instance._updateImageData(event);
				},

				_onFileSelect(event) {
					const instance = this;

					instance._cancelTimer();

					instance.rootNode.removeClass(CSS_DROP_ACTIVE);

					const file = event.fileList[0];

					const fileNameNode = instance._fileNameNode;

					if (fileNameNode) {
						const filename = file.get('name');

						const fileDataTemplate = A.Lang.sub(TPL_FILE_NAME, {
							extension: filename.substring(
								filename.indexOf(STR_DOT) + 1
							),
							name: filename.substring(
								0,
								filename.indexOf(STR_DOT)
							),
						});

						fileNameNode.html(fileDataTemplate);
					}

					instance._showImagePreview(file.get('file'));

					const queue = instance._uploader.queue;

					if (
						queue &&
						queue._currentState === A.Uploader.Queue.STOPPED
					) {
						queue.startUpload();
					}

					instance._uploader.uploadThese(event.fileList);
				},

				_onImageLoaded(event) {
					const instance = this;

					event.preventDefault();

					const changeImageControls = instance.one(
						'.change-image-controls'
					);

					const rootNode = instance.rootNode;

					rootNode.addClass(CSS_CHECK_ACTIVE);

					if (!instance._timer && instance._fileEntryId > 0) {
						instance._timer = A.later(
							CHANGE_IMAGE_CONTROLS_DELAY,
							instance,
							() => {
								rootNode.removeClass(CSS_CHECK_ACTIVE);

								changeImageControls.toggle(true);
							},
							[],
							false
						);
					}
				},

				_onUploadComplete(event) {
					const instance = this;

					instance._uploadCompleted = true;

					instance._stopProgress(event);

					const data = JSON.parse(event.data);

					const image = data.file;
					const success = data.success;

					let fireEvent = STR_IMAGE_DELETED;
					let imageData = null;

					if (success) {
						fireEvent = STR_IMAGE_UPLOADED;
						imageData = image;

						instance.fire(STR_IMAGE_DATA, {
							imageData: image,
						});
					}
					else {
						instance.fire(STR_ERROR_MESSAGE, {
							error: data.error,
						});
					}

					Liferay.fire(fireEvent, {
						imageData,
					});
				},

				_onUploadProgress(event) {
					const instance = this;

					const progressBar = instance._progressBar;

					if (progressBar) {
						const percentLoaded = Math.round(event.percentLoaded);

						progressBar.set(STR_VALUE, Math.ceil(percentLoaded));
					}

					const progressDataNode = instance._progressDataNode;

					if (progressDataNode) {
						const bytesLoaded = Liferay.Util.formatStorage(
							event.bytesLoaded
						);
						const bytesTotal = Liferay.Util.formatStorage(
							event.bytesTotal
						);

						const bytesLoadedSpaceIndex = bytesLoaded.indexOf(
							STR_SPACE
						);
						const bytesTotalSpaceIndex = bytesTotal.indexOf(
							STR_SPACE
						);

						const progressDataTemplate = A.Lang.sub(
							TPL_PROGRESS_DATA,
							{
								loaded: bytesLoaded.substring(
									0,
									bytesLoadedSpaceIndex
								),
								loadedUnit: bytesLoaded.substring(
									bytesLoadedSpaceIndex + 1
								),
								total: bytesTotal.substring(
									0,
									bytesTotalSpaceIndex
								),
								totalUnit: bytesTotal.substring(
									bytesTotalSpaceIndex + 1
								),
							}
						);

						progressDataNode.html(progressDataTemplate);
					}
				},

				_onUploadStart() {
					const instance = this;

					instance.rootNode.addClass(CSS_PROGRESS_ACTIVE);

					instance._errorNodeAlert.hide();

					instance._uploadCompleted = false;
				},

				_renderUploader() {
					const instance = this;

					instance._uploader = new A.Uploader({
						boundingBox: instance.rootNode,
						dragAndDropArea: instance.rootNode,
						fileFieldName: 'imageSelectorFileName',
						on: {
							dragleave: A.bind(
								'removeClass',
								instance.rootNode,
								CSS_DROP_ACTIVE
							),
							dragover: A.bind(
								'addClass',
								instance.rootNode,
								CSS_DROP_ACTIVE
							),
							fileselect: A.bind('_onFileSelect', instance),
							uploadcomplete: A.bind(
								'_onUploadComplete',
								instance
							),
							uploadprogress: A.bind(
								'_onUploadProgress',
								instance
							),
							uploadstart: A.bind('_onUploadStart', instance),
						},
						uploadURL: instance.get('uploadURL'),
					}).render();

					instance._createProgressBar();
				},

				_showErrorMessage(event) {
					const instance = this;

					instance._cancelTimer();

					const error = event.error;

					const errorType = error.errorType;

					let message = Liferay.Language.get(
						'an-unexpected-error-occurred-while-uploading-your-file'
					);

					if (
						errorType === STATUS_CODE.SC_FILE_ANTIVIRUS_EXCEPTION ||
						errorType === STATUS_CODE.SC_FILE_CUSTOM_EXCEPTION
					) {
						message = error.message;
					}
					else if (
						errorType === STATUS_CODE.SC_FILE_EXTENSION_EXCEPTION
					) {
						if (instance.get('validExtensions')) {
							message = Lang.sub(
								Liferay.Language.get(
									'please-enter-a-file-with-a-valid-extension-x'
								),
								[instance.get('validExtensions')]
							);
						}
						else {
							message = Lang.sub(
								Liferay.Language.get(
									'please-enter-a-file-with-a-valid-file-type'
								)
							);
						}
					}
					else if (
						errorType === STATUS_CODE.SC_FILE_NAME_EXCEPTION
					) {
						message = Liferay.Language.get(
							'please-enter-a-file-with-a-valid-file-name'
						);
					}
					else if (
						errorType === STATUS_CODE.SC_FILE_SIZE_EXCEPTION
					) {
						message = Lang.sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
							),
							[
								Liferay.Util.formatStorage(
									instance.get('maxFileSize')
								),
							]
						);
					}
					else if (
						errorType ===
						STATUS_CODE.SC_UPLOAD_REQUEST_SIZE_EXCEPTION
					) {
						const maxUploadRequestSize =
							Liferay.PropsValues
								.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

						message = Lang.sub(
							Liferay.Language.get(
								'request-is-larger-than-x-and-could-not-be-processed'
							),
							[Liferay.Util.formatStorage(maxUploadRequestSize)]
						);
					}

					const rootNode = instance.rootNode;

					const errorWrapper = rootNode.one('.error-wrapper');

					const errorMessage = errorWrapper.one('.error-message');

					errorMessage.html(message);

					errorWrapper.show();

					rootNode.removeClass(CSS_CHECK_ACTIVE);

					const errorNodeAlert = instance._errorNodeAlert;

					errorNodeAlert.show();

					const browseImageControls = instance.one(
						'.browse-image-controls'
					);

					errorNodeAlert.on('visibleChange', (event) => {
						if (!event.newVal) {
							browseImageControls.show();
						}
					});

					browseImageControls.hide();
				},

				_showImagePreview(file) {
					const instance = this;

					if (A.config.win.FileReader) {
						const reader = new FileReader();

						reader.addEventListener('loadend', () => {
							if (!instance._uploadCompleted) {
								instance._updateImageData({
									fileEntryId: '-1',
									url: reader.result,
								});
							}
						});

						reader.readAsDataURL(file);
					}
				},

				_stopProgress(event) {
					const instance = this;

					instance.rootNode.removeClass(CSS_PROGRESS_ACTIVE);

					instance._progressBar.set(STR_VALUE, 0);

					if (event) {
						instance._updateImageData(event);
					}
				},

				_updateImageData(imageData) {
					const instance = this;

					instance._errorNodeAlert.hide();

					instance.fire(STR_IMAGE_DATA, {
						imageData: {
							fileEntryId: imageData.fileEntryId || 0,
							url: imageData.url || '',
						},
					});
				},

				destructor() {
					const instance = this;

					instance._uploader.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					instance._fileEntryImageNode = instance.one('#image');

					const rootNode = instance.rootNode;

					instance._fileNameNode = rootNode.one(
						instance.get('fileNameNode')
					);
					instance._progressDataNode = rootNode.one(
						instance.get('progressDataNode')
					);

					const errorNode = rootNode.one(instance.get('errorNode'));

					instance._errorNodeAlert = A.Widget.getByNode(errorNode);

					instance.set('addSpaceBeforeSuffix', true);

					instance._bindUI();
					instance._renderUploader();
				},
			},
		});

		Liferay.ImageSelector = ImageSelector;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-progressbar',
			'liferay-portlet-base',
			'uploader',
		],
	}
);
