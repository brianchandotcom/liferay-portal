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

import React from 'react';
import {AppContext} from '../../../../src/main/resources/META-INF/resources/js/components/AppContext.es';
import {ErrorContext} from '../../../../src/main/resources/META-INF/resources/js/shared/components/request/Error.es';
import {LoadingContext} from '../../../../src/main/resources/META-INF/resources/js/shared/components/request/Loading.es';
import {MockRouter as Router} from '../../../mock/MockRouter.es';
import {render} from '@testing-library/react';
import {TimeRangeContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import {VelocityDataContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/completion-velocity/store/VelocityDataStore.es';
import {VelocityUnitContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/VelocityUnitStore.es';
import {waitForElement} from '@testing-library/dom';
import fetch from '../../../mock/fetch.es';
import VelocityChart from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/completion-velocity/VelocityChart.es';
import '@testing-library/jest-dom/extend-expect';

test('Should render velocity chart', async () => {
	const data = {
		items: [
			{
				dateEnd: '2019-09-05T18:40:40Z',
				dateStart: '2019-09-05T00:00:00Z',
				defaultTimeRange: false,
				id: 0,
				name: 'Today'
			},
			{
				dateEnd: '2019-09-04T23:59:59Z',
				dateStart: '2019-09-04T00:00:00Z',
				defaultTimeRange: false,
				id: 1,
				name: 'Yesterday'
			},
			{
				dateEnd: '2019-09-05T18:40:40Z',
				dateStart: '2019-08-30T00:00:00Z',
				defaultTimeRange: false,
				id: 7,
				name: 'Last 7 Days'
			},
			{
				dateEnd: '2019-09-05T18:40:40Z',
				dateStart: '2019-08-07T00:00:00Z',
				defaultTimeRange: true,
				id: 30,
				name: 'Last 30 Days'
			},
			{
				dateEnd: '2019-09-05T18:40:40Z',
				dateStart: '2019-06-08T00:00:00Z',
				defaultTimeRange: false,
				id: 90,
				name: 'Last 90 Days'
			},
			{
				dateEnd: '2019-09-05T18:40:40Z',
				dateStart: '2019-03-10T00:00:00Z',
				defaultTimeRange: false,
				id: 180,
				name: 'Last 180 Days'
			},
			{
				dateEnd: '2019-09-05T18:40:40Z',
				dateStart: '2018-09-05T00:00:00Z',
				defaultTimeRange: false,
				id: 365,
				name: 'Last Year'
			}
		],
		lastPage: 1,
		page: 1,
		pageSize: 7,
		totalCount: 7
	};

	const velocityData = {
		histograms: [
			{
				key: '2019-06-27T00:00',
				value: 0.0
			},
			{
				key: '2019-07-01T00:00',
				value: 0.0
			},
			{
				key: '2019-08-01T00:00',
				value: 0.0
			},
			{
				key: '2019-09-01T00:00',
				value: 0.0
			}
		],
		value: 0.0
	};

	const AppContextState = {
		client: fetch(data),
		companyId: '12345',
		defaultDelta: 20,
		deltas: 0,
		isAmPm: true,
		maxPages: 10,
		namespace: 'test',
		setStatus() {},
		setTitle() {},
		status: null,
		title: Liferay.Language.get('metrics')
	};

	const getSelectedTimeRange = () => true;

	const Wrapper = () => (
		<AppContext.Provider value={AppContextState}>
			<Router client={fetch(data)}>
				<ErrorContext.Provider value={{error: '', setError: () => {}}}>
					<LoadingContext.Provider value={{setLoading: () => {}}}>
						<TimeRangeContext.Provider
							value={{
								getSelectedTimeRange
							}}
						>
							<VelocityUnitContext.Provider
								value={{
									getSelectedVelocityUnit: () => ({})
								}}
							>
								<VelocityDataContext.Provider
									value={{
										velocityData
									}}
								>
									<VelocityChart />
								</VelocityDataContext.Provider>
							</VelocityUnitContext.Provider>
						</TimeRangeContext.Provider>
					</LoadingContext.Provider>
				</ErrorContext.Provider>
			</Router>
		</AppContext.Provider>
	);

	const {container, getByTestId} = render(<Wrapper></Wrapper>);

	const element = await waitForElement(() => getByTestId('velocity-chart'), {
		container
	});

	expect(element).toBeInTheDocument();
});

test('Should render velocity chart tooltip', async () => {
	const timeRange = {
		active: true,
		dateEnd: '2019-09-25T17:06:52.000Z',
		dateStart: '2019-06-28T00:00:00.000Z',
		defaultTimeRange: false,
		description: 'Jun 28 - Sep 25',
		id: 90,
		key: '90',
		name: 'Last 90 Days'
	};

	const dataPoints = [
		{
			id: 'data_1',
			index: 5,
			name: 'data_1',
			value: 0,
			x: new Date('2019-09-24T05:00:00.000Z')
		}
	];

	const text = VelocityChart.Tooltip(
		true,
		timeRange,
		'Months',
		'Inst / Month'
	)(dataPoints);

	const textDefault = VelocityChart.Tooltip(
		true,
		timeRange,
		'test',
		'Inst / Month'
	)(dataPoints);

	const textHours = VelocityChart.Tooltip(
		true,
		timeRange,
		'Hours',
		'Inst / Hours'
	)(dataPoints);

	const textHoursAmPm = VelocityChart.Tooltip(
		false,
		timeRange,
		'Hours',
		'Inst / Hours'
	)(dataPoints);

	const textMonths = VelocityChart.Tooltip(
		true,
		timeRange,
		'Months',
		'Inst / Months'
	)(dataPoints);

	const textWeeks = VelocityChart.Tooltip(
		true,
		timeRange,
		'Weeks',
		'Inst / Weeks'
	)(dataPoints);

	const textYears = VelocityChart.Tooltip(
		true,
		timeRange,
		'Years',
		'Inst / Years'
	)(dataPoints);

	expect(textHours.includes('0 Inst / Hours')).toBe(true);
	expect(textHoursAmPm.includes('0 Inst / Hours')).toBe(true);
	expect(textMonths.includes('0 Inst / Months')).toBe(true);
	expect(textWeeks.includes('0 Inst / Weeks')).toBe(true);
	expect(textYears.includes('0 Inst / Years')).toBe(true);
	expect(text.includes('0 Inst / Month')).toBe(true);
	expect(textDefault.includes('0 Inst / Month')).toBe(true);
});

test('Should render velocity chart tooltip with invalid date', async () => {
	const timeRange = {
		active: true,
		dateEnd: '2019-09-25T17:06:52.000Z',
		dateStart: '2019-06-28T00:00:00.000Z',
		defaultTimeRange: false,
		description: 'Jun 28 - Sep 25',
		id: 90,
		key: '90',
		name: 'Last 90 Days'
	};

	const dataPoints = [
		{
			id: 'data_1',
			index: 5,
			name: 'data_1',
			value: 0,
			x: new Date('1936-09-25T17:06:52.000Z')
		}
	];

	const textHours = VelocityChart.Tooltip(
		true,
		timeRange,
		'Hours',
		'Inst / Hours'
	)(dataPoints);

	dataPoints[0].x = null;

	const textDefault = VelocityChart.Tooltip(
		true,
		timeRange,
		'Hours',
		'Inst / Hours'
	)(dataPoints);

	expect(textHours.includes('0 Inst / Hours')).toBe(true);
	expect(textDefault.includes('0 Inst / Hours')).toBe(true);
});
