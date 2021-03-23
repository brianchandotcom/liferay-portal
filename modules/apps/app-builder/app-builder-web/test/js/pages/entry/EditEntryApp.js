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
import EditEntryApp from '../../../../src/main/resources/META-INF/resources/js/pages/entry/EditEntryApp.es';
import {DATA_DEFINITION_RESPONSES} from '../../constants.es';

const mockFetch = fetch;

const mockOpenModal = jest.fn();

jest.mock('frontend-js-web', () => ({
	debounce: jest.fn(),
	fetch: (...args) => mockFetch(...args),
	openModal: (...args) => mockOpenModal(...args),
}));

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/hooks/useLazy.es',
	() => () => () => <div>Fake Component</div>
);

const defaultState = {
	appTab: {
		editEntryPoint: '',
	},
};

const EditEntryAppWrapper = ({state = defaultState}) => (
	<AppContextProvider appId={1}>
		<div className="tools-control-group">
			<div className="control-menu-level-1-heading"> </div>
		</div>
		<EditEntryApp {...state} />
	</AppContextProvider>
);

describe('EditEntryApp', () => {
	beforeEach(() => {
		jest.useFakeTimers();

		window.themeDisplay = {
			...window.themeDisplay,
			isSignedIn: () => false,
		};

		window.Liferay = {
			...window.Liferay,
			themeDisplay: {
				...window.Liferay.themeDisplay,
			},
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

	it('renders with no-permission', async () => {
		fetch.mockResponseOnce(
			JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
		);

		const state = {
			appTab: {
				editEntryPoint: '',
			},
		};

		const {container, queryByText, rerender} = render(
			<EditEntryAppWrapper state={state} />
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('no-permissions')).toBeTruthy();
		expect(
			queryByText(
				'you-do-not-have-access-to-this-app-sign-in-to-access-it'
			)
		).toBeTruthy();
		expect(queryByText('sign-in')).toBeNull();

		state.appDeploymentType = 'standalone';

		const bodyHTML = `<html><body><span>Modal Data</span></body></html>`;

		fetch.mockResponseOnce(JSON.stringify(bodyHTML));

		window.Liferay = {
			...window.Liferay,
			Util: {
				addParams: jest.fn(),
			},
		};

		rerender(<EditEntryAppWrapper state={state} />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(container.querySelector('.modal')).toBeNull();

		const signInButton = queryByText('sign-in');

		expect(mockOpenModal).toHaveBeenCalledTimes(0);

		fireEvent.click(signInButton);

		expect(mockOpenModal).toHaveBeenCalledTimes(1);

		const [[openModalData]] = mockOpenModal.mock.calls;

		expect(openModalData.bodyHTML).toEqual(`"${bodyHTML}"`);
		expect(openModalData.title).toEqual('sign-in');
	});

	it('renders with add permission', async () => {
		fetch.mockResponseOnce(
			JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
		);
		fetch.mockResponseOnce(JSON.stringify(['ADD_DATA_RECORD']));

		const {queryByText} = render(<EditEntryAppWrapper />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('Fake Component')).toBeTruthy();
	});

	it('renders with view permission', async () => {
		fetch.mockResponseOnce(
			JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
		);
		fetch.mockResponseOnce(JSON.stringify(['UPDATE_DATA_RECORD']));

		const {queryByText} = render(<EditEntryAppWrapper />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('Fake Component')).toBeTruthy();
	});
});
