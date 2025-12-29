/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {act, cleanup, render, renderHook, screen} from '@testing-library/react';
import React, {useState} from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import {DSRContext} from '../../../src/main/resources/META-INF/resources/js/components/DSRTemplateInitializer';
import DSRTemplateSettingsStep from '../../../src/main/resources/META-INF/resources/js/components/DSRTemplateSettingsStep';
import {
	TDSRDataContext,
	TDSRRoomDetailsStepProps,
} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';
import {setFieldValue} from '../utils';

global.ResizeObserver = ResizeObserver;

let {result: useStateHookResult} = renderHook(() =>
	useState<TDSRDataContext>({
		banner: {},
		clientLogo: {},
		clientName: '',
		description: '',
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
}: TDSRRoomDetailsStepProps) => {
	return (
		<DSRContext.Provider
			value={{
				dataContext: useStateHookResult.current[0],
				setDataContext: useStateHookResult.current[1],
			}}
		>
			<DSRTemplateSettingsStep
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

describe('DSRTemplateSettingsStep', () => {
	beforeEach(() => {
		({result: useStateHookResult} = renderHook(() =>
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
		));
	});

	afterEach(() => {
		jest.clearAllMocks();

		cleanup();
	});

	it('renders the correct UI elements', async () => {
		renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: () => {},
		});

		expect(screen.getByTestId('roomNameInput')).toBeInTheDocument();
		expect(screen.getByTestId('descriptionInput')).toBeInTheDocument();
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

		expect(screen.queryByTestId('roomNameError')).not.toBeInTheDocument();

		await act(async () => {
			expect(await stepSubmit(new CustomEvent('event'))).toBeFalsy();
		});

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: submit})
		);

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

		expect(screen.queryByTestId('roomNameError')).not.toBeInTheDocument();
	});

	it('validate UI interaction', async () => {
		const stepSubmit = jest.fn();

		const container = renderComponent({
			numberOfSteps: 1,
			setHandleStepSubmit: stepSubmit,
		});

		await setFieldValue(screen.getByTestId('roomNameInput'), 'roomName');
		await setFieldValue(
			screen.getByTestId('descriptionInput'),
			'description'
		);

		let state = useStateHookResult.current[0];

		expect(state.roomName).not.toBe('');
		expect(state.description).not.toBe('');

		await setFieldValue(screen.getByTestId('roomNameInput'), '');
		await setFieldValue(screen.getByTestId('descriptionInput'), '');

		state = useStateHookResult.current[0];

		expect(state.roomName).toBe('');
		expect(state.description).toBe('');
		expect(state.errors.roomName).not.toBe('');
		expect(state.errors.description).toBe('');

		container.rerender(
			component({numberOfSteps: 1, setHandleStepSubmit: stepSubmit})
		);

		expect(screen.queryByTestId('roomNameError')).toBeInTheDocument();
	});
});
