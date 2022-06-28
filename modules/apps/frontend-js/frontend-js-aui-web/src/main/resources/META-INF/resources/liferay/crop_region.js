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

/**
 * The Crop Region Utility
 *
 * @deprecated As of Athanasius (7.3.x), replaced by Liferay.Util.getCropRegion
 * @module liferay-crop-region
 */

AUI.add(
	'liferay-crop-region',
	(A) => {
		const Lang = A.Lang;

		const CropRegion = function () {};

		CropRegion.prototype = {
			_getCropRegion(imagePreview, region) {
				const instance = this;
				let cropRegion;

				if (Liferay.Util.getCropRegion) {
					cropRegion = Liferay.Util.getCropRegion(
						imagePreview,
						region
					);
				}
				else {
					const naturalSize = instance._getImgNaturalSize(
						imagePreview
					);

					const scaleX = naturalSize.width / imagePreview.width();
					const scaleY = naturalSize.height / imagePreview.height();

					const regionHeight = region.height
						? region.height * scaleY
						: naturalSize.height;
					const regionWidth = region.width
						? region.width * scaleX
						: naturalSize.width;

					const regionX = region.x
						? Math.max(region.x * scaleX, 0)
						: 0;
					const regionY = region.y
						? Math.max(region.y * scaleY, 0)
						: 0;

					cropRegion = {
						height: regionHeight,
						width: regionWidth,
						x: regionX,
						y: regionY,
					};
				}

				return cropRegion;
			},

			_getImgNaturalSize(image) {
				let imageHeight = image.get('naturalHeight');
				let imageWidth = image.get('naturalWidth');

				if (
					Lang.isUndefined(imageHeight) ||
					Lang.isUndefined(imageWidth)
				) {
					const tmp = new Image();

					tmp.src = image.attr('src');

					imageHeight = tmp.height;
					imageWidth = tmp.width;
				}

				return {
					height: imageHeight,
					width: imageWidth,
				};
			},
		};

		Liferay.CropRegion = CropRegion;
	},
	'',
	{
		requires: ['aui-base'],
	}
);
