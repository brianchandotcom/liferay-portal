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

import PaneViewSelector from 'components/PaneViewSelector';
import {mount} from 'enzyme';
import React from 'react';

describe('PaneViewSelector', () => {
	it('renders correctly with list-by USERS selected by default', () => {
		const inputProps = {
				listBy: 'user',
				onViewSelected: () => {},
				totalAccounts: 4,
				totalUsers: 5,
			},
			wrapper = mount(<PaneViewSelector {...inputProps} />);

		expect(wrapper.find('span').get(0).props).toMatchObject({
			children: `${inputProps.listBy} (${inputProps.totalUsers})`,
			className: 'selected-pane',
			role: 'button',
			tabIndex: '-1',
		});
	});

	it('lists members by account if the related tab is selected', () => {
		const inputProps = {
				listBy: 'account',
				onViewSelected: () => {},
				totalAccounts: 4,
				totalUsers: 5,
			},
			wrapper = mount(<PaneViewSelector {...inputProps} />);

		expect(wrapper.find('span').get(1).props).toMatchObject({
			children: `account (${inputProps.totalAccounts})`,
			className: 'selected-pane',
			role: 'button',
			tabIndex: '-1',
		});
	});
});
