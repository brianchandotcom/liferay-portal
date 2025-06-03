/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {
	AddSpaceMembers,
	AddSpaceMembersProps,
} from '../../../../src/main/resources/META-INF/resources/js/main/spaces/AddSpaceMembers';

describe('AddSpaceMembers', () => {
	const props: AddSpaceMembersProps = {
		spaceName: 'Design',
	};

	it('renders with correct title, description, buttons', () => {
		render(<AddSpaceMembers {...props} />);

		expect(
			screen.getByRole('heading', {name: 'add-members-to-x'})
		).toBeInTheDocument();
		expect(
			screen.getByText(
				'add-team-members-to-this-space-to-start-collaborating'
			)
		).toBeInTheDocument();

		const learnMoreLink = screen.getByRole('link', {
			name: 'learn-more-about-memberships',
		});
		expect(learnMoreLink).toBeInTheDocument();
		expect(learnMoreLink).toHaveAttribute('href', '/');

		expect(
			screen.getByRole('button', {
				name: 'continue-without-members',
			})
		).toBeInTheDocument();
	});
});
