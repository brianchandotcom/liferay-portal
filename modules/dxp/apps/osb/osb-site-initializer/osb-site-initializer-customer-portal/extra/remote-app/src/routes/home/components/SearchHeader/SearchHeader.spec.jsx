/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {render, screen} from '@testing-library/react';
import {vi} from 'vitest';
import SearchHeader from '.';

describe('Home: SearchHeader', () => {
	const functionMock = vi.fn();

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
