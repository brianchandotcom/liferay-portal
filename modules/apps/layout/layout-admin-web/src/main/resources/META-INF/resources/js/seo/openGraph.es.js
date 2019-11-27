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

import {PreviewSeoFireChange} from './PreviewSeoEvents.es';

export default function({namespace, uploadOpenGraphImageURL: url}) {
	var openGraphImageButton = document.getElementById(
		`${namespace}openGraphImageButton`
	);

	if (openGraphImageButton) {
		var itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('done'),
			eventName: `${namespace}openGraphImageSelectedItem`,
			title: Liferay.Language.get('open-graph-image'),
			url
		});

		var openGraphImageFileEntryId = document.getElementById(
			`${namespace}openGraphImageFileEntryId`
		);

		var openGraphImageURL = document.getElementById(
			`${namespace}openGraphImageURL`
		);

		itemSelectorDialog.on('selectedItemChange', event => {
			var selectedItem = event.selectedItem;

			if (selectedItem) {
				var itemValue = JSON.parse(selectedItem.value);

				openGraphImageFileEntryId.value = itemValue.fileEntryId;
				openGraphImageURL.value = itemValue.url;

				PreviewSeoFireChange(namespace, {
					type: 'imgUrl',
					value: itemValue.url
				});
			}
		});

		openGraphImageButton.addEventListener('click', () => {
			itemSelectorDialog.open();
		});
	}

	var openGraphClearImageButton = document.getElementById(
		`${namespace}openGraphClearImageButton`
	);

	if (openGraphClearImageButton) {
		openGraphClearImageButton.addEventListener('click', () => {
			openGraphImageFileEntryId.value = '';
			openGraphImageURL.value = '';

			PreviewSeoFireChange(namespace, {
				type: 'imgUrl',
				value: ''
			});
		});
	}

	var openGraphTitleEnabledCheck = document.getElementById(
		`${namespace}openGraphTitleEnabled`
	);
	var openGraphTitleField = document.getElementById(
		`${namespace}openGraphTitle`
	);
	var openGraphTitleFieldDefaultLocale = document.getElementById(
		`${namespace}openGraphTitle_${Liferay.ThemeDisplay.getLanguageId()}`
	);

	if (openGraphTitleEnabledCheck && openGraphTitleField) {
		openGraphTitleEnabledCheck.addEventListener('click', event => {
			Liferay.Util.toggleDisabled(
				openGraphTitleField,
				!event.target.checked
			);

			Liferay.Util.toggleDisabled(
				openGraphTitleFieldDefaultLocale,
				!event.target.checked
			);
		});
	}

	var openGraphDescriptionEnabledCheck = document.getElementById(
		`${namespace}openGraphDescriptionEnabled`
	);
	var openGraphDescriptionField = document.getElementById(
		`${namespace}openGraphDescription`
	);
	var openGraphDescriptionFieldDefaultLocale = document.getElementById(
		`${namespace}openGraphDescription_${Liferay.ThemeDisplay.getLanguageId()}`
	);

	if (openGraphDescriptionEnabledCheck && openGraphDescriptionField) {
		openGraphDescriptionEnabledCheck.addEventListener('click', event => {
			Liferay.Util.toggleDisabled(
				openGraphDescriptionField,
				!event.target.checked
			);

			Liferay.Util.toggleDisabled(
				openGraphDescriptionFieldDefaultLocale,
				!event.target.checked
			);
		});
	}
}
