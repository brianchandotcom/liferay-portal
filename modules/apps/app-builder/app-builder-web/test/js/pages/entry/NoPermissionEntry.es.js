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

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import NoPermissionEntry from '../../../../src/main/resources/META-INF/resources/js/pages/entry/NoPermissionEntry.es';
import {DATA_DEFINITION_RESPONSES} from '../../constants.es';

describe('NoPermissionEntry', () => {
	beforeEach(() => {
		jest.useFakeTimers();

		window.Liferay = {
			...window.Liferay,
			Util: {
				...window.Liferay.Util,
				addParams: jest.fn(),
			},
		};

		window.themeDisplay = {
			...window.themeDisplay,
			isSignedIn: jest.fn(),
		};
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
		fetch.mockResponse(JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM));

		const {asFragment} = render(
			<AppContextProvider>
				<NoPermissionEntry />
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders as standalone and opens sign-in modal', async () => {
		fetch.mockResponse(JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM));

		const {queryByText} = render(
			<AppContextProvider appDeploymentType="standalone">
				<NoPermissionEntry />
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const signIn = queryByText('sign-in');

		await act(async () => {
			fireEvent.click(signIn);
		});
	});
});
