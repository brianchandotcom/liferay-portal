/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom';

import LoginCarousel from '../../src/main/resources/META-INF/resources/js/components/LoginCarousel';

const INTERVAL_MS = 7000;

const SLIDE_TITLES = [
	'structured-content-for-every-channel',
	'all-your-assets-one-smart-library',
	'collaboration-without-friction',
	'control-every-piece-of-content',
];

const [FIRST_SLIDE_TITLE, SECOND_SLIDE_TITLE, THIRD_SLIDE_TITLE] = SLIDE_TITLES;

describe('LoginCarousel', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		jest.useFakeTimers();
	});

	afterEach(() => {
		jest.useRealTimers();
	});

	it('renders a navigation dot for every slide and exposes only the active one', () => {
		render(<LoginCarousel />);

		SLIDE_TITLES.forEach((title) => {
			expect(screen.getByText(title)).toBeInTheDocument();
			expect(
				screen.getByRole('button', {description: title})
			).toBeInTheDocument();
		});

		expect(screen.getAllByRole('heading')).toHaveLength(1);
		expect(
			screen.getByRole('button', {current: true})
		).toHaveAccessibleDescription(FIRST_SLIDE_TITLE);
	});

	it('shows a pause control while the carousel is playing', () => {
		render(<LoginCarousel />);

		expect(screen.getByRole('button', {name: 'Pause'})).toBeInTheDocument();
	});

	it('toggles between pausing and resuming when the control is clicked', async () => {
		const user = userEvent.setup({advanceTimers: jest.advanceTimersByTime});

		render(<LoginCarousel />);

		await user.click(screen.getByRole('button', {name: 'Pause'}));

		expect(
			screen.getByRole('button', {name: 'Resume'})
		).toBeInTheDocument();

		await user.click(screen.getByRole('button', {name: 'Resume'}));

		expect(screen.getByRole('button', {name: 'Pause'})).toBeInTheDocument();
	});

	it('advances to the next slide automatically', async () => {
		render(<LoginCarousel />);

		expect(screen.getByRole('heading')).toHaveTextContent(
			FIRST_SLIDE_TITLE
		);

		await waitFor(
			() =>
				expect(screen.getByRole('heading')).toHaveTextContent(
					SECOND_SLIDE_TITLE
				),
			{interval: INTERVAL_MS, timeout: INTERVAL_MS * 2}
		);
	});

	it('stops advancing while paused', async () => {
		const user = userEvent.setup({advanceTimers: jest.advanceTimersByTime});

		render(<LoginCarousel />);

		await user.click(screen.getByRole('button', {name: 'Pause'}));

		jest.advanceTimersByTime(INTERVAL_MS * 2);

		expect(screen.getByRole('heading')).toHaveTextContent(
			FIRST_SLIDE_TITLE
		);
	});

	it('navigates to a slide when its dot is clicked', async () => {
		const user = userEvent.setup({advanceTimers: jest.advanceTimersByTime});

		render(<LoginCarousel />);

		await user.click(
			screen.getByRole('button', {description: THIRD_SLIDE_TITLE})
		);

		expect(screen.getByRole('heading')).toHaveTextContent(
			THIRD_SLIDE_TITLE
		);
		expect(
			screen.getByRole('button', {current: true})
		).toHaveAccessibleDescription(THIRD_SLIDE_TITLE);
	});

	it('has no accessibility violations', async () => {
		jest.useRealTimers();

		const {container} = render(<LoginCarousel />);

		await checkAccessibility({context: container});
	});
});
