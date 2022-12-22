/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import fetch from 'jest-fetch-mock';

import PeoplePage from '../PeoplePage';

const response = {
	syncAllAccounts: false,
	syncAllContacts: false,
	syncedAccountGroupIds: [],
	syncedOrganizationIds: [],
	syncedUserGroupIds: [],
};

describe('PeoplePage', () => {
	it('renders page title and description', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		await act(async () => {
			render(<PeoplePage title="People" />);
		});

		const title = screen.getByText('People');
		const description = screen.getByText('sync-people-description');

		expect(title).toBeInTheDocument();
		expect(description).toBeInTheDocument();
	});
});
