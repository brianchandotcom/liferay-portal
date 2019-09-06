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

import PageSizeEntries from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageSizeEntries';
import React from 'react';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should change page size', () => {
	const component = shallow(
		<PageSizeEntries
			pageSizeEntries={[10, 20, 30, 40]}
			selectedPageSize={30}
		/>
	);

	expect(component).toMatchSnapshot();
});

test('Should render component', () => {
	const component = shallow(
		<PageSizeEntries
			pageSizeEntries={[10, 20, 30, 40]}
			selectedPageSize={10}
		/>
	);

	expect(component).toMatchSnapshot();
});

test('Should render with default deltas', () => {
	const component = mount(
		<Router>
			<PageSizeEntries selectedPageSize={30} />
		</Router>
	);

	expect(component).toMatchSnapshot();
});
