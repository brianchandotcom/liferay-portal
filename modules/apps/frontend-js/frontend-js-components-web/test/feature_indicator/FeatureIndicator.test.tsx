/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import {FeatureIndicator} from '../../src/main/resources/META-INF/resources';

describe('FeatureIndicator', () => {
	describe('Type: Maintenance', () => {
		it('does NOT render the text label when type is maintenance', () => {
			render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="maintenance"
				/>
			);

			const label = screen.queryByText('maintenance');
			expect(label).not.toBeInTheDocument();
		});

		it('renders the info icon when type is maintenance', () => {
			const {container} = render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="maintenance"
				/>
			);

			const icon = container.querySelector(
				'svg.lexicon-icon-info-circle-open'
			);
			expect(icon).toBeInTheDocument();
		});

		it('does NOT have "inline-item-after" class on the icon span when maintenance', () => {
			const {container} = render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="maintenance"
				/>
			);

			const iconSpan = container.querySelector('.inline-item');
			expect(iconSpan).not.toHaveClass('inline-item-after');
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

			const iconSpan = container.querySelector('span.inline-item-after');
			expect(iconSpan).toBeInTheDocument();
		});
	});

	describe('Interactive Mode', () => {
		it('renders a button when interactive is true', () => {
			render(
				<FeatureIndicator
					interactive={true}
					learnResourceContext="https://learn.liferay.com/test-url"
					type="maintenance"
				/>
			);

			const button = screen.getByRole('button');
			expect(button).toBeInTheDocument();
		});

		it('renders a badge when interactive is false', () => {
			const {container} = render(
				<FeatureIndicator interactive={false} type="maintenance" />
			);

			const badge = container.querySelector('.badge');
			expect(badge).toBeInTheDocument();
		});
	});
});
