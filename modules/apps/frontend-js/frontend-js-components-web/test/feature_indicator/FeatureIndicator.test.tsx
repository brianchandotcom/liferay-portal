/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import {FeatureIndicator} from '../../src/main/resources/META-INF/resources';

describe('FeatureIndicator', () => {
	describe('Icon only', () => {
		it('does NOT render the text label when iconOnly', () => {
			render(
				<FeatureIndicator
					iconOnly
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="beta"
				/>
			);

			expect(screen.queryByText('beta')).not.toBeInTheDocument();
		});

		it('still renders the icon when iconOnly', () => {
			const {container} = render(
				<FeatureIndicator iconOnly type="beta" />
			);

			expect(
				container.querySelector('svg.lexicon-icon-info-circle-open')
			).toBeInTheDocument();
		});

		it('does NOT have "inline-item-after" class on the icon span when iconOnly', () => {
			const {container} = render(
				<FeatureIndicator
					iconOnly
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="beta"
				/>
			);

			expect(container.querySelector('.inline-item')).not.toHaveClass(
				'inline-item-after'
			);
		});

		it('exposes an accessible name on the non-interactive icon-only badge', () => {
			const {container} = render(
				<FeatureIndicator iconOnly type="beta" />
			);

			const badge = container.querySelector('.badge[role="img"]');

			expect(badge).toBeInTheDocument();
			expect(badge).toHaveAttribute('aria-label', 'beta');
		});

		it('exposes an accessible name on the interactive icon-only button', () => {
			render(
				<FeatureIndicator
					iconOnly
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="beta"
				/>
			);

			expect(
				screen.getByRole('button', {name: 'beta'})
			).toBeInTheDocument();
		});
	});

	describe('Type: Maintenance', () => {
		it('renders the text label when not iconOnly', () => {
			render(<FeatureIndicator type="maintenance" />);

			expect(screen.getByText('maintenance')).toBeInTheDocument();
		});
	});

	describe('Type: Beta (Standard Behavior)', () => {
		it('renders both label and icon for beta type', () => {
			render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="beta"
				/>
			);

			const label = screen.getByText('beta');

			expect(label).toBeInTheDocument();
			expect(label).toHaveClass('inline-item');
		});

		it('has "inline-item-after" class on the icon span when label is present', () => {
			const {container} = render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="beta"
				/>
			);

			expect(
				container.querySelector('span.inline-item-after')
			).toBeInTheDocument();
		});

		it('does NOT set a redundant aria-label when the label is visible', () => {
			render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="beta"
				/>
			);

			expect(screen.getByRole('button')).not.toHaveAttribute(
				'aria-label'
			);
		});
	});

	describe('Interactive Mode', () => {
		it('renders a button when interactive is true', () => {
			render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="beta"
				/>
			);

			expect(screen.getByRole('button')).toBeInTheDocument();
		});

		it('renders a badge when interactive is false', () => {
			const {container} = render(
				<FeatureIndicator interactive={false} type="beta" />
			);

			expect(container.querySelector('.badge')).toBeInTheDocument();
		});
	});
});
