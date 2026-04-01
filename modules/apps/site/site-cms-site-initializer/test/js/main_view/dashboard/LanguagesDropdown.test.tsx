/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import SpaceService from '../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService';
import {
	ViewDashboardContext,
	initialLanguage,
	initialSpace,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/ViewDashboardContext';
import {
	LanguagesDropdown,
	localizations,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/LanguagesDropdown';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService'
);

const mockedSpaceService = SpaceService as jest.Mocked<typeof SpaceService>;

const mockedContext = {
	changeLanguage: jest.fn(),
	changeSpace: jest.fn(),
	constants: {},
	filters: {
		language: initialLanguage,
		space: initialSpace,
	},
};

describe('[CMS Dashboard] Components: LanguagesDropdown', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('renders correctly with all languages when all spaces is selected', async () => {
		render(
			<ViewDashboardContext.Provider value={mockedContext}>
				<LanguagesDropdown />
			</ViewDashboardContext.Provider>
		);

		const button = screen.getByRole('button', {name: 'all-languages'});

		expect(button).toBeInTheDocument();

		fireEvent.click(button);

		expect(
			screen.getByRole('menuitem', {name: 'all-languages'})
		).toBeInTheDocument();

		const availableLanguages = Object.values(localizations);

		availableLanguages.forEach((translation) => {
			expect(
				screen.getByRole('menuitem', {name: translation})
			).toBeInTheDocument();
		});
	});

	it('filters languages when a specific space is selected', async () => {
		const spaceWithLimitedLanguages = {
			...initialSpace,
			externalReferenceCode: 'SPACE_ERC',
			value: '123',
		};

		mockedSpaceService.getSpace.mockResolvedValue({
			settings: {
				availableLanguageIds: ['en-US', 'es-ES'],
			},
		} as any);

		render(
			<ViewDashboardContext.Provider
				value={{
					...mockedContext,
					filters: {
						...mockedContext.filters,
						space: spaceWithLimitedLanguages,
					},
				}}
			>
				<LanguagesDropdown />
			</ViewDashboardContext.Provider>
		);

		await waitFor(() =>
			expect(mockedSpaceService.getSpace).toHaveBeenCalledWith(
				'SPACE_ERC'
			)
		);

		fireEvent.click(screen.getByRole('button', {name: 'all-languages'}));

		expect(
			screen.getByRole('menuitem', {name: 'all-languages'})
		).toBeInTheDocument();

		expect(
			screen.getByRole('menuitem', {name: localizations.en_US})
		).toBeInTheDocument();

		expect(
			screen.getByRole('menuitem', {name: localizations.es_ES})
		).toBeInTheDocument();

		// Should not show other languages

		expect(
			screen.queryByRole('menuitem', {name: localizations.pt_BR})
		).not.toBeInTheDocument();
	});

	it('shows all languages if space settings does not have availableLanguageIds', async () => {
		const spaceWithoutSettings = {
			...initialSpace,
			externalReferenceCode: 'SPACE_ERC',
			value: '123',
		};

		mockedSpaceService.getSpace.mockResolvedValue({
			settings: {},
		} as any);

		render(
			<ViewDashboardContext.Provider
				value={{
					...mockedContext,
					filters: {
						...mockedContext.filters,
						space: spaceWithoutSettings,
					},
				}}
			>
				<LanguagesDropdown />
			</ViewDashboardContext.Provider>
		);

		await waitFor(() =>
			expect(mockedSpaceService.getSpace).toHaveBeenCalled()
		);

		fireEvent.click(screen.getByRole('button', {name: 'all-languages'}));

		const availableLanguages = Object.values(localizations);

		availableLanguages.forEach((translation) => {
			expect(
				screen.getByRole('menuitem', {name: translation})
			).toBeInTheDocument();
		});
	});
});
