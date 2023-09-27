/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openSelectionModal} from 'frontend-js-web';
import * as React from 'react';

import ThemeCSSReplacementSelector from '../../../../src/main/resources/META-INF/resources/js/ThemeCSSReplacementSelector';

jest.mock('frontend-js-web', () => ({
	openSelectionModal: jest.fn(),
}));

const openSelectionModalMock = openSelectionModal as jest.Mock<
	typeof openSelectionModal
>;

describe('ThemeCSSReplacementSelector', () => {
	afterEach(() => {
		openSelectionModalMock.mockReset();
	});

	it('shows selected theme CSS', async () => {
		render(
			<ThemeCSSReplacementSelector
				placeholder=""
				portletNamespace=""
				selectThemeCSSClientExtensionEventName=""
				selectThemeCSSClientExtensionURL=""
				themeCSSCETExternalReferenceCode="nice-theme-ref-code"
				themeCSSExtensionName="Nice Theme CSS"
			/>
		);

		await screen.findByDisplayValue('Nice Theme CSS');
		await screen.findByDisplayValue('nice-theme-ref-code');
	});

	it('allows selecting a new theme CSS', async () => {
		openSelectionModalMock.mockImplementation(({onSelect}) =>
			onSelect({
				value: JSON.stringify({
					cetExternalReferenceCode: 'new-theme-css',
					name: 'New Theme CSS',
				}),
			})
		);

		render(
			<ThemeCSSReplacementSelector
				placeholder=""
				portletNamespace=""
				selectThemeCSSClientExtensionEventName=""
				selectThemeCSSClientExtensionURL=""
				themeCSSCETExternalReferenceCode=""
				themeCSSExtensionName=""
			/>
		);

		userEvent.click(await screen.findByRole('button', {name: 'select'}));
		expect(openSelectionModal).toHaveBeenCalled();

		await screen.findByDisplayValue('New Theme CSS');
		await screen.findByDisplayValue('new-theme-css');
	});

	it('allows replacing existing theme CSS', async () => {
		openSelectionModalMock.mockImplementation(({onSelect}) =>
			onSelect({
				value: JSON.stringify({
					cetExternalReferenceCode: 'replaced-theme-css',
					name: 'Replaced Theme CSS',
				}),
			})
		);

		render(
			<ThemeCSSReplacementSelector
				placeholder=""
				portletNamespace=""
				selectThemeCSSClientExtensionEventName=""
				selectThemeCSSClientExtensionURL=""
				themeCSSCETExternalReferenceCode="old-theme-css"
				themeCSSExtensionName="Old Theme CSS"
			/>
		);

		userEvent.click(await screen.findByRole('button', {name: 'replace'}));
		expect(openSelectionModal).toHaveBeenCalled();

		await screen.findByDisplayValue('Replaced Theme CSS');
		await screen.findByDisplayValue('replaced-theme-css');
	});

	it('allows removing existing theme CSS', async () => {
		render(
			<ThemeCSSReplacementSelector
				placeholder=""
				portletNamespace=""
				selectThemeCSSClientExtensionEventName=""
				selectThemeCSSClientExtensionURL=""
				themeCSSCETExternalReferenceCode="old-theme-css"
				themeCSSExtensionName="Old Theme CSS"
			/>
		);

		userEvent.click(await screen.findByRole('button', {name: 'delete'}));

		expect(
			await screen.queryByDisplayValue('Old Theme CSS')
		).not.toBeInTheDocument();

		expect(
			await screen.queryByDisplayValue('old-theme-css')
		).not.toBeInTheDocument();
	});
});
