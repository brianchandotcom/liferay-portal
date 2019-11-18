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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

const getDataDefinitionField = (dataDefinition, fieldName) => {
	return dataDefinition.dataDefinitionFields.find(field => {
		return field.name === fieldName;
	}, fieldName);
};

const getFieldLabel = (dataDefinition, fieldName) => {
	const field = getDataDefinitionField(dataDefinition, fieldName);

	return field ? field.label[themeDisplay.getLanguageId()] : fieldName;
};

const getOptionLabel = (options, value) => {
	return options.reduce((result, option) => {
		if (option.value === value) {
			return option.label[themeDisplay.getLanguageId()];
		}

		return result;
	}, value);
};

const documentRenderer = value => {
	const {folderId, groupId, title} = JSON.parse(value);

	const getDocumentIcon = fileName => {
		const extension = fileName.split('.').pop();

		if (extension === 'pdf') {
			return 'document-pdf';
		}

		if (extension === 'doc' || extension === 'txt') {
			return 'document-text';
		}

		return 'document-default';
	};

	const onClick = () => {
		location.href = `${themeDisplay.getPathContext()}/documents/${groupId}/${folderId ||
			'0'}/${encodeURIComponent(title)}?download=true`;
	};

	return (
		<ClayButton.Group className="data-record-document-field">
			<ClayButton displayType="secondary" onClick={onClick}>
				<ClayIcon className="mr-2" symbol={getDocumentIcon(title)} />
				{title}
			</ClayButton>
			<ClayButtonWithIcon
				displayType="secondary"
				onClick={onClick}
				symbol="download"
			/>
		</ClayButton.Group>
	);
};

const optionsRenderer = (options, values) => (
	<ul>
		{values.map((value, index) => (
			<li key={index}>{getOptionLabel(options, value)}</li>
		))}
	</ul>
);

const stringRenderer = ({value}) => <p>{value}</p>;

const getFieldValueRenderer = dataDefinitionField => {
	const {customProperties, fieldType} = dataDefinitionField;

	if (fieldType === 'checkbox_multiple') {
		const {options} = customProperties;

		return ({value}) => optionsRenderer(options, value);
	}

	if (fieldType === 'select') {
		const {multiple, options} = customProperties;

		if (multiple) {
			return ({value}) => optionsRenderer(options, value);
		}

		return ({value}) => stringRenderer({value: value[0] || ''});
	}

	if (fieldType === 'document_library') {
		return ({value}) => documentRenderer(value);
	}

	return stringRenderer;
};

export default ({dataDefinition, dataRecordValues, fieldName}) => {
	const dataDefinitionField = getDataDefinitionField(
		dataDefinition,
		fieldName
	);
	const label = getFieldLabel(dataDefinition, fieldName);
	const value = dataRecordValues[fieldName];

	const Renderer = getFieldValueRenderer(dataDefinitionField);

	return (
		<div className="data-record-field-preview">
			<label>{label}</label>

			<Renderer value={value} />
		</div>
	);
};
