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
import ProjectCard from '.';
import getDateCustomFormat from '../../../../../../common/utils/getDateCustomFormat';

describe('render Project Card component', () => {
	const koroneikiAccount = {
		accountKey: `KOR-${faker.random.numeric(5)}`,
		code: faker.commerce.product().toUpperCase(),
		name: faker.commerce.productName(),
		region: faker.address.country(),
		slaCurrentEndDate: faker.date.future(),
		status: 'Active',
	};

	const FORMAT_DATE = {
		day: '2-digit',
		month: 'short',
		year: 'numeric',
	};

	test('Test if the Project Card contains Project Name', () => {
		render(<ProjectCard {...koroneikiAccount} />);

		const projectName = screen.queryByRole('heading');
		expect(projectName).toHaveTextContent(koroneikiAccount.name);
	});

	test('Test if the Project Card contains Status', () => {
		render(<ProjectCard {...koroneikiAccount} />);

		const projectStatus = screen.getByText(koroneikiAccount.status);
		expect(projectStatus).toHaveTextContent(koroneikiAccount.status);
	});

	test('Test if the Project Card contains Subscription End Date.', () => {
		render(<ProjectCard {...koroneikiAccount} />);

		const projectEndDate = screen.getByText(
			getDateCustomFormat(
				koroneikiAccount.slaCurrentEndDate,
				FORMAT_DATE
			),
			{exact: false}
		);
		expect(projectEndDate).toHaveTextContent(
			getDateCustomFormat(koroneikiAccount.slaCurrentEndDate, FORMAT_DATE)
		);
	});

	test('Verify if the projects are displayed as cards if has less than 05 projects', () => {
		const compressed = false;

		const {container} = render(
			<ProjectCard compressed={compressed} {...koroneikiAccount} />
		);

		expect(container.getElementsByClassName('cp-project-card').length).toBe(
			1
		);
	});

	test('Verify if the projects are displayed as a list if has more than 05 projects', () => {
		const compressed = true;

		const {container} = render(
			<ProjectCard compressed={compressed} {...koroneikiAccount} />
		);

		expect(
			container.getElementsByClassName('cp-project-card-sm').length
		).toBe(1);
	});
});
