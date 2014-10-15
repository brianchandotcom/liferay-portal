AUI.add(
	'liferay-cover-cropper',
	function(A) {
		var Lang = A.Lang;

		var STR_BOTH = 'both';

		var STR_DIRECTION = 'direction';

		var STR_HORIZONTAL = 'horizontal';

		var STR_HOST = 'host';

		var STR_VERTICAL = 'vertical';

		var CoverCropper = A.Component.create(
			{
				ATTRS: {
					direction: {
						validator: A.Lang.isString
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'covercropper',

				NS: 'covercropper',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var dd = new A.DD.Drag(
							{
								node: host._image,
								on: {
									'drag:drag': A.bind('_constrainDrag', instance),
									'drag:end': A.bind('_onImageUpdated', instance)
								}
							}
						).plug(
							A.Plugin.DDConstrained,
							{
								constrain: instance._getConstrain()
							}
						);

						instance._dd = dd;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						instance._dd.destroy();
					},

					_bindUI: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._eventHandles = [
							host._image.on('load', instance._onImageUpdated, instance)
						];
					},

					_constrainDrag: function(event) {
						var instance = this;

						var direction = instance.get(STR_DIRECTION);

						var host = instance.get(STR_HOST);

						var previewWrapper = host._imageWrapper;

						var previewPosition = previewWrapper.getXY();

						var image = host._image;

						if (direction === STR_HORIZONTAL || direction === STR_BOTH) {
							var left = previewPosition[0];

							var right = left + previewWrapper.width() - image.width();

							var pageX = event.pageX;

							if (pageX >= left || pageX <= right) {
								event.preventDefault();
							}
						}
						else if (direction === STR_VERTICAL || direction === STR_BOTH) {
							var top = previewPosition[1];

							var bottom = top + previewWrapper.height() - image.height();

							var pageY = event.pageY;

							if (pageY >= top || pageY <= bottom) {
								event.preventDefault();
							}
						}
					},

					_getConstrain: function() {
						var instance = this;

						var constrain = {};

						var host = instance.get(STR_HOST);

						var previewWrapper = host._imageWrapper;

						var previewWrapperPos = previewWrapper.getXY();

						var direction = instance.get(STR_DIRECTION);

						if (direction === STR_VERTICAL) {
							var peviewWrapperX = previewWrapperPos[0];

							constrain = {
								left: peviewWrapperX,
								right: peviewWrapperX + previewWrapper.width()
							};
						}
						else if (direction === STR_HORIZONTAL) {
							var previewWrapperY = previewWrapperPos[1];

							constrain = {
								bottom: previewWrapperY + previewWrapper.height(),
								top: previewWrapperY
							};
						}

						return constrain;
					},

					_getCropRegion: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						var imagePreview = host._image;

						var imagePreviewWrapper = host._imageWrapper;

						var naturalSize = instance._getImgNaturalSize(imagePreview);

						var scaleY = naturalSize.height / imagePreview.height();
						var scaleX = naturalSize.width / imagePreview.width();

						var posX = 0;

						if (naturalSize.width > imagePreviewWrapper.width()) {
							posX = Math.ceil((imagePreviewWrapper.getX() - imagePreview.getX()) * scaleX);
						}

						var posY = 0;

						if (naturalSize.height > imagePreviewWrapper.height()) {
							posY = Math.ceil((imagePreviewWrapper.getY() - imagePreview.getY()) * scaleY);
						}

						var cropRegion = {
							height: Math.ceil(imagePreviewWrapper.height() * scaleY),
							width: naturalSize.width,
							x: posX,
							y: posY
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

					_onImageUpdated: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var cropRegion = instance._getCropRegion();

						var cropRegionNode = host.rootNode.one('#' + host.get('paramName') + 'CropRegion');

						cropRegionNode.val(A.JSON.stringify(cropRegion));
					}
				}
			}
		);

		Liferay.CoverCropper = CoverCropper;
	},
	'',
	{
		requires: ['aui-base', 'dd-constrain', 'dd-drag', 'plugin']
	}
);