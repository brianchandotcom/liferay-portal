/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import EditAppContext, {
	reducer,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useReducer, useState} from 'react';

import DeployAppModal from '../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/DeployAppModal.es';
import configReducer, {
	getInitialConfig,
} from '../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/configReducer.es';
import AppContextProviderWrapper from '../../../AppContextProviderWrapper.es';

const mockToast = jest.fn();

jest.mock('frontend-js-web', () => ({
	createResourceURL: jest.fn(() => 'http://resource_url?'),
	fetch: jest.fn().mockResolvedValue(),
}));

jest.mock('app-builder-web/js/utils/client.es', () => ({
	parseResponse: jest
		.fn()
		.mockReturnValueOnce({app: {appDeployments: [{type: 'standalone'}]}})
		.mockRejectedValue({title: 'App name can not be null'}),
}));

jest.mock('app-builder-web/js/utils/toast.es', () => ({
	__esModule: true,
	errorToast: (title) => mockToast(title),
	successToast: (title) => mockToast(title),
}));

const EditAppContextProviderWrapper = ({children, edit}) => {
	const [{app}, dispatch] = useReducer(reducer, {
		app: {
			active: true,
			appDeployments: edit ? [{settings: {}, type: 'standalone'}] : [],
			dataLayoutId: null,
			dataListViewId: null,
			name: {
				en_US: '',
			},
			scope: 'workflow',
		},
	});
	const [config, dispatchConfig] = useReducer(
		configReducer,
		getInitialConfig()
	);

	const [isModalVisible, setModalVisible] = useState(true);

	const editState = {
		appId: edit ? 1234 : undefined,
		config,
		dispatch,
		dispatchConfig,
		isModalVisible,
		setModalVisible,
		state: {app},
	};

	return (
		<AppContextProviderWrapper>
			<EditAppContext.Provider value={editState}>
				{children}
			</EditAppContext.Provider>
		</AppContextProviderWrapper>
	);
};

describe('DeployAppModal', () => {
	const mockOnCancel = jest.fn();

	beforeAll(() => {
		jest.useFakeTimers();
	});

	afterAll(() => {
		cleanup();
		jest.resetAllMocks();
		jest.useRealTimers();
	});

	describe('Add', () => {
		it('renders correctly and submit', async () => {
			const {baseElement, getByText} = render(
				<DeployAppModal onCancel={mockOnCancel} />,
				{wrapper: EditAppContextProviderWrapper}
			);

			act(() => {
				jest.runAllTimers();
			});

			const deployButton = getByText('done');

			const toggleSwitches = baseElement.querySelectorAll(
				'input.toggle-switch-check'
			);

			expect(deployButton).toBeDisabled();
			expect(toggleSwitches.length).toBe(3);

			await act(async () => {
				await fireEvent.click(toggleSwitches[1]);
			});

			expect(deployButton).toBeEnabled();

			await act(async () => {
				await fireEvent.click(deployButton);
			});
		});

		it('call success toast', () => {
			expect(mockOnCancel).toHaveBeenCalled();
			expect(mockToast.mock.calls.length).toBe(1);
			expect(JSON.stringify(mockToast.mock.calls[0][0])).toContain(
				'the-app-was-deployed-successfully'
			);
		});
	});

	describe('Edit', () => {
		it('renders correctly and submit', async () => {
			const {baseElement, getByText} = render(
				<EditAppContextProviderWrapper edit>
					<DeployAppModal onCancel={mockOnCancel} />
				</EditAppContextProviderWrapper>
			);

			act(() => {
				jest.runAllTimers();
			});

			const deployButton = getByText('done');
			const toggleSwitches = baseElement.querySelectorAll(
				'input.toggle-switch-check'
			);

			expect(deployButton).toBeEnabled();
			expect(toggleSwitches.length).toBe(3);
			expect(toggleSwitches[1].checked).toBeTruthy();

			await act(async () => {
				await fireEvent.click(deployButton);
			});
		});

		it('call error toast', () => {
			expect(mockOnCancel.mock.calls.length).toBe(1);
			expect(mockToast.mock.calls.length).toBe(2);
			expect(mockToast.mock.calls[1][0]).toContain(
				'App name can not be null'
			);
		});
	});
});
