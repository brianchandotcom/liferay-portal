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
import PortalEntry from '../../../../src/main/resources/META-INF/resources/js/pages/entry/PortalEntry.es';
import PermissionContextProvider from '../../PermissionsContextProviderWrapper.es';

const defaultLanguageId = 'en-US';

const PortalEntryWrapper = ({children, ...props}) => (
	<AppContextProvider>
		<PermissionContextProvider>
			{children}
			<PortalEntry {...props} />
		</PermissionContextProvider>
	</AppContextProvider>
);

const location = window.location;

describe('PortalEntry', () => {
	beforeEach(() => {
		jest.useFakeTimers();

		delete window.location;

		window.location = {
			origin: 'localhost',
		};

		window.themeDisplay = {
			...window.themeDisplay,
			getDefaultLanguageId: () => defaultLanguageId,
			getLanguageId: () => defaultLanguageId,
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

		window.location = location;
	});

	it('renders', () => {
		const {asFragment} = render(<PortalEntryWrapper />);

		expect(asFragment).toMatchSnapshot();
	});

	it('renders with and without logged user', () => {
		window.themeDisplay = {
			isSignedIn: () => false,
		};

		const {container, queryByText, rerender} = render(
			<PortalEntryWrapper>
				<div id="app-personal-menu"></div>
			</PortalEntryWrapper>
		);

		expect(
			container.querySelector('#app-personal-menu').hasChildNodes()
		).toBe(false);

		window.themeDisplay = {
			getUserName: () => 'Liferay User',
			isSignedIn: () => true,
		};

		rerender(
			<PortalEntryWrapper>
				<div id="app-personal-menu"></div>
			</PortalEntryWrapper>
		);

		expect(
			container.querySelector('#app-personal-menu').hasChildNodes()
		).toBe(true);

		expect(queryByText('Liferay User')).toBeTruthy();
		expect(window.location.href).toBeUndefined();

		fireEvent.click(queryByText('sign-out'));

		expect(window.location.href).toBe('localhost/c/portal/logout');
	});

	it('renders with translation name', async () => {
		window.location = location;

		fetch.mockResponseOnce(
			JSON.stringify({
				name: {
					'en-US': 'Liferay Engineering',
					'pt-BR': 'Liferay Engenharia',
				},
			})
		);

		fetch.mockResponseOnce(
			JSON.stringify({
				availableLanguageIds: [defaultLanguageId, 'pt-BR'],
				defaultLanguageId,
			})
		);

		const {queryByText, rerender} = render(
			<PortalEntryWrapper
				appId={1}
				dataDefinitionId={1}
				showAppName
				userLanguageId={defaultLanguageId}
			>
				<h1 id="appStandaloneName" />
			</PortalEntryWrapper>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(fetch).toHaveBeenCalledTimes(2);
		expect(queryByText('Liferay Engineering')).toBeTruthy();

		rerender(
			<PortalEntryWrapper
				appId={1}
				dataDefinitionId={1}
				showAppName
				userLanguageId="pt-BR"
			>
				<h1 id="appStandaloneName" />
			</PortalEntryWrapper>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('Liferay Engenharia')).toBeTruthy();
	});

	it('renders with translation manager', async () => {
		window.location = location;

		const setUserLanguageId = jest.fn();

		fetch.mockResponseOnce(
			JSON.stringify({
				name: {
					'en-US': 'Liferay Engineering',
					'pt-BR': 'Liferay Engenharia',
				},
			})
		);

		fetch.mockResponseOnce(
			JSON.stringify({
				availableLanguageIds: [defaultLanguageId, 'pt-BR'],
				defaultLanguageId,
			})
		);

		const {baseElement} = render(
			<PortalEntryWrapper
				appId={1}
				dataDefinitionId={1}
				setUserLanguageId={setUserLanguageId}
				showAppName
				userLanguageId={defaultLanguageId}
			>
				<h1 id="appTranslationManager" />
			</PortalEntryWrapper>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const languages = baseElement.querySelectorAll(
			'.localizable-dropdown-ul li'
		);

		expect(languages).toHaveLength(2);
		expect(languages[0].querySelector('.lexicon-icon-en-us')).toBeTruthy();
		expect(languages[1].querySelector('.lexicon-icon-pt-br')).toBeTruthy();
		expect(setUserLanguageId).toHaveBeenCalledTimes(0);

		fireEvent.click(languages[0].querySelector('button'));

		expect(setUserLanguageId).toHaveBeenCalledWith('en-US');

		fireEvent.click(languages[1].querySelector('button'));

		expect(setUserLanguageId).toHaveBeenCalledWith('pt-BR');
		expect(setUserLanguageId).toHaveBeenCalledTimes(2);
	});
});
