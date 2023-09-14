/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {OBJECT_RELATIONSHIP} from './Constants';
import {FDSViewType} from './FDSViews';
import {IField, IPickList} from './types';
import openDefaultFailureToast from './utils/openDefaultFailureToast';

const LOCALIZABLE_PROPERTY_SUFFIX = '_i18n';

const NOT_ALLOWED_KEYS_AS_FIELD_NAME = [
	'actions',
	'scopeKey',
	'x-class-name',
	'x-schema-name',
];

function getValidFields(properties: any): Array<IField> {
	const fields: Array<IField> = [];

	Object.keys(properties).map((propertyKey) => {
		const propertyValue = properties[propertyKey];

		if (NOT_ALLOWED_KEYS_AS_FIELD_NAME.includes(propertyKey)) {
			return;
		}

		if (propertyKey.includes(LOCALIZABLE_PROPERTY_SUFFIX)) {
			return;
		}

		const type = propertyValue.type;

		if (type === 'array') {
			return;
		}

		if (propertyValue.$ref) {
			return;
		}

		fields.push({
			format: properties[propertyKey].format || type,
			label: propertyKey,
			name: propertyKey,
			type,
		});
	});

	return fields;
}

export async function getFields(fdsView: FDSViewType) {
	const {restApplication, restSchema} = fdsView[
		OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW
	];

	const response = await fetch(`/o${restApplication}/openapi.json`);

	if (!response.ok) {
		openDefaultFailureToast();

		return [];
	}

	const responseJSON = await response.json();

	const properties =
		responseJSON?.components?.schemas[restSchema]?.properties;

	if (!properties) {
		openDefaultFailureToast();

		return [];
	}

	const fieldsArray: Array<IField> = getValidFields(properties);

	return fieldsArray;
}

export async function getAllPicklists(
	page: number = 1,
	items: IPickList[] = []
) {
	const response = await fetch(
		`/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=100&page=${page}`
	);

	if (!response.ok) {
		openDefaultFailureToast();

		return [];
	}

	const responseJSON = await response.json();

	items = [...items, ...responseJSON.items];

	if (responseJSON.lastPage > page) {
		items = await getAllPicklists(page + 1, items);
	}

	return items;
}
