/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {act, render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {NewImport} from '../../../../../src/main/resources/META-INF/resources/revamp/js/pages/import/NewImport';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/revamp/js/utils/getValidateLarFile',
	() => ({
		getValidateLarFile: jest.fn(() =>
			Promise.resolve({
				data: {
					errorMessages: [],
					success: true,
					tempFilePath: '/tmp/site.lar',
				},
				error: null,
			})
		),
	})
);

const renderComponent = () =>
	render(<NewImport backURL="/some/back/url" groupId={0} />);

let user: ReturnType<typeof userEvent.setup>;

const uploadFile = async (fileName = 'site.lar') => {
	jest.useFakeTimers();

	const fakeUser = userEvent.setup({
		advanceTimers: jest.advanceTimersByTime,
	});

	const file = new File(['test'], fileName, {type: '.lar'});
	const fileInput = document.querySelector(
		'input[type="file"]'
	) as HTMLInputElement;

	await fakeUser.upload(fileInput, file);

	act(() => {
		jest.runAllTimers();
	});

	jest.useRealTimers();
};

describe('NewImport', () => {
	beforeAll(() => {
		global.Liferay.Util.formatStorage = jest.fn(
			(bytes: number) => `${bytes} B`
		);
	});

	beforeEach(() => {
		user = userEvent.setup();
	});

	it('renders the FileSelectionStep with the Name field', async () => {
		const {container} = renderComponent();

		expect(screen.getByLabelText(/^name/i)).toBeInTheDocument();

		expect(screen.getByText('file-upload')).toBeInTheDocument();

		expect(
			screen.getByText('select-and-upload-your-prepared-file')
		).toBeInTheDocument();

		expect(
			screen.getByRole('button', {name: /drag-and-drop-to-upload/i})
		).toBeInTheDocument();

		await checkAccessibility({context: container});
	});

	it('shows a required error when the Name field is left empty on blur', async () => {
		renderComponent();

		const nameInput = screen.getByLabelText(/^name/i);

		await user.click(nameInput);
		nameInput.blur();

		await screen.findByText('this-field-is-required');
	});

	it('auto-fills the Name field with the uploaded file name when Name is empty', async () => {
		renderComponent();

		const nameInput = screen.getByLabelText(/^name/i) as HTMLInputElement;

		expect(nameInput).toHaveValue('');

		await uploadFile('site.lar');

		await waitFor(() => {
			expect(nameInput).toHaveValue('site');
		});
	});

	it('preserves the user-provided Name when a file is uploaded', async () => {
		renderComponent();

		const nameInput = screen.getByLabelText(/^name/i) as HTMLInputElement;

		await user.type(nameInput, 'My custom import');

		await uploadFile('site.lar');

		await waitFor(() => {
			expect(nameInput).toHaveValue('My custom import');
		});
	});

	it('re-fills the Name field with the new file name when Name has been cleared', async () => {
		renderComponent();

		const nameInput = screen.getByLabelText(/^name/i) as HTMLInputElement;

		await uploadFile('first.lar');

		await waitFor(() => {
			expect(nameInput).toHaveValue('first');
		});

		await user.clear(nameInput);

		await user.click(
			await screen.findByRole('button', {name: /remove-file/i})
		);

		await uploadFile('second.lar');

		await waitFor(() => {
			expect(nameInput).toHaveValue('second');
		});
	});

	it('does not re-fill the Name after the user manually clears it while the same file is still selected', async () => {
		renderComponent();

		const nameInput = screen.getByLabelText(/^name/i) as HTMLInputElement;

		await uploadFile('site.lar');

		await waitFor(() => {
			expect(nameInput).toHaveValue('site');
		});

		await user.clear(nameInput);

		expect(nameInput).toHaveValue('');
	});

	it('does not auto-fill the Name when the user clears a name they typed before uploading the file', async () => {
		renderComponent();

		const nameInput = screen.getByLabelText(/^name/i) as HTMLInputElement;

		await user.type(nameInput, 'My custom import');

		await uploadFile('site.lar');

		await waitFor(() => {
			expect(nameInput).toHaveValue('My custom import');
		});

		await user.clear(nameInput);

		expect(nameInput).toHaveValue('');
	});
});
