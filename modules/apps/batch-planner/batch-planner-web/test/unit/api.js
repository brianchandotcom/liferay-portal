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
	getExportStatus,
	getExportTaskStatusURL,
} from '../../src/main/resources/META-INF/resources/js/BatchPlannerExport';
import {
	EXPORT_PROCESS_COMPLETED,
	EXPORT_PROCESS_FAILED,
	EXPORT_PROCESS_STARTED,
} from '../../src/main/resources/META-INF/resources/js/constants';

const taskId = 1234;
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
		fetchMock.mock(getExportTaskStatusURL(taskId), {
			className:
				'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
			contentType: 'CSV',
			endTime: null,
			errorMessage: null,
			executeStatus: EXPORT_PROCESS_STARTED,
			id: taskId,
			processedItemsCount: 25,
			startTime: '2021-11-10T10:36:08Z',
			totalItemsCount: 50,
		});

		await getExportStatus({
			onProgress,
			taskId,
		});

		expect(onProgress).toBeCalledWith('CSV', 50);
	});

	it('must call onFail when status is FAILED', async () => {
		const mockErrorMessage = 'Test FAILED Polling';
		fetchMock.mock(getExportTaskStatusURL(taskId), {
			className:
				'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
			contentType: 'CSV',
			endTime: null,
			errorMessage: mockErrorMessage,
			executeStatus: EXPORT_PROCESS_FAILED,
			id: taskId,
			processedItemsCount: 25,
			startTime: '2021-11-10T10:36:08Z',
			totalItemsCount: 50,
		});

		await getExportStatus({
			onFail,
			taskId,
		});

		expect(onFail).toBeCalledWith(mockErrorMessage);
	});

	it('must call onSuccess when status is COMPLETED', async () => {
		fetchMock.mock(getExportTaskStatusURL(taskId), {
			className:
				'com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product',
			contentType: 'CSV',
			endTime: null,
			errorMessage: null,
			executeStatus: EXPORT_PROCESS_COMPLETED,
			id: taskId,
			processedItemsCount: 25,
			startTime: '2021-11-10T10:36:08Z',
			totalItemsCount: 50,
		});

		await getExportStatus({
			onSuccess,
			taskId,
		});

		expect(onSuccess).toBeCalledWith('CSV');
	});
});
