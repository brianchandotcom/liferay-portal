/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {
	act,
	fireEvent,
	render,
	renderHook,
	screen,
	waitFor,
} from '@testing-library/react';
import React, {useState} from 'react';

import {DSRContext} from '../../../src/main/resources/META-INF/resources/js/components/DSRInitializer';
import DSRRoomDetailsStep from '../../../src/main/resources/META-INF/resources/js/components/DSRRoomDetailsStep';
import {
	TDSRDataContext,
	TDSRRoomDetailsStepProps,
} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';
import {setFieldValue} from '../utils';

let {result: useStateHookResult} = renderHook(() =>
	useState<TDSRDataContext>({
		banner: {},
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
	numberOfSteps = 1,
	setHandleStepSubmit,
	showHeader = true,
}: TDSRRoomDetailsStepProps) => {
	return (
		<DSRContext.Provider
			value={{
				dataContext: useStateHookResult.current[0],
				setDataContext: useStateHookResult.current[1],
			}}
		>
			<DSRRoomDetailsStep
				numberOfSteps={numberOfSteps}
				setHandleStepSubmit={setHandleStepSubmit}
				showHeader={showHeader}
			/>
		</DSRContext.Provider>
	);
};

const renderComponent = ({
	numberOfSteps = 1,
	setHandleStepSubmit,
	showHeader = true,
}: TDSRRoomDetailsStepProps) => {
	return render(component({numberOfSteps, setHandleStepSubmit, showHeader}));
};

describe('DSRRoomDetailsStep', () => {
	beforeEach(() => {
		({result: useStateHookResult} = renderHook(() =>
			useState({
				banner: {},
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

	it('renders the correct UI elements', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(screen.getByTestId('bannerImage')).toBeInTheDocument();
		expect(screen.getByTestId('clientNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('clientLogoSticker')).toBeInTheDocument();
		expect(screen.getByTestId('friendlyURLInput')).toBeInTheDocument();
		expect(screen.getByTestId('primaryColorInput')).toBeInTheDocument();
		expect(screen.getByTestId('roomNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('secondaryColorInput')).toBeInTheDocument();
		expect(screen.getByTestId('stepLocator')).toBeInTheDocument();
		expect(screen.getByTestId('stepTitle')).toBeInTheDocument();
	});

	it('hides header based on the parameter', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
			showHeader: false,
		});

		expect(screen.getByTestId('bannerImage')).toBeInTheDocument();
		expect(screen.getByTestId('clientNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('clientLogoSticker')).toBeInTheDocument();
		expect(screen.getByTestId('friendlyURLInput')).toBeInTheDocument();
		expect(screen.getByTestId('primaryColorInput')).toBeInTheDocument();
		expect(screen.getByTestId('roomNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('secondaryColorInput')).toBeInTheDocument();
		expect(screen.queryByTestId('stepLocator')).not.toBeInTheDocument();
		expect(screen.queryByTestId('stepTitle')).not.toBeInTheDocument();
	});

	it.skip('upload client logo', async () => {
		const container = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(screen.getByTestId('clientLogoButton')).toBeInTheDocument();
		expect(
			screen.queryByTestId('clientLogoDeleteButton')
		).not.toBeInTheDocument();

		const file = new File(['(⌐□_□)'], 'logo.png', {
			type: 'image/png',
		});

		await waitFor(() => {
			fireEvent.change(screen.getByTestId('clientLogoInput'), {
				target: {files: [file]},
			});
		});

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: () => {}})
		);

		expect(screen.getByTestId('clientLogoButton')).toBeInTheDocument();
		expect(
			screen.getByTestId('clientLogoDeleteButton')
		).toBeInTheDocument();
	});

	it('validate step', async () => {
		let stepSubmit: Function = function () {};

		const submit = (fun: Function) => {
			stepSubmit = fun();
		};

		const container = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: submit,
		});

		expect(screen.queryByTestId('clientNameError')).not.toBeInTheDocument();
		expect(screen.queryByTestId('roomNameError')).not.toBeInTheDocument();

		await act(async () => {
			expect(await stepSubmit(new CustomEvent('event'))).toBeFalsy();
		});

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: submit})
		);

		expect(screen.queryByTestId('clientNameError')).toBeInTheDocument();
		expect(screen.queryByTestId('roomNameError')).toBeInTheDocument();

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'clientName'
		);
		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: submit})
		);

		await act(async () => {
			expect(await stepSubmit(new CustomEvent('event'))).toBeFalsy();
		});

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: submit})
		);

		expect(screen.queryByTestId('clientNameError')).not.toBeInTheDocument();
		expect(screen.queryByTestId('roomNameError')).toBeInTheDocument();

		await setFieldValue(screen.getByTestId('roomNameInput'), 'roomName');
		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: submit})
		);

		await act(async () => {
			expect(await stepSubmit(new CustomEvent('event'))).toBeTruthy();
		});

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: submit})
		);

		expect(screen.queryByTestId('clientNameError')).not.toBeInTheDocument();
		expect(screen.queryByTestId('roomNameError')).not.toBeInTheDocument();
	});

	it('validate UI interaction', async () => {
		const stepSubmit = jest.fn();

		const container = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: stepSubmit,
		});

		await setFieldValue(
			screen.getByTestId('clientNameInput'),
			'clientName'
		);
		await setFieldValue(screen.getByTestId('roomNameInput'), 'roomName');
		await setFieldValue(screen.getByTestId('primaryColorInput'), '#000');
		await setFieldValue(
			screen.getByTestId('secondaryColorInput'),
			'#FF0000'
		);
		await setFieldValue(
			screen.getByTestId('friendlyURLInput'),
			'friendlyURL'
		);

		let state = useStateHookResult.current[0];

		expect(state.clientName).not.toBe('');
		expect(state.roomName).not.toBe('');
		expect(state.primaryColor).not.toBe('');
		expect(state.secondaryColor).not.toBe('');
		expect(state.friendlyURL).not.toBe('');
		expect(state.errors.clientName).toBe('');
		expect(state.errors.roomName).toBe('');
		expect(state.errors.primaryColor).toBe('');
		expect(state.errors.secondaryColor).toBe('');
		expect(state.errors.friendlyURL).toBe('');

		await setFieldValue(screen.getByTestId('clientNameInput'), '');
		await setFieldValue(screen.getByTestId('roomNameInput'), '');

		state = useStateHookResult.current[0];

		expect(state.clientName).toBe('');
		expect(state.roomName).toBe('');
		expect(state.primaryColor).not.toBe('');
		expect(state.secondaryColor).not.toBe('');
		expect(state.friendlyURL).not.toBe('');
		expect(state.errors.clientName).not.toBe('');
		expect(state.errors.roomName).not.toBe('');
		expect(state.errors.primaryColor).toBe('');
		expect(state.errors.secondaryColor).toBe('');
		expect(state.errors.friendlyURL).toBe('');

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: stepSubmit})
		);

		expect(screen.queryByTestId('clientNameError')).toBeInTheDocument();
		expect(screen.queryByTestId('roomNameError')).toBeInTheDocument();
	});
});
