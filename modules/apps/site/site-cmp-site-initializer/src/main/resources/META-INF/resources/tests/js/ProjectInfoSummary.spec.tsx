/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import ProjectInfoSummary from '../../js/components/project/ProjectInfoSummary';

const mockStates = [
	{key: 'notStarted', name: 'Not Started', nextStates: ['inProgress']},
	{key: 'inProgress', name: 'In Progress', nextStates: ['notStarted']},
];

const mockManager = {image: '', name: 'Manager Name'};
const mockSponsor = {image: '', name: 'Sponsor Name'};

describe('ProjectInfoSummary', () => {
	it('renders with props', () => {
		const {getByText} = render(
			<ProjectInfoSummary
				dueDate="2023-12-31"
				initialState="notStarted"
				manager={mockManager}
				projectId="123"
				sponsor={mockSponsor}
				states={mockStates}
				tags={['tag1', 'tag2']}
			/>
		);

		expect(getByText('Manager Name')).toBeInTheDocument();
		expect(getByText('Sponsor Name')).toBeInTheDocument();
		expect(getByText('2023-12-31')).toBeInTheDocument();
		expect(getByText('tag1')).toBeInTheDocument();
		expect(getByText('tag2')).toBeInTheDocument();
		expect(getByText('Not Started')).toBeInTheDocument();
	});
});
