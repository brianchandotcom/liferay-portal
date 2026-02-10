/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-components-web';

import {config} from '../app/config/index';

export type SelectedImage = {
	classNameId?: number | string;
	classPK?: number | string;
	fileEntryId?: number | string;
	title?: string;
	url?: string;
};

type SelectedItem = {
	returnType: string;
	value: string;
};

type FileEntry = {
	classNameId: number | string;
	fileEntryId: number | string;
	title: string;
	url: string;
};

export function openImageSelector(
	callback: (image: SelectedImage) => void,
	destroyedCallback?: () => void
) {
	openSelectionModal<SelectedItem>({
		onClose: destroyedCallback,
		onSelect: (selectedItem) => {
			const {returnType, value} = selectedItem;

			const selectedImage: SelectedImage = {};

			if (returnType === 'URL') {
				selectedImage.title = '';
				selectedImage.url = value;
			}
			else {
				const fileEntry = JSON.parse(value) as FileEntry;

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
