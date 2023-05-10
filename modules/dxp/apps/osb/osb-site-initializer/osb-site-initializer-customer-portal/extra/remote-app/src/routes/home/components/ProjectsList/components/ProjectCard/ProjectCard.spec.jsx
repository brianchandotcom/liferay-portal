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
import ProjectCard from '.';

describe('Project Card', () => {
	it('contains Project Name', () => {
		render(<ProjectCard name="Test Account 01" />);

		const projectName = screen.queryByRole('heading');
		expect(projectName).toHaveTextContent('Test Account 01');
	});

	it('contains Project Status', () => {
		render(<ProjectCard status="Active" />);

		const projectStatus = screen.getByText('Active');
		expect(projectStatus).toHaveTextContent('Active');
	});

	it('contains Subscription End Date.', () => {
		render(<ProjectCard slaCurrentEndDate="2014-12-31T00:00:00-05:00" />);

		const projectEndDate = screen.getByText('Dec 31, 2014');
		expect(projectEndDate).toHaveTextContent('Dec 31, 2014');
	});

	it('displays projects as cards if has less than 05 projects', () => {
		const {container} = render(<ProjectCard compressed={false} />);

		expect(
			container.getElementsByClassName('cp-project-card-lg').length
		).toBe(1);
	});

	it('displays projects as a list if has more than 05 projects', () => {
		const {container} = render(<ProjectCard compressed />);

		expect(container.getElementsByClassName('card-horizontal').length).toBe(
			1
		);
	});
});
