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

import '@testing-library/jest-dom/extend-expect';
import {act, render} from '@testing-library/react';
import fetch from 'jest-fetch-mock';
import React from 'react';

import PeopleStep from '../PeopleStep';

const response = {
	syncAllAccounts: false,
	syncAllContacts: false,
	syncedAccountGroupIds: [],
	syncedOrganizationIds: [],
	syncedUserGroupIds: [],
};

describe('People Step', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('render PeopleStep without crashing', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		await act(async () => {
			const {container, getByText} = render(
				<PeopleStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const peopleStepTitle = getByText('sync-people');

			const peopleStepDescription = getByText('sync-people-description');

			expect(peopleStepTitle).toBeInTheDocument();

			expect(peopleStepDescription).toBeInTheDocument();

			expect(container.firstChild).toHaveClass('sheet');
		});
	});
});
