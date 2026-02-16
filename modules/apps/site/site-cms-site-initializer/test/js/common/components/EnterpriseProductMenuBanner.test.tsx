/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import EnterpriseProductMenuBanner from '../../../../src/main/resources/META-INF/resources/js/common/components/EnterpriseProductMenuBanner';

describe('EnterpriseProductMenuBanner', () => {
	it('renders the banner with all its elements', () => {
		render(<EnterpriseProductMenuBanner />);

		expect(screen.getByText('enterprise')).toBeInTheDocument();

		expect(
			screen.getByText('get-more-with-enterprise')
		).toBeInTheDocument();

		expect(
			screen.getByText(
				'share-your-email-and-we-ll-show-you-what-you-can-unlock-with-our-enterprise-plan'
			)
		).toBeInTheDocument();

		const detailsLink = screen.getByRole('link', {
			name: 'get-enterprise-details',
		});

		expect(detailsLink).toBeInTheDocument();
		expect(detailsLink).toHaveAttribute(
			'href',
			'https://www.liferay.com/en/contact-sales'
		);
	});
});
