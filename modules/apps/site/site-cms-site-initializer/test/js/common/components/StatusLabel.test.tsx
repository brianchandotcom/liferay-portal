/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import StatusLabel from '../../../../src/main/resources/META-INF/resources/js/common/components/StatusLabel';
import {MS_PER_DAY} from '../../../../src/main/resources/META-INF/resources/js/common/utils/constants';

const NOW = new Date('2026-04-21T10:00:00Z');

describe('StatusLabel', () => {
	it.each([
		['approved', 'success'],
		['denied', 'danger'],
		['draft', 'secondary'],
		['expired', 'danger'],
		['in-trash', 'info'],
		['inactive', 'secondary'],
		['incomplete', 'warning'],
		['pending', 'info'],
		['scheduled', 'info'],
	] as const)('renders with the "%s" status', (label, displayType) => {
		render(<StatusLabel label={label} />);

		const labelElement = screen.getByText(label);

		expect(labelElement).toBeInTheDocument();
		expect(labelElement.parentElement).toHaveClass(
			'label',
			`label-${displayType}`
		);
	});

	describe('Expiring Soon badge', () => {
		beforeAll(() => {
			jest.useFakeTimers();
			jest.setSystemTime(NOW);
		});

		afterAll(() => {
			jest.useRealTimers();
		});

		it('is not shown when no expirationDate is provided', () => {
			render(<StatusLabel label="approved" />);

			expect(screen.queryByText('expiring-soon')).not.toBeInTheDocument();
		});

		it('is not shown when the expiration date is beyond the threshold', () => {
			const beyond = new Date(
				NOW.getTime() + 8 * MS_PER_DAY
			).toISOString();

			render(<StatusLabel expirationDate={beyond} label="approved" />);

			expect(screen.queryByText('expiring-soon')).not.toBeInTheDocument();
		});

		it('is shown when an approved item is within the threshold', () => {
			const within = new Date(
				NOW.getTime() + 3 * MS_PER_DAY
			).toISOString();

			render(<StatusLabel expirationDate={within} label="approved" />);

			expect(screen.getByText('approved')).toBeInTheDocument();
			expect(screen.getByText('expiring-soon')).toBeInTheDocument();
		});

		it('exposes focus, tooltip and aria-label attributes', () => {
			const within = new Date(
				NOW.getTime() + 3 * MS_PER_DAY
			).toISOString();

			render(<StatusLabel expirationDate={within} label="approved" />);

			const badge = screen
				.getByText('expiring-soon')
				.closest('span[tabindex]') as HTMLElement;

			expect(badge).not.toBeNull();
			expect(badge).toHaveAttribute('tabindex', '0');
			expect(badge.getAttribute('title')).toBeTruthy();
			expect(badge.getAttribute('aria-label')).toBeTruthy();
		});

		it('is not shown for non-approved statuses even within the threshold', () => {
			const within = new Date(
				NOW.getTime() + 3 * MS_PER_DAY
			).toISOString();

			render(<StatusLabel expirationDate={within} label="draft" />);

			expect(screen.getByText('draft')).toBeInTheDocument();
			expect(screen.queryByText('expiring-soon')).not.toBeInTheDocument();
		});

		it('renders only the Expired label (no Approved, no Expiring Soon) once the expiration date is reached', () => {
			const past = new Date(NOW.getTime() - MS_PER_DAY).toISOString();

			const {container} = render(
				<StatusLabel expirationDate={past} label="expired" />
			);

			expect(screen.getByText('expired')).toBeInTheDocument();
			expect(screen.queryByText('approved')).not.toBeInTheDocument();
			expect(screen.queryByText('expiring-soon')).not.toBeInTheDocument();
			expect(container.querySelectorAll('.label')).toHaveLength(1);
		});
	});

	describe('Expired tooltip', () => {
		beforeAll(() => {
			jest.useFakeTimers();
			jest.setSystemTime(NOW);
		});

		afterAll(() => {
			jest.useRealTimers();
		});

		it('exposes focus, tooltip and aria-label attributes on the Expired label', () => {
			const past = new Date(NOW.getTime() - MS_PER_DAY).toISOString();

			render(<StatusLabel expirationDate={past} label="expired" />);

			const badge = screen
				.getByText('expired')
				.closest('span[tabindex]') as HTMLElement;

			expect(badge).not.toBeNull();
			expect(badge).toHaveAttribute('tabindex', '0');
			expect(badge.getAttribute('title')).toBeTruthy();
			expect(badge.getAttribute('aria-label')).toBeTruthy();
		});

		it('falls back to the plain Expired label when no expirationDate is provided', () => {
			render(<StatusLabel label="expired" />);

			const badge = screen.getByText('expired').closest('span[tabindex]');

			expect(badge).toBeNull();
		});
	});
});
