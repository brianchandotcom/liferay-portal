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
import {
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import Export from '../../../src/main/resources/META-INF/resources/js/Export';

const INPUT_VALUE_TEST = 'test';
const BASE_PROPS = {
	formExportDataQuerySelector: 'form',
	formExportURL: 'https://formUrl.test',
	portletNamespace: 'test',
};

describe('ExportModal', () => {
	beforeEach(() => {
		const form = document.createElement('form');

		form.innerHTML = `
             <input type="text" value="${INPUT_VALUE_TEST}" />
         `;

		document.body.appendChild(form);
	});

	afterEach(() => {
		fetchMock.restore();
		cleanup();
	});

	it('must render export button', () => {
		const {getByText} = render(<Export {...BASE_PROPS} />);

		expect(getByText(Liferay.Language.get('export'))).toBeInTheDocument();
	});

	it('must show modal when the button is clicked', async () => {
		const {getByText} = render(<Export {...BASE_PROPS} />);

		fireEvent.click(getByText(Liferay.Language.get('export')));

		const exportButton = await waitForElement(() =>
			getByText(Liferay.Language.get('download'))
		);

		expect(exportButton).toBeInTheDocument();
	});

	it('must call api with form data', async () => {
		const mockedApi = fetchMock.mock(BASE_PROPS.formExportURL, () => {
			return {test: 'test'};
		});

		const {getByText} = render(<Export {...BASE_PROPS} />);

		fireEvent.click(getByText(Liferay.Language.get('export')));

		await waitForElement(() => getByText(Liferay.Language.get('download')));

		fireEvent.click(getByText(Liferay.Language.get('download')));

		expect(mockedApi.called()).toBe(true);
	});
});
