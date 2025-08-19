/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen, waitFor, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import SiteService from '../../../../src/main/resources/META-INF/resources/js/common/services/SiteService';
import {Site} from '../../../../src/main/resources/META-INF/resources/js/common/types/Site';
import SpaceSitesModal from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceSitesModal';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/SiteService'
);

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

const mockGetConnectedSitesFromSpace =
	SiteService.getConnectedSitesFromSpace as jest.MockedFunction<
		typeof SiteService.getConnectedSitesFromSpace
	>;

const mockGetAllSites = SiteService.getAllSites as jest.MockedFunction<
	typeof SiteService.getAllSites
>;

const mockConnectSiteToSpace =
	SiteService.connectSiteToSpace as jest.MockedFunction<
		typeof SiteService.connectSiteToSpace
	>;

const mockDisconnectSiteFromSpace =
	SiteService.disconnectSiteFromSpace as jest.MockedFunction<
		typeof SiteService.disconnectSiteFromSpace
	>;

const mockedOpenToast = openToast as jest.Mock;

const mockConnectedSites: Site[] = [
	{
		externalReferenceCode: '1',
		id: '1',
		logo: 'logo1.png',
		name: 'Connected Site 1',
		searchable: true,
	},
	{
		externalReferenceCode: '2',
		id: '2',
		logo: 'logo2.png',
		name: 'Connected Site 2',
		searchable: false,
	},
];

const mockUnconnectedSite: Site = {
	externalReferenceCode: '3',
	id: '3',
	logo: 'logo3.png',
	name: 'Unconnected Site 3',
	searchable: true,
};

const DEFAULT_PROPS = {
	groupId: '123',
	hasConnectSitesPermission: true,
};

const errorMessage = 'Connection failed';

const renderComponent = (props = DEFAULT_PROPS) => {
	return render(<SpaceSitesModal {...props} />);
};

const assertErrorToast = async () => {
	await waitFor(() => {
		expect(mockedOpenToast).toHaveBeenCalledTimes(1);

		expect(mockedOpenToast).toHaveBeenCalledWith({
			message: errorMessage,
			type: 'danger',
		});
	});
};

