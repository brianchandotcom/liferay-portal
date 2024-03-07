/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FIELD_TYPES} from '../../../utils/constants.es';

export default function getFieldsWithoutOptions(
	dataDefinitionFields,
	defaultLanguageId
) {
	const hasValidOption = (options) =>
		options.some(({label, value}) => label && value);

	return dataDefinitionFields.filter(({customProperties, fieldType}) => {
		switch (fieldType) {
			case FIELD_TYPES.radio:
			case FIELD_TYPES.checkboxMultiple:
			case FIELD_TYPES.select: {
				return !hasValidOption(
					customProperties.options[defaultLanguageId]
				);
			}
			case FIELD_TYPES.grid: {
				return !(
					hasValidOption(
						customProperties.columns[defaultLanguageId]
					) &&
					hasValidOption(customProperties.rows[defaultLanguageId])
				);
			}
			default: {
				return false;
			}
		}
	});
}
