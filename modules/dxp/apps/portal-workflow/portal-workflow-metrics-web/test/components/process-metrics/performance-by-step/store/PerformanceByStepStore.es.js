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
import {MockRouter as Router} from '../../../../mock/MockRouter.es';
import {render} from '@testing-library/react';
import {PerformanceDataProvider} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-step/store/PerformanceByStepStore.es';
import '@testing-library/jest-dom/extend-expect';

test('Should render velocity data provider', async () => {
	const data = {
		items: [],
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
				<PerformanceDataProvider
					page={1}
					pageSize={10}
					processId="123456"
					sort={'overdueInstanceCount:asc'}
				>
					<div>{'test'}</div>
				</PerformanceDataProvider>
			</Router>
		</AppContext.Provider>
	);

	const {findByText, unmount} = render(<Wrapper></Wrapper>);

	const testElement = await findByText('test', {exact: false});

	expect(testElement).toBeInTheDocument();

	unmount();
});

test('Should render velocity without page', async () => {
	const data = {
		items: [],
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
				<PerformanceDataProvider
					page={0}
					pageSize={10}
					processId="123456"
					sort={'overdueInstanceCount:asc'}
				>
					<div>{'test'}</div>
				</PerformanceDataProvider>
			</Router>
		</AppContext.Provider>
	);

	const {findByText, unmount} = render(<Wrapper></Wrapper>);

	const testElement = await findByText('test', {exact: false});

	expect(testElement).toBeInTheDocument();

	unmount();
});
