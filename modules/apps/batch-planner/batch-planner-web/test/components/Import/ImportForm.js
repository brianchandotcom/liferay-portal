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
import {act, cleanup, fireEvent, render, within} from '@testing-library/react';
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

const SCHEMA = {
	'currencyCode': {
		type: 'string',
	},
	'id': {
		format: 'int64',
		type: 'integer',
	},
	'name': {
		type: 'string',
	},
	'type': {
		type: 'string',
	},
	'x-class-name': {
		default: 'com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel',
		readOnly: true,
		type: 'string',
	},
};

const fileSchema = ['currencyCode', 'type', 'name'];

describe('ImportForm', () => {
	afterEach(cleanup);

	it('must render', () => {
		render(<ImportForm {...BASE_PROPS} />);
	});

	it('must show import mapping on schema change', () => {
		const {getByLabelText} = render(<ImportForm {...BASE_PROPS} />);

		act(() =>
			Liferay.fire(SCHEMA_SELECTED_EVENT, {
				schema: SCHEMA,
			})
		);

		act(() =>
			Liferay.fire(FILE_SCHEMA_EVENT, {
				schema: fileSchema,
			})
		);

		fileSchema.forEach((field) => getByLabelText(field));
	});

	it('must select the item on user click dropdown item', () => {
		const selectedField = 'type';
		const {getAllByRole} = render(<ImportForm {...BASE_PROPS} />);

		act(() =>
			Liferay.fire(SCHEMA_SELECTED_EVENT, {
				schema: SCHEMA,
			})
		);

		act(() =>
			Liferay.fire(FILE_SCHEMA_EVENT, {
				schema: fileSchema,
			})
		);

		act(() => {
			fireEvent.click(getAllByRole('button')[0]);
		});

		act(() => {
			fireEvent.click(
				within(getAllByRole('list')[0]).getByText(selectedField)
			);
		});

		expect(getAllByRole('button')[0].textContent).toBe(selectedField);
	});

	it('must not show previously selected items on other dropdowns', () => {
		const selectedField = 'type';
		const {getAllByRole} = render(<ImportForm {...BASE_PROPS} />);

		act(() =>
			Liferay.fire(SCHEMA_SELECTED_EVENT, {
				schema: SCHEMA,
			})
		);

		act(() =>
			Liferay.fire(FILE_SCHEMA_EVENT, {
				schema: fileSchema,
			})
		);

		act(() => {
			fireEvent.click(getAllByRole('button')[0]);
		});

		act(() => {
			fireEvent.click(
				within(getAllByRole('list')[0]).getByText(selectedField)
			);
		});

		act(() => {
			fireEvent.click(getAllByRole('button')[1]);
		});

		expect(
			within(getAllByRole('list')[1]).queryByText(selectedField)
		).toBeNull();
	});
});
