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
			colorIdentifier: 'hsl(0,100%,100%)',
			listBy: 'user',
			onLookUp: expect.any(Function),
			onViewSelected: expect.any(Function),
			orgName: 'Org name',
			spritemap: expect.any(String),
			totalAccounts: 1,
			totalSubOrg: 1,
			totalUsers: 1,
		};

		const wrapper = shallow(<PaneHeader {...inputProps} />);

		expect(wrapper.children('PaneOrgInfo').props()).toMatchObject({
			childrenNo: inputProps.totalSubOrg,
			colorIdentifier: inputProps.colorIdentifier,
			orgName: inputProps.orgName,
			showMenu: expect.any(Function),
		});
		expect(wrapper.children('PaneViewSelector').props()).toMatchObject({
			listBy: inputProps.listBy,
			onViewSelected: inputProps.onViewSelected,
			totalAccounts: inputProps.totalAccounts,
			totalUsers: inputProps.totalUsers,
		});
		expect(wrapper.children('PaneSearchBar').props()).toMatchObject({
			onLookUp: inputProps.onLookUp,
			spritemap: inputProps.spritemap,
		});
	});
});
