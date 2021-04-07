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

import {act, cleanup, render} from '@testing-library/react';
import {PageProvider} from 'data-engine-js-components-web';
import React from 'react';

import DocumentLibrary from '../../../src/main/resources/META-INF/resources/DocumentLibrary/DocumentLibrary.es';

const globalLanguageDirection = Liferay.Language.direction;

const spritemap = 'icons.svg';

const defaultDocumentLibraryConfig = {
	name: 'uploadField',
	spritemap,
};

const DocumentLibraryWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<DocumentLibrary {...props} />
	</PageProvider>
);

describe('Field DocumentLibrary', () => {
	// eslint-disable-next-line no-console
	const originalWarn = console.warn;

	beforeAll(() => {
		// eslint-disable-next-line no-console
		console.warn = (...args) => {
			if (/DataProvider: Trying/.test(args[0])) {
				return;
			}
			originalWarn.call(console, ...args);
		};

		Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	afterAll(() => {
		// eslint-disable-next-line no-console
		console.warn = originalWarn;

		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponseOnce(JSON.stringify({}));
	});

	it('is not readOnly', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				readOnly={false}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is readOnly', () => {
		render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				readOnly={true}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const uploadFieldInput = document.getElementById(
			'uploadFieldinputFile'
		);

		expect(uploadFieldInput.disabled).toBeTruthy();

		const uploadFieldInputSelectButton = document.querySelector(
			'.select-button'
		);

		expect(uploadFieldInputSelectButton.disabled).toBeTruthy();
	});

	it('is readOnly when allowed for guest users', () => {
		const mockIsSignedIn = jest.fn();

		Liferay.ThemeDisplay.isSignedIn = mockIsSignedIn;

		render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				allowGuestUsers={true}
				readOnly={true}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const guestUploadFieldInput = document.getElementById(
			'uploadFieldinputFileGuestUpload'
		);

		expect(guestUploadFieldInput.disabled).toBeTruthy();

		const guestUploadFieldInputLabel = document.querySelector(
			'.select-button'
		);

		expect(guestUploadFieldInputLabel.classList).toContain('disabled');
	});

	it('has a helptext', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				tip="Type something"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				id="ID"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				label="label"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				placeholder="Placeholder"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is not required', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				required={false}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				label="text"
				showLabel
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		const {container} = render(
			<DocumentLibraryWithProvider {...defaultDocumentLibraryConfig} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				value='{"id":"123"}'
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('shows guest upload field if allowGuestUsers property is enabled', () => {
		const mockIsSignedIn = jest.fn();

		Liferay.ThemeDisplay.isSignedIn = mockIsSignedIn;

		render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				allowGuestUsers={true}
				value='{"id":"123"}'
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const guestUploadFieldInput = document.getElementById(
			'uploadFieldinputFileGuestUpload'
		);

		expect(guestUploadFieldInput).not.toBe(null);
	});

	it('hide guest upload field if allowGuestUsers property is disabled', () => {
		const mockIsSignedIn = jest.fn();

		Liferay.ThemeDisplay.isSignedIn = mockIsSignedIn;

		render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				allowGuestUsers={false}
				value='{"id":"123"}'
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const guestUploadFieldInput = document.getElementById(
			'uploadFieldinputFileGuestUpload'
		);

		expect(guestUploadFieldInput).toBe(null);
	});

	it('disables guest upload field if maximumSubmissionLimitReached property is true', () => {
		const mockIsSignedIn = jest.fn();

		Liferay.ThemeDisplay.isSignedIn = mockIsSignedIn;

		render(
			<DocumentLibraryWithProvider
				{...defaultDocumentLibraryConfig}
				allowGuestUsers={true}
				maximumSubmissionLimitReached={true}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const guestUploadFieldInput = document.getElementById(
			'uploadFieldinputFileGuestUpload'
		);

		expect(guestUploadFieldInput.disabled).toBeTruthy();

		const guestUploadFieldInputLabel = document.querySelector(
			'.select-button'
		);

		expect(guestUploadFieldInputLabel.classList).toContain('disabled');
	});
});
