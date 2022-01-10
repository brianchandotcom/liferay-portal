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

import {useResource} from '@clayui/data-provider';
import {usePrevious} from '@liferay/frontend-js-react-web';
import {
	getFields,
	getObjectFieldName,
	getSelectedValue,
	useFormState,
} from 'data-engine-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo} from 'react';

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
			focusedField: {dataType, type: focusedFieldType},
			pages,
		},
	} = useFormState();

	const normalizedDataType = useMemo(() => normalizeDataType(dataType), [
		dataType,
	]);

	const options = useMemo(() => {
		const filteredObjectFields = objectFields.filter(
			({listTypeDefinitionId, relationshipType, type}) => {
				if (
					!listTypeDefinitionId &&
					(focusedFieldType === 'radio' ||
						focusedFieldType === 'select') &&
					normalizedDataType.includes(type.toLowerCase())
				) {
					return false;
				}
				else if (
					listTypeDefinitionId &&
					(focusedFieldType === 'checkbox_multiple' ||
						focusedFieldType === 'color' ||
						focusedFieldType === 'grid' ||
						focusedFieldType === 'rich_text' ||
						focusedFieldType === 'text') &&
					normalizedDataType.includes(type.toLowerCase())
				) {
					return false;
				}
				else if (focusedFieldType === 'text' && type === 'Clob') {
					return true;
				}
				else if (relationshipType) {
					return false;
				}

				return normalizedDataType.includes(type.toLowerCase());
			}
		);

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
				label: label[themeDisplay.getDefaultLanguageId()] ?? name,
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
	}, [focusedFieldType, normalizedDataType, objectFields, pages]);

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

const ObjectDefinitionObjectField = ({
	label,
	objectDefinitionId,
	onChange,
	readOnly,
	spritemap,
	value = {},
	visible,
}) => {
	const {refetch, resource} = useResource({
		fetch,
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`,
	});

	const previousObjectDefinitionId = usePrevious(objectDefinitionId);

	useEffect(() => {
		if (
			objectDefinitionId &&
			objectDefinitionId !== previousObjectDefinitionId
		) {
			refetch();
		}
	}, [objectDefinitionId, previousObjectDefinitionId, refetch]);

	const options =
		resource?.objectFields?.map(({label, name}) => {
			return {
				label: label[themeDisplay.getDefaultLanguageId()] ?? name,
				value: name,
			};
		}) || [];

	return (
		<Select
			label={label}
			name="selectedObjectField"
			onChange={onChange}
			options={options}
			readOnly={readOnly}
			showEmptyOption={!!options.length}
			spritemap={spritemap}
			value={value}
			visible={visible}
		/>
	);
};

const ObjectFieldWrapper = ({objectDefinitionId, ...props}) => {
	const {objectFields} = useFormState();

	if (!objectFields?.length) {
		if (objectDefinitionId) {
			return (
				<ObjectDefinitionObjectField
					objectDefinitionId={objectDefinitionId}
					{...props}
				/>
			);
		}

		return null;
	}

	return <ObjectField objectFields={objectFields} {...props} />;
};

export default ObjectFieldWrapper;
