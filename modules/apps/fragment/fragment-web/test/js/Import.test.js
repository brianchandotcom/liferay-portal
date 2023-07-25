/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import Import from '../../src/main/resources/META-INF/resources/js/Import';

describe('ImportModal', () => {
	beforeAll(() => {
		jest.useFakeTimers();
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

	it('renders file input and overwrite checkbox', () => {
		render(<Import portletNamespace="namespace" />);

		act(() => {
			jest.runAllTimers();
		});

		expect(screen.getByLabelText('file')).toBeInTheDocument();
		expect(screen.getByTestId('namespacefile')).toBeInTheDocument();

		expect(
			screen.getByLabelText('overwrite-existing-entries')
		).toBeInTheDocument();
		expect(screen.getByTestId('namespaceoverwrite')).toBeInTheDocument();
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

		fireEvent.change(screen.getByTestId('namespacefile'), {
			target: {files: [file]},
		});

		expect(button.disabled).toBeFalsy();
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

		fireEvent.change(screen.getByTestId('namespacefile'), {
			target: {files: [file]},
		});

		expect(button.disabled).toBeTruthy();
		expect(
			screen.getByText('only-zip-files-are-allowed')
		).toBeInTheDocument();
	});
});
