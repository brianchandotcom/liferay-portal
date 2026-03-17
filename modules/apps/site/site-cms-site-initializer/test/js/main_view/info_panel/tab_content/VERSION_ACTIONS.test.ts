/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup} from '@testing-library/react';

import {FDS_EVENT_UPDATE_DISPLAY} from '../../../../../src/main/resources/META-INF/resources/js/common/utils/constants';
import {VERSION_ACTIONS} from '../../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/tab_content';
import confirmAndDeleteEntryAction from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/confirmAndDeleteEntryAction';
import {executeAsyncItemAction} from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/utils/executeAsyncItemAction';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/utils/executeAsyncItemAction'
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/confirmAndDeleteEntryAction'
);

const mockLiferay = {
	Language: {
		get: jest.fn((key) => key),
	},
	Util: {
		escapeHTML: jest.fn((str) => str),
	},
	fire: jest.fn(),
};

(global as any).Liferay = mockLiferay;

describe('VERSION_ACTIONS', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	const mockObjectEntry = {
		actions: {
			delete: {href: '/delete', method: 'DELETE'},
			expire: {href: '/expire', method: 'PUT'},
			restore: {href: '/restore', method: 'PUT'},
		},
		systemProperties: {
			version: {number: '1.0'},
		},
	};

	const dataSetId = 'test-dataset-id';
	const refreshData = jest.fn();
	const event = {preventDefault: jest.fn()} as any;

	it('EXPIRE action fires FDS_EVENT_UPDATE_DISPLAY with dataSetId', async () => {
		(executeAsyncItemAction as jest.Mock).mockImplementation(
			({refreshData}) => {
				refreshData({title: 'Test Asset'});

				return Promise.resolve();
			}
		);

		await VERSION_ACTIONS.expire.action(
			event,
			mockObjectEntry,
			refreshData,
			'Test Title',
			dataSetId
		);

		expect(mockLiferay.fire).toHaveBeenCalledWith(
			FDS_EVENT_UPDATE_DISPLAY,
			{
				id: dataSetId,
			}
		);

		expect(refreshData).toHaveBeenCalled();
	});

	it('RESTORE action fires FDS_EVENT_UPDATE_DISPLAY with dataSetId', async () => {
		(executeAsyncItemAction as jest.Mock).mockImplementation(
			({refreshData}) => {
				refreshData({title: 'Test Asset'});

				return Promise.resolve();
			}
		);

		await VERSION_ACTIONS.restore.action(
			event,
			mockObjectEntry,
			refreshData,
			'Test Title',
			dataSetId
		);

		expect(mockLiferay.fire).toHaveBeenCalledWith(
			FDS_EVENT_UPDATE_DISPLAY,
			{
				id: dataSetId,
			}
		);

		expect(refreshData).toHaveBeenCalled();
	});

	it('DELETE_VERSION action passes dataSetId to confirmAndDeleteEntryAction', async () => {
		await VERSION_ACTIONS.delete.action(
			event,
			mockObjectEntry,
			refreshData,
			'Test Title',
			dataSetId
		);

		expect(confirmAndDeleteEntryAction).toHaveBeenCalledWith(
			expect.objectContaining({
				dataSetId,
				loadData: refreshData,
			})
		);
	});
});
