/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal, toggleDisabled} from 'frontend-js-web';

export default function ({namespace, uploadOpenGraphImageURL}) {
	const openGraphImageButton = document.getElementById(
		`${namespace}openGraphImageButton`
	);

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
		`${namespace}openGraphImageAlt_${Liferay.ThemeDisplay.getDefaultLanguageId()}`
	);
	const openGraphImageAltLabel = document.querySelector(
		`[for="${namespace}openGraphImageAlt"]`
	);

	openGraphImageButton.addEventListener('click', (event) => {
		event.preventDefault();

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					openGraphImageFileEntryId.value = itemValue.fileEntryId;
					openGraphImageTitle.value = itemValue.title;
					openGraphPreviewImage.src = itemValue.url;
					openGraphPreviewImage.classList.remove('hide');

					toggleDisabled(openGraphImageAltField, false);
					toggleDisabled(openGraphImageAltFieldDefaultLocale, false);
					toggleDisabled(openGraphImageAltLabel, false);
				}
			},
			selectEventName: `${namespace}openGraphImageSelectedItem`,
			title: Liferay.Language.get('select-image'),
			url: uploadOpenGraphImageURL,
		});
	});

	const openGraphClearImageButton = document.getElementById(
		`${namespace}openGraphClearImageButton`
	);

	openGraphClearImageButton.addEventListener('click', () => {
		openGraphImageFileEntryId.value = '';
		openGraphImageTitle.value = '';
		openGraphPreviewImage.src = '';

		toggleDisabled(openGraphImageAltField, true);
		toggleDisabled(openGraphImageAltFieldDefaultLocale, true);
		toggleDisabled(openGraphImageAltLabel, true);

		openGraphPreviewImage.classList.add('hide');
	});

	const openGraphEnabledCheck = document.getElementById(
		`${namespace}openGraphEnabled`
	);
	const openGraphSettings = document.getElementById(
		`${namespace}openGraphSettings`
	);

	openGraphEnabledCheck.addEventListener('click', (event) => {
		const disabled = !event.target.checked;
		const openGraphImageAltDisabled =
			disabled || !openGraphImageTitle.value;

		toggleDisabled(openGraphImageTitle, disabled);
		toggleDisabled(openGraphImageButton, disabled);
		toggleDisabled(openGraphClearImageButton, disabled);

		toggleDisabled(openGraphImageAltField, openGraphImageAltDisabled);
		toggleDisabled(
			openGraphImageAltFieldDefaultLocale,
			openGraphImageAltDisabled
		);
		toggleDisabled(openGraphImageAltLabel, openGraphImageAltDisabled);

		openGraphSettings.classList.toggle('disabled');
	});
}
