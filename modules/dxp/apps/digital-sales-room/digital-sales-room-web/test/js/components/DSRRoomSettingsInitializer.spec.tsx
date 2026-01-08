/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import fetchMock from 'fetch-mock';

import '@testing-library/jest-dom';
import {cleanup, render, screen, waitFor} from '@testing-library/react';
import React from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import {DSRRoomSettingsInitializer} from '../../../src/main/resources/META-INF/resources/js';
import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import {setFieldValue} from '../utils';

global.ResizeObserver = ResizeObserver;

const renderComponent = ({
	cancelURL = '',
	digitalSalesRoomId = 100,
	step = 'general',
}: {
	cancelURL?: string;
	digitalSalesRoomId?: number;
	step?: string;
}) => {
	return render(
		<DSRRoomSettingsInitializer
			cancelURL={cancelURL}
			digitalSalesRoomId={digitalSalesRoomId}
			step={step}
		></DSRRoomSettingsInitializer>
	);
};

describe('DSRRoomSettingsInitializer', () => {
	afterEach(() => {
		fetchMock.restore();
		jest.clearAllMocks();

		cleanup();
	});

	it('renders the correct UI elements with step general', async () => {
		renderComponent({step: 'general'});

		expect(
			screen.getByRole('button', {name: 'cancel'})
		).toBeInTheDocument();
		expect(screen.getByRole('button', {name: 'save'})).toBeInTheDocument();
		expect(
			screen.getByRole('heading', {
				name: 'general',
			})
		).toBeInTheDocument();

		expect(screen.getByTestId('bannerImage')).toBeInTheDocument();
		expect(screen.getByTestId('clientNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('clientLogoSticker')).toBeInTheDocument();
		expect(screen.getByTestId('friendlyURLInput')).toBeInTheDocument();
		expect(screen.getByTestId('primaryColorInput')).toBeInTheDocument();
		expect(screen.getByTestId('roomNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('secondaryColorInput')).toBeInTheDocument();
	});

	it('preload fields with step general', async () => {
		const spyOnGetDigitalSalesRoom = jest.spyOn(
			DigitalSalesRoomService,
			'getDigitalSalesRoom'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-rooms.*/i,
			() => {
				return {
					accountId: 0,
					channelId: 0,
					channelName: '',
					clientName: 'clientName1',
					description: 'description1',
					friendlyUrlPath: '/path1',
					id: 101,
					name: 'name1',
					primaryColor: 'red',
					secondaryColor: '#FF8A1C',
				};
			}
		);

		renderComponent({
			digitalSalesRoomId: 101,
			step: 'general',
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnGetDigitalSalesRoom).toBeCalledWith(101);

		await waitFor(() => {
			expect(screen.getByTestId('clientNameInput')).toHaveValue(
				'clientName1'
			);
			expect(screen.getByTestId('friendlyURLInput')).toHaveValue('path1');
			expect(screen.getByTestId('primaryColorInput')).toHaveValue('red');
			expect(screen.getByTestId('roomNameInput')).toHaveValue('name1');
			expect(screen.getByTestId('secondaryColorInput')).toHaveValue(
				'FF8A1C'
			);
		});
	});

	it('calls API on save button with step general', async () => {
		const spyOnPatchDigitalSalesRoom = jest.spyOn(
			DigitalSalesRoomService,
			'patchDigitalSalesRoom'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-rooms.*/i,
			() => {
				return {
					accountId: 0,
					channelId: 0,
					channelName: '',
					clientName: 'clientName1',
					friendlyUrlPath: '/path1',
					id: 101,
					name: 'name1',
					primaryColor: 'red',
					secondaryColor: '#FF8A1C',
				};
			}
		);

		renderComponent({
			digitalSalesRoomId: 100,
			step: 'general',
		});

		await waitFor(() => {
			expect(screen.getByTestId('roomNameInput')).toHaveValue('name1');
		});

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'clientName2'
		);
		await setFieldValue(screen.getByTestId('friendlyURLInput'), 'path2');
		await setFieldValue(screen.getByTestId('primaryColorInput'), 'green');
		await setFieldValue(screen.getByTestId('roomNameInput'), 'name2');
		await setFieldValue(
			screen.getByTestId('secondaryColorInput'),
			'00FFFF'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPatchDigitalSalesRoom).toBeCalledWith(100, {
			banner: {
				fileBase64: '',
			},
			clientLogo: {
				fileBase64: '',
			},
			clientName: 'clientName2',
			description: '',
			friendlyUrlPath: '/path2',
			name: 'name2',
			primaryColor: 'green',
			secondaryColor: '#00FFFF',
		});
	});
});
