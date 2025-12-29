/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen, waitFor} from '@testing-library/react';
import React from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import DSRTemplateInitializer from '../../../src/main/resources/META-INF/resources/js/components/DSRTemplateInitializer';
import {TDSRInitializerProps} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';
import {setFieldValue} from '../utils';

global.ResizeObserver = ResizeObserver;

const renderComponent = ({
	closeModal,
	numberOfSteps = 1,
}: TDSRInitializerProps) => {
	return render(
		<DSRTemplateInitializer
			closeModal={closeModal}
			numberOfSteps={numberOfSteps}
		></DSRTemplateInitializer>
	);
};

describe('DSRTemplateInitializer', () => {
	afterEach(() => {
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
				name: 'create-new-digital-sales-room-template',
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
		const spyOnPostDigitalSalesRoomTemplate = jest.spyOn(
			DigitalSalesRoomService,
			'postDigitalSalesRoomTemplate'
		);

		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 1,
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(screen.queryByTestId('roomNameError')).toBeInTheDocument();

		await setFieldValue(
			screen.getByTestId('roomNameInput'),
			'testRoomName'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(screen.queryByTestId('roomNameError')).not.toBeInTheDocument();

		expect(spyOnPostDigitalSalesRoomTemplate).toBeCalledWith({
			banner: {
				fileBase64: '',
			},
			clientLogo: {
				fileBase64: '',
			},
			clientName: '',
			description: '',
			name: 'testRoomName',
			primaryColor: '#0B5FFF',
			secondaryColor: '#FFFFFF',
		});

		await setFieldValue(
			screen.getByTestId('descriptionInput'),
			'description'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPostDigitalSalesRoomTemplate).toBeCalledWith({
			banner: {
				fileBase64: '',
			},
			clientLogo: {
				fileBase64: '',
			},
			clientName: '',
			description: 'description',
			name: 'testRoomName',
			primaryColor: '#0B5FFF',
			secondaryColor: '#FFFFFF',
		});
	});

	it('calls API on save button with two steps', async () => {
		const spyOnPostDigitalSalesRoomTemplate = jest.spyOn(
			DigitalSalesRoomService,
			'postDigitalSalesRoomTemplate'
		);

		renderComponent({
			closeModal: jest.fn(),
			numberOfSteps: 2,
		});

		await setFieldValue(
			screen.getByTestId('roomNameInput'),
			'testRoomName'
		);
		await setFieldValue(
			screen.getByTestId('descriptionInput'),
			'description'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'next'}).click();
		});

		await setFieldValue(screen.getByTestId('primaryColorInput'), 'red');
		await setFieldValue(
			screen.getByTestId('secondaryColorInput'),
			'FF0000'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPostDigitalSalesRoomTemplate).toBeCalledWith({
			banner: {
				fileBase64: '',
			},
			clientLogo: {
				fileBase64: '',
			},
			clientName: '',
			description: 'description',
			name: 'testRoomName',
			primaryColor: 'red',
			secondaryColor: '#FF0000',
		});
	});
});
