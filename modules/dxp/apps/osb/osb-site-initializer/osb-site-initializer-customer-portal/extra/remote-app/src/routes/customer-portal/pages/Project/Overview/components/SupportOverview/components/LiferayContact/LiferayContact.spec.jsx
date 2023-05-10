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
import LiferayContact from './LiferayContact';

describe('LiferayContact', () => {
	const koroneikiAccount = {
		liferayContactEmailAddress: 'janedoe@company.com',
		liferayContactName: 'Jane Doe',
		liferayContactRole: 'Administrator',
	};

	it('displays project support liferay contact name', () => {
		render(<LiferayContact koroneikiAccount={koroneikiAccount} />);
		const linkElementContactName = screen.getByText('Jane Doe');

		expect(linkElementContactName).toBeInTheDocument();
	});

	it('displays project support liferay contact email address', () => {
		render(<LiferayContact koroneikiAccount={koroneikiAccount} />);

		const linkElementContactEmailAddress = screen.getByText(
			/janedoe@company/i,
			{exact: false}
		);

		expect(linkElementContactEmailAddress).toBeInTheDocument();
	});

	it('displays project support liferay contact role', () => {
		render(<LiferayContact koroneikiAccount={koroneikiAccount} />);

		const linkElementContactRole = screen.getByText(/administrator/i);

		expect(linkElementContactRole).toBeInTheDocument();
	});
});
