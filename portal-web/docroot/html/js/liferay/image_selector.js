AUI.add(
	'liferay-image-selector',
	function(A) {
		var Lang = A.Lang;

		var CSS_DROP_ACTIVE = 'drop-active';

		var STR_CLICK = 'click';

		var STR_IMAGE_DATA = 'imageData';

		var ImageSelector = A.Component.create(
			{
				ATTRS: {
					documentSelectorURL: {
						validator: Lang.isString
					},

					draggableImage: {
						validator: Lang.isString
					},

					paramName: {
						validator: Lang.isString
					},

					uploadURL: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'imageselector',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._image = instance.one('#image');
						instance._imageWrapper = instance.one('.image-wrapper');

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
							instance.rootNode.delegate(STR_CLICK, instance._onBrowseClick, '.browse-image', instance),
							instance.one('#image').on('load', instance._onImageUpdated, instance),
							instance.one('#removeImage').on(STR_CLICK, instance._updateImageData, instance)
						];
					},

					_constrainHorizontal: function(event) {
						var pageX = event.pageX;

						var instance = this;

						var previewWrapper = instance._imageWrapper;

						var left = previewWrapper.getX();

						var right = left + previewWrapper.width() - instance._image.width();

						if (pageX >= left || pageX <= right) {
							event.preventDefault();
						}
					},

					_constrainVertical: function(event) {
						var pageY = event.pageY;

						var instance = this;

						var previewWrapper = instance._imageWrapper;

						var top = previewWrapper.getY();

						var bottom = top + previewWrapper.height() - instance._image.height();

						if (pageY >= top || pageY <= bottom) {
							event.preventDefault();
						}
					},

					_defImageDataFn: function(event) {
						var instance = this;

						var fileEntryIdNode = instance.rootNode.one('#' + instance.get('paramName') + 'Id');

						var fileEntryImage = instance.one('#image');

						var browseImageControls = instance.one('.browse-image-controls');
						var changeImageControls = instance.one('.change-image-controls');

						var fileEntryId = event.imageData.fileEntryId;
						var fileEntryUrl = event.imageData.url;

						fileEntryIdNode.val(fileEntryId);

						fileEntryImage.attr('src', fileEntryUrl);

						var showImageControls = (fileEntryId !== 0 && fileEntryUrl !== '');

						fileEntryImage.toggle(showImageControls);

						changeImageControls.toggle(showImageControls);
						browseImageControls.toggle(!showImageControls);

						instance.rootNode.toggleClass('drop-enabled', !showImageControls);
					},

					_getCropRegion: function() {
						var instance = this;

						var imagePreview = instance._image;

						var imagePreviewWrapper = instance._imageWrapper;

						var naturalSize = instance._getImgNaturalSize(imagePreview);

						var scaleY = naturalSize.height / imagePreview.height();
						var scaleX = naturalSize.width / imagePreview.width();

						var cropRegion = {
							height: Math.ceil(imagePreviewWrapper.height() * scaleY),
							width: naturalSize.width,
							x: Math.ceil((imagePreviewWrapper.getX() - imagePreview.getX()) * scaleX),
							y: Math.ceil((imagePreviewWrapper.getY() - imagePreview.getY()) * scaleY)
						};

						return cropRegion;
					},

					_getImgNaturalSize: function(img) {
						var imageHeight = img.get('naturalHeight');
						var imageWidth = img.get('naturalWidth');

						if (Lang.isUndefined(imageHeight) || Lang.isUndefined(imageWidth)) {
							var tmp = new Image();

							tmp.src = img.attr('src');

							imageHeight = tmp.height;
							imageWidth = tmp.width;
						}

						return {
							height: imageHeight,
							width: imageWidth
						};
					},

					_initDD: function() {
						var instance = this;

						var dd = instance._dd;

						if (!dd) {
							var previewWrapper = instance._imageWrapper;

							var constrain = {};

							var draggableImage = instance.get('draggableImage');

							if (draggableImage === 'vertical') {
								constrain = {
									left: previewWrapper.getX(),
									right: previewWrapper.getX() + previewWrapper.width()
								};
							}

							if (draggableImage === 'horizontal') {
								constrain = {
									top: previewWrapper.getY(),
									bottom: previewWrapper.getY() + previewWrapper.height()
								};
							}

							dd = new A.DD.Drag(
								{
									node: instance._image,
									on: {
										'drag:drag': function(event) {
											var draggableImage = instance.get('draggableImage');

											if ((draggableImage === 'horizontal') || (draggableImage === 'both')) {
												instance._constrainHorizontal(event);
											}

											if ((draggableImage === 'vertical') || (draggableImage === 'both')) {
												instance._constrainVertical(event);
											}
										},
										'drag:end': A.bind(instance._onImageUpdated, instance)
									}
								}
							).plug(
								A.Plugin.DDConstrained,
								{
									constrain: constrain
								}
							);

							instance._dd = dd;
						}
					},

					_onImageUpdated: function(event) {
						var instance = this;

						if (instance.get('draggableImage') !== 'none') {
							var cropRegion = instance._getCropRegion();

							var cropRegionNode = instance.rootNode.one('#' + instance.get('paramName') + 'CropRegion');

							cropRegionNode.val(A.JSON.stringify(cropRegion));
						}
					},

					_onBrowseClick: function(event) {
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

						instance.rootNode.removeClass(CSS_DROP_ACTIVE);

						instance._uploader.uploadAll();
					},

					_onUploadComplete: function(event) {
						var instance = this;

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

							if (instance.get('draggableImage') !== 'none') {
								instance._initDD();
							}
						}
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
									uploadcomplete: A.bind('_onUploadComplete', instance)
								},
								uploadURL: instance.get('uploadURL')
							}
						).render();
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

						if (instance.get('draggableImage') !== 'none') {
							instance._initDD();
						}
					}
				}
			}
		);

		Liferay.ImageSelector = ImageSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base', 'uploader']
	}
);