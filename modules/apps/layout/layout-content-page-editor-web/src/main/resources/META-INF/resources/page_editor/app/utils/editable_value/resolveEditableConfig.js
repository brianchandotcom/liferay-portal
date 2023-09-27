/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import InfoItemService from '../../services/InfoItemService';
import resolveEditableValue from './resolveEditableValue';

export default function resolveEditableConfig(
	editableConfig,
	languageId = null,
	getFieldValue = InfoItemService.getInfoItemFieldValue
) {
	return resolveEditableValue(editableConfig, languageId, getFieldValue).then(
		(value) => {
			if (value.href || value.url || typeof value === 'string') {
				return {
					...editableConfig,
					href: value.href || value.url || value,
					rel: value.nofollow ? 'nofollow' : '',
				};
			}

			return {...editableConfig};
		}
	);
}
