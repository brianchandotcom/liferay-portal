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
	(A) => {
		const Lang = A.Lang;

		const STR_BOTH = 'both';

		const STR_DIRECTION = 'direction';

		const STR_HORIZONTAL = 'horizontal';

		const STR_HOST = 'host';

		const STR_VERTICAL = 'vertical';

		const CoverCropper = A.Component.create({
			ATTRS: {
				direction: {
					validator: Lang.isString,
				},

				imageContainerSelector: {
					validator: Lang.isString,
				},

				imageSelector: {
					validator: Lang.isString,
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'covercropper',

			NS: 'covercropper',

			prototype: {
				_bindUI() {
					const instance = this;

					instance._eventHandles = [
						instance._image.on(
							'load',
							instance._onImageUpdated,
							instance
						),
					];
				},

				_constrainDrag(event) {
					const instance = this;

					const direction = instance.get(STR_DIRECTION);

					const image = instance._image;

					const imageContainer = instance._imageContainer;

					const containerPos = imageContainer.getXY();

					if (
						direction === STR_HORIZONTAL ||
						direction === STR_BOTH
					) {
						const left = containerPos[0];

						const right =
							left + imageContainer.width() - image.width();

						const pageX = event.pageX;

						if (pageX >= left || pageX <= right) {
							event.preventDefault();
						}
					}

					if (direction === STR_VERTICAL || direction === STR_BOTH) {
						const top = containerPos[1];

						const bottom =
							top + imageContainer.height() - image.height();

						const pageY = event.pageY;

						if (pageY >= top || pageY <= bottom) {
							event.preventDefault();
						}
					}
				},

				_getConstrain() {
					const instance = this;

					let constrain = {};

					const direction = instance.get(STR_DIRECTION);

					const imageContainer = instance._imageContainer;

					const containerPos = imageContainer.getXY();

					if (direction === STR_VERTICAL) {
						const containerX = containerPos[0];

						constrain = {
							left: containerX,
							right: containerX + imageContainer.width(),
						};
					}
					else if (direction === STR_HORIZONTAL) {
						const containerY = containerPos[1];

						constrain = {
							bottom: containerY + imageContainer.height(),
							top: containerY,
						};
					}

					return constrain;
				},

				_onImageUpdated() {
					const instance = this;

					const host = instance.get(STR_HOST);

					const imageContainer = instance._imageContainer;

					const containerPos = imageContainer.getXY();

					const image = instance._image;

					const imagePos = image.getXY();

					const cropRegion = Liferay.Util.getCropRegion(image._node, {
						height: imageContainer.height(),
						x: containerPos[0] - imagePos[0],
						y: containerPos[1] - imagePos[1],
					});

					const cropRegionNode = host.rootNode.one(
						'#' + host.get('paramName') + 'CropRegion'
					);

					cropRegionNode.val(JSON.stringify(cropRegion));
				},

				destructor() {
					const instance = this;

					instance._dd.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					const host = instance.get(STR_HOST);

					instance._image = host.one(instance.get('imageSelector'));
					instance._imageContainer = host.one(
						instance.get('imageContainerSelector')
					);

					const dd = new A.DD.Drag({
						node: instance._image,
						on: {
							'drag:drag': A.bind('_constrainDrag', instance),
							'drag:end': A.bind('_onImageUpdated', instance),
						},
					}).plug(A.Plugin.DDConstrained, {
						constrain: instance._getConstrain(),
					});

					instance._dd = dd;

					instance._bindUI();
				},
			},
		});

		Liferay.CoverCropper = CoverCropper;
	},
	'',
	{
		requires: ['aui-base', 'dd-constrain', 'dd-drag', 'plugin'],
	}
);
