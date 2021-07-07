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

import {EVENT_TYPES} from 'data-engine-js-components-web/js/custom/form/eventTypes.es';
import {fetch} from 'frontend-js-web';

const HEADERS = {
	Accept: 'application/json',
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
	return fetch(
		getURL(`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`),
		{
			headers: HEADERS,
			method: 'GET',
		}
	).then((response) => response.json());
};

export const getFieldsGroupedByTypes = (fields) => {
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
};

export const addObjectFields = async (dispatch) => {
	const settingsDDMForm = await Liferay.componentReady('settingsDDMForm');

	const objectDefinitionId = settingsDDMForm.reactComponentRef.current.getObjectDefinitionId();

	if (objectDefinitionId) {
		const {objectFields} = await fetchObjectFields(objectDefinitionId);

		dispatch({
			payload: {objectFields},
			type: EVENT_TYPES.OBJECT_FIELDS.ADD,
		});
	}
};

export const updateObjectFields = async (dispatch) => {
	const settingsDDMForm = await Liferay.componentReady('settingsDDMForm');

	const objectDefinitionId = settingsDDMForm.reactComponentRef.current.getObjectDefinitionId();

	if (objectDefinitionId) {
		const {objectFields} = await fetchObjectFields(objectDefinitionId);

		dispatch({
			payload: {objectFields},
			type: EVENT_TYPES.OBJECT_FIELDS.ADD,
		});
	}
	else {
		dispatch({
			payload: {objectFields: []},
			type: EVENT_TYPES.OBJECT_FIELDS.ADD,
		});
	}
};

const DATA_TYPES = {
	BigDecimal: 'double',
	Blob: 'blob',
	Double: 'double',
	Integer: 'integer',
	Long: 'double',
	String: 'string',
};

export const normalizeDataType = (type) => {
	return DATA_TYPES[type] ?? type;
};
