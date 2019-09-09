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

import fetch from '../../mock/fetch.es';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../mock/MockRouter.es';
import SLAListCard from '../../../src/main/resources/META-INF/resources/js/components/sla/SLAListCard.es';

test('Should render component', () => {
	const data = {
		items: [
			{
				dateModified: new Date(
					Date.UTC('2019', '04', '06', '20', '32', '18')
				),
				description: 'Total time to complete the request.',
				duration: 1553879089,
				name: 'Total resolution time'
			}
		],
		totalCount: 0
	};

	const component = renderer.create(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component after item was removed', () => {
	const data = {items: [], totalCount: 0};
	const component = renderer.create(
		<Router client={fetch(data)}>
			<SLAListCard itemRemoved={'test'} />
		</Router>
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render toast with SLA saved message', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const instance = component.find(SLAListCard).instance();

	instance.showStatusMessage();

	expect(component).toMatchSnapshot();
});

test('Should render toast with SLA updated message', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const instance = component.find(SLAListCard).instance();

	instance.showStatusMessage();

	expect(component).toMatchSnapshot();
});

test('Should remove a item', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);
	const instance = component.find(SLAListCard).instance();

	instance.removeItem();
	expect(component).toMatchSnapshot();
});

test('Should test props change', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);
	const instance = component.find(SLAListCard).instance();

	instance.componentWillReceiveProps({});
	expect(component).toMatchSnapshot();
});
