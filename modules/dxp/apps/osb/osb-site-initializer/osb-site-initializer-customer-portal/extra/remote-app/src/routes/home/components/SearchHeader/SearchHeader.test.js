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
import SearchHeader from '.';

describe('render Search Header component', () => {
	test('test if number of projects is working with one result', async () => {
		const functionMock = jest.fn();
		const countProject = 1;

		render(
			<SearchHeader count={countProject} onSearchSubmit={functionMock} />
		);

		const projectsNumber = screen.getByRole('heading');

		expect(projectsNumber).toHaveTextContent(`${countProject} project`);
	});

	test('test if number of projects is working with more than one result', async () => {
		const functionMock = jest.fn();
		const countProject = faker.random.numeric();

		render(
			<SearchHeader count={countProject} onSearchSubmit={functionMock} />
		);

		const projectsNumber = screen.getByRole('heading');

		expect(projectsNumber).toHaveTextContent(`${countProject} projects`);
	});
});
