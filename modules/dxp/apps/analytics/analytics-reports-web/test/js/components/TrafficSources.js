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

import TrafficSources from '../../../src/main/resources/META-INF/resources/js/components/TrafficSources';

describe('TrafficSources', () => {
	afterEach(cleanup);

	it('displays the sources accosding to API', async () => {
		const mockedAPI = jest.fn(() =>
			Promise.resolve({
				analyticsReportsTrafficSources: [
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
				],
			})
		);

		const {getByText} = render(
			<TrafficSources dataProvider={mockedAPI} languageTag="en-EN" />
		);

		await wait(() => expect(mockedAPI).toHaveBeenCalled());

		expect(getByText('Testing')).toBeInTheDocument();
		expect(getByText('32,178')).toBeInTheDocument();

		expect(getByText('Second Testing')).toBeInTheDocument();
		expect(getByText('278,256')).toBeInTheDocument();

		expect(mockedAPI).toHaveBeenCalledTimes(1);
	});
});
