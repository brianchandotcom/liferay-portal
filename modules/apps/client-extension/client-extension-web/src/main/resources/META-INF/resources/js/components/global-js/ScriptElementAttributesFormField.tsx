/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

// @ts-nocheck

import React, {useEffect, useState} from 'react';

// @ts-ignore - Check possibility to install package in ts format

import {v4 as uuidv4} from 'uuid';

import {disableFormSubmitButton} from '../../clientExtensionUtil';
import AttributeFields, {TYPE_BOOLEAN, TYPE_STRING} from './AttributeFields';

type Attribute = {
	id: string;
	name: string;
	type: string;
	value: boolean | string;
};

type PartialAttribute = {
	name?: string;
	type?: string;
	value?: string | boolean;
};

const emptyRow = () => ({id: uuidv4(), name: '', type: TYPE_STRING, value: ''});

const toJSONObjectString = (attributes: Attribute[]) => {
	const validAttributes = attributes.filter(
		(attribute) => attribute.name.trim().length
	);

	if (!validAttributes.length) {
		return '';
	}

	const attributesObject: {
		[key: string]: boolean | string;
	} = {};

	validAttributes.forEach((attribute) => {
		attributesObject[attribute.name] = attribute.value;
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

type Errors = {
	[key: string]: any;
};

export default function ScriptElementAttributesFormField({
	disabled,
	portletNamespace,
	scriptElementAttributesJSON: initialAttributes,
}: IProps) {
	const [attributes, setAttributes] = useState<Attribute[]>(() =>
		initialAttributes ? parseAttributes(initialAttributes) : []
	);
	const [errors, setErrors] = useState<Errors>({});

	const numberOfErrors = Object.keys(errors).length;

	useEffect(() => {
		disableFormSubmitButton(numberOfErrors > 0, portletNamespace);
	}, [numberOfErrors, portletNamespace]);

	const handleAddClick = () => {
		setAttributes((prevAttributes) => [...prevAttributes, emptyRow()]);
	};

	const handleAttributeChange = (
		index: number,
		updatedValue: PartialAttribute
	) => {
		setAttributes((prevAttributes) => {
			const newAttributes = [...prevAttributes];

			newAttributes[index] = {...newAttributes[index], ...updatedValue};

			return newAttributes;
		});
	};

	const handleErrorChange = (id: string, error: boolean) => {
		if (error) {
			setErrors((prevErrors) => ({...prevErrors, [id]: true}));
		}
		else {
			setErrors((prevErrors) => {
				const newErrors = {...prevErrors};

				delete newErrors[id];

				return newErrors;
			});
		}
	};

	const handleRemoveClick = (index: number) => {
		handleErrorChange(attributes[index].id, false);

		setAttributes((prevAttributes) => [
			...prevAttributes.slice(0, index),
			...prevAttributes.slice(index + 1),
		]);
	};

	return (
		<fieldset role="group">
			<legend>
				<span className="mb-0 sheet-tertiary-title">
					{Liferay.Language.get('attributes')}
				</span>
			</legend>

			<input
				disabled={disabled}
				name={`${portletNamespace}scriptElementAttributesJSON`}
				type="hidden"
				value={toJSONObjectString(attributes)}
			/>

			{attributes.map((attribute, index) => (
				<AttributeFields
					disabled={disabled}
					id={attribute.id}
					index={index}
					key={attribute.id}
					name={attribute.name}
					onAttributeChange={handleAttributeChange}
					onErrorChange={handleErrorChange}
					onRemoveClick={handleRemoveClick}
					portletNamespace={portletNamespace}
					type={attribute.type}
					value={attribute.value}
				/>
			))}

			<ClayButton displayType="secondary" onClick={handleAddClick}>
				{Liferay.Language.get('add-attribute-group')}
			</ClayButton>
		</fieldset>
	);
}
