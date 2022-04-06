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

	it('must call onProgress when status is STARTED', async () => {
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

	it('must call onFail when status is FAILED', async () => {
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

	it('must call onSuccess when status is COMPLETED', async () => {
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
