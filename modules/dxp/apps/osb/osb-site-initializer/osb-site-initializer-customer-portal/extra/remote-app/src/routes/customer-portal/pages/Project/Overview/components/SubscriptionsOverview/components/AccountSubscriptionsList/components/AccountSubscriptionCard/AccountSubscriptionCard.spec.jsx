/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
