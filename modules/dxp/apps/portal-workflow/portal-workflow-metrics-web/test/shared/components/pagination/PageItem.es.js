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

import PageItem from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageItem';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should render component as type default', () => {
	const component = renderer.create(
		<Router>
			<PageItem page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as active', () => {
	const component = renderer.create(
		<Router>
			<PageItem active page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as type Next', () => {
	const component = renderer.create(
		<Router>
			<PageItem page={1} type="next" />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as type Previous', () => {
	const component = renderer.create(
		<Router>
			<PageItem page={1} type="prev" />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

xtest('Should change page', () => {
	const onChangePage = () => () => 'test';

	const component = mount(
		<Router>
			<PageItem onChangePage={onChangePage()} page={2} />
		</Router>
	);

	const instance = component.find(PageItem).instance();

	instance.setPage();

	expect(component).toMatchSnapshot();
});

xtest('Should test change page when disabled', () => {
	const onChangePage = () => () => 'test';

	const component = mount(
		<Router>
			<PageItem disabled onChangePage={onChangePage()} page={2} />
		</Router>
	);

	const instance = component.find(PageItem).instance();

	instance.setPage();

	expect(component).toMatchSnapshot();
});
