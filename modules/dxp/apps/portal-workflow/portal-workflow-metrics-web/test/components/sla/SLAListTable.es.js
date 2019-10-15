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
import {MockRouter as Router} from '../../mock/MockRouter.es';
import SLAListTable from '../../../src/main/resources/META-INF/resources/js/components/sla/SLAListTable.es';

test('Should render component', () => {
	const data = [
		{
			dateModified: new Date(
				Date.UTC('2019', '04', '06', '20', '32', '18')
			),
			description: 'Total time to complete the request.',
			duration: 863999940000,
			name: 'Total resolution time'
		},
		{
			dateModified: new Date(
				Date.UTC('2019', '04', '06', '20', '32', '18')
			),
			description: 'Total time to complete the request.',
			duration: 60000,
			name: 'Total resolution time'
		},
		{
			dateModified: new Date(
				Date.UTC('2019', '04', '06', '20', '32', '18')
			),
			description: 'Total time to complete the request.',
			duration: 7140000,
			name: 'Total resolution time'
		}
	];

	const component = renderer.create(
		<Router>
			<SLAListTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
