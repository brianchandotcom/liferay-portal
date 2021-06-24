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

import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import Select from '../Select/Select.es';

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

export const getURL = (path, params) => {
	params = {
		['p_auth']: Liferay.authToken,
		t: Date.now(),
		...params,
	};

	const uri = new URL(`${window.location.origin}${path}`);
	const keys = Object.keys(params);

	keys.forEach((key) => uri.searchParams.set(key, params[key]));

	return uri.toString();
};

const getItem = (endpoint) => {
	return fetch(getURL(endpoint), {
		headers: HEADERS,
		method: 'GET',
	}).then((response) => response.json());
};

const getSelectedValue = (value) => {
	return typeof value === 'string' && value !== ''
		? JSON.parse(value)
		: value;
};

const getObjectDefinitionId = (settingsDDMFormRef) => {
	const settingsDDMForm = settingsDDMFormRef.current?.reactComponentRef.current.getFields();

	return settingsDDMForm?.find(
		({fieldName}) => fieldName === 'objectDefinitionId'
	);
};

const ObjectField = ({
	label,
	onChange,
	readOnly,
	spritemap,
	value = {},
	visible,
}) => {
	const [objectFields, setObjectFields] = useState([]);

	const selectedValue = getSelectedValue(value);

	const settingsDDMFormRef = useRef(null);

	useEffect(() => {
		const getAsync = async () => {
			settingsDDMFormRef.current = await Liferay.componentReady(
				'settingsDDMForm'
			);
		};

		getAsync();
	}, [settingsDDMFormRef]);

	const objectDefinitionId = getObjectDefinitionId(settingsDDMFormRef)
		?.value[0];

	useEffect(() => {
		if (objectDefinitionId) {
			getItem(
				`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`
			).then(({objectFields}) => {
				setObjectFields(
					objectFields.map((objectField) => {
						return {
							label: objectField.name,
							value: objectField.name,
						};
					})
				);
			});
		}
	}, [objectDefinitionId]);

	return objectDefinitionId ? (
		<Select
			label={label}
			name="selectedObjectField"
			onChange={onChange}
			options={objectFields}
			placeholder={Liferay.Language.get('choose-an-option')}
			readOnly={readOnly}
			spritemap={spritemap}
			value={selectedValue}
			visible={visible}
		/>
	) : (
		<></>
	);
};

export default ObjectField;
