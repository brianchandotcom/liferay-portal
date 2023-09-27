/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {EVENT_TYPES} from '../custom/form/eventTypes';

const HEADERS = {
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

const getURL = (path, params) => {
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

const fetchObjectFields = (objectDefinitionId) => {
	const pathContext = themeDisplay.getPathContext();

	return fetch(
		getURL(
			pathContext +
				`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`
		),
		{
			headers: HEADERS,
			method: 'GET',
		}
	).then((response) => response.json());
};

export function getFieldsGroupedByTypes(fields) {
	const types = fields.map(({type}) => type);
	const uniqueTypes = types.filter(
		(type, index) => types.indexOf(type) === index
	);

	return uniqueTypes.map((uniqueTypes) => {
		return {
			fields: fields.filter(({type}) => uniqueTypes === type),
			type: uniqueTypes,
		};
	});
}

export async function addObjectFields(dispatch) {
	const settingsDDMForm = await Liferay.componentReady('formSettingsAPI');
	const objectDefinitionId = settingsDDMForm.reactComponentRef.current.getObjectDefinitionId();

	if (objectDefinitionId) {
		const {objectFields} = await fetchObjectFields(objectDefinitionId);

		dispatch({
			payload: {objectFields},
			type: EVENT_TYPES.OBJECT.FIELDS_CHANGE,
		});
	}
}

export async function updateObjectFields(dispatch) {
	const settingsDDMForm = await Liferay.componentReady('formSettingsAPI');
	const objectDefinitionId = settingsDDMForm.reactComponentRef.current.getObjectDefinitionId();

	if (objectDefinitionId) {
		const {objectFields} = await fetchObjectFields(objectDefinitionId);

		dispatch({
			payload: {objectFields},
			type: EVENT_TYPES.OBJECT.FIELDS_CHANGE,
		});
	}
	else {
		dispatch({
			payload: {objectFields: []},
			type: EVENT_TYPES.OBJECT.FIELDS_CHANGE,
		});
	}
}

export function getSelectedValue(value) {
	if (typeof value === 'string' && value !== '') {
		const newValue = JSON.parse(value);

		return Array.isArray(newValue) ? newValue[0] : newValue;
	}

	return value[0];
}

export function getObjectFieldName({settingsContext}) {
	const getAdvancedColumn = ({title}) =>
		title === Liferay.Language.get('advanced');
	const fieldsFromAdvancedColumn = settingsContext.pages.find(
		getAdvancedColumn
	)?.rows[0].columns[0].fields;

	if (settingsContext.type === 'fieldset' || !fieldsFromAdvancedColumn) {
		return;
	}

	const objectFieldName = fieldsFromAdvancedColumn.find(
		(field) => field.fieldName === 'objectFieldName'
	);

	return objectFieldName;
}
