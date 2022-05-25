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

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import {
	FILE_SCHEMA_EVENT,
	SCHEMA_SELECTED_EVENT,
} from '../../../src/main/resources/META-INF/resources/js/constants';
import ImportForm from '../../../src/main/resources/META-INF/resources/js/import/ImportForm';

const BASE_PROPS = {
	backUrl: 'https://liferay.com/backurl',
	formDataQuerySelector: 'form',
	formImportURL: 'https://formUrl.test',
	formSaveAsTemplateURL: 'https://formUrl.test/saveTemplateUrl',
	portletNamespace: 'test',
};

const SCHEMA = [
	{
		name: 'currencyCode',
		type: 'string',
	},
	{
		name: 'id',
		type: 'integer',
	},
	{
		name: 'name',
		type: 'string',
	},
	{
		name: 'type',
		type: 'string',
	},
	{
		name: 'x-class-name',
		readOnly: true,
		type: 'string',
	},
];

const FILE_SCHEMA = ['currencyCode', 'type', 'name'];
const fileContent = [
	['USD', 'bike', 'default'],
	['EUR', 'truck', 'default'],
];

describe('ImportForm', () => {
	beforeEach(() => {
		const mockDiv = document.createElement('div');
		mockDiv.setAttribute(
			'id',
			`${BASE_PROPS.portletNamespace}downloadTemplateAlert`
		);
		document.body.appendChild(mockDiv);
	});

	afterEach(cleanup);

	it('must render', () => {
		render(<ImportForm {...BASE_PROPS} />);
	});

	it('must show import mapping on schema change', () => {
		const {getByLabelText} = render(<ImportForm {...BASE_PROPS} />);

		act(() => {
			Liferay.fire(SCHEMA_SELECTED_EVENT, {
				schema: SCHEMA,
			});

			Liferay.fire(FILE_SCHEMA_EVENT, {
				fileContent,
				schema: FILE_SCHEMA,
			});
		});

		FILE_SCHEMA.forEach((field) => getByLabelText(field));
	});

	it('must automatically map matching field names', () => {
		const {getAllByRole} = render(<ImportForm {...BASE_PROPS} />);

		act(() => {
			Liferay.fire(SCHEMA_SELECTED_EVENT, {
				schema: SCHEMA,
			});

			Liferay.fire(FILE_SCHEMA_EVENT, {
				fileContent,
				schema: FILE_SCHEMA,
			});
		});

		getAllByRole('combobox').forEach((dbFieldSelect) => {
			if (!dbFieldSelect.id.startsWith('input-')) {
				return;
			}

			if (dbFieldSelect.value) {
				expect(FILE_SCHEMA).toContain(dbFieldSelect.value);
			}
			else {
				expect(FILE_SCHEMA).not.toContain(dbFieldSelect.value);
			}
		});
	});
});
