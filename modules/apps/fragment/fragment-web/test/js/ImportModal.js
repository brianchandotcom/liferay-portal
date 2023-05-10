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
import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import ImportModal from '../../src/main/resources/META-INF/resources/js/ImportModal';

describe('ImportModal', () => {
	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('renders text informing the user should upload a ZIP file', () => {
		render(<ImportModal portletNamespace="namespace" />);

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
		render(<ImportModal portletNamespace="namespace" />);

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
		render(<ImportModal portletNamespace="namespace" />);

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
		render(<ImportModal portletNamespace="namespace" />);

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
