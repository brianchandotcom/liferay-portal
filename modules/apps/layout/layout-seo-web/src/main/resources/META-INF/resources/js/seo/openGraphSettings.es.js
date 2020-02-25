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

export default function({namespace, uploadOpenGraphImageURL}) {
	const openGraphImageButton = document.getElementById(
		`${namespace}openGraphImageButton`
	);

	const itemSelectorDialog = new ItemSelectorDialog({
		buttonAddLabel: Liferay.Language.get('done'),
		eventName: `${namespace}openGraphImageSelectedItem`,
		singleSelect: true,
		title: Liferay.Language.get('open-graph-image'),
		url: uploadOpenGraphImageURL,
	});

	const openGraphImageFileEntryId = document.getElementById(
		`${namespace}openGraphImageFileEntryId`
	);
	const openGraphImageTitle = document.getElementById(
		`${namespace}openGraphImageTitle`
	);
	const openGraphPreviewImage = document.getElementById(
		`${namespace}openGraphPreviewImage`
	);

	const openGraphImageAltField = document.getElementById(
		`${namespace}openGraphImageAlt`
	);
	const openGraphImageAltFieldDefaultLocale = document.getElementById(
		`${namespace}openGraphImageAlt_${Liferay.ThemeDisplay.getLanguageId()}`
	);
	const openGraphImageAltLabel = document.querySelector(
		`[for="${namespace}openGraphImageAlt"`
	);

	itemSelectorDialog.on('selectedItemChange', event => {
		const selectedItem = event.selectedItem;

		if (selectedItem) {
			const itemValue = JSON.parse(selectedItem.value);

			openGraphImageFileEntryId.value = itemValue.fileEntryId;
			openGraphImageTitle.value = itemValue.title;
			openGraphPreviewImage.src = itemValue.url;

			Liferay.Util.toggleDisabled(openGraphImageAltField, false);
			Liferay.Util.toggleDisabled(
				openGraphImageAltFieldDefaultLocale,
				false
			);
			Liferay.Util.toggleDisabled(openGraphImageAltLabel, false);

			openGraphPreviewImage.classList.remove('hide');
		}
	});

	openGraphImageButton.addEventListener('click', event => {
		event.preventDefault();
		itemSelectorDialog.open();
	});

	const openGraphClearImageButton = document.getElementById(
		`${namespace}openGraphClearImageButton`
	);

	openGraphClearImageButton.addEventListener('click', () => {
		openGraphImageFileEntryId.value = '';
		openGraphImageTitle.value = '';
		openGraphPreviewImage.src = '';

		Liferay.Util.toggleDisabled(openGraphImageAltField, true);
		Liferay.Util.toggleDisabled(openGraphImageAltFieldDefaultLocale, true);
		Liferay.Util.toggleDisabled(openGraphImageAltLabel, true);

		openGraphPreviewImage.classList.add('hide');
	});

	const openGraphEnabledCheck = document.getElementById(
		`${namespace}openGraphEnabled`
	);
	const openGraphSettings = document.getElementById(
		`${namespace}openGraphSettings`
	);

	openGraphEnabledCheck.addEventListener('click', event => {
		const disabled = !event.target.checked;
		const openGraphImageAltDisabled =
			disabled || !openGraphImageTitle.value;

		Liferay.Util.toggleDisabled(openGraphImageTitle, disabled);
		Liferay.Util.toggleDisabled(openGraphImageButton, disabled);
		Liferay.Util.toggleDisabled(openGraphClearImageButton, disabled);

		Liferay.Util.toggleDisabled(
			openGraphImageAltField,
			openGraphImageAltDisabled
		);
		Liferay.Util.toggleDisabled(
			openGraphImageAltFieldDefaultLocale,
			openGraphImageAltDisabled
		);
		Liferay.Util.toggleDisabled(
			openGraphImageAltLabel,
			openGraphImageAltDisabled
		);

		openGraphSettings.classList.toggle('disabled');
	});
}