describe('SpaceSitesModal', () => {
	const {ResizeObserver: ResizeObserverOriginal} = window;

	beforeAll(() => {
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	beforeEach(() => {
		jest.clearAllMocks();

		mockGetConnectedSitesFromSpace.mockResolvedValue({
			data: {items: mockConnectedSites},
			error: null,
		});

		mockGetAllSites.mockResolvedValue({
			data: {items: mockConnectedSites},
			error: null,
		});

		mockConnectSiteToSpace.mockImplementation(
			async (_groupId, siteId, searchable) => ({
				data: {
					externalReferenceCode:
						mockUnconnectedSite.externalReferenceCode,
					id: siteId,
					logo: mockUnconnectedSite.logo,
					name: mockUnconnectedSite.name,
					searchable: !!JSON.parse(searchable || 'false'),
				},
				error: null,
			})
		);
		mockDisconnectSiteFromSpace.mockResolvedValue({
			data: null,
			error: null,
		});
	});

	afterAll(() => {
		window.ResizeObserver = ResizeObserverOriginal;
		jest.restoreAllMocks();
	});

	afterEach(() => {
		jest.restoreAllMocks();
		mockedOpenToast.mockClear();
	});

	it('checks the accessibility of the modal', async () => {
		const {container} = renderComponent();

		await checkAccessibility({bestPractices: true, context: container});
	});

	it('renders the modal header', async () => {
		renderComponent();

		expect(screen.getByText('all-sites')).toBeInTheDocument();

		await waitFor(() => {
			expect(mockGetConnectedSitesFromSpace).toHaveBeenCalledWith(
				DEFAULT_PROPS.groupId
			);
		});

		expect(await screen.findByText('Connected Site 1')).toBeInTheDocument();
		expect(await screen.findByText('Connected Site 2')).toBeInTheDocument();
	});

	it('displays an empty state message when no sites are connected', async () => {
		mockGetConnectedSitesFromSpace.mockResolvedValue({
			data: {items: []},
			error: null,
		});

		renderComponent();

		expect(
			await screen.findByText('no-sites-are-connected-yet')
		).toBeInTheDocument();
	});

	describe('when hasConnectSitesPermission is true', () => {
		it('allows connecting a new site', async () => {
			mockGetAllSites.mockResolvedValue({
				data: {items: [mockUnconnectedSite]},
				error: null,
			});

			renderComponent();

			await waitFor(() => {
				expect(mockGetAllSites).toHaveBeenCalled();
			});

			await userEvent.click(screen.getByPlaceholderText('select-a-site'));

			await screen
				.getByRole('option', {name: mockUnconnectedSite.name})
				.click();

			await userEvent.click(
				screen.getByRole('button', {name: 'connect'})
			);

			await waitFor(() => {
				expect(mockConnectSiteToSpace).toHaveBeenCalledWith(
					DEFAULT_PROPS.groupId,
					mockUnconnectedSite.id
				);
			});

			expect(
				screen.getByText(mockUnconnectedSite.name)
			).toBeInTheDocument();
		});

		it('shows an error toast if connecting a site fails', async () => {
			mockGetAllSites.mockResolvedValue({
				data: {items: [mockUnconnectedSite]},
				error: null,
			});

			mockConnectSiteToSpace.mockResolvedValue({
				data: null,
				error: errorMessage,
			});

			renderComponent();

			await waitFor(() => {
				expect(mockGetAllSites).toHaveBeenCalled();
			});

			await userEvent.click(screen.getByPlaceholderText('select-a-site'));

			await screen
				.getByRole('option', {name: mockUnconnectedSite.name})
				.click();

			await userEvent.click(
				screen.getByRole('button', {name: 'connect'})
			);

			await assertErrorToast();
		});

		it('allows disconnecting a site', async () => {
			renderComponent();

			const site1Row = (
				await screen.findByText('Connected Site 1')
			).closest('li')!;
			const actionsButton = within(site1Row).getByRole('button', {
				name: 'site-actions',
			});

			await userEvent.click(actionsButton);
			await userEvent.click(
				await screen.findByRole('menuitem', {name: 'disconnect'})
			);

			await waitFor(() => {
				expect(mockDisconnectSiteFromSpace).toHaveBeenCalledWith(
					DEFAULT_PROPS.groupId,
					'1'
				);
			});

			expect(
				screen.queryByText('Connected Site 1')
			).not.toBeInTheDocument();
		});

		it('shows an error toast if disconnecting a site fails', async () => {
			mockDisconnectSiteFromSpace.mockResolvedValue({
				data: null,
				error: errorMessage,
			});

			renderComponent();

			const site1Row = (
				await screen.findByText('Connected Site 1')
			).closest('li')!;
			const actionsButton = within(site1Row).getByRole('button', {
				name: 'site-actions',
			});

			await userEvent.click(actionsButton);
			await userEvent.click(
				await screen.findByRole('menuitem', {name: 'disconnect'})
			);

			await assertErrorToast();
		});

		it('allows changing a site to be unsearchable', async () => {
			renderComponent();

			const site1Row = (
				await screen.findByText('Connected Site 1')
			).closest('li')!;

			expect(
				within(site1Row).getByText(/searchable-content: yes/)
			).toBeInTheDocument();

			const actionsButton = within(site1Row).getByRole('button', {
				name: 'site-actions',
			});
			await userEvent.click(actionsButton);
			await userEvent.click(
				await screen.findByRole('menuitem', {name: 'make-unsearchable'})
			);

			await waitFor(() => {
				expect(mockConnectSiteToSpace).toHaveBeenCalledWith(
					DEFAULT_PROPS.groupId,
					'1',
					'false'
				);
			});

			await waitFor(() => {
				expect(
					within(site1Row).getByText(/searchable-content: no/)
				).toBeInTheDocument();
			});
		});

		it('shows an error toast if changing a site to be unsearchable fails', async () => {
			mockConnectSiteToSpace.mockResolvedValue({
				data: null,
				error: errorMessage,
			});

			renderComponent();

			const site1Row = (
				await screen.findByText('Connected Site 1')
			).closest('li')!;

			expect(
				within(site1Row).getByText(/searchable-content: yes/)
			).toBeInTheDocument();

			const actionsButton = within(site1Row).getByRole('button', {
				name: 'site-actions',
			});
			await userEvent.click(actionsButton);
			await userEvent.click(
				await screen.findByRole('menuitem', {name: 'make-unsearchable'})
			);

			await assertErrorToast();
		});
	});

	describe('without connect permissions', () => {
		const propsWithoutPermission = {
			...DEFAULT_PROPS,
			hasConnectSitesPermission: false,
		};

		it('does not render the site selector', async () => {
			renderComponent(propsWithoutPermission);

			await waitFor(() => {
				expect(
					screen.getByText('Connected Site 1')
				).toBeInTheDocument();
			});

			expect(
				screen.queryByRole('combobox', {name: 'site'})
			).not.toBeInTheDocument();
			expect(
				screen.queryByRole('button', {name: 'connect'})
			).not.toBeInTheDocument();
		});

		it('does not render site actions', async () => {
			renderComponent(propsWithoutPermission);

			const site1Row = (
				await screen.findByText('Connected Site 1')
			).closest('li')!;
			expect(
				within(site1Row).queryByRole('button', {name: 'site-actions'})
			).not.toBeInTheDocument();
		});
	});
});
