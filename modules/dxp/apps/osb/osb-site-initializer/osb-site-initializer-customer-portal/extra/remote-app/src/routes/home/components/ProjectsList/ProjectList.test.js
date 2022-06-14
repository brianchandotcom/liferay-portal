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

import {faker} from '@faker-js/faker';
import {render, screen} from '@testing-library/react';
import ProjectList from '.';

describe('render Project List component', () => {
	const koroneikiAccounts = {
		items: [
			{
				accountKey: `KOR-${faker.random.numeric(5)}`,
				code: faker.commerce.product().toUpperCase(),
				name: faker.commerce.productName(),
				region: faker.address.country(),
				slaCurrentEndDate: faker.date.future(),
				status: 'Active',
			},
			{
				accountKey: `KOR-${faker.random.numeric(5)}`,
				code: faker.commerce.product().toUpperCase(),
				name: faker.commerce.productName(),
				region: faker.address.country(),
				slaCurrentEndDate: faker.date.future(),
				status: 'Active',
			},
			{
				accountKey: `KOR-${faker.random.numeric(5)}`,
				code: faker.commerce.product().toUpperCase(),
				name: faker.commerce.productName(),
				region: faker.address.country(),
				slaCurrentEndDate: faker.date.future(),
				status: 'Active',
			},
			{
				accountKey: `KOR-${faker.random.numeric(5)}`,
				code: faker.commerce.product().toUpperCase(),
				name: faker.commerce.productName(),
				region: faker.address.country(),
				slaCurrentEndDate: faker.date.future(),
				status: 'Active',
			},
			{
				accountKey: `KOR-${faker.random.numeric(5)}`,
				code: faker.commerce.product().toUpperCase(),
				name: faker.commerce.productName(),
				region: faker.address.country(),
				slaCurrentEndDate: faker.date.future(),
				status: 'Active',
			},
			{
				accountKey: `KOR-${faker.random.numeric(5)}`,
				code: faker.commerce.product().toUpperCase(),
				name: faker.commerce.productName(),
				region: faker.address.country(),
				slaCurrentEndDate: faker.date.future(),
				status: 'Active',
			},
		],
		totalCount: 6,
	};

	window.IntersectionObserver = jest.fn(() => ({
		observer: jest.fn(),
		unobserver: jest.fn(),
	}));

	test('Verify if the message "results not found" appears if there is no project to show', () => {
		render(<ProjectList />);

		const showNotFoundMessage = screen.getByText(/no results found/i);
		expect(showNotFoundMessage).toBeInTheDocument();
	});

	test('Verify if the projects are displayed as cards if has less than 05 projects', () => {
		const compressed = false;

		const {container} = render(
			<ProjectList
				hasManyProjects={compressed}
				koroneikiAccounts={koroneikiAccounts}
				totalCount={koroneikiAccounts.totalCount}
			/>
		);

		expect(
			container.getElementsByClassName('cp-home-projects').length
		).toBe(1);
	});

	test('Verify if the projects are displayed as a list if has more than 05 projects', () => {
		const compressed = true;

		const {container} = render(
			<ProjectList
				hasManyProjects={compressed}
				koroneikiAccounts={koroneikiAccounts}
				totalCount={koroneikiAccounts.totalCount}
			/>
		);

		expect(
			container.getElementsByClassName('cp-home-projects-sm').length
		).toBe(1);
	});
});
