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

import {openImageSelector} from '../../core/openImageSelector';

function createEditor(element, changeCallback, destroyCallback, config) {
	openImageSelector(
		image => changeCallback(image && image.url ? image.url : ''),
		destroyCallback
	);
}

function destroyEditor(_element) {}

function render(element, value) {
	element.style.backgroundImage = value
		? `url("${value.url ? value.url : value}")`
		: '';
	element.style.backgroundSize = 'cover';
}

export default {
	createEditor,
	destroyEditor,
	render
};
