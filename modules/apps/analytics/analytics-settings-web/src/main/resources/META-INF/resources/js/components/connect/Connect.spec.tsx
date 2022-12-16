/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {AppContextData, EPageView} from '../../App';
import Connect from './Connect';

describe('Connect', () => {
	it('renders DISCONNECTED component without crashing', () => {
		const {container} = render(<Connect title="testConnect" />);

		const sheetTextDiv = container.querySelector(
			'div.sheet h2.sheet-title ~ div.sheet-text'
		);

		const formControl = container.querySelector(
			'div.form-group input.form-control'
		);

		const footer = container.querySelector('div.sheet-footer');

		const button = container.querySelector('button.btn');

		expect(sheetTextDiv).toBeInTheDocument();

		expect(sheetTextDiv).toHaveTextContent(
			'use-the-token-genereted-in-your-analytics-cloud-to-connect-this-workspace'
		);

		expect(formControl).toBeInTheDocument();

		expect(footer).toBeInTheDocument();

		expect(button).toBeInTheDocument();

		expect(button).toHaveTextContent('connect');
	});

	it('renders CONNECTED component without crashing', () => {
		const {container} = render(
			<AppContextData.Provider
				value={{
					connected: true,
					liferayAnalyticsURL: '',
					pageView: EPageView.Default,
					token: '',
				}}
			>
				<Connect title="testConnect" />
			</AppContextData.Provider>
		);

		const sheetTextDiv = container.querySelector(
			'div.sheet h2.sheet-title ~ div.sheet-text'
		);

		const formControl = container.querySelector(
			'div.form-group input.form-control'
		);

		const footer = container.querySelector('div.sheet-footer');

		const button = container.querySelector(
			'button ~ button.btn[type="button"]'
		);

		expect(sheetTextDiv).toBeInTheDocument();

		expect(formControl).toBeInTheDocument();

		expect(footer).toBeInTheDocument();

		expect(button).toBeInTheDocument();

		expect(button).toHaveTextContent('disconnect');
	});

	it('renders modal when disconnect button is clicked', () => {
		const {container, getByText} = render(
			<AppContextData.Provider
				value={{
					connected: true,
					liferayAnalyticsURL: '',
					pageView: EPageView.Default,
					token: '',
				}}
			>
				<Connect title="testConnect" />
			</AppContextData.Provider>
		);

		const sheetTextDiv = container.querySelector(
			'div.sheet h2.sheet-title ~ div.sheet-text'
		);

		const formControl = container.querySelector(
			'div.form-group input.form-control'
		);

		const footer = container.querySelector('div.sheet-footer');

		const modalContent = container.getElementsByClassName('modal-content');

		expect(sheetTextDiv).toBeInTheDocument();

		expect(formControl).toBeInTheDocument();

		expect(footer).toBeInTheDocument();

		const disconnectBtn = getByText('disconnect');

		userEvent.click(disconnectBtn);

		expect(modalContent).toBeTruthy();
	});
});
