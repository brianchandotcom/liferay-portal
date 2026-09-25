/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import ProjectSelector from '../../js/components/ProjectSelector';

describe('ProjectSelector', () => {
	it('renders picker with items and shows selected project', () => {
		render(
			<ProjectSelector
				items={[
					{
						label: 'Test Project',
						value: '123',
					},
				]}
			/>
		);

		expect(screen.getByDisplayValue('123')).toBeInTheDocument();
		expect(screen.getByText('projects')).toBeInTheDocument();
		expect(screen.getByText('Test Project')).toBeInTheDocument();
		expect(screen.getByRole('combobox')).toBeDisabled();
	});

	it('does not render anything when no items', () => {
		render(<ProjectSelector items={[]} />);

		expect(screen.queryByText('projects')).not.toBeInTheDocument();
	});
});
