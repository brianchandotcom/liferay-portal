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
import {cleanup, render, wait} from '@testing-library/react';
import React from 'react';

import Chart from '../../../src/main/resources/META-INF/resources/js/components/Chart';

const readsDataProvider = jest.fn(() =>
	Promise.resolve({
		analyticsReportsHistoricalReads: {
			histogram: [
				{
					key: '2020-01-27T00:00',
					previousKey: '2020-01-20T00:00',
					previousValue: 22.0,
					value: 33.0
				},
				{
					key: '2020-01-28T00:00',
					previousKey: '2020-01-21T00:00',
					previousValue: 23.0,
					value: 33.0
				},
				{
					key: '2020-01-29T00:00',
					previousKey: '2020-01-22T00:00',
					previousValue: 24.0,
					value: 34.0
				},
				{
					key: '2020-01-30T00:00',
					previousKey: '2020-01-23T00:00',
					previousValue: 25.0,
					value: 33.0
				},
				{
					key: '2020-01-31T00:00',
					previousKey: '2020-01-24T00:00',
					previousValue: 26.0,
					value: 32.0
				},
				{
					key: '2020-02-01T00:00',
					previousKey: '2020-01-25T00:00',
					previousValue: 27.0,
					value: 31.0
				},
				{
					key: '2020-02-02T00:00',
					previousKey: '2020-01-26T00:00',
					previousValue: 28.0,
					value: 30.0
				}
			],
			previousValue: 175.0,
			value: 226.0
		}
	})
);

const viewsDataProvider = jest.fn(() =>
	Promise.resolve({
		analyticsReportsHistoricalViews: {
			histogram: [
				{
					key: '2020-01-27T00:00',
					previousKey: '2020-01-20T00:00',
					previousValue: 22.0,
					value: 32.0
				},
				{
					key: '2020-01-28T00:00',
					previousKey: '2020-01-21T00:00',
					previousValue: 23.0,
					value: 33.0
				},
				{
					key: '2020-01-29T00:00',
					previousKey: '2020-01-22T00:00',
					previousValue: 24.0,
					value: 34.0
				},
				{
					key: '2020-01-30T00:00',
					previousKey: '2020-01-23T00:00',
					previousValue: 25.0,
					value: 33.0
				},
				{
					key: '2020-01-31T00:00',
					previousKey: '2020-01-24T00:00',
					previousValue: 26.0,
					value: 32.0
				},
				{
					key: '2020-02-01T00:00',
					previousKey: '2020-01-25T00:00',
					previousValue: 27.0,
					value: 31.0
				},
				{
					key: '2020-02-02T00:00',
					previousKey: '2020-01-26T00:00',
					previousValue: 28.0,
					value: 30.0
				}
			],
			previousValue: 175.0,
			value: 225.0
		}
	})
);

describe('Chart', () => {
	afterEach(cleanup);

	it('displays total views and date range title', async () => {
		const {getByText} = render(
			<Chart dataProviders={[viewsDataProvider]} languageTag={'en-EN'} />
		);

		await wait(() => expect(viewsDataProvider).toHaveBeenCalledTimes(1));

		expect(getByText('225')).toBeInTheDocument();

		expect(getByText('Jan 27 - Feb 2, 2020')).toBeInTheDocument();
	});

	it('displays total views and reads and date range title', async () => {
		const {getByText} = render(
			<Chart
				dataProviders={[viewsDataProvider, readsDataProvider]}
				languageTag={'en-EN'}
			/>
		);

		await wait(() => expect(viewsDataProvider).toHaveBeenCalledTimes(2));

		expect(getByText('225')).toBeInTheDocument();
		expect(getByText('226')).toBeInTheDocument();

		expect(getByText('Jan 27 - Feb 2, 2020')).toBeInTheDocument();
	});
});
