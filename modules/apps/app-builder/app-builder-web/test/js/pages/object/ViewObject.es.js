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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ViewObject from '../../../../src/main/resources/META-INF/resources/js/pages/object/ViewObject.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';

import '@testing-library/jest-dom/extend-expect';

describe('ViewObject', () => {
	const RESPONSE = {
		name: {
			en_US: 'Custom Object',
		},
	};

	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSE));

		const {asFragment} = render(
			<ViewObject
				match={{
					params: {
						dataDefinitionId: 1,
					},
				}}
			/>,
			{wrapper: AppContextProviderWrapper}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('clicks on tabs and checks if they are active', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSE));

		const {queryByText} = render(
			<ViewObject
				match={{
					params: {
						dataDefinitionId: 1,
					},
				}}
			/>,
			{wrapper: AppContextProviderWrapper}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText(RESPONSE.name.en_US)).toBeTruthy();

		const tableViewsTab = queryByText('table-views');
		fireEvent.click(tableViewsTab);
		expect(tableViewsTab.classList.contains('active')).toBeTruthy();
	});
});
