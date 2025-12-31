/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';

// @ts-ignore

import fetchMock from 'fetch-mock';
import React from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import DSRInitializer from '../../../src/main/resources/META-INF/resources/js/components/DSRInitializer';
import {TDSRInitializerProps} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';
import {setFieldValue} from '../utils';

global.ResizeObserver = ResizeObserver;

const renderComponent = ({
	closeModal,
	numberOfSteps = 1,
}: TDSRInitializerProps) => {
	return render(
		<DSRInitializer
			closeModal={closeModal}
			numberOfSteps={numberOfSteps}
		></DSRInitializer>
	);
};

describe('DSRInitializer', () => {
	afterEach(() => {
		fetchMock.restore();
		jest.clearAllMocks();

		cleanup();
	});

	it('renders the correct UI elements', async () => {
		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 1,
		});

		expect(
			screen.queryByRole('button', {name: 'cancel'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'save'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('heading', {
				name: 'create-new-digital-sales-room',
			})
		).toBeInTheDocument();
	});

	it('navigates between steps', async () => {
		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 2,
		});

		expect(
			screen.queryByRole('button', {name: 'back'})
		).not.toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'cancel'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'next'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'save'})
		).not.toBeInTheDocument();

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'clientName'
		);
		await setFieldValue(screen.getByTestId('roomNameInput'), 'roomName');

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		expect(screen.queryByTestId('roomNameInput')).not.toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'back'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'cancel'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'next'})
		).not.toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'save'})
		).toBeInTheDocument();

		await waitFor(() => {
			screen.getByRole('button', {name: 'back'}).click();
		});

		expect(screen.queryByTestId('roomNameInput')).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'back'})
		).not.toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'cancel'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'next'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'save'})
		).not.toBeInTheDocument();
	});

	it('closes modal on cancel button', async () => {
		const closeModal = jest.fn();

		renderComponent({
			closeModal,
			numberOfSteps: 1,
		});

		screen.getByRole('button', {name: 'cancel'}).click();

		expect(closeModal).toBeCalledTimes(1);
	});

	it('calls API on save button with single step', async () => {
		const spyOnPostDigitalSalesRoom = jest.spyOn(
			DigitalSalesRoomService,
			'postDigitalSalesRoom'
		);

		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 1,
		});

		screen.getByRole('button', {name: 'save'}).click();

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'testClientName'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(screen.queryByTestId('clientNameError')).not.toBeInTheDocument();
		expect(screen.queryByTestId('roomNameError')).toBeInTheDocument();

		await setFieldValue(
			screen.getByTestId('roomNameInput'),
			'testRoomName'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(screen.queryByTestId('clientNameError')).not.toBeInTheDocument();
		expect(screen.queryByTestId('roomNameError')).not.toBeInTheDocument();

		expect(spyOnPostDigitalSalesRoom).toBeCalledWith({
			accountId: 0,
			banner: {
				fileBase64: '',
			},
			channelId: 0,
			channelName: '',
			clientLogo: {
				fileBase64: '',
			},
			clientName: 'testClientName',
			friendlyUrlPath: '',
			name: 'testRoomName',
			primaryColor: '#0B5FFF',
			secondaryColor: '#FFFFFF',
			userAccountBriefs: [],
		});

		await setFieldValue(screen.getByTestId('primaryColorInput'), 'red');
		await setFieldValue(
			screen.getByTestId('secondaryColorInput'),
			'FF0000'
		);
		await setFieldValue(
			screen.getByTestId('friendlyURLInput'),
			'testFriendlyURL'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPostDigitalSalesRoom).toBeCalledWith({
			accountId: 0,
			banner: {
				fileBase64: '',
			},
			channelId: 0,
			channelName: '',
			clientLogo: {
				fileBase64: '',
			},
			clientName: 'testClientName',
			friendlyUrlPath: '/testFriendlyURL',
			name: 'testRoomName',
			primaryColor: 'red',
			secondaryColor: '#FF0000',
			userAccountBriefs: [],
		});
	});

	it('calls API on save button with two steps', async () => {
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

		const spyOnPostDigitalSalesRoom = jest.spyOn(
			DigitalSalesRoomService,
			'postDigitalSalesRoom'
		);

		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 2,
		});

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'testClientName'
		);
		await setFieldValue(
			screen.getByTestId('roomNameInput'),
			'testRoomName'
		);
		await setFieldValue(screen.getByTestId('primaryColorInput'), 'red');
		await setFieldValue(
			screen.getByTestId('secondaryColorInput'),
			'FF0000'
		);
		await setFieldValue(
			screen.getByTestId('friendlyURLInput'),
			'testFriendlyURL'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		await setFieldValue(screen.getByTestId('selectAccountInput'), 'ac');
		screen.getByRole('option', {name: 'account1'}).click();
		await setFieldValue(screen.getByTestId('selectChannelInput'), 'ch');
		screen.getByRole('option', {name: 'channel2'}).click();

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPostDigitalSalesRoom).toBeCalledWith({
			accountId: 100,
			banner: {
				fileBase64: '',
			},
			channelId: 201,
			channelName: 'channel2',
			clientLogo: {
				fileBase64: '',
			},
			clientName: 'testClientName',
			friendlyUrlPath: '/testFriendlyURL',
			name: 'testRoomName',
			primaryColor: 'red',
			secondaryColor: '#FF0000',
			userAccountBriefs: [],
		});
	});

	it('calls API on save button with three steps', async () => {
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

		const spyOnPostDigitalSalesRoom = jest.spyOn(
			DigitalSalesRoomService,
			'postDigitalSalesRoom'
		);

		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 3,
		});

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'testClientName'
		);
		await setFieldValue(
			screen.getByTestId('roomNameInput'),
			'testRoomName'
		);
		await setFieldValue(screen.getByTestId('primaryColorInput'), 'red');
		await setFieldValue(
			screen.getByTestId('secondaryColorInput'),
			'FF0000'
		);
		await setFieldValue(
			screen.getByTestId('friendlyURLInput'),
			'testFriendlyURL'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		await setFieldValue(screen.getByTestId('selectAccountInput'), 'ac');
		screen.getByRole('option', {name: 'account1'}).click();
		await setFieldValue(screen.getByTestId('selectChannelInput'), 'ch');
		screen.getByRole('option', {name: 'channel2'}).click();

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		await waitFor(async () => {
			await userEvent.type(
				screen.getByTestId('emailAddressesInput'),
				'test@liferay.com, test1@liferay.com,'
			);
			screen.getByTestId('roleKeyButton').click();
			screen.getByTestId('roleKeyItem_edit').click();
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPostDigitalSalesRoom).toBeCalledWith({
			accountId: 100,
			banner: {
				fileBase64: '',
			},
			channelId: 201,
			channelName: 'channel2',
			clientLogo: {
				fileBase64: '',
			},
			clientName: 'testClientName',
			friendlyUrlPath: '/testFriendlyURL',
			name: 'testRoomName',
			primaryColor: 'red',
			secondaryColor: '#FF0000',
			userAccountBriefs: [
				{
					emailAddress: 'test@liferay.com',
					roleKey: 'Site Administrator',
				},
				{
					emailAddress: 'test1@liferay.com',
					roleKey: 'Site Administrator',
				},
			],
		});
	});

	it('calls API on save button with four steps', async () => {
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
		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-room-templates.*/i,
			() => {
				return {
					items: [
						{
							banner: {
								fileBase64: '/9j/4template1BannerBase64',
							},
							clientLogo: {
								fileBase64: '/9j/4template1ClientLogoBase64',
							},
							description: 'template1descr',
							id: 100,
							name: 'template1',
							primaryColor: 'red',
							secondaryColor: 'green',
						},
						{
							banner: {
								fileBase64: '/9j/4template2BannerBase64',
							},
							clientLogo: {
								fileBase64: '/9j/4template2ClientLogoBase64',
							},
							description: 'template2descr',
							id: 101,
							name: 'template2',
							primaryColor: 'black',
							secondaryColor: 'white',
						},
					],
				};
			}
		);

		const spyOnPostDigitalSalesRoomTemplateDigitalSalesRoom = jest.spyOn(
			DigitalSalesRoomService,
			'postDigitalSalesRoomTemplateDigitalSalesRoom'
		);

		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 4,
		});

		await waitFor(() => {
			expect(screen.getByTestId(`template_${100}`)).toBeInTheDocument();
			expect(screen.getByTestId(`template_${101}`)).toBeInTheDocument();
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		expect(screen.queryByTestId('clientNameInput')).not.toBeInTheDocument();

		await waitFor(() => {
			screen.getByTestId(`templateName_${101}`).click();
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		await waitFor(() => {
			expect(screen.getByTestId('clientNameInput')).toBeInTheDocument();
		});

		expect(screen.getByTestId('primaryColorInput')).toHaveValue('black');
		expect(screen.getByTestId('secondaryColorInput')).toHaveValue('white');

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'testClientName'
		);
		await setFieldValue(
			screen.getByTestId('roomNameInput'),
			'testRoomName'
		);
		await setFieldValue(
			screen.getByTestId('friendlyURLInput'),
			'testFriendlyURL'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		await setFieldValue(screen.getByTestId('selectAccountInput'), 'ac');
		screen.getByRole('option', {name: 'account1'}).click();
		await setFieldValue(screen.getByTestId('selectChannelInput'), 'ch');
		screen.getByRole('option', {name: 'channel2'}).click();

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		await waitFor(async () => {
			await userEvent.type(
				screen.getByTestId('emailAddressesInput'),
				'test@liferay.com, test1@liferay.com,'
			);
			screen.getByTestId('roleKeyButton').click();
			screen.getByTestId('roleKeyItem_edit').click();
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(
			spyOnPostDigitalSalesRoomTemplateDigitalSalesRoom
		).toBeCalledWith(101, {
			accountId: 100,
			banner: {
				fileBase64: '/9j/4template2BannerBase64',
			},
			channelId: 201,
			channelName: 'channel2',
			clientLogo: {
				fileBase64: '/9j/4template2ClientLogoBase64',
			},
			clientName: 'testClientName',
			friendlyUrlPath: '/testFriendlyURL',
			name: 'testRoomName',
			primaryColor: 'black',
			secondaryColor: 'white',
			userAccountBriefs: [
				{
					emailAddress: 'test@liferay.com',
					roleKey: 'Site Administrator',
				},
				{
					emailAddress: 'test1@liferay.com',
					roleKey: 'Site Administrator',
				},
			],
		});
	});
});
