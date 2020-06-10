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
import {fireEvent, render} from '@testing-library/react';
import EditAppContext, {
	reducer,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useReducer, useState} from 'react';

import DeployAppModal from '../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/DeployAppModal.es';
import configReducer from '../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/configReducer.es';
import AppContextProviderWrapper from '../../../AppContextProviderWrapper.es';

const EditAppContextProviderWrapper = ({children}) => {
	const [{app}, dispatch] = useReducer(reducer, {
		app: {
			active: true,
			appDeployments: [],
			dataLayoutId: null,
			dataListViewId: null,
			name: {
				en_US: '',
			},
			scope: 'workflow',
		},
	});
	const [config, dispatchConfig] = useReducer(configReducer, {
		dataObject: {},
		formView: {},
		tableView: {},
	});

	const [isModalVisible, setModalVisible] = useState(true);

	const editState = {
		appId: 1234,
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
	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('renders all deployment modes correctly', async () => {
		const {getByText, queryByText} = render(<DeployAppModal />, {
			wrapper: EditAppContextProviderWrapper,
		});

		jest.runAllTimers();

		const toggleSwitches = document.querySelectorAll(
			'input.toggle-switch-check'
		);
		const deployButton = getByText('done');

		expect(deployButton).toBeDisabled();
		expect(toggleSwitches.length).toBe(3);
		expect(queryByText('widget')).toBeTruthy();
		expect(queryByText('deploy-a-widget')).toBeTruthy();
		expect(queryByText('standalone')).toBeTruthy();
		expect(
			queryByText('deploy-a-standalone-app-with-a-direct-link')
		).toBeTruthy();
		expect(queryByText('product-menu')).toBeTruthy();
		expect(
			queryByText('deploy-to-the-control-panel-or-a-site-menu')
		).toBeTruthy();
		expect(queryByText('cancel')).toBeTruthy();

		await fireEvent.click(toggleSwitches[1]);

		expect(deployButton).toBeEnabled();
	});
});
