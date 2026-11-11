/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {act, render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import RenameLayoutPageTemplateEntryDesignLibraryModalContent from '../../src/main/resources/META-INF/resources/js/RenameLayoutPageTemplateEntryDesignLibraryModalContent';

const mockFetch = jest.fn();

jest.mock('frontend-js-web', () => ({
	fetch: (url: string, options: any) => mockFetch(url, options),
}));

const UPDATE_ACTION = {href: 'http://localhost/rename', method: 'PATCH'};

function renderModal() {
	const closeModal = jest.fn();
	const loadData = jest.fn();

	const {container} = render(
		<RenameLayoutPageTemplateEntryDesignLibraryModalContent
			closeModal={closeModal}
			itemData={{
				actions: {update: UPDATE_ACTION},
				embedded: {name: 'Current name'},
			}}
			loadData={loadData}
			title="rename-master-page"
		/>
	);

	return {closeModal, container, loadData};
}

describe('RenameLayoutPageTemplateEntryDesignLibraryModalContent', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('shows the title', () => {
		renderModal();

		expect(screen.getByText('rename-master-page')).toBeInTheDocument();
	});

	it('shows the current name', () => {
		renderModal();

		expect(screen.getByRole('textbox', {name: /name/})).toHaveValue(
			'Current name'
		);
	});

	it('disables the save button when the name is empty', async () => {
		renderModal();

		await userEvent.clear(screen.getByRole('textbox', {name: /name/}));

		expect(screen.getByRole('button', {name: 'save'})).toBeDisabled();
	});

	it('sends the new name through the update action, closes the modal and reloads the data', async () => {
		mockFetch.mockResolvedValue({json: async () => ({}), ok: true});

		const {closeModal, loadData} = renderModal();

		await userEvent.clear(screen.getByRole('textbox', {name: /name/}));
		await userEvent.type(
			screen.getByRole('textbox', {name: /name/}),
			'New name'
		);
		await userEvent.click(screen.getByRole('button', {name: 'save'}));

		await waitFor(() => expect(closeModal).toHaveBeenCalledTimes(1));

		expect(mockFetch).toHaveBeenCalledTimes(1);

		const [url, options] = mockFetch.mock.calls[0];

		expect(url).toBe(UPDATE_ACTION.href);
		expect(options.method).toBe(UPDATE_ACTION.method);
		expect(JSON.parse(options.body)).toEqual({name: 'New name'});

		expect(loadData).toHaveBeenCalledTimes(1);
	});

	it('shows the error under the name and keeps the modal open when the rename fails', async () => {
		mockFetch.mockResolvedValue({
			json: async () => ({title: 'Duplicate name'}),
			ok: false,
		});

		const {closeModal, loadData} = renderModal();

		await userEvent.click(screen.getByRole('button', {name: 'save'}));

		expect(await screen.findByText('Duplicate name')).toBeInTheDocument();

		expect(closeModal).not.toHaveBeenCalled();
		expect(loadData).not.toHaveBeenCalled();
	});

	it('does not send a blank name when the form is submitted with Enter', async () => {
		const {closeModal} = renderModal();

		await userEvent.clear(screen.getByRole('textbox', {name: /name/}));
		await userEvent.type(
			screen.getByRole('textbox', {name: /name/}),
			'{enter}'
		);

		expect(closeModal).not.toHaveBeenCalled();
		expect(mockFetch).not.toHaveBeenCalled();
	});

	it('sends the new name once when the form is submitted twice with Enter', async () => {
		mockFetch.mockReturnValue(
			new Promise((resolve) =>
				setTimeout(
					() => resolve({json: async () => ({}), ok: true}),
					50
				)
			)
		);

		const {closeModal} = renderModal();

		await userEvent.type(
			screen.getByRole('textbox', {name: /name/}),
			'{enter}{enter}'
		);

		await waitFor(() => expect(closeModal).toHaveBeenCalledTimes(1));

		expect(mockFetch).toHaveBeenCalledTimes(1);
	});

	it('has no accessibility violations', async () => {
		const {container} = renderModal();

		await act(async () => {
			await checkAccessibility({
				bestPractices: true,
				context: container,
			});
		});
	});
});
