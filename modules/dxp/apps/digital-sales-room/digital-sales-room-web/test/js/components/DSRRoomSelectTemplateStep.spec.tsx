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
import DSRRoomSelectTemplateStep from '../../../src/main/resources/META-INF/resources/js/components/DSRRoomSelectTemplateStep';
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
		templateId: 0,
	})
);

const component = ({
	numberOfSteps = 1,
	setHandleStepSubmit,
}: TDSRRoomDetailsStepProps) => {
	return (
		<DSRContext.Provider
			value={{
				dataContext: useStateHookResult.current[0],
				setDataContext: useStateHookResult.current[1],
			}}
		>
			<DSRRoomSelectTemplateStep
				numberOfSteps={numberOfSteps}
				setHandleStepSubmit={setHandleStepSubmit}
			/>
		</DSRContext.Provider>
	);
};

const renderComponent = ({
	numberOfSteps = 1,
	setHandleStepSubmit,
}: TDSRRoomDetailsStepProps) => {
	return render(component({numberOfSteps, setHandleStepSubmit}));
};

describe('DSRRoomSelectTemplateStep', () => {
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
				templateId: 0,
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

		expect(screen.getByTestId('templatePreview')).toBeInTheDocument();
		expect(
			screen.queryByTestId('templatePreviewFrame')
		).not.toBeInTheDocument();
		expect(screen.getByTestId('savedTemplates')).toBeInTheDocument();
		expect(screen.getByTestId('stepLocator')).toBeInTheDocument();
		expect(screen.getByTestId('stepTitle')).toBeInTheDocument();
	});

	it('loads templates', async () => {
		const spyOnGetDigitalSalesRoomTemplates = jest.spyOn(
			DigitalSalesRoomService,
			'getDigitalSalesRoomTemplates'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-room-templates.*/i,
			() => {
				return {
					items: [
						{
							description: 'template1descr',
							id: 100,
							name: 'template1',
						},
						{
							description: 'template2descr',
							id: 101,
							name: 'template2',
						},
					],
				};
			}
		);

		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(spyOnGetDigitalSalesRoomTemplates).toBeCalledTimes(1);

		await waitFor(() => {
			expect(screen.getByTestId(`template_${100}`)).toBeInTheDocument();
			expect(
				screen.getByTestId(`templateName_${100}`)
			).toBeInTheDocument();
			expect(screen.getByTestId(`templateName_${100}`)).toHaveTextContent(
				'template1'
			);
			expect(
				screen.getByTestId(`templateDescription_${100}`)
			).toBeInTheDocument();
			expect(
				screen.getByTestId(`templateDescription_${100}`)
			).toHaveTextContent('template1descr');
			expect(screen.getByTestId(`template_${101}`)).toBeInTheDocument();
			expect(
				screen.getByTestId(`templateName_${101}`)
			).toBeInTheDocument();
			expect(screen.getByTestId(`templateName_${101}`)).toHaveTextContent(
				'template2'
			);
			expect(
				screen.getByTestId(`templateDescription_${101}`)
			).toBeInTheDocument();
			expect(
				screen.getByTestId(`templateDescription_${101}`)
			).toHaveTextContent('template2descr');
		});
	});

	it('validate UI interaction', async () => {
		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-room-templates.*/i,
			() => {
				return {
					items: [
						{
							description: 'template1descr',
							id: 100,
							name: 'template1',
						},
						{
							description: 'template2descr',
							id: 101,
							name: 'template2',
						},
					],
				};
			}
		);

		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		await waitFor(() => {
			expect(screen.getByTestId(`template_${100}`)).toBeInTheDocument();
		});

		screen.getByTestId(`templateName_${100}`).click();

		await waitFor(() => {
			const state = useStateHookResult.current[0];

			expect(state.templateId).toBe(100);
			expect(
				screen.getByTestId('templatePreviewFrame')
			).toBeInTheDocument();
		});

		screen.getByTestId(`templateName_${101}`).click();

		await waitFor(() => {
			const state = useStateHookResult.current[0];

			expect(state.templateId).toBe(101);
			expect(
				screen.getByTestId('templatePreviewFrame')
			).toBeInTheDocument();
		});
	});
});
