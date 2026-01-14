/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import fetchMock from 'fetch-mock';

import '@testing-library/jest-dom';
import {
	cleanup,
	render,
	renderHook,
	screen,
	waitFor,
} from '@testing-library/react';
import React, {useState} from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import {DSRContext} from '../../../src/main/resources/META-INF/resources/js/components/DSRInitializer';
import DSRRoomSettingsStep from '../../../src/main/resources/META-INF/resources/js/components/DSRRoomSettingsStep';
import {
	TDSRDataContext,
	TDSRRoomDetailsStepProps,
} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';
import {setFieldValue} from '../utils';

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
			<DSRRoomSettingsStep
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

describe('DSRRoomSettingsStep', () => {
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
			})
		));
	});

	afterEach(() => {
		fetchMock.restore();
		jest.clearAllMocks();

		cleanup();
	});

	it('renders the correct UI elements', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(screen.getByTestId('selectAccountInput')).toBeInTheDocument();
		expect(screen.getByTestId('selectChannelInput')).toBeInTheDocument();
		expect(screen.getByTestId('stepLocator')).toBeInTheDocument();
		expect(screen.getByTestId('stepTitle')).toBeInTheDocument();
	});

	it('hides header based on the parameter', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
			showHeader: false,
		});

		expect(screen.getByTestId('selectAccountInput')).toBeInTheDocument();
		expect(screen.getByTestId('selectChannelInput')).toBeInTheDocument();
		expect(screen.queryByTestId('stepLocator')).not.toBeInTheDocument();
		expect(screen.queryByTestId('stepTitle')).not.toBeInTheDocument();
	});

	it('loads accounts', async () => {
		const spyOnGetAccounts = jest.spyOn(
			DigitalSalesRoomService,
			'getAccounts'
		);

		fetchMock.get(/headless-admin-user\/.*\/accounts.*/i, () => {
			return {
				items: [
					{
						id: 100,
						name: 'account1',
					},
					{
						id: 101,
						name: 'account2',
					},
				],
			};
		});

		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(spyOnGetAccounts).toBeCalledTimes(1);

		await setFieldValue(screen.getByTestId('selectAccountInput'), 'ac');

		await waitFor(() => {
			expect(spyOnGetAccounts).toHaveBeenLastCalledWith('ac');
		});

		expect(
			screen.getByRole('option', {name: 'account1'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('option', {name: 'account2'})
		).toBeInTheDocument();

		screen.getByRole('option', {name: 'account1'}).click();

		await waitFor(() => {
			expect(spyOnGetAccounts).toHaveBeenLastCalledWith('account1');
		});

		let state = useStateHookResult.current[0];

		expect(state.accountId).toBe(100);
		expect(state.accountName).toBe('account1');

		await setFieldValue(screen.getByTestId('selectAccountInput'), '');

		await waitFor(() => {
			expect(spyOnGetAccounts).toHaveBeenLastCalledWith('');
		});

		state = useStateHookResult.current[0];

		expect(state.accountId).toBe(0);
		expect(state.accountName).toBe('');
	});

	it('loads channels', async () => {
		const spyOnGetChannels = jest.spyOn(
			DigitalSalesRoomService,
			'getChannels'
		);

		fetchMock.get(
			/headless-commerce-delivery-catalog\/.*\/channels.*/i,
			() => {
				return {
					items: [
						{
							id: 200,
							name: 'channel1',
						},
						{
							id: 201,
							name: 'channel2',
						},
					],
				};
			}
		);

		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(spyOnGetChannels).toBeCalledTimes(1);

		await setFieldValue(screen.getByTestId('selectChannelInput'), 'ch');

		await waitFor(() => {
			expect(spyOnGetChannels).toHaveBeenLastCalledWith('ch');
		});

		expect(
			screen.getByRole('option', {name: 'channel1'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('option', {name: 'channel2'})
		).toBeInTheDocument();

		screen.getByRole('option', {name: 'channel2'}).click();

		await waitFor(() => {
			expect(spyOnGetChannels).toHaveBeenLastCalledWith('channel2');
		});

		let state = useStateHookResult.current[0];

		expect(state.channelId).toBe(201);
		expect(state.channelName).toBe('channel2');

		await setFieldValue(screen.getByTestId('selectChannelInput'), '');

		await waitFor(() => {
			expect(spyOnGetChannels).toHaveBeenLastCalledWith('');
		});

		state = useStateHookResult.current[0];

		expect(state.channelId).toBe(0);
		expect(state.channelName).toBe('');
	});

	it('validate UI interaction', async () => {
		fetchMock.get(/headless-admin-user\/.*\/accounts.*/i, () => {
			return {
				items: [
					{
						id: 100,
						name: 'account1',
					},
					{
						id: 101,
						name: 'account2',
					},
				],
			};
		});
		fetchMock.get(
			/headless-commerce-delivery-catalog\/.*\/channels.*/i,
			() => {
				return {
					items: [
						{
							id: 200,
							name: 'channel1',
						},
						{
							id: 201,
							name: 'channel2',
						},
					],
				};
			}
		);

		const container = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await setFieldValue(screen.getByTestId('selectAccountInput'), 'ac');

		screen.getByRole('option', {name: 'account1'}).click();

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: jest.fn()})
		);

		await setFieldValue(screen.getByTestId('selectChannelInput'), 'ch');

		screen.getByRole('option', {name: 'channel2'}).click();

		await waitFor(() => {
			const state = useStateHookResult.current[0];

			expect(state.accountId).toBe(100);
			expect(state.accountName).toBe('account1');
			expect(state.channelId).toBe(201);
			expect(state.channelName).toBe('channel2');
		});
	});

	it('disables form fields when loading is true', async () => {
		renderComponent({
			loading: true,
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(screen.getByTestId('selectAccountInput')).toBeDisabled();
		expect(screen.getByTestId('selectChannelInput')).toBeDisabled();
	});
});
