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

import PaneHeader from 'components/PaneHeader';
import {shallow} from 'enzyme';
import React from 'react';

describe('PaneHeader', () => {
	it('renders the pane header with the PaneOrgInfo, PaneViewSelector and PaneSearchBar components', () => {
		const inputProps = {
			orgName: 'Org name',
			totalSubOrg: 1,
			colorIdentifier: 'hsl(0,100%,100%)',
			onLookUp: expect.any(Function),
			onViewSelected: expect.any(Function),
			totalAccounts: 1,
			totalUsers: 1,
			spritemap: expect.any(String),
			listBy: 'user',
		};

		const wrapper = shallow(<PaneHeader {...inputProps} />);

		expect(wrapper.children('PaneOrgInfo').props()).toMatchObject({
			orgName: inputProps.orgName,
			childrenNo: inputProps.totalSubOrg,
			colorIdentifier: inputProps.colorIdentifier,
			showMenu: expect.any(Function),
		});
		expect(wrapper.children('PaneViewSelector').props()).toMatchObject({
			listBy: inputProps.listBy,
			totalAccounts: inputProps.totalAccounts,
			totalUsers: inputProps.totalUsers,
			onViewSelected: inputProps.onViewSelected,
		});
		expect(wrapper.children('PaneSearchBar').props()).toMatchObject({
			onLookUp: inputProps.onLookUp,
			spritemap: inputProps.spritemap,
		});
	});
});
