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
import {act, findByRole, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openSelectionModal} from 'frontend-js-web';
import * as React from 'react';

import GlobalJSCETsConfiguration from '../../../../src/main/resources/META-INF/resources/js/layout/look_and_feel/GlobalJSCETsConfiguration';

jest.mock('frontend-js-web', () => ({
	openSelectionModal: jest.fn(),
}));

describe('GlobalJSCETsConfiguration', () => {
	afterEach(() => {
		openSelectionModal.mockReset();
	});

	it('shows "no extensions loaded" if there are no extensions', async () => {
		render(
			<GlobalJSCETsConfiguration
				globalJSCETSelectorURL=""
				globalJSCETs={[]}
				portletNamespace=""
				selectGlobalJSCETsEventName=""
			/>
		);

		await screen.findByText('no-javascript-extensions-were-loaded');
	});

	it('renders the given list of global extensions', async () => {
		render(
			<GlobalJSCETsConfiguration
				globalJSCETSelectorURL=""
				globalJSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						name: 'Nice Global JS',
					},
				]}
				portletNamespace=""
				selectGlobalJSCETsEventName=""
			/>
		);

		await screen.findByText('Nice Global JS');
	});

	it('renders a hidden input with the list of selected extensions', async () => {
		render(
			<GlobalJSCETsConfiguration
				globalJSCETSelectorURL=""
				globalJSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						name: 'Nice Global JS',
					},
					{
						cetExternalReferenceCode: 'anotherNiceId',
						name: 'Nice Global JS v2',
					},
				]}
				portletNamespace=""
				selectGlobalJSCETsEventName=""
			/>
		);

		await screen.findByDisplayValue('niceId,anotherNiceId');
	});

	it('opens a selection modal when "add" button is pressed', async () => {
		render(
			<GlobalJSCETsConfiguration
				globalJSCETSelectorURL=""
				globalJSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						name: 'Nice Global JS',
					},
				]}
				portletNamespace=""
				selectGlobalJSCETsEventName=""
			/>
		);

		userEvent.click(
			await screen.findByRole('button', {
				name: 'add-javascript-extensions',
			})
		);

		expect(openSelectionModal).toHaveBeenCalled();
	});

	it('removes duplicated extensions if any', async () => {
		openSelectionModal.mockImplementation(() => {});

		render(
			<GlobalJSCETsConfiguration
				globalJSCETSelectorURL=""
				globalJSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						name: 'Nice Global JS',
					},
				]}
				portletNamespace=""
				selectGlobalJSCETsEventName=""
			/>
		);

		userEvent.click(
			await screen.findByRole('button', {
				name: 'add-javascript-extensions',
			})
		);

		expect(openSelectionModal).toHaveBeenCalledTimes(1);

		expect(openSelectionModal).toHaveBeenCalledWith(
			expect.objectContaining({
				onSelect: expect.any(Function),
			})
		);

		const [[{onSelect}]] = openSelectionModal.mock.calls;

		act(() => {
			onSelect({
				value: [
					JSON.stringify({
						cetExternalReferenceCode: 'niceId',
						name: 'Nice Global JS',
					}),
					JSON.stringify({
						cetExternalReferenceCode: 'someNiceId',
						name: 'Some Nice Global JS',
					}),
				],
			});
		});

		await screen.findByDisplayValue('niceId,someNiceId');
	});

	it('allows removing extensions by pressing dropdown "remove" button', async () => {
		render(
			<GlobalJSCETsConfiguration
				globalJSCETSelectorURL=""
				globalJSCETs={[
					{
						cetExternalReferenceCode: 'niceId',
						name: 'Nice Global JS',
					},
				]}
				portletNamespace=""
				selectGlobalJSCETsEventName=""
			/>
		);

		userEvent.click(
			await screen.findByRole('button', {name: 'show-options'})
		);

		userEvent.click(
			await findByRole(
				await screen.findByRole('menu', {name: 'show-options'}),
				'button',
				{name: 'delete'}
			)
		);

		await screen.findByText('no-javascript-extensions-were-loaded');
	});
});
