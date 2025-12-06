/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen, waitFor} from '@testing-library/react';

// @ts-ignore

import fetchMock from 'fetch-mock';
import React from 'react';

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import DSRInitializer from '../../../src/main/resources/META-INF/resources/js/components/DSRInitializer';
import {TDSRInitializerProps} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';
import {setFieldValue} from '../utils';

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

	it('calls API on save button', async () => {
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
		});
	});
});
