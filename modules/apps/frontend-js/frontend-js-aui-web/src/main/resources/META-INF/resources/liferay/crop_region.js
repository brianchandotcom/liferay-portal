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
 * @deprecated As of Athanasius(7.3.x), replaced by Liferay.Util.getCropRegion
 * @module liferay-crop-region
 */

AUI.add(
	'liferay-crop-region',
	A => {
		var Lang = A.Lang;

		var CropRegion = function() {};

		CropRegion.prototype = {
			_getCropRegion(imagePreview, region) {
				var instance = this;
				var cropRegion;

				if (Liferay.Util.getCropRegion) {
					cropRegion = Liferay.Util.getCropRegion(
						imagePreview,
						region
					);
				} else {
					var naturalSize = instance._getImgNaturalSize(imagePreview);

					var scaleX = naturalSize.width / imagePreview.width();
					var scaleY = naturalSize.height / imagePreview.height();

					var regionHeight = region.height
						? region.height * scaleY
						: naturalSize.height;
					var regionWidth = region.width
						? region.width * scaleX
						: naturalSize.width;

					var regionX = region.x ? Math.max(region.x * scaleX, 0) : 0;
					var regionY = region.y ? Math.max(region.y * scaleY, 0) : 0;

					cropRegion = {
						height: regionHeight,
						width: regionWidth,
						x: regionX,
						y: regionY
					};
				}

				return cropRegion;
			},

			_getImgNaturalSize(img) {
				var imageHeight = img.get('naturalHeight');
				var imageWidth = img.get('naturalWidth');

				if (
					Lang.isUndefined(imageHeight) ||
					Lang.isUndefined(imageWidth)
				) {
					var tmp = new Image();

					tmp.src = img.attr('src');

					imageHeight = tmp.height;
					imageWidth = tmp.width;
				}

				return {
					height: imageHeight,
					width: imageWidth
				};
			}
		};

		Liferay.CropRegion = CropRegion;
	},
	'',
	{
		requires: ['aui-base']
	}
);
