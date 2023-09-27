/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isObject from './is_object';

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
		y,
	};
}
