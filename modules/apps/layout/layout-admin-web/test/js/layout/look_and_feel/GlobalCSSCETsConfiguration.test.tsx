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

import '@testing-library/jest-dom/extend-expect';
import {
	act,
	findByRole,
	fireEvent,
	render,
	screen,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openSelectionModal} from 'frontend-js-web';
import * as React from 'react';

import GlobalCSSCETsConfiguration from '../../../../src/main/resources/META-INF/resources/js/layout/look_and_feel/GlobalCSSCETsConfiguration';

jest.mock('frontend-js-web', () => ({
	openSelectionModal: jest.fn(),
	openToast: () => {},
}));

const openSelectionModalMock = openSelectionModal as jest.Mock<
	typeof openSelectionModal
>;

describe('GlobalCSSCETsConfiguration', () => {
	afterEach(() => {
		openSelectionModalMock.mockReset();
	});

	it('shows "no extensions loaded" if there are no extensions', async () => {
		render(
			<GlobalCSSCETsConfiguration
				globalCSSCETSelectorURL=""
				globalCSSCETs={[]}
				portletNamespace=""
				selectGlobalCSSCETsEventName=""
			/>
		);

		await screen.findByText('no-css-client-extensions-were-loaded');
	});

	it('renders the given list of global extensions', async () => {
		render(
			<GlobalCSSCETsConfiguration
				globalCSSCETSelectorURL=""
				globalCSSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Nice Global CSS',
					},
				]}
				portletNamespace=""
				selectGlobalCSSCETsEventName=""
			/>
		);

		await screen.findByText('Nice Global CSS');
	});

	it('renders a hidden input with the list of selected extensions', async () => {
		render(
			<GlobalCSSCETsConfiguration
				globalCSSCETSelectorURL=""
				globalCSSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Nice Global CSS',
					},
					{
						cetExternalReferenceCode: 'anotherNiceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Nice Global CSS v2',
					},
				]}
				portletNamespace=""
				selectGlobalCSSCETsEventName=""
			/>
		);

		await screen.findByDisplayValue('niceId');
		await screen.findByDisplayValue('anotherNiceId');
	});

	it('opens a selection modal when "add" button is pressed', async () => {
		render(
			<GlobalCSSCETsConfiguration
				globalCSSCETSelectorURL=""
				globalCSSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Nice Global CSS',
					},
				]}
				portletNamespace=""
				selectGlobalCSSCETsEventName=""
			/>
		);

		userEvent.click(
			await screen.findByRole('button', {
				name: 'add-css-client-extensions',
			})
		);

		expect(openSelectionModal).toHaveBeenCalled();
	});

	it('removes duplicated extensions if any', async () => {
		openSelectionModalMock.mockImplementation(() => () => {});

		render(
			<GlobalCSSCETsConfiguration
				globalCSSCETSelectorURL=""
				globalCSSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Nice Global CSS',
					},
				]}
				portletNamespace=""
				selectGlobalCSSCETsEventName=""
			/>
		);

		userEvent.click(
			await screen.findByRole('button', {
				name: 'add-css-client-extensions',
			})
		);

		expect(openSelectionModal).toHaveBeenCalledTimes(1);

		expect(openSelectionModal).toHaveBeenCalledWith(
			expect.objectContaining({
				onSelect: expect.any(Function),
			})
		);

		const [[{onSelect}]] = openSelectionModalMock.mock.calls;

		act(() => {
			onSelect({
				value: [
					JSON.stringify({
						cetExternalReferenceCode: 'niceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Nice Global CSS',
					}),
					JSON.stringify({
						cetExternalReferenceCode: 'someNiceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Some Nice Global CSS',
					}),
				],
			});
		});

		await screen.findByDisplayValue('niceId');
		await screen.findByDisplayValue('someNiceId');
	});

	it('allows removing extensions by pressing dropdown "remove" button', async () => {
		render(
			<GlobalCSSCETsConfiguration
				globalCSSCETSelectorURL=""
				globalCSSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						inherited: false,
						inheritedLabel: '',
						name: 'Nice Global CSS',
					},
				]}
				portletNamespace=""
				selectGlobalCSSCETsEventName=""
			/>
		);

		fireEvent.click(
			await screen.findByRole('button', {name: 'show-options'})
		);

		const item = await findByRole(
			await screen.findByRole('presentation', {name: 'show-options'}),
			'menuitem',
			{name: 'delete'}
		);

		fireEvent.click(item.firstChild!);

		await screen.findByText('no-css-client-extensions-were-loaded');
	});
});
