/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom';

import {IconSelector} from '../';

// `IconSelector` fetches the spritemap on mount to extract the available icon
// names. The indicator does not depend on that fetch, so a no-op stub keeps
// jsdom happy without affecting what these tests assert.

beforeAll(() => {
	(global as any).fetch = jest.fn(() =>
		Promise.resolve({
			text: () => Promise.resolve('<svg></svg>'),
		})
	);
});

describe('IconSelector keyboard arrows indicator', () => {
	afterEach(cleanup);

	it('does not render the indicator by default', () => {
		const {getByRole} = render(<IconSelector spritemap="/foo/bar.svg" />);

		userEvent.click(getByRole('button'));

		expect(
			document.body.querySelector('.clay-keyboard-arrows-indicator')
		).not.toBeInTheDocument();
	});

	it('renders the floating indicator with direction "vertical" when enabled', () => {
		const {getByRole} = render(
			<IconSelector
				displayKeyboardArrowsIndicator
				spritemap="/foo/bar.svg"
			/>
		);

		userEvent.click(getByRole('button'));

		const indicator = document.body.querySelector(
			'.clay-keyboard-arrows-indicator'
		);

		expect(indicator).toBeInTheDocument();
		expect(indicator).toHaveClass('clay-keyboard-arrows-vertical');
		expect(indicator).toHaveClass(
			'clay-keyboard-arrows-indicator-floating'
		);
	});

	it('uses the localized label on the indicator', () => {
		const {getByRole} = render(
			<IconSelector
				displayKeyboardArrowsIndicator
				keyboardArrowsIndicatorLabel="Use up and down arrow keys to step through the icons"
				spritemap="/foo/bar.svg"
			/>
		);

		userEvent.click(getByRole('button'));

		expect(
			document.body.querySelector('.clay-keyboard-arrows-indicator')
		).toHaveAttribute(
			'aria-label',
			'Use up and down arrow keys to step through the icons'
		);
	});
});
