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

import {isObject} from 'metal';

/**
 * Returns dimensions and coordinates representing a cropped region
 * @param {!Element} imagePreview Image that will be cropped
 * @param {!Object} region Object representing the coordinates which should be
 * cropped
 * @return {!Object} Object representing dimensions and coordinates of the
 * cropped image
 * @review
 */

export default function getCropRegion(imagePreview, region) {
	if (
		!isObject(imagePreview) ||
		(isObject(imagePreview) && imagePreview.tagName !== 'IMG')
	) {
		throw new TypeError('Parameter imagePreview must be an image');
	}

	if (!isObject(region)) {
		throw new TypeError('Parameter region must be an object');
	}

	const scaleX = imagePreview.naturalWidth / imagePreview.offsetWidth;
	const scaleY = imagePreview.naturalHeight / imagePreview.offsetHeight;

	const height = region.height
		? region.height * scaleY
		: imagePreview.naturalHeight;
	const width = region.width
		? region.width * scaleX
		: imagePreview.naturalWidth;

	const x = region.x ? Math.max(region.x * scaleX, 0) : 0;
	const y = region.y ? Math.max(region.y * scaleY, 0) : 0;

	return {
		height,
		width,
		x,
		y
	};
}
