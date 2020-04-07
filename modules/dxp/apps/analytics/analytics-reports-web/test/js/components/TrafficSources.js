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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import TrafficSources from '../../../src/main/resources/META-INF/resources/js/components/TrafficSources';

describe('TrafficSources', () => {
	afterEach(cleanup);

	it('displays the sources according to API', () => {
		const mockTrafficSources = [
			{
				helpMessage: 'Testing Help Message',
				name: 'testing',
				title: 'Testing',
				value: 32178,
			},
			{
				helpMessage: 'Second Testing Help Message',
				name: 'second-testing',
				title: 'Second Testing',
				value: 278256,
			},
		];

		const {getByText} = render(
			<TrafficSources
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
				trafficSources={mockTrafficSources}
			/>
		);

		expect(getByText('Testing')).toBeInTheDocument();
		expect(getByText('32,178')).toBeInTheDocument();

		expect(getByText('Second Testing')).toBeInTheDocument();
		expect(getByText('278,256')).toBeInTheDocument();
	});

	it('displays a dash instead of value when the value is missing', () => {
		const mockTrafficSources = [
			{
				helpMessage: 'Testing Help Message',
				name: 'testing',
				title: 'Testing',
				value: 32178,
			},
			{
				helpMessage: 'Second Testing Help Message',
				name: 'second-testing',
				title: 'Second Testing',
			},
		];

		const {getByText} = render(
			<TrafficSources
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
				trafficSources={mockTrafficSources}
			/>
		);

		expect(getByText('Testing')).toBeInTheDocument();
		expect(getByText('32,178')).toBeInTheDocument();

		expect(getByText('Second Testing')).toBeInTheDocument();
		expect(getByText('-')).toBeInTheDocument();
	});

	it('displays a message informing the user that there is no incoming traffic from search engines yet', () => {
		const mockTrafficSources = [
			{
				helpMessage: 'Testing Help Message',
				name: 'testing',
				title: 'Testing',
				value: 0,
			},
			{
				helpMessage: 'Second Testing Help Message',
				name: 'second-testing',
				title: 'Second Testing',
				value: 0,
			},
		];

		const {getAllByText, getByText} = render(
			<TrafficSources
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
				trafficSources={mockTrafficSources}
			/>
		);

		expect(getByText('Testing')).toBeInTheDocument();
		expect(getByText('Second Testing')).toBeInTheDocument();

		const zeroValues = getAllByText('0');
		expect(zeroValues.length).toBe(2);

		expect(
			getByText(
				'your-page-has-no-incoming-traffic-from-search-engines-yet'
			)
		).toBeInTheDocument();
	});
});
