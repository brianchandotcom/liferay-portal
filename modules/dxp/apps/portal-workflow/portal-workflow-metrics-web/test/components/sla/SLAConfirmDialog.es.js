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
import SLAConfirmDialog from '../../../src/main/resources/META-INF/resources/js/components/sla/SLAConfirmDialog.es';
import SLAListCard from '../../../src/main/resources/META-INF/resources/js/components/sla/SLAListCard.es';

jest.useFakeTimers();

test('Should render component', () => {
	const component = renderer.create(<SLAConfirmDialog itemToRemove={1234} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should cancel dialog', () => {
	const component = mount(
		<Router>
			<SLAConfirmDialog itemToRemove={1234} />
		</Router>
	);

	const dialog = component.find(SLAConfirmDialog).instance();

	dialog.context = {
		hideConfirmDialog: () => {}
	};

	dialog.cancel();

	expect(component).toMatchSnapshot();
});

test('Should cancel dialog through SLA List', () => {
	const data = {
		items: [
			{
				description: 'Total time to complete the request.',
				duration: 1553879089,
				id: 1234,
				name: 'Total resolution time'
			}
		],
		totalCount: 0
	};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const {slaContextState} = component.find(SLAListCard).instance();

	slaContextState.showConfirmDialog(1234);
	jest.runAllTimers();
	expect(component).toMatchSnapshot();

	slaContextState.hideConfirmDialog();
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should remove item', () => {
	const component = mount(
		<Router>
			<SLAConfirmDialog itemToRemove={1234} />
		</Router>
	);

	const dialog = component.find(SLAConfirmDialog).instance();

	dialog.context = {
		removeItem: () => {}
	};

	dialog.removeItem();

	expect(component).toMatchSnapshot();
});
