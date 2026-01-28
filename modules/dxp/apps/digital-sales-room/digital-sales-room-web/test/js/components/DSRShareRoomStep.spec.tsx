/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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

import {DSRContext} from '../../../src/main/resources/META-INF/resources/js/components/DSRInitializer';
import DSRShareRoomStep from '../../../src/main/resources/META-INF/resources/js/components/DSRShareRoomStep';
import {
	TDSRDataContext,
	TDSRRoomDetailsStepProps,
} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';

global.ResizeObserver = ResizeObserver;

let {result: useStateHookResult} = renderHook(() =>
	useState<TDSRDataContext>({
		accountId: 0,
		banner: {},
		channelId: 0,
		channelName: '',
		clientLogo: {},
		clientName: '',
		errors: {},
		friendlyURL: '',
		primaryColor: '0B5FFF',
		roomName: '',
		secondaryColor: 'FFFFFF',
		share: {
			emailAddresses: [],
			roleKey: '',
		},
	})
);

const component = ({
	loading = false,
	numberOfSteps = 1,
	setHandleStepSubmit,
}: TDSRRoomDetailsStepProps & {loading?: boolean}) => {
	return (
		<DSRContext.Provider
			value={{
				dataContext: useStateHookResult.current[0],
				loading,
				setDataContext: useStateHookResult.current[1],
			}}
		>
			<DSRShareRoomStep
				numberOfSteps={numberOfSteps}
				setHandleStepSubmit={setHandleStepSubmit}
			/>
		</DSRContext.Provider>
	);
};

const renderComponent = ({
	loading = false,
	numberOfSteps = 1,
	setHandleStepSubmit,
}: TDSRRoomDetailsStepProps & {loading?: boolean}) => {
	return render(
		component({
			loading,
			numberOfSteps,
			setHandleStepSubmit,
		})
	);
};

describe('DSRShareRoomStep', () => {
	beforeEach(() => {
		({result: useStateHookResult} = renderHook(() =>
			useState<TDSRDataContext>({
				accountId: 0,
				banner: {},
				channelId: 0,
				channelName: '',
				clientLogo: {},
				clientName: '',
				errors: {},
				friendlyURL: '',
				primaryColor: '0B5FFF',
				roomName: '',
				secondaryColor: 'FFFFFF',
				share: {
					emailAddresses: [],
					roleKey: '',
				},
			})
		));
	});

	afterEach(() => {
		cleanup();
	});

	it('renders the correct UI elements', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(screen.getByTestId('emailAddressesInput')).toBeInTheDocument();
		expect(screen.getByTestId('roleKeyButton')).toBeInTheDocument();
		expect(screen.getByTestId('roleKeyButton')).toHaveTextContent('viewer');

		screen.getByTestId('roleKeyButton').click();

		expect(
			screen.getByTestId('roleKeyItem_contributor')
		).toBeInTheDocument();
		expect(screen.getByTestId('roleKeyItem_viewer')).toBeInTheDocument();
		expect(screen.getByTestId('stepLocator')).toBeInTheDocument();
		expect(screen.getByTestId('stepTitle')).toBeInTheDocument();
	});

	it('adds valid emails', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(async () => {
			await userEvent.type(
				screen.getByTestId('emailAddressesInput'),
				'test@liferay.com, test1@liferay.com, '
			);
		});

		expect(
			screen.getByRole('gridcell', {name: 'test@liferay.com'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('gridcell', {name: 'test1@liferay.com'})
		).toBeInTheDocument();

		let state = useStateHookResult.current[0];

		expect(state.share).toEqual({
			emailAddresses: ['test@liferay.com', 'test1@liferay.com'],
			roleKey: '',
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'Clear All'}).click();
		});

		state = useStateHookResult.current[0];

		expect(state.share).toEqual({
			emailAddresses: [],
			roleKey: '',
		});
	});

	it('adds invalid emails', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(async () => {
			await userEvent.type(
				screen.getByTestId('emailAddressesInput'),
				'test@liferay.com, test.com, test, test1@liferay.com, '
			);
		});

		expect(
			screen.getByRole('gridcell', {name: 'test@liferay.com'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('gridcell', {name: 'test1@liferay.com'})
		).toBeInTheDocument();

		const state = useStateHookResult.current[0];

		expect(state.share).toEqual({
			emailAddresses: ['test@liferay.com', 'test1@liferay.com'],
			roleKey: '',
		});
	});

	it('changes role', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			screen.getByTestId('roleKeyButton').click();
			screen.getByTestId('roleKeyItem_contributor').click();
		});

		expect(screen.getByTestId('roleKeyButton')).toHaveTextContent(
			'contributor'
		);

		let state = useStateHookResult.current[0];

		expect(state.share).toEqual({
			emailAddresses: [],
			roleKey: 'Site Administrator',
		});

		await waitFor(() => {
			screen.getByTestId('roleKeyButton').click();
			screen.getByTestId('roleKeyItem_viewer').click();
		});

		expect(screen.getByTestId('roleKeyButton')).toHaveTextContent('viewer');

		state = useStateHookResult.current[0];

		expect(state.share).toEqual({
			emailAddresses: [],
			roleKey: 'Site Member',
		});
	});

	it('validates UI interaction', async () => {
		const container = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(async () => {
			await userEvent.type(
				screen.getByTestId('emailAddressesInput'),
				'test@liferay.com, test1@liferay.com, '
			);
		});

		expect(
			screen.getByRole('gridcell', {name: 'test@liferay.com'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('gridcell', {name: 'test1@liferay.com'})
		).toBeInTheDocument();

		await waitFor(() => {
			screen.getByTestId('roleKeyButton').click();
			screen.getByTestId('roleKeyItem_contributor').click();
		});

		const state = useStateHookResult.current[0];

		expect(state.share).toEqual({
			emailAddresses: ['test@liferay.com', 'test1@liferay.com'],
			roleKey: 'Site Administrator',
		});

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: jest.fn()})
		);

		expect(
			screen.getByRole('gridcell', {name: 'test@liferay.com'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('gridcell', {name: 'test1@liferay.com'})
		).toBeInTheDocument();
		expect(screen.getByTestId('roleKeyButton')).toHaveTextContent(
			'contributor'
		);
	});

	it('disables fields and buttons when loading is true', async () => {
		renderComponent({
			loading: true,
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(screen.getByTestId('emailAddressesInput')).toBeDisabled();
		expect(screen.getByTestId('roleKeyButton')).toBeDisabled();
	});
});
