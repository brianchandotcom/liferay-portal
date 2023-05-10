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

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import AccountSubscriptionGroupsDropdown from '.';

describe('Account Subscription Dropdown', () => {
	const functionMock = jest.fn();

	const accountSubscriptionGroups = [
		{
			name: 'account test',
		},
		{
			name: 'account test 2',
		},
	];

	it('Change Subscription With the user click in Dropdown', async () => {
		const user = userEvent.setup();

		render(
			<AccountSubscriptionGroupsDropdown
				accountSubscriptionGroups={accountSubscriptionGroups}
				onSelect={functionMock}
				selectedIndex={0}
			/>
		);

		const accountSubscriptionGroupsDropdown = screen.getByTestId(
			'subscriptionDropDown'
		);
		expect(accountSubscriptionGroupsDropdown).toBeInTheDocument();
		await user.click(accountSubscriptionGroupsDropdown);

		const accountSubscriptionGroupsFirstItem = screen.getByRole(
			'menuitem',
			{
				name: 'account test',
			}
		);
		expect(accountSubscriptionGroupsFirstItem).toBeInTheDocument();
		await user.click(accountSubscriptionGroupsDropdown);

		const accountSubscriptionGroupsSecondItem = screen.getByRole(
			'menuitem',
			{name: 'account test 2'}
		);
		expect(accountSubscriptionGroupsSecondItem).toBeInTheDocument();
	});
});
