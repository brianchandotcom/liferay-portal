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
import {act, fireEvent, render} from '@testing-library/react';
import fetch from 'jest-fetch-mock';

import Attributes from '../../../components/attributes/Attributes';
import People from '../../../components/people/People';
import Properties from '../../../components/properties/Properties';
import {fetchPropertiesResponse} from '../../../utils/tests/mocks';
import DefaultPage from '../DefaultPage';

describe('DefaultPage', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders DefaultPage component without crashing ', () => {
		const {getByText} = render(<DefaultPage />);

		const menu = getByText('Menu');

		expect(menu).toBeInTheDocument();
	});

	it('renders the Workspace Connection page based on the selected menu item', async () => {
		const {findByRole} = render(<DefaultPage />);

		fireEvent.click(
			await findByRole('menuitem', {
				name: 'workspace-connection',
			})
		);

		const title = document.querySelector('.sheet-title');
		expect(title).toBeInTheDocument();
		expect(title?.textContent).toBe('workspace-connection');
	});

	it('renders the People page based on the selected menu item', async () => {
		await act(async () => {
			const {findByRole} = render(<DefaultPage />);

			fireEvent.click(
				await findByRole('menuitem', {
					name: 'people',
				})
			);

			fetch.mockResponse(
				JSON.stringify({
					syncAllAccounts: false,
					syncAllContacts: false,
					syncedAccountGroupIds: [],
					syncedOrganizationIds: [],
					syncedUserGroupIds: [],
				})
			);
			render(<People />);

			const title = document.querySelector('.sheet-title');
			expect(title).toBeInTheDocument();
			expect(title?.textContent).toBe('people');
		});
	});

	it('renders the Properties page based on the selected menu item', async () => {
		await act(async () => {
			const {findByRole} = render(<DefaultPage />);
			fireEvent.click(
				await findByRole('menuitem', {
					name: 'properties',
				})
			);

			fetch.mockResponse(JSON.stringify(fetchPropertiesResponse));
			render(<Properties />);

			const title = document.querySelector('.sheet-title');
			expect(title).toBeInTheDocument();
			expect(title?.textContent).toBe('properties');
		});
	});

	it('renders the Attributes page based on the selected menu item', async () => {
		await act(async () => {
			const {findByRole} = render(<DefaultPage />);

			fireEvent.click(
				await findByRole('menuitem', {
					name: 'attributes',
				})
			);

			fetch.mockResponse(
				JSON.stringify({
					account: 25,
					order: 35,
					people: 44,
					product: 34,
				})
			);
			render(<Attributes />);

			const title = document.querySelector('.sheet-title');
			expect(title).toBeInTheDocument();
			expect(title?.textContent).toBe('attributes');
		});
	});
});
