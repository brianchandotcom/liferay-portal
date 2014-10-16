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
						validator: Lang.isString
					},

					imageContainerSelector: {
						validator: Lang.isString
					},

					imageSelector: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.CropRegion],

				EXTENDS: A.Plugin.Base,

				NAME: 'covercropper',

				NS: 'covercropper',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._image = host.one(instance.get('imageSelector'));
						instance._imageContainer = host.one(instance.get('imageContainerSelector'));

						var dd = new A.DD.Drag(
							{
								node: instance._image,
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

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance._image.on('load', instance._onImageUpdated, instance)
						];
					},

					_constrainDrag: function(event) {
						var instance = this;

						var direction = instance.get(STR_DIRECTION);

						var image = instance._image;

						var imageContainer = instance._imageContainer;

						var containerPos = imageContainer.getXY();

						if (direction === STR_HORIZONTAL || direction === STR_BOTH) {
							var left = containerPos[0];

							var right = left + imageContainer.width() - image.width();

							var pageX = event.pageX;

							if (pageX >= left || pageX <= right) {
								event.preventDefault();
							}
						}

						if (direction === STR_VERTICAL || direction === STR_BOTH) {
							var top = containerPos[1];

							var bottom = top + imageContainer.height() - image.height();

							var pageY = event.pageY;

							if (pageY >= top || pageY <= bottom) {
								event.preventDefault();
							}
						}
					},

					_getConstrain: function() {
						var instance = this;

						var constrain = {};

						var direction = instance.get(STR_DIRECTION);

						var imageContainer = instance._imageContainer;

						var containerPos = imageContainer.getXY();

						if (direction === STR_VERTICAL) {
							var containerX = containerPos[0];

							constrain = {
								left: containerX,
								right: containerX + imageContainer.width()
							};
						}
						else if (direction === STR_HORIZONTAL) {
							var containerY = containerPos[1];

							constrain = {
								bottom: containerY + imageContainer.height(),
								top: containerY
							};
						}

						return constrain;
					},

					_onImageUpdated: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var imageContainer = instance._imageContainer;

						var containerPos = imageContainer.getXY();

						var image = instance._image;

						var imagePos = image.getXY();

						var cropRegion = instance._getCropRegion(
							image,
							{
								height: imageContainer.height(),
								x: containerPos[0] - imagePos[0],
								y: containerPos[1] - imagePos[1]
							}
						);

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
		requires: ['aui-base', 'dd-constrain', 'dd-drag', 'liferay-crop-region', 'plugin']
	}
);