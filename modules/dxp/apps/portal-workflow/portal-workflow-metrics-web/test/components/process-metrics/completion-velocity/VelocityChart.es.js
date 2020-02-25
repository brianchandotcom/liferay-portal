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

import {waitForElement} from '@testing-library/dom';
import {render} from '@testing-library/react';
import React from 'react';

import {AppContext} from '../../../../src/main/resources/META-INF/resources/js/components/AppContext.es';
import VelocityChart from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/completion-velocity/VelocityChart.es';

import '@testing-library/jest-dom/extend-expect';

describe('The velocity chart should', () => {
	test('Be rendered in document', async () => {
		const velocityData = {
			histograms: [
				{
					key: '2019-12-03T00:00',
					value: 0.0,
				},
				{
					key: '2019-12-04T00:00',
					value: 0.0,
				},
				{
					key: '2019-12-05T00:00',
					value: 0.0,
				},
				{
					key: '2019-12-06T00:00',
					value: 0.0,
				},
				{
					key: '2019-12-07T00:00',
					value: 0.2,
				},
				{
					key: '2019-12-08T00:00',
					value: 0.8,
				},
				{
					key: '2019-12-09T00:00',
					value: 0.0,
				},
			],
			value: 0.0,
		};

		const {getByTestId} = render(
			<AppContext.Provider value={{isAmPm: true}}>
				<VelocityChart
					timeRange={{
						dateEnd: '2019-12-09T00:00:00Z',
						dateStart: '2019-12-03T00:00:00Z',
					}}
					velocityData={velocityData}
					velocityUnit={{key: 'Days', name: 'Int / Day'}}
				/>
			</AppContext.Provider>
		);

		const velocityChat = await waitForElement(() =>
			getByTestId('velocity-chart')
		);

		expect(velocityChat).toBeInTheDocument();
	});

	describe('Be render tooltip', () => {
		test('with valid dates', async () => {
			const timeRange = {
				active: true,
				dateEnd: '2019-09-25T17:06:52.000Z',
				dateStart: '2019-06-28T00:00:00.000Z',
				defaultTimeRange: false,
				description: 'Jun 28 - Sep 25',
				id: 90,
				key: '90',
				name: 'Last 90 Days',
			};

			const dataPoints = [
				{
					id: 'data_1',
					index: 5,
					name: 'data_1',
					value: 0,
					x: new Date('2019-09-24T05:00:00.000Z'),
				},
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

			expect(textHours).toContain('0 Inst / Hours');
			expect(textHoursAmPm).toContain('0 Inst / Hours');
			expect(textMonths).toContain('0 Inst / Months');
			expect(textWeeks).toContain('0 Inst / Weeks');
			expect(textYears).toContain('0 Inst / Years');
			expect(text).toContain('0 Inst / Month');
			expect(textDefault).toContain('0 Inst / Month');
		});

		test('with invalid date', async () => {
			const timeRange = {
				active: true,
				dateEnd: '2019-09-25T17:06:52.000Z',
				dateStart: '2019-06-28T00:00:00.000Z',
				defaultTimeRange: false,
				description: 'Jun 28 - Sep 25',
				id: 90,
				key: '90',
				name: 'Last 90 Days',
			};

			const dataPoints = [
				{
					id: 'data_1',
					index: 5,
					name: 'data_1',
					value: 0,
					x: new Date('1936-09-25T17:06:52.000Z'),
				},
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

			expect(textHours).toContain('0 Inst / Hours');
			expect(textDefault).toContain('0 Inst / Hours');
		});
	});
});
