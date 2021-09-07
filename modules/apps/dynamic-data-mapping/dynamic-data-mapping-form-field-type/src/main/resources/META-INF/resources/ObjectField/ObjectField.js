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

import {useFormState} from 'data-engine-js-components-web';
import {getFields} from 'data-engine-js-components-web/js/utils/fields.es';
import {
	getObjectFieldName,
	getSelectedValue,
} from 'data-engine-js-components-web/js/utils/objectFields';
import React, {useMemo} from 'react';

import Select from '../Select/Select.es';

const dataTypes = {
	double: ['double', 'bigdecimal'],
	image: ['blob'],
	integer: ['integer', 'long'],
};

const normalizeDataType = (type) => {
	const formattedType = type.toLowerCase();

	return dataTypes[formattedType] ?? formattedType;
};

const formatLanguageId = (languageId) => {
	return languageId.replace('_', '-');
};

const ObjectField = ({
	label,
	objectFields,
	onChange,
	readOnly,
	spritemap,
	value = {},
	visible,
}) => {
	const {
		formBuilder: {
			focusedField: {dataType},
			pages,
		},
	} = useFormState();

	const normalizedDataType = useMemo(() => normalizeDataType(dataType), [
		dataType,
	]);

	const options = useMemo(() => {
		const filteredObjectFields = objectFields.filter(({type}) => {
			return normalizedDataType.includes(type.toLowerCase());
		});

		if (filteredObjectFields.length) {
			const mappedFields = getFields(pages)
				.map((field) => {
					const objectFieldName = getObjectFieldName(field);

					return (
						objectFieldName &&
						getSelectedValue(objectFieldName.value)
					);
				})
				.filter(Boolean);

			return filteredObjectFields.map(({label, name}) => ({
				disabled: !!mappedFields.includes(name),
				label:
					label[
						formatLanguageId(themeDisplay.getDefaultLanguageId())
					] ?? name,
				value: name,
			}));
		}
		else {
			const emptyStateMessage = Liferay.Language.get(
				'there-are-no-compatible-object-fields-to-map'
			);

			return [
				{
					disabled: true,
					label: emptyStateMessage,
					value: emptyStateMessage,
				},
			];
		}
	}, [normalizedDataType, objectFields, pages]);

	return (
		<Select
			label={label}
			name="selectedObjectField"
			onChange={onChange}
			options={options}
			readOnly={readOnly}
			showEmptyOption={!!options.length}
			spritemap={spritemap}
			value={getSelectedValue(value)}
			visible={visible}
		/>
	);
};

const ObjectFieldWrapper = (props) => {
	const {objectFields} = useFormState();

	if (!objectFields?.length) {
		return null;
	}

	return <ObjectField objectFields={objectFields} {...props} />;
};

export default ObjectFieldWrapper;
