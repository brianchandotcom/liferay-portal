/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import EnterpriseOnlyPlaceholder from '../../../../src/main/resources/META-INF/resources/js/common/components/EnterpriseOnlyPlaceholder';

const mockLearnResources = {
	'site-cms-site-initializer': {
		dashboard: {
			en_US: {
				message: 'Test Message',
				url: 'https://learn.liferay.com/test-url',
			},
		},
	},
};

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/utils/getImage',
	() => ({
		getImage: (imageName: string) => `path/to/${imageName}`,
	})
);

jest.mock('frontend-js-components-web', () => {
	const originalModule = jest.requireActual('frontend-js-components-web');

	return {
		...originalModule,
		LearnMessage: jest.fn(() => null),
	};
});

describe('EnterpriseOnlyPlaceholder', () => {
	it('renders the placeholder with all its elements', () => {
		render(
			<EnterpriseOnlyPlaceholder learnResources={mockLearnResources} />
		);

		const image = screen.getByAltText('empty-state-image');
		expect(image).toBeInTheDocument();
		expect(image).toHaveAttribute('src', 'path/to/unlock_dashboard.svg');

		expect(screen.getByText('enterprise')).toBeInTheDocument();
		expect(screen.getByText('unlock-the-dashboard')).toBeInTheDocument();
		expect(
			screen.getByText(
				'use-the-dashboard-to-monitor-asset-usage-and-performance-across-spaces'
			)
		).toBeInTheDocument();

		const {LearnMessage} = jest.requireMock('frontend-js-components-web');
		expect(LearnMessage).toHaveBeenCalledWith(
			expect.objectContaining({
				resource: 'site-cms-site-initializer',
				resourceKey: 'dashboard',
			}),
			{}
		);

		const detailsLink = screen.getByRole('link', {
			name: 'x-opens-new-window',
		});
		expect(detailsLink).toBeInTheDocument();
		expect(detailsLink).toHaveAttribute(
			'href',
			'https://www.liferay.com/web/lr/cms-upgrade'
		);
	});
});
