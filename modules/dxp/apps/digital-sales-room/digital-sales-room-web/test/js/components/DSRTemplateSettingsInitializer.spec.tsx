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

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import DSRTemplateSettingsInitializer from '../../../src/main/resources/META-INF/resources/js/components/DSRTemplateSettingsInitializer';
import {setFieldValue} from '../utils';

global.ResizeObserver = ResizeObserver;

const renderComponent = ({
	cancelURL = '',
	digitalSalesRoomTemplateId = 100,
	step = 'general',
}: {
	cancelURL?: string;
	digitalSalesRoomTemplateId?: number;
	step?: string;
}) => {
	return render(
		<DSRTemplateSettingsInitializer
			cancelURL={cancelURL}
			digitalSalesRoomTemplateId={digitalSalesRoomTemplateId}
			step={step}
		></DSRTemplateSettingsInitializer>
	);
};

describe('DSRTemplateSettingsInitializer', () => {
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
		expect(screen.getByTestId('roomNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('descriptionInput')).toBeInTheDocument();
	});

	it('renders the correct UI elements with step lookAndFeel', async () => {
		renderComponent({step: 'lookAndFeel'});

		expect(
			screen.getByRole('button', {name: 'cancel'})
		).toBeInTheDocument();
		expect(screen.getByRole('button', {name: 'save'})).toBeInTheDocument();
		expect(
			screen.getByRole('heading', {
				name: 'look-and-feel',
			})
		).toBeInTheDocument();
		expect(screen.getByTestId('bannerImage')).toBeInTheDocument();
		expect(screen.getByTestId('clientLogoSticker')).toBeInTheDocument();
		expect(screen.getByTestId('primaryColorInput')).toBeInTheDocument();
		expect(screen.getByTestId('secondaryColorInput')).toBeInTheDocument();
	});

	it('preload fields with step general', async () => {
		const spyOnGetDigitalSalesRoomTemplate = jest.spyOn(
			DigitalSalesRoomService,
			'getDigitalSalesRoomTemplate'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-room-templates.*/i,
			() => {
				return {
					description: 'template1descr',
					id: 101,
					name: 'template1',
					primaryColor: 'red',
					secondaryColor: '#FF8A1C',
				};
			}
		);

		renderComponent({
			digitalSalesRoomTemplateId: 101,
			step: 'general',
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnGetDigitalSalesRoomTemplate).toBeCalledWith(101);

		await waitFor(() => {
			expect(screen.getByTestId('roomNameInput')).toHaveValue(
				'template1'
			);
			expect(screen.getByTestId('descriptionInput')).toHaveValue(
				'template1descr'
			);
		});
	});

	it('preload fields with step lookAndFeel', async () => {
		const spyOnGetDigitalSalesRoomTemplate = jest.spyOn(
			DigitalSalesRoomService,
			'getDigitalSalesRoomTemplate'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-room-templates.*/i,
			() => {
				return {
					description: 'template1descr',
					id: 101,
					name: 'template1',
					primaryColor: 'red',
					secondaryColor: '#FF8A1C',
				};
			}
		);

		renderComponent({
			digitalSalesRoomTemplateId: 101,
			step: 'lookAndFeel',
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnGetDigitalSalesRoomTemplate).toBeCalledWith(101);

		await waitFor(() => {
			expect(screen.getByTestId('primaryColorInput')).toHaveValue('red');
			expect(screen.getByTestId('secondaryColorInput')).toHaveValue(
				'FF8A1C'
			);
		});
	});

	it('calls API on save button with step general', async () => {
		const spyOnPatchDigitalSalesRoomTemplate = jest.spyOn(
			DigitalSalesRoomService,
			'patchDigitalSalesRoomTemplate'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-room-templates.*/i,
			() => {
				return {
					description: 'template1descr',
					id: 100,
					name: 'template1',
					primaryColor: 'red',
					secondaryColor: '#FF8A1C',
				};
			}
		);

		renderComponent({
			digitalSalesRoomTemplateId: 100,
			step: 'general',
		});

		await waitFor(() => {
			expect(screen.getByTestId('roomNameInput')).toHaveValue(
				'template1'
			);
		});

		await setFieldValue(screen.getByTestId('roomNameInput'), 'templateMod');
		await setFieldValue(
			screen.getByTestId('descriptionInput'),
			'templateDescrMod'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPatchDigitalSalesRoomTemplate).toBeCalledWith(100, {
			description: 'templateDescrMod',
			name: 'templateMod',
		});
	});

	it('calls API on save button with step lookAndFeel', async () => {
		const spyOnPatchDigitalSalesRoomTemplate = jest.spyOn(
			DigitalSalesRoomService,
			'patchDigitalSalesRoomTemplate'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-room-templates.*/i,
			() => {
				return {
					description: 'template1descr',
					id: 100,
					name: 'template1',
					primaryColor: 'red',
					secondaryColor: '#FF8A1C',
				};
			}
		);

		renderComponent({
			digitalSalesRoomTemplateId: 100,
			step: 'lookAndFeel',
		});

		await waitFor(() => {
			expect(screen.getByTestId('primaryColorInput')).toHaveValue('red');
		});

		await setFieldValue(screen.getByTestId('primaryColorInput'), 'green');
		await setFieldValue(
			screen.getByTestId('secondaryColorInput'),
			'FF0000'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPatchDigitalSalesRoomTemplate).toBeCalledWith(100, {
			banner: {
				fileBase64: '',
			},
			clientLogo: {
				fileBase64: '',
			},
			clientName: '',
			primaryColor: 'green',
			secondaryColor: '#FF0000',
		});
	});
});
