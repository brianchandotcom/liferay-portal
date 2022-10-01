/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ('License'). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {render, screen} from '@testing-library/react';
import AccountSubscriptionCard from '.';

describe('Account Subscription Card', () => {
	it('Contains the Subscription Image', () => {
		render(
			<AccountSubscriptionCard logoPath="http://www.test.com/test_icon" />
		);

		const subscriptionImage = screen.getByRole('img');
		expect(subscriptionImage).toHaveAttribute(
			'src',
			'http://www.test.com/test_icon'
		);
	});

	it('Contains Subscriptions Name', () => {
		render(<AccountSubscriptionCard name="Test" />);

		const subscriptionName = screen.queryByRole('heading');
		expect(subscriptionName).toHaveTextContent('Test');
	});

	it('Contains the Subscription number of Instances Size', () => {
		render(<AccountSubscriptionCard instanceSize={3} />);

		const subscriptionInstanceSize = screen.getByText(/instance size: 3/i);
		expect(subscriptionInstanceSize).toHaveTextContent('Instance Size: 3');
	});

	it('Contains the Subscription Start and End Date', () => {
		render(
			<AccountSubscriptionCard
				endDate="2018-07-25T00:00:00Z"
				startDate="2017-08-25T00:00:00Z"
			/>
		);

		const subscriptionStartDate = screen.getByText('08/24/2017', {
			exact: false,
		});
		expect(subscriptionStartDate).toHaveTextContent('08/24/2017');

		const subscriptionEndDate = screen.getByText('07/24/2018', {
			exact: false,
		});
		expect(subscriptionEndDate).toHaveTextContent('07/24/2018');
	});

	it('Contains the Subscription Status', () => {
		render(<AccountSubscriptionCard subscriptionStatus="active" />);

		const subscriptionStatus = screen.getByText(/active/i);
		expect(subscriptionStatus).toHaveTextContent('Active');
	});
});
