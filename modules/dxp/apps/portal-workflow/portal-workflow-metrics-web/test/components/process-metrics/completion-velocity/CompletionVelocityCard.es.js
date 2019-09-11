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
import fetch from '../../../mock/fetch.es';
import CompletionVelocityCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/completion-velocity/CompletionVelocityCard.es';
import {MockRouter as Router} from '../../../mock/MockRouter.es';

test('Should render component', () => {
	const props = {
		processId: 12345,
		query:
			'?backPath=%2Fprocesses%2F20%2F1%2FoverdueInstanceC…eRange%5B0%5D=30&filters.velocityUnit%5B0%5D=Days'
	};

	const data = {
		items: [
			{
				instanceCount: 1,
				name: 'Task Name',
				onTimeInstanceCount: 1,
				overdueInstanceCount: 0
			}
		],
		totalCount: 1
	};

	const component = mount(
		<Router client={fetch(data)}>
			<CompletionVelocityCard {...props} />
		</Router>
	);

	expect(
		component
			.find('.dashboard-panel-header .mr-2')
			.html()
			.includes(Liferay.Language.get('completion-velocity'))
	).toEqual(true);
});
