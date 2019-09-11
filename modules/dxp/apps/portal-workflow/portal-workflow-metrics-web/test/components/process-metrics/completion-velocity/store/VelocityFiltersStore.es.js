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
import fetch from '../../../../mock/fetch.es';
import {AppContext} from '../../../../../src/main/resources/META-INF/resources/js/components/AppContext.es';
import {ErrorContext} from '../../../../../src/main/resources/META-INF/resources/js/shared/components/request/Error.es';
import {LoadingContext} from '../../../../../src/main/resources/META-INF/resources/js/shared/components/request/Loading.es';
import {MockRouter as Router} from '../../../../mock/MockRouter.es';
import {render} from '@testing-library/react';
import {TimeRangeProvider} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import {VelocityFiltersProvider} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/completion-velocity/store/VelocityFiltersStore.es';
import {VelocityUnitProvider} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/VelocityUnitStore.es';
import '@testing-library/jest-dom/extend-expect';

test('Should render velocity data provider', async () => {
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

	const Wrapper = () => (
		<AppContext.Provider value={AppContextState}>
			<Router client={fetch(data)}>
				<ErrorContext.Provider value={{error: '', setError: () => {}}}>
					<LoadingContext.Provider value={{setLoading: () => {}}}>
						<TimeRangeProvider processId="123456">
							<VelocityUnitProvider velocityUnitKeys={[]}>
								<VelocityFiltersProvider>
									<div>{'test'}</div>
								</VelocityFiltersProvider>
							</VelocityUnitProvider>
						</TimeRangeProvider>
					</LoadingContext.Provider>
				</ErrorContext.Provider>
			</Router>
		</AppContext.Provider>
	);

	const {findByText} = render(<Wrapper></Wrapper>);

	const testElement = await findByText('test', {exact: false});

	expect(testElement).toBeInTheDocument();
});
