AUI.add(
	'liferay-image-selector',
	function(A) {
		var Lang = A.Lang;

		var BYTE_SIZES = ['Bytes', 'KB', 'MB', 'GB', 'TB'];

		var CSS_DROP_ACTIVE = 'drop-active';

		var CSS_CHECK_ACTIVE = 'check-active';

		var CSS_PROGRESS_ACTIVE = 'progress-active';

		var PROGRESS_HEIGHT = '6';

		var STR_CLICK = 'click';

		var STR_DOT = '.';

		var STR_IMAGE_DATA = 'imageData';

		var STR_NA = 'n/a';

		var STR_VALUE = 'value';

		var TPL_FILE_NAME = '<strong>{name}</strong>.{extension}';

		var TPL_PROGRESS_DATA = '<strong>{loaded}</strong> {loadedUnit} of <strong>{total}</strong> {totalUnit}';

		var ImageSelector = A.Component.create(
			{
				ATTRS: {
					documentSelectorURL: {
						validator: Lang.isString
					},

					fileNameNode: {
						validator: Lang.isString,
						value: '#file-name'
					},

					paramName: {
						validator: Lang.isString
					},

					progressDataNode: {
						validator: Lang.isString,
						value: '#progress-data'
					},

					uploadURL: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'imageselector',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._fileEntryImageNode = instance.one('#image');

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
							STR_IMAGE_DATA,
							{
								defaultFn: A.bind('_defImageDataFn', instance)
							}
						);

						instance._eventHandles = [
							instance._fileEntryImageNode.on('load', instance._onImageLoaded, instance),
							instance.rootNode.delegate(STR_CLICK, instance._onBrowseClick, '.browse-image', instance),
							instance.one('#removeImage').on(STR_CLICK, instance._updateImageData, instance),
							instance.one('#cancelUpload').on(STR_CLICK, instance._cancelUpload, instance)
						];
					},

					_cancelUpload: function() {
						var instance = this;

						instance._uploader.queue.cancelUpload();

						instance._stopProgress();
					},

					_createProgressBar: function() {
						var instance = this;

						var progressbar = new A.ProgressBar(
							{
								boundingBox: instance.one('.progressbar'),
								height: PROGRESS_HEIGHT
							}
						).render();

						instance._progressbar = progressbar;
					},

					_defImageDataFn: function(event) {
						var instance = this;

						var fileEntryId = event.imageData.fileEntryId;
						var fileEntryUrl = event.imageData.url;

						var fileEntryIdNode = instance.rootNode.one('#' + instance.get('paramName') + 'Id');

						fileEntryIdNode.val(fileEntryId);

						instance._fileEntryImageNode.attr('src', fileEntryUrl);

						instance._fileEntryId = fileEntryId;

						instance._fileEntryUrl = fileEntryUrl;
					},

					_onBrowseClick: function() {
						var instance = this;

						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true
								},
								eventName: instance.ns('selectImage'),
								id: instance.ns('selectImage'),
								title: Liferay.Language.get('select-image'),
								uri: instance.get('documentSelectorURL')
							},
							instance._updateImageDataFn
						);
					},

					_onFileSelect: function(event) {
						var instance = this;

						var fileNameNode = instance.rootNode.one(instance.get('fileNameNode'));

						if (fileNameNode) {
							var filename = event.fileList[0].get('name');

							var fileDataTemplate = A.Lang.sub(
								TPL_FILE_NAME,
								{
									extension: filename.substring(filename.indexOf(STR_DOT) + 1),
									name: filename.substring(0, filename.indexOf(STR_DOT))
								}
							);

							fileNameNode.html(fileDataTemplate);
						}

						instance.rootNode.removeClass(CSS_DROP_ACTIVE);

						var queue = instance._uploader.queue;

						if (queue && queue._currentState === A.Uploader.Queue.STOPPED) {
							queue.startUpload();
						}

						instance._uploader.uploadThese(event.fileList);
					},

					_onImageLoaded: function() {
						var instance = this;

						var browseImageControls = instance.one('.browse-image-controls');

						var changeImageControls = instance.one('.change-image-controls');

						var showImageControls = (instance._fileEntryId !== 0 && instance._fileEntryUrl !== '');

						instance._fileEntryImageNode.toggle(showImageControls);

						browseImageControls.toggle(!showImageControls);

						instance.rootNode.toggleClass('drop-enabled', !showImageControls);

						instance.rootNode.addClass(CSS_CHECK_ACTIVE);

						setTimeout(
							function() {
								instance.rootNode.removeClass(CSS_CHECK_ACTIVE);

								changeImageControls.toggle(showImageControls);
							},
							3000
						);
					},

					_onUploadComplete: function(event) {
						var instance = this;

						instance._stopProgress(event);

						var data = event.data;

						try {
							data = A.JSON.parse(data);
						}
						catch (err) {
						}

						if (data.success) {
							instance.fire(
								STR_IMAGE_DATA,
								{
									imageData: data.image
								}
							);
						}
					},

					_onUploadProgress: function(event) {
						var instance = this;

						var progressbar = instance._progressbar;

						if (progressbar) {
							var percentLoaded = Math.round(event.percentLoaded);

							progressbar.set(STR_VALUE, Math.ceil(percentLoaded));
						}

						var progressDataNode = instance.rootNode.one(instance.get('progressDataNode'));

						if (progressDataNode) {
							var bytesLoaded = instance._parseBytesToSize(event.bytesLoaded);

							var bytesTotal = instance._parseBytesToSize(event.bytesTotal);

							var progressDataTemplate = A.Lang.sub(
								TPL_PROGRESS_DATA,
								{
									loaded: bytesLoaded.size,
									loadedUnit: bytesLoaded.unit,
									total: bytesTotal.size,
									totalUnit: bytesTotal.unit
								}
							);

							progressDataNode.html(progressDataTemplate);
						}
					},

					_parseBytesToSize: function(bytes) {
						var data = {
							size: STR_NA,
							unit: ''
						};

					    if (bytes === 0) {
					    	return data;
					    }

					    var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));

					    if (i === 0) {
					    	data.size = bytes;
					    }
					    else {
					    	data.size = (bytes / Math.pow(1024, i)).toFixed(1);
					    }

					    data.unit = BYTE_SIZES[i];

					    return data;
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
									uploadstart: A.bind('addClass', instance.rootNode, CSS_PROGRESS_ACTIVE)
								},
								uploadURL: instance.get('uploadURL')
							}
						).render();

						instance._createProgressBar();
					},

					_stopProgress: function(event) {
						var instance = this;

						instance.rootNode.removeClass(CSS_PROGRESS_ACTIVE);

						instance._progressbar.set(STR_VALUE, 0);

						if (event) {
							instance._updateImageData(event);
						}
					},

					_updateImageData: function(event) {
						var instance = this;

						instance.fire(
							STR_IMAGE_DATA,
							{
								imageData: {
									fileEntryId: event.fileentryid || 0,
									url: event.url || ''
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
		requires: ['aui-base', 'aui-progressbar', 'liferay-portlet-base', 'uploader']
	}
);