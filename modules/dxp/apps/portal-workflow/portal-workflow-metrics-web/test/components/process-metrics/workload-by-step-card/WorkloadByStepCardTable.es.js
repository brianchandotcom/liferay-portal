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
import renderer from 'react-test-renderer';

import WorkloadByStepCardTable from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-step-card/WorkloadByStepCardTable.es';
import {MockRouter as Router} from '../../../mock/MockRouter.es';
import fetch from '../../../mock/fetch.es';

test('Should display hyphen when the task has no count', () => {
	const data = [
		{
			name: 'Single Approver'
		}
	];

	const component = renderer.create(
		<Router client={fetch(data)}>
			<WorkloadByStepCardTable items={data} processId={123456} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component', () => {
	const data = [
		{
			instanceCount: 1,
			name: 'Single Approver',
			onTimeInstanceCount: 1,
			overdueInstanceCount: 0
		}
	];

	const component = renderer.create(
		<Router client={fetch(data)}>
			<WorkloadByStepCardTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
