/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import React, {useEffect, useState} from 'react';

import Select from '../Select/Select';

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
	const [options, setOptions] = useState([]);

	const {
		formBuilder: {focusedField, pages},
	} = useFormState();

	useEffect(() => {
		if (focusedField) {
			const normalizedDataType = normalizeDataType(focusedField.dataType);

			const filteredObjectFields = objectFields.filter(
				({
					businessType,
					listTypeDefinitionExternalReferenceCode,
					localized,
					relationshipType,
					system,
					type,
				}) => {
					if (businessType === 'AutoIncrement') {
						return false;
					}

					if (
						!listTypeDefinitionExternalReferenceCode &&
						(focusedField.type === 'radio' ||
							focusedField.type === 'select') &&
						normalizedDataType.includes(type.toLowerCase())
					) {
						return false;
					}
					if (
						listTypeDefinitionExternalReferenceCode &&
						(focusedField.type === 'checkbox_multiple' ||
							focusedField.type === 'color' ||
							focusedField.type === 'grid' ||
							focusedField.type === 'rich_text' ||
							focusedField.type === 'text') &&
						normalizedDataType.includes(type.toLowerCase())
					) {
						return false;
					}

					if (localized) {
						return false;
					}

					if (
						(focusedField.type === 'rich_text' ||
							focusedField.type === 'text') &&
						type === 'Clob'
					) {
						return true;
					}

					if (relationshipType || system) {
						return false;
					}

					if (
						businessType === 'Attachment' &&
						focusedField.type === 'document_library'
					) {
						return true;
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

				const newOptions = filteredObjectFields.map(
					({label, name}) => ({
						disabled: !!mappedFields.includes(name),
						label:
							label[themeDisplay.getDefaultLanguageId()] ?? name,
						value: name,
					})
				);

				setOptions(newOptions);
			}
			else {
				const emptyStateMessage = Liferay.Language.get(
					'there-are-no-compatible-object-fields-to-map'
				);

				setOptions([
					{
						disabled: true,
						label: emptyStateMessage,
						value: emptyStateMessage,
					},
				]);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [focusedField]);

	return (
		<Select
			label={label}
			name="selectedObjectField"
			onChange={onChange}
			options={options}
			readOnly={readOnly}
			showEmptyOption={!!options.length}
			spritemap={spritemap}
			type="select"
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
		resource?.objectFields
			?.filter(({localized}) => !localized)
			.map(({label, name}) => {
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
			type="select"
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
