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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {CustomTimeRangeForm} from '../../../src/main/resources/META-INF/resources/js/components/filter/CustomTimeRangeForm.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query =
	'?filters.dateEnd=2019-12-09&filters.dateStart=2019-12-03&filters.timeRange%5B0%5D=custom';

describe('The performance by assignee card component should', () => {
	let getAllByPlaceholderText;

	afterEach(cleanup);

	beforeEach(() => {
		const wrapper = ({children}) => (
			<MockRouter query={query}>{children}</MockRouter>
		);

		const renderResult = render(<CustomTimeRangeForm />, {
			wrapper,
		});

		getAllByPlaceholderText = renderResult.getAllByPlaceholderText;
	});

	test('Be redered with default custom dates', () => {
		const dates = getAllByPlaceholderText('MM/DD/YYYY');

		expect(dates[0].value).toBe('12/03/2019');
		expect(dates[1].value).toBe('12/09/2019');
	});
});
