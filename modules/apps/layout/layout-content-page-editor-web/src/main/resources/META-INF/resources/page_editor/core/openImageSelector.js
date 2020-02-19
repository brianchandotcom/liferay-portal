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

import {ItemSelectorDialog} from 'frontend-js-web';

import {config} from '../app/config/index';

export function openImageSelector(callback, destroyedCallback = null) {
	const itemSelectorDialog = new ItemSelectorDialog({
		eventName: `${config.portletNamespace}selectImage`,
		singleSelect: true,
		title: Liferay.Language.get('select'),
		url: config.imageSelectorURL
	});

	itemSelectorDialog.on('selectedItemChange', event => {
		const selectedItem = event.selectedItem || {};

		const {returnType, value} = selectedItem;
		const selectedImage = {};

		if (returnType === 'URL') {
			selectedImage.title = value;
			selectedImage.url = value;
		}

		if (returnType) {
			const fileEntry = JSON.parse(value);

			selectedImage.title = fileEntry.title;
			selectedImage.url = fileEntry.url;
		}

		if (selectedImage.url) {
			callback(selectedImage);
		}
	});

	itemSelectorDialog.on('visibleChange', () => {
		if (destroyedCallback) {
			destroyedCallback();
		}
	});

	itemSelectorDialog.open();
}
