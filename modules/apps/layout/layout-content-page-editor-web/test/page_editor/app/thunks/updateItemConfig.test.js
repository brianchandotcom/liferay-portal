/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateItemConfigAction from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateItemConfig';
import LayoutService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService';
import updateItemConfig from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService',
	() => ({updateItemConfig: jest.fn()})
);

describe('updateItemConfig', () => {
	afterEach(() => {
		LayoutService.updateItemConfig.mockClear();
		updateItemConfigAction.mockClear();
	});

	const runThunk = () =>
		updateItemConfig({
			config: {},
			itemConfig: {},
			itemId: '0',
			segmentsExperienceId: '0',
		})(
			() => {},
			() => ({})
		);

	it('calls LayoutService.updateItemConfig with the given information', () => {
		LayoutService.updateItemConfig.mockImplementation(() =>
			Promise.resolve({})
		);

		runThunk();
		expect(LayoutService.updateItemConfig).toHaveBeenCalled();
	});

	it('dispatches updateItemConfig and updatePageContents actions', async () => {
		LayoutService.updateItemConfig.mockImplementation(() =>
			Promise.resolve({
				layoutData: {
					items: {},
					version: 1,
				},
				pageContents: [
					{
						classPK: 'pk',
						name: 'contents',
						title: 'title',
					},
				],
			})
		);

		await runThunk();

		expect(updateItemConfigAction).toHaveBeenCalledWith({
			itemId: '0',
			layoutData: {
				items: {},
				version: 1,
			},
			overridePreviousConfig: false,
			pageContents: [
				{
					classPK: 'pk',
					name: 'contents',
					title: 'title',
				},
			],
		});
	});
});
