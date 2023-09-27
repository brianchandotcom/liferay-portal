/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, fireEvent, render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {navigate} from 'frontend-js-web';
import React from 'react';

import {Import} from '../../../src/main/resources/META-INF/resources/js';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	fetch: () => Promise.resolve({json: () => ({valid: false})}),
	navigate: jest.fn(),
}));

describe('Import', () => {
	beforeAll(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	it('renders text informing the user should upload a ZIP file', () => {
		render(<Import portletNamespace="namespace" />);

		act(() => {
			jest.runAllTimers();
		});

		expect(
			screen.getByText(
				'select-a-zip-file-containing-one-or-multiple-entries'
			)
		).toBeInTheDocument();
	});

	it('renders file input', () => {
		render(<Import portletNamespace="namespace" />);

		act(() => {
			jest.runAllTimers();
		});

		expect(screen.getByLabelText('file-upload')).toBeInTheDocument();
	});

	it('renders submit button disabled until file input has a valid value', () => {
		render(<Import portletNamespace="namespace" />);

		act(() => {
			jest.runAllTimers();
		});

		const button = screen.getByRole('button', {name: /import/i});
		expect(button.disabled).toBeTruthy();

		const file = new File(['(⌐□_□)'], 'example.zip', {
			type: 'application/zip',
		});

		fireEvent.change(screen.getByLabelText('file-upload'), {
			target: {files: [file]},
		});

		expect(button.disabled).toBeFalsy();
	});

	it('renders cancel button enabled', () => {
		render(
			<Import backURL="http://test.com" portletNamespace="namespace" />
		);

		act(() => {
			jest.runAllTimers();
		});

		const button = screen.getByRole('button', {name: /cancel/i});
		expect(button.disabled).toBeFalsy();

		userEvent.click(button);

		expect(navigate).toHaveBeenCalled();
	});

	it('shows required validation when a file with an invalid extension is introduced', () => {
		render(<Import portletNamespace="namespace" />);

		act(() => {
			jest.runAllTimers();
		});

		const button = screen.getByRole('button', {name: /import/i});

		const file = new File(['(⌐□_□)'], 'example.png', {
			type: 'image/png',
		});

		fireEvent.change(screen.getByLabelText('file-upload'), {
			target: {files: [file]},
		});

		expect(button.disabled).toBeTruthy();
		expect(
			screen.getByText('only-zip-files-are-allowed')
		).toBeInTheDocument();
	});

	it('renders help link', () => {
		const {getByText} = render(
			<Import
				helpLink={{href: 'http://example.com', message: 'Learn more'}}
				portletNamespace="namespace"
			/>
		);

		expect(getByText('Learn more')).toBeInTheDocument();
	});

	it('renders Import Options modal', async () => {
		const {getByText} = render(<Import portletNamespace="namespace" />);

		const button = screen.getByRole('button', {name: /import/i});
		const file = new File(['(⌐□_□)'], 'example.zip', {
			type: 'image/png',
		});

		fireEvent.change(screen.getByLabelText('file-upload'), {
			target: {files: [file]},
		});

		expect(button.disabled).toBeFalsy();

		userEvent.click(button);

		await waitFor(() =>
			expect(getByText('import-options')).toBeInTheDocument()
		);
	});
});
