/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import CustomTimeRangeForm from '../../../src/main/resources/META-INF/resources/js/components/filter/CustomTimeRangeForm.es';
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
