/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openSelectionModal, openToast} from 'frontend-js-components-web';
import React from 'react';

import DLFolderSelector from '../../../src/main/resources/META-INF/resources/js/document_library/DLFolderSelector';

jest.mock('frontend-js-components-web', () => ({
	...jest.requireActual('frontend-js-components-web'),
	openSelectionModal: jest.fn(),
	openToast: jest.fn(),
}));

function getErrorMessages(length) {
	return Array.from(
		{length},
		(_, index) => `A file entry already exists with title ${index}`
	);
}

async function copy(json) {
	fetch.mockResponseOnce(JSON.stringify(json));

	openSelectionModal.mockImplementation(({onSelect}) =>
		onSelect({
			repositoryid: 1,
			repositoryname: 'Repository',
			resourceid: 2,
			resourcename: 'Folder',
		})
	);

	render(
		<DLFolderSelector
			copyActionURL="http://localhost/copy"
			dlObjectIds={[1]}
			portletNamespace="_portletNamespace_"
			redirect="redirect"
			selectionModalURL="selectionModalURL"
			size={1}
			sourceRepositoryId={1}
		/>
	);

	await userEvent.click(screen.getByRole('button', {name: 'select'}));
	await userEvent.click(screen.getByRole('button', {name: 'copy'}));

	await waitFor(() => expect(openToast).toHaveBeenCalledTimes(1));

	return openToast.mock.calls[0][0];
}

describe('DLFolderSelector', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('lists the error message of one failed item', async () => {
		const toast = await copy({
			errorMessages: getErrorMessages(1),
			failedItems: 1,
			successItems: 0,
		});

		expect(toast.type).toBe('danger');
		expect(toast.message).toContain('x-item-could-not-be-copied');
		expect(toast.message).toContain(
			'<li>A file entry already exists with title 0</li>'
		);
	});

	it('lists the error messages of ten failed items', async () => {
		const errorMessages = getErrorMessages(10);

		const toast = await copy({
			errorMessages,
			failedItems: 10,
			successItems: 0,
		});

		expect(toast.type).toBe('danger');
		expect(toast.message).toContain('x-items-could-not-be-copied');

		errorMessages.forEach((errorMessage) =>
			expect(toast.message).toContain(`<li>${errorMessage}</li>`)
		);
	});

	it('shows only the count of eleven failed items', async () => {
		const toast = await copy({
			errorMessages: getErrorMessages(11),
			failedItems: 11,
			successItems: 0,
		});

		expect(toast.type).toBe('danger');
		expect(toast.message).toContain('x-items-could-not-be-copied');
		expect(toast.message).not.toContain('<li>');
	});
});
