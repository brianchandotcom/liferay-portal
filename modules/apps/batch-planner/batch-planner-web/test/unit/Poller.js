/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetchMock from 'fetch-mock';

import {
	exportStatus,
	getExportTaskStatusURL,
} from '../../src/main/resources/META-INF/resources/js/BatchPlannerExport';
import {getTaskStatus} from '../../src/main/resources/META-INF/resources/js/Poller';
import {
	PROCESS_COMPLETED,
	PROCESS_FAILED,
	PROCESS_STARTED,
} from '../../src/main/resources/META-INF/resources/js/constants';

const externalReferenceCode = '1234';
const onFail = jest.fn();
const onProgress = jest.fn();
const onSuccess = jest.fn();

describe('Polling Export Status Process', () => {
	beforeEach(() => {
		jest.resetAllMocks();
	});

	afterEach(() => {
		fetchMock.restore();
	});

	it.skip('must call onProgress when status is STARTED', async () => {
		fetchMock.mock(getExportTaskStatusURL(externalReferenceCode), {
			className:
				'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
			contentType: 'CSV',
			endTime: null,
			errorMessage: null,
			executeStatus: PROCESS_STARTED,
			externalReferenceCode,
			processedItemsCount: 25,
			startTime: '2021-11-10T10:36:08Z',
			totalItemsCount: 50,
		});

		await getTaskStatus({
			externalReferenceCode,
			onProgress,
			requestTaskStatus: exportStatus,
		});

		expect(onProgress).toBeCalledWith('CSV', 50);
	});

	it.skip('must call onFail when status is FAILED', async () => {
		const mockErrorMessage = 'Test FAILED Polling';
		fetchMock.mock(getExportTaskStatusURL(externalReferenceCode), {
			className:
				'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
			contentType: 'CSV',
			endTime: null,
			errorMessage: mockErrorMessage,
			executeStatus: PROCESS_FAILED,
			externalReferenceCode,
			processedItemsCount: 25,
			startTime: '2021-11-10T10:36:08Z',
			totalItemsCount: 50,
		});

		await getTaskStatus({
			externalReferenceCode,
			onFail,
			requestTaskStatus: exportStatus,
		});

		expect(onFail).toBeCalledWith(mockErrorMessage);
	});

	it.skip('must call onSuccess when status is COMPLETED', async () => {
		fetchMock.mock(getExportTaskStatusURL(externalReferenceCode), {
			className:
				'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
			contentType: 'CSV',
			endTime: null,
			errorMessage: null,
			executeStatus: PROCESS_COMPLETED,
			externalReferenceCode,
			processedItemsCount: 25,
			startTime: '2021-11-10T10:36:08Z',
			totalItemsCount: 50,
		});

		await getTaskStatus({
			externalReferenceCode,
			onSuccess,
			requestTaskStatus: exportStatus,
		});

		expect(onSuccess).toBeCalledWith('CSV');
	});
});
