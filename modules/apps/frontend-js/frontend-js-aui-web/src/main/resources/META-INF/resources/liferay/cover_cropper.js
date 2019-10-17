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
	'liferay-cover-cropper',
	A => {
		var Lang = A.Lang;

		var STR_BOTH = 'both';

		var STR_DIRECTION = 'direction';

		var STR_HORIZONTAL = 'horizontal';

		var STR_HOST = 'host';

		var STR_VERTICAL = 'vertical';

		var CoverCropper = A.Component.create({
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

			EXTENDS: A.Plugin.Base,

			NAME: 'covercropper',

			NS: 'covercropper',

			prototype: {
				_bindUI() {
					var instance = this;

					instance._eventHandles = [
						instance._image.on(
							'load',
							instance._onImageUpdated,
							instance
						)
					];
				},

				_constrainDrag(event) {
					var instance = this;

					var direction = instance.get(STR_DIRECTION);

					var image = instance._image;

					var imageContainer = instance._imageContainer;

					var containerPos = imageContainer.getXY();

					if (
						direction === STR_HORIZONTAL ||
						direction === STR_BOTH
					) {
						var left = containerPos[0];

						var right =
							left + imageContainer.width() - image.width();

						var pageX = event.pageX;

						if (pageX >= left || pageX <= right) {
							event.preventDefault();
						}
					}

					if (direction === STR_VERTICAL || direction === STR_BOTH) {
						var top = containerPos[1];

						var bottom =
							top + imageContainer.height() - image.height();

						var pageY = event.pageY;

						if (pageY >= top || pageY <= bottom) {
							event.preventDefault();
						}
					}
				},

				_getConstrain() {
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
					} else if (direction === STR_HORIZONTAL) {
						var containerY = containerPos[1];

						constrain = {
							bottom: containerY + imageContainer.height(),
							top: containerY
						};
					}

					return constrain;
				},

				_onImageUpdated() {
					var instance = this;

					var host = instance.get(STR_HOST);

					var imageContainer = instance._imageContainer;

					var containerPos = imageContainer.getXY();

					var image = instance._image;

					var imagePos = image.getXY();

					var cropRegion = Liferay.Util.getCropRegion(image._node, {
						height: imageContainer.height(),
						x: containerPos[0] - imagePos[0],
						y: containerPos[1] - imagePos[1]
					});

					var cropRegionNode = host.rootNode.one(
						'#' + host.get('paramName') + 'CropRegion'
					);

					cropRegionNode.val(JSON.stringify(cropRegion));
				},

				destructor() {
					var instance = this;

					instance._dd.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					var host = instance.get(STR_HOST);

					instance._image = host.one(instance.get('imageSelector'));
					instance._imageContainer = host.one(
						instance.get('imageContainerSelector')
					);

					var dd = new A.DD.Drag({
						node: instance._image,
						on: {
							'drag:drag': A.bind('_constrainDrag', instance),
							'drag:end': A.bind('_onImageUpdated', instance)
						}
					}).plug(A.Plugin.DDConstrained, {
						constrain: instance._getConstrain()
					});

					instance._dd = dd;

					instance._bindUI();
				}
			}
		});

		Liferay.CoverCropper = CoverCropper;
	},
	'',
	{
		requires: ['aui-base', 'dd-constrain', 'dd-drag', 'plugin']
	}
);
