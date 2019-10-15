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

import PageLink from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageLink.es';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter.es';

test('Should render component as type default', () => {
	const component = renderer.create(
		<Router>
			<PageLink page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as active', () => {
	const component = renderer.create(
		<Router>
			<PageLink disabled page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const component = shallow(
		<Router>
			<PageLink page={2} />
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should test change page when disabled', () => {
	const component = shallow(
		<Router>
			<PageLink disabled page={2} />
		</Router>
	);

	expect(component).toMatchSnapshot();
});
