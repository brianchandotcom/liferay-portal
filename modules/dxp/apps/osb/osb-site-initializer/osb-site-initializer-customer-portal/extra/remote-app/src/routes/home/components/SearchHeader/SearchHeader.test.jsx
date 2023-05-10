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
import SearchHeader from '.';

describe('Home: SearchHeader', () => {
	const functionMock = jest.fn();

	it('displays one project when there is just one result found', () => {
		render(<SearchHeader count={1} onSearchSubmit={functionMock} />);

		const projectsNumber = screen.queryByRole('heading');
		expect(projectsNumber).toHaveTextContent(/1 project/i);
	});

	it('displays the number of projects when there is more than one result found', () => {
		render(<SearchHeader count={10} onSearchSubmit={functionMock} />);

		const projectsNumber = screen.getByRole('heading');
		expect(projectsNumber).toHaveTextContent(/10 projects/i);
	});
});
