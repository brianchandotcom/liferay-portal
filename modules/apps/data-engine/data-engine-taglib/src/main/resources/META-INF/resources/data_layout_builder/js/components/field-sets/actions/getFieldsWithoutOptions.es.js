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
	const fieldTypesWithOptions = [
		FIELD_TYPES.grid,
		FIELD_TYPES.radio,
		FIELD_TYPES.checkboxMultiple,
		FIELD_TYPES.select,
	];

	const fieldHasOptions = (options) =>
		options[defaultLanguageId][0].edited &&
		options[defaultLanguageId][0].label;

	return dataDefinitionFields.filter((field) => {
		const {customProperties, fieldType} = field;

		return (
			fieldTypesWithOptions.includes(fieldType) &&
			(customProperties.options
				? !fieldHasOptions(customProperties.options)
				: !(
						fieldHasOptions(customProperties.columns) &&
						fieldHasOptions(customProperties.rows)
				  ))
		);
	});
}
