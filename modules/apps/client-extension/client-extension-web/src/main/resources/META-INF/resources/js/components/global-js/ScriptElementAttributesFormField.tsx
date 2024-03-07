/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import AttributeFields, {TYPE_BOOLEAN, TYPE_STRING} from './AttributeFields';

const emptyRow = () => ({id: uuidv4(), name: '', type: TYPE_STRING, value: ''});

const toJSONObjectString = (attributes) => {
	const attributesObject = {};

	attributes.map((attribute) => {
		let value = attribute.value;

		if (attribute.type === 'Boolean') {
			value = JSON.parse(value);
		}

		if (attribute.name) {
			attributesObject[attribute.name] = value;
		}
	});

	return JSON.stringify(attributesObject);
};

interface IProps {
	attributes?: {
		name: string;
		value: boolean | string;
	}[];
	portletNamespace: string;
}

export default function ScriptElementAttributesFormField({
	attributes: initialAttributes,
	portletNamespace,
}: IProps) {
	const [attributes, settAtributes] = useState(() =>
		initialAttributes && !!initialAttributes.length
			? [
					initialAttributes.map((attribute) => ({
						...attribute,
						id: uuidv4(),
						type:
							typeof attribute.value === 'boolean'
								? TYPE_BOOLEAN
								: TYPE_STRING,
					})),
			  ]
			: [emptyRow()]
	);

	const handleAddClick = (index) => {
		settAtributes(attributes.toSpliced(index + 1, 0, emptyRow()));
	};

	const handleRemoveClick = (index) => {
		settAtributes(attributes.toSpliced(index, 1));
	};

	const updateAttributeList = (index, updatedValue) => {
		settAtributes((prevList) =>
			prevList.with(index, {...prevList[index], ...updatedValue})
		);
	};

	return (
		<>
			<input
				name={`${portletNamespace}scriptElementAttributes`}
				type="hidden"
				value={toJSONObjectString(attributes)}
			/>

			{attributes.map((item, index) => (
				<AttributeFields
					index={index}
					key={item.id}
					name={item.name}
					onAddClick={handleAddClick}
					onAttributeChange={updateAttributeList}
					onRemoveClick={handleRemoveClick}
					portletNamespace={portletNamespace}
					type={item.type}
					value={item.value}
				/>
			))}
		</>
	);
}
