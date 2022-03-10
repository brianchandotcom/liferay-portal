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
	act,
	configure,
	fireEvent,
	render,
	waitFor,
} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import {getImportTaskStatusURL} from '../../../src/main/resources/META-INF/resources/js/BatchPlannerImport';
import {
	POLL_INTERVAL,
	PROCESS_COMPLETED,
	PROCESS_STARTED,
} from '../../../src/main/resources/META-INF/resources/js/constants';
import ImportSubmit from '../../../src/main/resources/META-INF/resources/js/import/ImportSubmit';

const BASE_PROPS = {
	evaluateForm: () => {},
	formDataQuerySelector: 'form',
	formImportURL: 'https://formUrl.test',
	formIsValid: true,
	portletNamespace: 'test',
};

const internalFieldName = 'name';
const externalFieldName = 'external';

let mockApi;
const mockTaskID = '1234';

configure({asyncUtilTimeout: 5000});

describe('ImportSubmit', () => {
	beforeEach(() => {
		const form = document.createElement('form');

		form.innerHTML = `
            <input name="${BASE_PROPS.portletNamespace}internalFieldName_${internalFieldName}" value="${internalFieldName}" />
            <input name="${BASE_PROPS.portletNamespace}externalFieldName_${externalFieldName}" value="${externalFieldName}" />
        `;

		document.body.appendChild(form);

		mockApi = fetchMock.mock(BASE_PROPS.formImportURL, () => ({
			importTaskId: mockTaskID,
		}));
	});

	afterEach(() => {
		fetchMock.restore();
	});

	it('must start import task', () => {
		const {getByText} = render(<ImportSubmit {...BASE_PROPS} />);

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('import')));
		});

		expect(mockApi.called(BASE_PROPS.formImportURL)).toBeTruthy();
	});

	it('must start polling import status', async () => {
		jest.useFakeTimers();
		const importTaskStatusURL = getImportTaskStatusURL(mockTaskID);

		mockApi.mock(
			importTaskStatusURL,
			{
				body: {
					className:
						'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
					contentType: 'CSV',
					endTime: null,
					errorMessage: null,
					executeStatus: PROCESS_STARTED,
					id: mockTaskID,
					processedItemsCount: 25,
					startTime: '2021-11-10T10:36:08Z',
					totalItemsCount: 50,
				},
			},
			{sendAsJson: false}
		);
		const {getByText} = render(<ImportSubmit {...BASE_PROPS} />);

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('import')));
		});

		await act(async () => {
			jest.advanceTimersByTime(POLL_INTERVAL);
		});

		await waitFor(() => {
			expect(mockApi.called(importTaskStatusURL)).toBeTruthy();
		});

		jest.useRealTimers();
	});

	it('must enable button when import process is completed', async () => {
		const importTaskStatusURL = getImportTaskStatusURL(mockTaskID);

		mockApi.mock(
			importTaskStatusURL,
			{
				body: {
					className:
						'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
					contentType: 'CSV',
					endTime: null,
					errorMessage: null,
					executeStatus: PROCESS_COMPLETED,
					id: mockTaskID,
					processedItemsCount: 25,
					startTime: '2021-11-10T10:36:08Z',
					totalItemsCount: 50,
				},
			},
			{sendAsJson: false}
		);
		const {getByText} = render(<ImportSubmit {...BASE_PROPS} />);

		await act(async () => {
			fireEvent.click(getByText(Liferay.Language.get('import')));
		});

		await waitFor(() => {
			expect(
				getByText(Liferay.Language.get('done'), {
					selector: 'button',
				})
			).not.toBeDisabled();
		});
	});
});
