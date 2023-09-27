/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateRowColumns from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateRowColumns';
import LayoutService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService';
import updateRowColumnsThunk from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateRowColumns';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateRowColumns',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService',
	() => ({updateRowColumns: jest.fn()})
);

describe('updateRowColumns', () => {
	beforeEach(() => {
		LayoutService.updateRowColumns.mockClear();
		updateRowColumns.mockClear();

		LayoutService.updateRowColumns.mockImplementation(() =>
			Promise.resolve()
		);

		LayoutService.updateRowColumns.mockImplementation(() =>
			Promise.resolve({
				layoutData: {
					items: {},
					version: 1,
				},
			})
		);
	});

	const runThunk = () =>
		updateRowColumnsThunk({
			itemId: '0',
			numberOfColumns: 6,
		})(
			() => {},
			() => ({})
		);

	it('calls LayoutService.updateRowColumns with the given information', () => {
		runThunk();

		expect(LayoutService.updateRowColumns).toHaveBeenCalledWith(
			expect.objectContaining({
				itemId: '0',
				numberOfColumns: 6,
			})
		);
	});

	it('dispatch updateRowColumns action when the promise is resolved', async () => {
		await runThunk();

		expect(updateRowColumns).toHaveBeenCalledWith(
			expect.objectContaining({
				itemId: '0',
				layoutData: {
					items: {},
					version: 1,
				},
			})
		);
	});
});
