/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import EnterpriseFeatureIndicator from '../../../../src/main/resources/META-INF/resources/js/common/components/EnterpriseFeatureIndicator';

describe('EnterpriseFeatureIndicator', () => {
	const assertBaseComponent = () => {
		expect(screen.getByText('enterprise')).toBeInTheDocument();
		expect(
			document.querySelector('.lexicon-icon-crown')
		).toBeInTheDocument();
	};

	it('renders the component without a tooltip', () => {
		render(<EnterpriseFeatureIndicator />);

		assertBaseComponent();

		expect(
			screen.queryByText('get-more-with-enterprise')
		).not.toBeInTheDocument();
	});

	it('renders the component with a tooltip', async () => {
		render(<EnterpriseFeatureIndicator showTooltip />);

		assertBaseComponent();

		await userEvent.hover(screen.getByText('enterprise'), {delay: null});

		expect(
			await screen.findByText('get-more-with-enterprise')
		).toBeInTheDocument();

		expect(
			screen.getByText(
				'this-feature-is-only-available-on-the-enterprise-subscription'
			)
		).toBeInTheDocument();

		const detailsLink = screen.getByRole('link', {
			name: 'x-opens-new-window',
		});
		expect(detailsLink).toBeInTheDocument();
		expect(detailsLink).toHaveAttribute(
			'href',
			'https://www.liferay.com/en/contact-sales'
		);

		await userEvent.unhover(screen.getByText('enterprise'), {delay: null});

		await waitFor(() => {
			expect(
				screen.queryByText('get-more-with-enterprise')
			).not.toBeInTheDocument();
		});
	});
});
