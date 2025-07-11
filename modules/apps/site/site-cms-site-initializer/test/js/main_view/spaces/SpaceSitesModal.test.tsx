/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import SiteService from '../../../../src/main/resources/META-INF/resources/js/common/services/SiteService';
import SpaceSitesModal from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceSitesModal';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/SiteService'
);

const mockGetConnectedSitesToSpace =
	SiteService.getConnectedSitesToSpace as jest.MockedFunction<
		typeof SiteService.getConnectedSitesToSpace
	>;

const mockGetAllSites = SiteService.getAllSites as jest.MockedFunction<
	typeof SiteService.getAllSites
>;

const DEFAULT_PROPS = {
	groupId: '123',
	isAdmin: true,
};

const renderComponent = (props = DEFAULT_PROPS) => {
	return render(<SpaceSitesModal {...props} />);
};

describe('SpaceSitesModal', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		mockGetConnectedSitesToSpace.mockResolvedValue({
			data: {
				items: [
					{
						id: '1',
						logo: 'logo1.png',
						name: 'Site 1',
						searchable: true,
					},
					{
						id: '2',
						logo: 'logo2.png',
						name: 'Site 2',
						searchable: false,
					},
				],
			},
		} as any);

		mockGetAllSites.mockResolvedValue({
			data: {
				items: [
					{
						id: '1',
						logo: 'logo1.png',
						name: 'Site 1',
						searchable: true,
					},
					{
						id: '2',
						logo: 'logo2.png',
						name: 'Site 2',
						searchable: false,
					},
					{
						id: '3',
						logo: 'logo1.png',
						name: 'Site 3',
						searchable: true,
					},
					{
						id: '4',
						logo: 'logo2.png',
						name: 'Site 4',
						searchable: false,
					},
				],
			},
		} as any);
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders the modal header', async () => {
		renderComponent();

		expect(screen.getByText('all-sites')).toBeInTheDocument();

		await waitFor(() => {
			expect(screen.getByText('Site 1')).toBeInTheDocument();
			expect(screen.getByText('Site 2')).toBeInTheDocument();
		});
	});

	it('checks the accessibility of the modal', async () => {
		const {container} = renderComponent();

		await checkAccessibility({bestPractices: true, context: container});
	});
});
