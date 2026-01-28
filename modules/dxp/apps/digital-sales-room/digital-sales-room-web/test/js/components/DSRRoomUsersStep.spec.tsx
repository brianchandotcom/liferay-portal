/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {
	cleanup,
	render,
	renderHook,
	screen,
	waitFor,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import {DSRContext} from '../../../src/main/resources/META-INF/resources/js/components/DSRInitializer';
import DSRRoomUsersStep from '../../../src/main/resources/META-INF/resources/js/components/DSRRoomUsersStep';
import {
	TDSRDataContext,
	TDSRRoomDetailsStepProps,
} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';

global.ResizeObserver = ResizeObserver;

const mockOpenToast = jest.fn();

jest.mock('frontend-js-components-web', () => ({
	openToast: (...args: unknown[]) => mockOpenToast(...args),
}));

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService',
	() => ({
		__esModule: true,
		default: {
			addDigitalSalesRoomUserAccountBrief: jest.fn(),
			deleteDigitalSalesRoomUserAccountBrief: jest.fn(),
			getDigitalSalesRoomUserAccountBriefs: jest.fn(),
			updateDigitalSalesRoomUserAccountBrief: jest.fn(),
		},
	})
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/FDSPropsTransformer/actions/deleteDSRUserAction',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

const mockUsers = [
	{
		emailAddress: 'john.doe@liferay.com',
		id: 1,
		name: 'John Doe',
		roleKey: 'Site Owner',
	},
	{
		emailAddress: 'ran.doe@liferay.com',
		id: 2,
		name: 'Ran Doe',
		roleKey: 'Site Administrator',
	},
	{
		emailAddress: 'win.doe@liferay.com',
		id: 3,
		name: 'Win Doe',
		roleKey: 'Site Member',
	},
];

let {result: useStateHookResult} = renderHook(() =>
	useState<TDSRDataContext>({
		accountId: 0,
		banner: {},
		channelId: 0,
		channelName: '',
		clientLogo: {},
		clientName: '',
		digitalSalesRoomId: 123,
		errors: {},
		friendlyURL: '',
		primaryColor: '0B5FFF',
		roomName: '',
		secondaryColor: 'FFFFFF',
	})
);

const component = ({
	loading = false,
	numberOfSteps = 1,
	setHandleStepSubmit,
	showHeader = true,
}: TDSRRoomDetailsStepProps & {loading?: boolean}) => {
	return (
		<DSRContext.Provider
			value={{
				dataContext: useStateHookResult.current[0],
				loading,
				setDataContext: useStateHookResult.current[1],
			}}
		>
			<DSRRoomUsersStep
				numberOfSteps={numberOfSteps}
				setHandleStepSubmit={setHandleStepSubmit}
				showHeader={showHeader}
			/>
		</DSRContext.Provider>
	);
};

const renderComponent = ({
	loading = false,
	numberOfSteps = 1,
	setHandleStepSubmit,
	showHeader = true,
}: TDSRRoomDetailsStepProps & {loading?: boolean}) => {
	return render(
		component({
			loading,
			numberOfSteps,
			setHandleStepSubmit,
			showHeader,
		})
	);
};

describe('DSRRoomUsersStep', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		mockOpenToast.mockClear();

		(
			DigitalSalesRoomService.getDigitalSalesRoomUserAccountBriefs as jest.Mock
		).mockResolvedValue(mockUsers);

		(
			DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief as jest.Mock
		).mockResolvedValue({});

		(
			DigitalSalesRoomService.updateDigitalSalesRoomUserAccountBrief as jest.Mock
		).mockImplementation((roomId, userId, data) => {
			const user = mockUsers.find((u) => u.id === userId);

			return Promise.resolve({
				...user,
				...data,
			});
		});

		({result: useStateHookResult} = renderHook(() =>
			useState<TDSRDataContext>({
				accountId: 0,
				banner: {},
				channelId: 0,
				channelName: '',
				clientLogo: {},
				clientName: '',
				digitalSalesRoomId: 123,
				errors: {},
				friendlyURL: '',
				primaryColor: '0B5FFF',
				roomName: '',
				secondaryColor: 'FFFFFF',
			})
		));
	});

	afterEach(() => {
		cleanup();
	});

	it('renders the correct UI elements', async () => {
		const {container} = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
		});

		expect(
			container.querySelector('[data-testid="emailAddressesInput"]')
		).toBeInTheDocument();
		expect(
			container.querySelector('[data-testid="roleKeyButton"]')
		).toBeInTheDocument();
		expect(
			container.querySelector('[data-testid="inviteButton"]')
		).toBeInTheDocument();
		expect(screen.getByText('users')).toBeInTheDocument();
	});

	it('loads and displays users', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(
				DigitalSalesRoomService.getDigitalSalesRoomUserAccountBriefs
			).toHaveBeenCalledWith(123);
		});

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
			expect(screen.getByText('Ran Doe')).toBeInTheDocument();
			expect(screen.getByText('Win Doe')).toBeInTheDocument();
		});
	});

	it('displays owner role without dropdown', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
		});

		expect(screen.getByText('owner')).toBeInTheDocument();
	});

	it('invites a new user with valid email', async () => {
		const {container} = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
		});

		const emailInput = container.querySelector(
			'[data-testid="emailAddressesInput"]'
		) as HTMLInputElement;
		const inviteButton = container.querySelector(
			'[data-testid="inviteButton"]'
		) as HTMLButtonElement;

		await userEvent.type(emailInput, 'newuser@liferay.com,');
		await userEvent.click(inviteButton);

		await waitFor(() => {
			expect(
				DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief
			).toHaveBeenCalledWith(123, {
				emailAddress: 'newuser@liferay.com',
				roleKey: 'Site Member',
			});
		});
	});

	it('invites multiple users with comma-separated emails', async () => {
		const {container} = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
		});

		const emailInput = container.querySelector(
			'[data-testid="emailAddressesInput"]'
		) as HTMLInputElement;
		const inviteButton = container.querySelector(
			'[data-testid="inviteButton"]'
		) as HTMLButtonElement;

		await userEvent.type(
			emailInput,
			'user1@liferay.com,user2@liferay.com,'
		);

		await userEvent.click(inviteButton);

		await waitFor(() => {
			expect(
				DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief
			).toHaveBeenCalledTimes(2);
			expect(
				DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief
			).toHaveBeenCalledWith(123, {
				emailAddress: 'user1@liferay.com',
				roleKey: 'Site Member',
			});
			expect(
				DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief
			).toHaveBeenCalledWith(123, {
				emailAddress: 'user2@liferay.com',
				roleKey: 'Site Member',
			});
		});
	});

	it.skip('shows error for already invited user', async () => {
		const {container} = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
		});

		const emailInput = container.querySelector(
			'[data-testid="emailAddressesInput"]'
		) as HTMLInputElement;
		const inviteButton = container.querySelector(
			'[data-testid="inviteButton"]'
		) as HTMLButtonElement;

		await userEvent.type(emailInput, 'john.doe@liferay.com,');
		await userEvent.click(inviteButton);

		await waitFor(() => {
			expect(
				DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief
			).not.toHaveBeenCalled();
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({
					type: 'danger',
				})
			);
		});
	});

	it('changes role for a user', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('Win Doe')).toBeInTheDocument();
		});

		const userRow = screen.getByText('Win Doe').closest('.mb-3');
		const dropdownButton = userRow?.querySelector(
			'.dropdown-toggle'
		) as HTMLElement;

		expect(dropdownButton.textContent).toContain('viewer');

		await userEvent.click(dropdownButton);

		await waitFor(() => {
			expect(document.querySelector('.dropdown-menu.show')).toBeTruthy();
		});

		const openDropdownMenu = document.querySelector('.dropdown-menu.show');
		const contributorMenuItem = openDropdownMenu?.querySelector(
			'.dropdown-item'
		) as HTMLElement;

		expect(contributorMenuItem.textContent).toContain('contributor');

		await userEvent.click(contributorMenuItem);

		await waitFor(() => {
			expect(
				DigitalSalesRoomService.updateDigitalSalesRoomUserAccountBrief
			).toHaveBeenCalledWith(123, 3, {
				roleKey: 'Site Administrator',
			});
		});
	});

	it('shows error toast when loading users fails', async () => {
		const errorMessage = 'Failed to load users';

		(
			DigitalSalesRoomService.getDigitalSalesRoomUserAccountBriefs as jest.Mock
		).mockRejectedValue(new Error(errorMessage));

		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(mockOpenToast).toHaveBeenCalledWith({
				message: errorMessage,
				type: 'danger',
			});
		});
	});

	it('disables fields and buttons when loading users', async () => {
		let resolveUsersFetch: (value: any) => void;

		const usersPromise = new Promise((resolve) => {
			resolveUsersFetch = resolve;
		});

		(
			DigitalSalesRoomService.getDigitalSalesRoomUserAccountBriefs as jest.Mock
		).mockReturnValue(usersPromise);

		const {container} = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await new Promise((resolve) => setTimeout(resolve, 10));

		expect(
			container.querySelector('[data-testid="emailAddressesInput"]')
		).toBeDisabled();
		expect(
			container.querySelector('[data-testid="roleKeyButton"]')
		).toBeDisabled();
		expect(
			container.querySelector('[data-testid="inviteButton"]')
		).toBeDisabled();

		resolveUsersFetch!(mockUsers);

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
		});
	});

	it('selects role from dropdown before inviting', async () => {
		const {container} = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
		});

		const roleKeyButton = container.querySelector(
			'[data-testid="roleKeyButton"]'
		) as HTMLButtonElement;
		await userEvent.click(roleKeyButton);

		await waitFor(() => {
			document.querySelector('[data-testid="roleKeyItem_contributor"]');
		});

		const contributorItem = document.querySelector(
			'[data-testid="roleKeyItem_contributor"]'
		) as HTMLButtonElement;
		await userEvent.click(contributorItem);

		const emailInput = container.querySelector(
			'[data-testid="emailAddressesInput"]'
		) as HTMLInputElement;
		await userEvent.type(emailInput, 'newuser@liferay.com,');

		const inviteButton = container.querySelector(
			'[data-testid="inviteButton"]'
		) as HTMLButtonElement;
		await userEvent.click(inviteButton);

		await waitFor(() => {
			expect(
				DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief
			).toHaveBeenCalledWith(123, {
				emailAddress: 'newuser@liferay.com',
				roleKey: 'Site Administrator',
			});
		});
	});

	it('displays user initials when no image is available', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByText('J')).toBeInTheDocument();
			expect(screen.getByText('R')).toBeInTheDocument();
			expect(screen.getByText('W')).toBeInTheDocument();
		});
	});

	it('shows users count in the header', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(
				screen.getByText('who-has-access-3-users')
			).toBeInTheDocument();
		});
	});
});
