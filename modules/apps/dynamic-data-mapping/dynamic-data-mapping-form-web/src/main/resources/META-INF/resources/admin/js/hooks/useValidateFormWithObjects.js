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
import {useCallback} from 'react';

const getObjectDefinitionId = async () => {
	const settingsDDMForm = await Liferay.componentReady('settingsDDMForm');
	const fields = settingsDDMForm?.reactComponentRef.current?.getFields();
	const getObjectDefinitionFieldName = ({fieldName}) =>
		fieldName === 'objectDefinitionId';

	return fields.find(getObjectDefinitionFieldName)?.value[0];
};

const getUnmappedFields = (fields) => {
	const getAdvancedColumn = ({title}) => title.toLowerCase() === 'advanced';
	const getObjectFieldName = ({fieldName}) => fieldName === 'objectFieldName';

	return fields.filter(({settingsContext}) => {
		const fieldsFromAdvancedColumn = settingsContext.pages.find(
			getAdvancedColumn
		).rows[0].columns[0].fields;
		const objectFieldName = fieldsFromAdvancedColumn.find(
			getObjectFieldName
		);

		return objectFieldName && !objectFieldName.value;
	});
};

/**
 * This hook returns false when there is no objectDefinitionId
 * and if any Forms field is not mapped with Object fields
 */
export const useValidateFormWithObjects = () => {
	const {pages} = useFormState();

	return useCallback(
		async (callbackFn) => {
			const objectDefinitionId = await getObjectDefinitionId();

			if (objectDefinitionId) {
				const unmappedFields = getUnmappedFields(getFields(pages));

				if (callbackFn && unmappedFields.length) {
					callbackFn(unmappedFields);
				}

				return false;
			}

			return true;
		},
		[pages]
	);
};
