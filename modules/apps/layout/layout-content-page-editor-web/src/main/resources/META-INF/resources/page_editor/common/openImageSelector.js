/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

import {config} from '../app/config/index';

export function openImageSelector(callback, destroyedCallback = null) {
	openSelectionModal({
		onClose: destroyedCallback,
		onSelect: (selectedItem) => {
			const {returnType, value} = selectedItem;

			const selectedImage = {};

			if (returnType === 'URL') {
				selectedImage.title = '';
				selectedImage.url = value;
			}
			else {
				const fileEntry = JSON.parse(value);

				selectedImage.classNameId = fileEntry.classNameId;
				selectedImage.classPK = fileEntry.fileEntryId;
				selectedImage.fileEntryId = fileEntry.fileEntryId;
				selectedImage.title = fileEntry.title;
				selectedImage.url = fileEntry.url;
			}

			callback(selectedImage);
		},
		selectEventName: `${config.portletNamespace}selectImage`,
		title: Liferay.Language.get('select'),
		url: config.imageSelectorURL,
	});
}
