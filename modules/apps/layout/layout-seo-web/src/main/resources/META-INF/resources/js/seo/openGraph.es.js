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

import {openSelectionModal, toggleDisabled} from 'frontend-js-web';

import {previewSeoFireChange} from './PreviewSeoEvents.es';

export default function ({namespace, uploadOpenGraphImageURL}) {
	const openGraphImageButton = document.getElementById(
		`${namespace}openGraphImageButton`
	);
	const openGraphClearImageButton = document.getElementById(
		`${namespace}openGraphClearImageButton`
	);
	const openGraphImageInput = document.getElementById(
		`${namespace}openGraphImageInput`
	);
	const openGraphImageFileEntryId = document.getElementById(
		`${namespace}openGraphImageFileEntryId`
	);
	const openGraphImageAltField = document.getElementById(
		`${namespace}openGraphImageAlt`
	);
	const openGraphImageAltFieldDefaultLocale = document.getElementById(
		`${namespace}openGraphImageAlt_${Liferay.ThemeDisplay.getDefaultLanguageId()}`
	);
	const openGraphImageAltLabel = document.querySelector(
		`[for="${namespace}openGraphImageAlt"`
	);

	const openImageSelector = () => {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					openGraphImageFileEntryId.value = itemValue.fileEntryId;
					openGraphImageInput.value = itemValue.title;

					previewSeoFireChange(namespace, {
						type: 'imgUrl',
						value: itemValue.url,
					});

					toggleDisabled(
						[
							openGraphClearImageButton,
							openGraphImageAltField,
							openGraphImageAltFieldDefaultLocale,
							openGraphImageAltLabel,
						],
						false
					);
				}
			},
			selectEventName: `${namespace}openGraphImageSelectedItem`,
			title: Liferay.Language.get('select-image'),
			url: uploadOpenGraphImageURL,
		});
	};

	openGraphImageButton.addEventListener('click', openImageSelector);
	openGraphImageInput.addEventListener('click', openImageSelector);

	openGraphClearImageButton.addEventListener('click', () => {
		openGraphImageFileEntryId.value = '';
		openGraphImageInput.value = '';

		toggleDisabled(
			[
				openGraphClearImageButton,
				openGraphImageAltField,
				openGraphImageAltFieldDefaultLocale,
				openGraphImageAltLabel,
			],
			true
		);

		previewSeoFireChange(namespace, {
			type: 'imgUrl',
			value: '',
		});
	});

	const openGraphTitleEnabledCheck = document.getElementById(
		`${namespace}openGraphTitleEnabled`
	);
	const openGraphTitleField = document.getElementById(
		`${namespace}openGraphTitle`
	);
	const openGraphTitleFieldDefaultLocale = document.getElementById(
		`${namespace}openGraphTitle_${Liferay.ThemeDisplay.getLanguageId()}`
	);
	const openGraphTitleWrapper = document.getElementById(
		`${namespace}openGraphTitleWrapper`
	);

	openGraphTitleEnabledCheck.addEventListener('click', (event) => {
		const disabled = !event.target.checked;

		const label = openGraphTitleWrapper.querySelector('label');

		toggleDisabled(
			[openGraphTitleField, openGraphTitleFieldDefaultLocale, label],
			disabled
		);

		previewSeoFireChange(namespace, {
			disabled,
			type: 'title',
			value: openGraphTitleField.value,
		});
	});

	const openGraphDescriptionEnabledCheck = document.getElementById(
		`${namespace}openGraphDescriptionEnabled`
	);
	const openGraphDescriptionField = document.getElementById(
		`${namespace}openGraphDescription`
	);
	const openGraphDescriptionFieldDefaultLocale = document.getElementById(
		`${namespace}openGraphDescription_${Liferay.ThemeDisplay.getLanguageId()}`
	);
	const openGraphDescriptionWrapper = document.getElementById(
		`${namespace}openGraphDescriptionWrapper`
	);

	openGraphDescriptionEnabledCheck.addEventListener('click', (event) => {
		const disabled = !event.target.checked;

		const label = openGraphDescriptionWrapper.querySelector('label');

		toggleDisabled(
			[
				openGraphDescriptionField,
				openGraphDescriptionFieldDefaultLocale,
				label,
			],
			disabled
		);

		previewSeoFireChange(namespace, {
			disabled,
			type: 'description',
			value: openGraphDescriptionField.value,
		});
	});
}
