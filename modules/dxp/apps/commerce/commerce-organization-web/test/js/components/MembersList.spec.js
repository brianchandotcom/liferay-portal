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

import MembersList from 'components/MembersList';
import {shallow} from 'enzyme';
import React from 'react';

describe('MembersList', () => {
	it('renders the members list if there are members', () => {
		const props = {
				isLoading: false,
				members: [{}, {}, {}],
			},
			wrapper = shallow(<MembersList {...props} />);

		expect(wrapper.find('div').some('.pane-members-list')).toBe(true);
		expect(wrapper.find('ul').children().length).toEqual(3);
	});

	it('renders NoMembers component if there are no members', () => {
		const props = {
				isLoading: false,
				members: [],
			},
			wrapper = shallow(<MembersList {...props} />);

		expect(wrapper.find('div').some('.pane-members-list')).toBe(true);
		expect(wrapper.children('Member').length).toEqual(0);
		expect(wrapper.children('NoMembers').length).toEqual(1);
	});
});
