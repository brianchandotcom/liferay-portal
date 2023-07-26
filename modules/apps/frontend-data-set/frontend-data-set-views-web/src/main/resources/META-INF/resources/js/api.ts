/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, openToast} from 'frontend-js-web';

import {OBJECT_RELATIONSHIP} from './Constants';
import {FDSViewType} from './FDSViews';

interface IField {
	format: string;
	label: string;
	name: string;
	type: string;
}

export async function getFields(fdsView: FDSViewType) {
	const {restApplication, restSchema} = fdsView[
		OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW
	];

	const response = await fetch(`/o${restApplication}/openapi.json`);

	if (!response.ok) {
		openToast({
			message: Liferay.Language.get('your-request-failed-to-complete'),
			type: 'danger',
		});

		return [];
	}

	const responseJSON = await response.json();

	const properties =
		responseJSON?.components?.schemas[restSchema]?.properties;

	if (!properties) {
		openToast({
			message: Liferay.Language.get('your-request-failed-to-complete'),
			type: 'danger',
		});

		return [];
	}

	const fieldsArray: Array<IField> = [];

	const isObjectSchema =
		responseJSON.components.schemas[restSchema].xml.name === 'ObjectEntry';

	Object.keys(properties).map((propertyKey) => {
		const propertyValue = properties[propertyKey];

		if (isObjectSchema && !propertyValue.extensions) {
			return;
		}

		if (propertyKey === 'x-class-name') {
			return;
		}

		const type = propertyValue.type;

		if (type === 'object' || type === 'array') {
			return;
		}

		if (propertyValue.$ref) {
			return;
		}

		fieldsArray.push({
			format: properties[propertyKey].format || type,
			label: propertyKey,
			name: propertyKey,
			type,
		});
	});

	return fieldsArray;
}

export interface IPickList {
	externalReferenceCode: string;
	id: string;
	listTypeEntries: IListTypeEntry[];
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}

interface IListTypeEntry {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}

export async function getAllPicklists(
	page: number = 1,
	items: IPickList[] = []
) {
	const response = await fetch(
		`/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=100&page=${page}`
	);

	if (!response.ok) {
		openToast({
			message: Liferay.Language.get('your-request-failed-to-complete'),
			type: 'danger',
		});

		return [];
	}

	const responseJSON = await response.json();

	items = [...items, ...responseJSON.items];

	if (responseJSON.lastPage > page) {
		items = await getAllPicklists(page + 1, items);
	}

	return items;
}
