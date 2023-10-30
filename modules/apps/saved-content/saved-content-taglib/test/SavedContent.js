/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {
	fetch as mockedFetch,
	openToast as mockedOpenToast,
} from 'frontend-js-web';

import SavedContent from '../src/main/resources/META-INF/resources/js/SavedContent';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	fetch: jest.fn(),
	openToast: jest.fn(),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

function renderComponent(props) {
	return render(
		<SavedContent
			className="className"
			classPK="classPK"
			contentTitle="contentTitle"
			enabled
			mySavedContentURL="/MY-saved-content-url"
			portletNamespace="namespace"
			savedContentURL="/saved-content-url"
			{...props}
		/>
	);
}

describe('SavedContent', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	describe('render', () => {
		it('disabled', () => {
			const {container} = renderComponent({enabled: false});
			const button = screen.getByRole('button');

			expect(container).toMatchSnapshot();
			expect(button).toBeDisabled();
		});

		it('enabled', () => {
			const {container} = renderComponent();
			const button = screen.getByRole('button');

			expect(container).toMatchSnapshot();
			expect(button).toBeEnabled();
		});
	});

	it('calls backend with the given config', async () => {
		mockedFetch.mockReturnValue(
			Promise.resolve({
				json: () => Promise.resolve({saved: true}),
			})
		);

		renderComponent();

		await act(async () => {
			userEvent.click(screen.getByRole('button'));
		});

		expect(mockedFetch).toHaveBeenCalledTimes(1);

		const [[url, {body}]] = mockedFetch.mock.calls;

		expect(url).toBe('/saved-content-url');
		expect(body.get('namespaceclassName')).toBe('className');
		expect(body.get('namespaceclassPK')).toBe('classPK');
	});

	it('saves the content and nofity', async () => {
		mockedFetch.mockReturnValue(
			Promise.resolve({
				json: () => Promise.resolve({saved: true}),
			})
		);

		renderComponent();

		const button = screen.getByRole('button', {
			name: 'save-contentTitle',
		});

		await act(async () => {
			userEvent.click(button);
		});

		expect(button).toHaveAttribute('aria-label', 'remove-contentTitle');
		expect(mockedOpenToast.mock.calls[0][0].message).toBe(
			'contentTitle-has-been-saved-in-x'
		);
	});

	it('removes the content and nofity', async () => {
		mockedFetch.mockReturnValue(
			Promise.resolve({
				json: () => Promise.resolve({saved: false}),
			})
		);

		renderComponent({saved: true});

		const button = screen.getByRole('button', {
			name: 'remove-contentTitle',
		});

		await act(async () => {
			userEvent.click(button);
		});

		expect(button).toHaveAttribute('aria-label', 'save-contentTitle');
		expect(mockedOpenToast.mock.calls[0][0].message).toBe(
			'contentTitle-has-been-successfully-removed-from-x'
		);
	});
});
