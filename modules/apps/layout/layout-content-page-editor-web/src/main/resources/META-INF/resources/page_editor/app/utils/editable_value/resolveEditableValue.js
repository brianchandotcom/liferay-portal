/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ImageService from '../../services/ImageService';
import InfoItemService from '../../services/InfoItemService';
import {getEditableLocalizedValue} from '../getEditableLocalizedValue';
import isMapped from './isMapped';

export default function resolveEditableValue(
	editableValue,
	languageId = null,
	getFieldValue = InfoItemService.getInfoItemFieldValue
) {
	if (isMapped(editableValue) && getFieldValue) {
		return getFieldValue({
			...editableValue,
			editableTypeOptions: editableValue.config,
			languageId,
		}).catch(() =>
			Promise.resolve(
				getEditableLocalizedValue(
					editableValue,
					languageId,
					editableValue
				)
			)
		);
	}

	const localizedEditableValue = getEditableLocalizedValue(
		editableValue,
		languageId,
		editableValue
	);

	if (!localizedEditableValue.fileEntryId) {
		return Promise.resolve(localizedEditableValue);
	}

	return ImageService.getFileEntry({
		fileEntryId: localizedEditableValue.fileEntryId,
	})
		.then(({fileEntryURL}) => ({
			...localizedEditableValue,
			url: fileEntryURL,
		}))
		.catch(() => localizedEditableValue);
}
