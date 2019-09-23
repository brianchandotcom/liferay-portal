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

import React, {useContext} from 'react';
import FieldTypeList from '../../components/field-types/FieldTypeList.es';
import FormViewContext from './FormViewContext.es';
import {containsField} from '../../utils/dataLayoutVisitor.es';
import {DRAG_CUSTOM_OBJECT_FIELD} from '../../utils/dragTypes.es';

const getFieldTypes = ({dataDefinition, dataLayout, fieldTypes}) => {
	const {dataDefinitionFields} = dataDefinition;
	const {dataLayoutPages} = dataLayout;

	return dataDefinitionFields.map(({label, fieldType, name}) => {
		const fieldTypeSettings = fieldTypes.find(({name}) => {
			return name === fieldType;
		});

		return {
			className: 'custom-object-field',
			description: fieldTypeSettings.label,
			disabled: containsField(dataLayoutPages, name),
			dragAlignment: 'right',
			dragType: DRAG_CUSTOM_OBJECT_FIELD,
			icon: fieldTypeSettings.icon,
			label: label.en_US,
			name
		};
	});
};

export default ({keywords}) => {
	const [state] = useContext(FormViewContext);
	const fieldTypes = getFieldTypes(state);

	return <FieldTypeList fieldTypes={fieldTypes} keywords={keywords} />;
};
