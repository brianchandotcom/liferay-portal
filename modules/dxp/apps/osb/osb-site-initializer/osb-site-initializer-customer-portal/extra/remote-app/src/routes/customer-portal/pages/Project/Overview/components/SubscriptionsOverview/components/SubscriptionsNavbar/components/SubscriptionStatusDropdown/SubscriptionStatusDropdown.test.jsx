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
import SubscriptionStatusDropdown from '.';

describe('Subscription Status Dropdown', () => {
	it('Change the Status with the user click in DropDown', async () => {
		const user = userEvent.setup();
		render(<SubscriptionStatusDropdown />);

		const statusDropdown = screen.getByRole('button');

		expect(statusDropdown).toBeInTheDocument();
		await user.click(statusDropdown);

		const activeStatus = screen.getByRole('menuitem', {name: 'Active'});
		expect(activeStatus).toBeInTheDocument();
		await user.click(statusDropdown);

		const expiredStatus = screen.getByRole('menuitem', {name: 'Expired'});
		expect(expiredStatus).toBeInTheDocument();
		await user.click(statusDropdown);

		const futureStatus = screen.getByRole('menuitem', {name: 'Future'});
		expect(futureStatus).toBeInTheDocument();
	});
});
