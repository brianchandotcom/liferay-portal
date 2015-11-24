AUI.add(
	'liferay-image-selector',
	function(A) {
		var Lang = A.Lang;

		var CHANGE_IMAGE_CONTROLS_DELAY = 5000;

		var CSS_CHECK_ACTIVE = 'check-active';

		var CSS_DROP_ACTIVE = 'drop-active';

		var CSS_PROGRESS_ACTIVE = 'progress-active';

		var PROGRESS_HEIGHT = '6';

		var STATUS_CODE = Liferay.STATUS_CODE;

		var STR_CLICK = 'click';

		var STR_DOT = '.';

		var STR_ERROR_MESSAGE = 'errorMessage';

		var STR_IMAGE_DATA = 'imageData';

		var STR_IMAGE_DELETED = 'coverImageDeleted';

		var STR_IMAGE_SELECTED = 'coverImageSelected';

		var STR_IMAGE_UPLOADED = 'coverImageUploaded';

		var STR_SPACE = ' ';

		var STR_VALUE = 'value';

		var TPL_FILE_NAME = '<strong>{name}</strong>.{extension}';

		var TPL_PROGRESS_DATA = '<strong>{loaded}</strong> {loadedUnit} of <strong>{total}</strong> {totalUnit}';

		var ImageSelector = A.Component.create(
			{
				ATTRS: {
					errorNode: {
						validator: Lang.isString
					},

					fileEntryImageNode: {
						validator: Lang.isString
					},

					fileNameNode: {
						validator: Lang.isString,
						value: '.file-name'
					},

					itemSelectorURL: {
						validator: Lang.isString
					},

					maxFileSize: {
						setter: Lang.toInt,
						value: 0
					},

					paramName: {
						validator: Lang.isString
					},

					progressDataNode: {
						validator: Lang.isString,
						value: '.progress-data'
					},

					uploadURL: {
						validator: Lang.isString
					},

					validExtensions: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase, Liferay.StorageFormatter],

				EXTENDS: A.Base,

				NAME: 'imageselector',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._fileEntryImageNode = instance.one('#image');

						var rootNode = instance.rootNode;

						instance._fileNameNode = rootNode.one(instance.get('fileNameNode'));
						instance._progressDataNode = rootNode.one(instance.get('progressDataNode'));

						var errorNode = rootNode.one(instance.get('errorNode'));

						instance._errorNodeAlert = A.Widget.getByNode(errorNode);

						instance.set('addSpaceBeforeSuffix', true);

						instance._bindUI();
						instance._renderUploader();
					},

					destructor: function() {
						var instance = this;

						instance._uploader.destroy();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._updateImageDataFn = A.bind('_updateImageData', instance);

						instance.publish(
							STR_ERROR_MESSAGE,
							{
								defaultFn: A.bind('_showErrorMessage', instance)
							}
						);

						instance.publish(
							STR_IMAGE_DATA,
							{
								defaultFn: A.bind('_defImageDataFn', instance)
							}
						);

						instance._eventHandles = [
							instance._fileEntryImageNode.on('load', instance._onImageLoaded, instance),
							instance.rootNode.delegate(STR_CLICK, instance._onBrowseClick, '.browse-image', instance),
							instance.one('#removeImage').on(STR_CLICK, instance._onDeleteClick, instance),
							instance.one('#cancelUpload').on(STR_CLICK, instance._cancelUpload, instance)
						];
					},

					_cancelTimer: function() {
						var instance = this;

						if (instance._timer) {
							instance._timer.cancel();

							instance._timer = null;
						}
					},

					_cancelUpload: function() {
						var instance = this;

						instance._uploader.queue.cancelUpload();

						instance._stopProgress();
					},

					_createProgressBar: function() {
						var instance = this;

						var progressBar = new A.ProgressBar(
							{
								boundingBox: instance.one('.progressbar'),
								height: PROGRESS_HEIGHT
							}
						).render();

						instance._progressBar = progressBar;
					},

					_defImageDataFn: function(event) {
						var instance = this;

						var fileEntryId = event.imageData.fileEntryId;
						var fileEntryUrl = event.imageData.url;

						var rootNode = instance.rootNode;

						var fileEntryIdNode = rootNode.one('#' + instance.get('paramName') + 'Id');

						fileEntryIdNode.val(fileEntryId);

						var fileEntryImageNode = instance._fileEntryImageNode;

						fileEntryImageNode.attr('src', fileEntryUrl);

						instance._fileEntryId = fileEntryId;

						var showImageControls = fileEntryId !== 0 && fileEntryUrl !== '';

						fileEntryImageNode.toggle(showImageControls);

						var browseImageControls = instance.one('.browse-image-controls');
						var changeImageControls = instance.one('.change-image-controls');

						rootNode.toggleClass('drop-enabled', !showImageControls);

						browseImageControls.toggle(!showImageControls);

						if (!showImageControls) {
							changeImageControls.toggle(showImageControls);
						}
					},

					_onBrowseClick: function() {
						var instance = this;

						var itemSelectorDialog = new A.LiferayItemSelectorDialog(
							{
								after: {
									selectedItemChange: function(event) {
										var selectedItem = event.newVal;

										if (selectedItem) {
											instance._updateImageData(JSON.parse(selectedItem.value));

											Liferay.fire(STR_IMAGE_SELECTED);
										}
									}
								},
								eventName: instance.ns('selectImage'),
								url: instance.get('itemSelectorURL')
							}
						);

						itemSelectorDialog.open();

						instance._cancelTimer();
					},

					_onDeleteClick: function(event) {
						var instance = this;

						Liferay.fire(
							STR_IMAGE_DELETED,
							{
								imageData: null
							}
						);

						instance._updateImageData(event);
					},

					_onFileSelect: function(event) {
						var instance = this;

						instance._cancelTimer();

						instance.rootNode.removeClass(CSS_DROP_ACTIVE);

						var file = event.fileList[0];

						var fileNameNode = instance._fileNameNode;

						if (fileNameNode) {
							var filename = file.get('name');

							var fileDataTemplate = A.Lang.sub(
								TPL_FILE_NAME,
								{
									extension: filename.substring(filename.indexOf(STR_DOT) + 1),
									name: filename.substring(0, filename.indexOf(STR_DOT))
								}
							);

							fileNameNode.html(fileDataTemplate);
						}

						instance._showImagePreview(file.get('file'));

						var queue = instance._uploader.queue;

						if (queue && queue._currentState === A.Uploader.Queue.STOPPED) {
							queue.startUpload();
						}

						instance._uploader.uploadThese(event.fileList);
					},

					_onImageLoaded: function(event) {
						var instance = this;

						event.preventDefault();

						var changeImageControls = instance.one('.change-image-controls');

						var rootNode = instance.rootNode;

						rootNode.addClass(CSS_CHECK_ACTIVE);

						if (!instance._timer && instance._fileEntryId > 0) {
							instance._timer = A.later(
								CHANGE_IMAGE_CONTROLS_DELAY,
								instance,
								function() {
									rootNode.removeClass(CSS_CHECK_ACTIVE);

									changeImageControls.toggle(true);
								},
								[],
								false
							);
						}
					},

					_onUploadComplete: function(event) {
						var instance = this;

						instance._uploadCompleted = true;

						instance._stopProgress(event);

						var data = JSON.parse(event.data);

						var image = data.file;
						var success = data.success;

						var fireEvent = STR_IMAGE_DELETED;
						var imageData = null;

						if (success) {
							fireEvent = STR_IMAGE_UPLOADED;
							imageData = image;

							instance.fire(
								STR_IMAGE_DATA,
								{
									imageData: image
								}
							);
						}
						else {
							instance.fire(
								STR_ERROR_MESSAGE,
								{
									error: data.error
								}
							);
						}

						Liferay.fire(
							fireEvent,
							{
								imageData: imageData
							}
						);
					},

					_onUploadProgress: function(event) {
						var instance = this;

						var progressBar = instance._progressBar;

						if (progressBar) {
							var percentLoaded = Math.round(event.percentLoaded);

							progressBar.set(STR_VALUE, Math.ceil(percentLoaded));
						}

						var progressDataNode = instance._progressDataNode;

						if (progressDataNode) {
							var bytesLoaded = instance.formatStorage(event.bytesLoaded);
							var bytesTotal = instance.formatStorage(event.bytesTotal);

							var bytesLoadedSpaceIndex = bytesLoaded.indexOf(STR_SPACE);
							var bytesTotalSpaceIndex = bytesTotal.indexOf(STR_SPACE);

							var progressDataTemplate = A.Lang.sub(
								TPL_PROGRESS_DATA,
								{
									loaded: bytesLoaded.substring(0, bytesLoadedSpaceIndex),
									loadedUnit: bytesLoaded.substring(bytesLoadedSpaceIndex + 1),
									total: bytesTotal.substring(0, bytesTotalSpaceIndex),
									totalUnit: bytesTotal.substring(bytesTotalSpaceIndex + 1)
								}
							);

							progressDataNode.html(progressDataTemplate);
						}
					},

					_onUploadStart: function() {
						var instance = this;

						instance.rootNode.addClass(CSS_PROGRESS_ACTIVE);

						instance._errorNodeAlert.hide();

						instance._uploadCompleted = false;
					},

					_renderUploader: function() {
						var instance = this;

						instance._uploader = new A.Uploader(
							{
								boundingBox: instance.rootNode,
								dragAndDropArea: instance.rootNode,
								fileFieldName: 'imageSelectorFileName',
								on: {
									dragleave: A.bind('removeClass', instance.rootNode, CSS_DROP_ACTIVE),
									dragover: A.bind('addClass', instance.rootNode, CSS_DROP_ACTIVE),
									fileselect: A.bind('_onFileSelect', instance),
									uploadcomplete: A.bind('_onUploadComplete', instance),
									uploadprogress: A.bind('_onUploadProgress', instance),
									uploadstart: A.bind('_onUploadStart', instance)
								},
								uploadURL: instance.get('uploadURL')
							}
						).render();

						instance._createProgressBar();
					},

					_showErrorMessage: function(event) {
						var instance = this;

						instance._cancelTimer();

						var error = event.error;

						var errorType = error.errorType;

						var message = Liferay.Language.get('an-unexpected-error-occurred-while-uploading-your-file');

						if (errorType === STATUS_CODE.SC_FILE_ANTIVIRUS_EXCEPTION) {
							message = error.message;
						}
						else if (errorType === STATUS_CODE.SC_FILE_EXTENSION_EXCEPTION) {
							message = Lang.sub(Liferay.Language.get('please-enter-a-file-with-a-valid-extension-x'), [instance.get('validExtensions')]);
						}
						else if (errorType === STATUS_CODE.SC_FILE_NAME_EXCEPTION) {
							message = Liferay.Language.get('please-enter-a-file-with-a-valid-file-name');
						}
						else if (errorType === STATUS_CODE.SC_FILE_SIZE_EXCEPTION) {
							message = Lang.sub(Liferay.Language.get('please-enter-a-file-with-a-valid-file-size-no-larger-than-x'), [instance.formatStorage(instance.get('maxFileSize'))]);
						}
						else if (errorType === STATUS_CODE.SC_UPLOAD_REQUEST_SIZE_EXCEPTION) {
							var maxUploadRequestSize = Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

							message = Lang.sub(Liferay.Language.get('upload-request-is-larger-than-x-and-could-not-be-processed'), [instance.formatStorage(maxUploadRequestSize)]);
						}

						var rootNode = instance.rootNode;

						var errorWrapper = rootNode.one('.error-wrapper');

						var errorMessage = errorWrapper.one('.error-message');

						errorMessage.html(message);

						errorWrapper.show();

						rootNode.removeClass(CSS_CHECK_ACTIVE);

						var errorNodeAlert = instance._errorNodeAlert;

						errorNodeAlert.show();

						var browseImageControls = instance.one('.browse-image-controls');

						errorNodeAlert.on(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									browseImageControls.show();
								}
							}
						);

						browseImageControls.hide();
					},

					_showImagePreview: function(file) {
						var instance = this;

						if (A.config.win.FileReader) {
							var reader = new FileReader();

							reader.addEventListener(
								'loadend',
								function() {
									if (!instance._uploadCompleted) {
										instance._updateImageData(
											{
												fileEntryId: '-1',
												url: reader.result
											}
										);
									}
								}
							);

							reader.readAsDataURL(file);
						}
					},

					_stopProgress: function(event) {
						var instance = this;

						instance.rootNode.removeClass(CSS_PROGRESS_ACTIVE);

						instance._progressBar.set(STR_VALUE, 0);

						if (event) {
							instance._updateImageData(event);
						}
					},

					_updateImageData: function(imageData) {
						var instance = this;

						instance._errorNodeAlert.hide();

						instance.fire(
							STR_IMAGE_DATA,
							{
								imageData: {
									fileEntryId: imageData.fileEntryId || 0,
									url: imageData.url || ''
								}
							}
						);
					}
				}
			}
		);

		Liferay.ImageSelector = ImageSelector;
	},
	'',
	{
		requires: ['aui-base', 'aui-progressbar', 'liferay-item-selector-dialog', 'liferay-portlet-base', 'liferay-storage-formatter', 'uploader']
	}
);