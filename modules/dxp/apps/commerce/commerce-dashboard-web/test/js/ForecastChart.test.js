/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, waitForElementToBeRemoved} from '@testing-library/react';
import {fetch as mockedFetch} from 'frontend-js-web';
import React from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import {ForecastChart} from '../../src/main/resources/META-INF/resources/js';
import {ACCOUNT_CATEGORY_FORECASTS_MOCK} from './mocks';

global.ResizeObserver = ResizeObserver;

const mathRandomSpy = jest.spyOn(Math, 'random').mockReturnValue(0.5);

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn(),
}));

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children}) => (
			<OriginalModule.ResponsiveContainer height={350} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
		Tooltip: ({children, ...props}) => (
			<OriginalModule.Tooltip {...props} active>
				{children}
			</OriginalModule.Tooltip>
		),
	};
});

describe('ForecastChart component', () => {
	afterAll(() => {
		mathRandomSpy.mockRestore();
		mockedFetch.mockReset();

		delete global.ResizeObserver;
	});

	it('render', async () => {
		mockedFetch.mockReturnValue(
			Promise.resolve({
				json: () => Promise.resolve(ACCOUNT_CATEGORY_FORECASTS_MOCK),
			})
		);

		const {container} = render(
			<ForecastChart
				accountIds='["39339"]'
				apiURL="/o/headless-commerce-machine-learning/v1.0/accountCategoryForecasts/by-monthlyRevenue"
				categoryIds="[]"
			/>
		);

		await waitForElementToBeRemoved(
			container.querySelector('.loading-animation')
		);

		expect(container).toMatchSnapshot();
	});
});
