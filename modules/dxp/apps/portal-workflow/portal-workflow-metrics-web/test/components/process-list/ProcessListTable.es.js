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

import ProcessListTable from '../../../src/main/resources/META-INF/resources/js/components/process-list/ProcessListTable';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../mock/MockRouter';

test('Should render component', () => {
	const data = [
		{
			id: 36401,
			instancesCount: 0,
			title: 'test'
		}
	];

	const component = renderer.create(
		<Router>
			<ProcessListTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
