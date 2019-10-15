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

import ProcessListItem from '../../../src/main/resources/META-INF/resources/js/components/process-list/ProcessListItem.es';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../mock/MockRouter.es';

test('Should render component with one list item', () => {
	const component = renderer.create(
		<Router>
			<ProcessListItem
				id={36401}
				instanceCount={10}
				onTimeInstanceCount={5}
				overdueInstanceCount={5}
				title="Process test"
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with 1 instance count', () => {
	const component = renderer.create(
		<Router>
			<ProcessListItem
				id={36401}
				instanceCount={1}
				onTimeInstanceCount={5}
				overdueInstanceCount={5}
				title="Process test"
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
