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

import {
	fetchExportedFile,
	getExportTaskStatusURL,
} from '../../../src/main/resources/META-INF/resources/js/BatchPlannerExport';
import {
	PROCESS_COMPLETED,
	PROCESS_FAILED,
	PROCESS_STARTED,
	SCHEMA_SELECTED_EVENT,
} from '../../../src/main/resources/META-INF/resources/js/constants';
import Export from '../../../src/main/resources/META-INF/resources/js/export/Export';

const INPUT_VALUE_TEST = 'test';
const BASE_PROPS = {
	formExportDataQuerySelector: 'form',
	formExportURL: 'https://formUrl.test',
	portletNamespace: 'test',
};

const mockTaskID = 1234;
let mockApi;

const mockCreateObjectUrl = jest.fn(() => 'test.url/bloburl');
window.URL.createObjectURL = mockCreateObjectUrl;
window.URL.revokeObjectURL = jest.fn();

configure({asyncUtilTimeout: 5000});

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/BatchPlannerExport',
	() => ({
		...jest.requireActual(
			'../../../src/main/resources/META-INF/resources/js/BatchPlannerExport'
		),
		fetchExportedFile: jest.fn(),
	})
);

function fireSchemaChangeEvent() {
	Liferay.fire(SCHEMA_SELECTED_EVENT, {schema: 'something'});
}

describe('Export', () => {
	beforeAll(() => {
		const form = document.createElement('form');

		form.innerHTML = `
             <input type="text" value="${INPUT_VALUE_TEST}" />
         `;

		document.body.appendChild(form);
	});

	beforeEach(() => {
		const blob = new Blob(['a', 'b', 'c', 'd']);

		mockApi = fetchMock
			.mock(BASE_PROPS.formExportURL, () => ({
				exportTaskId: mockTaskID,
			}))
			.mock(
				`/o/headless-batch-engine/v1.0/export-task/${mockTaskID}/content`,
				{
					body: blob,
					headers: {'Content-Type': 'application/pdf'},
				},
				{sendAsJson: false}
			);
	});

	afterEach(() => {
		fetchMock.restore();
	});

	it('must render export button', () => {
		const {getByText} = render(<Export {...BASE_PROPS} />);

		expect(getByText(Liferay.Language.get('export'))).toBeInTheDocument();
	});

	it('must has button disabled initially', () => {
		const {getByText} = render(<Export {...BASE_PROPS} />);

		expect(getByText(Liferay.Language.get('export'))).toBeDisabled();
	});

	it('must enable button on Schema Change Event', () => {
		const {getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		expect(getByText(Liferay.Language.get('export'))).not.toBeDisabled();
	});

	it('must show modal when the button is clicked', async () => {
		const {findByText, getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('export')));
		});

		const exportButton = await findByText(Liferay.Language.get('download'));

		expect(exportButton).toBeInTheDocument();
	});

	it('must show modal with disabled button', async () => {
		const {findByText, getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('export')));
		});

		const exportButton = await findByText(Liferay.Language.get('download'));

		expect(exportButton).toBeDisabled();
	});

	it('must call export API only one time on mount', async () => {
		const {findByText, getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('export')));
		});

		await findByText(Liferay.Language.get('download'));

		expect(mockApi.calls(BASE_PROPS.formExportURL).length).toBe(1);
	});

	it('must show the correct progress percentage', async () => {
		const exportTaskStatusURL = getExportTaskStatusURL(mockTaskID);

		fetchMock.mock(exportTaskStatusURL, () => ({
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
		}));

		const {findByText, getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('export')));
		});

		const progress = await findByText('50%');

		expect(progress).toBeInTheDocument();
	});

	it('must show the error when execcuteStatus FAILED', async () => {
		const error = 'some test error';

		const exportTaskStatusURL = getExportTaskStatusURL(mockTaskID);

		fetchMock
			.mock(BASE_PROPS.formExportURL, () => ({
				exportTaskId: mockTaskID,
			}))
			.mock(exportTaskStatusURL, () => ({
				body: {
					className:
						'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
					contentType: 'CSV',
					endTime: null,
					errorMessage: error,
					executeStatus: PROCESS_FAILED,
					id: mockTaskID,
					processedItemsCount: 25,
					startTime: '2021-11-10T10:36:08Z',
					totalItemsCount: 50,
				},
			}));

		const {findByText, getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('export')));
		});

		const errorElement = await findByText(error);

		expect(errorElement).toBeInTheDocument();
	});

	it('must enable the download button when export task is COMPLETED', async () => {
		const exportTaskStatusURL = getExportTaskStatusURL(mockTaskID);

		fetchMock.mock(exportTaskStatusURL, () => ({
			body: {
				className:
					'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
				contentType: 'CSV',
				endTime: null,
				errorMessage: null,
				executeStatus: PROCESS_COMPLETED,
				id: mockTaskID,
				processedItemsCount: 50,
				startTime: '2021-11-10T10:36:08Z',
				totalItemsCount: 50,
			},
		}));

		const {getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		act(() => {
			fireEvent.click(getByText(Liferay.Language.get('export')));
		});

		await waitFor(() => {
			expect(
				getByText(Liferay.Language.get('download'), {
					selector: 'button',
				})
			).not.toBeDisabled();
		});
	});

	it('must create the blob file and download it when download button pressed', async () => {
		const exportTaskStatusURL = getExportTaskStatusURL(mockTaskID);

		fetchMock.mock(exportTaskStatusURL, () => ({
			body: {
				className:
					'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
				contentType: 'CSV',
				endTime: null,
				errorMessage: null,
				executeStatus: PROCESS_COMPLETED,
				id: mockTaskID,
				processedItemsCount: 50,
				startTime: '2021-11-10T10:36:08Z',
				totalItemsCount: 50,
			},
		}));

		const {getByText} = render(<Export {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		await act(async () => {
			fireEvent.click(getByText(Liferay.Language.get('export')));
		});

		await waitFor(() => {
			expect(
				getByText(Liferay.Language.get('download'), {
					selector: 'button',
				})
			).not.toBeDisabled();
		});

		act(() => {
			fireEvent.click(
				getByText(Liferay.Language.get('download'), {
					selector: 'button',
				})
			);
		});

		await waitFor(() => {
			expect(fetchExportedFile).toBeCalled();
		});
	});
});
