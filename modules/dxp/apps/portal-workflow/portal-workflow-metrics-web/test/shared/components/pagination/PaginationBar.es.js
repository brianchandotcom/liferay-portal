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

import PaginationBar from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PaginationBar';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/components/AppContext'
);

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<PaginationBar
				page={1}
				pageCount={5}
				pageSize={5}
				totalCount={22}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with total count minor than last page', () => {
	const component = renderer.create(
		<Router>
			<PaginationBar page={1} pageCount={5} pageSize={5} totalCount={5} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
