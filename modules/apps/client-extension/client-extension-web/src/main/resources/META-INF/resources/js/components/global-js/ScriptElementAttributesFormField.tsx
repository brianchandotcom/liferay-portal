/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-nocheck

import React, {useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import AttributeFields, {TYPE_BOOLEAN, TYPE_STRING} from './AttributeFields';

const emptyRow = () => ({id: uuidv4(), name: '', type: TYPE_STRING, value: ''});

const toJSONObjectString = (attributes) => {
	const validAttributes = attributes.filter(
		(attribute) => attribute.name.trim().length
	);

	if (!validAttributes.length) {
		return '';
	}

	const attributesObject = {};

	validAttributes.forEach((attribute) => {
		let value = attribute.value;

		if (attribute.type === 'Boolean') {
			value = JSON.parse(value);
		}

		attributesObject[attribute.name] = value;
	});

	return JSON.stringify(attributesObject);
};

const parseAttributes = (attributes: string) => {
	const scriptElementAttributesJSONObject: {
		[key: string]: boolean | string;
	} = JSON.parse(attributes);

	return Object.keys(scriptElementAttributesJSONObject).map((key) => ({
		id: uuidv4(),
		name: key,
		type:
			typeof scriptElementAttributesJSONObject[key] === 'boolean'
				? TYPE_BOOLEAN
				: TYPE_STRING,
		value: scriptElementAttributesJSONObject[key],
	}));
};

interface IProps {
	disabled?: boolean;
	portletNamespace: string;
	scriptElementAttributesJSON?: string;
}

export default function ScriptElementAttributesFormField({
	disabled,
	portletNamespace,
	scriptElementAttributesJSON: initialAttributes,
}: IProps) {
	const [attributes, setAttributes] = useState(() =>
		initialAttributes ? parseAttributes(initialAttributes) : [emptyRow()]
	);

	const handleAddClick = (index) => {
		setAttributes(attributes.toSpliced(index + 1, 0, emptyRow()));
	};

	const handleAttributeChange = (index, updatedValue) => {
		setAttributes((prevList) =>
			prevList.with(index, {...prevList[index], ...updatedValue})
		);
	};

	const handleRemoveClick = (index) => {
		setAttributes(attributes.toSpliced(index, 1));
	};

	return (
		<>
			<input
				disabled={disabled}
				name={`${portletNamespace}scriptElementAttributesJSON`}
				type="hidden"
				value={toJSONObjectString(attributes)}
			/>

			{attributes.map((attribute, index) => (
				<AttributeFields
					disabled={disabled}
					index={index}
					key={attribute.id}
					name={attribute.name}
					onAddClick={handleAddClick}
					onAttributeChange={handleAttributeChange}
					onRemoveClick={handleRemoveClick}
					portletNamespace={portletNamespace}
					type={attribute.type}
					value={attribute.value}
				/>
			))}
		</>
	);
}
