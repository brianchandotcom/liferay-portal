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
