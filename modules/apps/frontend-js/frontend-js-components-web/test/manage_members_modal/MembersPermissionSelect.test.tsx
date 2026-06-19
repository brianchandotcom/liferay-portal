/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import {MembersPermissionSelect} from '../../src/main/resources/META-INF/resources/manage_members_modal/MembersPermissionSelect';
import {Role} from '../../src/main/resources/META-INF/resources/manage_members_modal/types';

const ROLES: Role[] = [
	{externalReferenceCode: 'erc-member', id: 1, name: 'Member', name_i18n: {}},
	{externalReferenceCode: 'erc-owner', id: 2, name: 'Owner', name_i18n: {}},
	{
		externalReferenceCode: 'erc-consumer',
		id: 3,
		name: 'Consumer',
		name_i18n: {},
	},
	{externalReferenceCode: 'erc-editor', id: 4, name: 'Editor', name_i18n: {}},
];

describe('MembersPermissionSelect', () => {
	beforeAll(() => {
		(Liferay.ThemeDisplay as any).getBCP47LanguageId = () => 'en-US';
	});

	function renderOpened(
		props: Partial<
			React.ComponentProps<typeof MembersPermissionSelect>
		> = {}
	) {
		render(
			<MembersPermissionSelect
				defaultRoleName="Member"
				hiddenRoleNames={['Owner', 'Consumer']}
				onChange={() => {}}
				roles={ROLES}
				selectedRoles={['Member']}
				{...props}
			/>
		);

		fireEvent.click(screen.getByRole('button'));
	}

	it('hides the roles listed in hiddenRoleNames', () => {
		renderOpened();

		expect(screen.queryByText('Owner')).not.toBeInTheDocument();
		expect(screen.queryByText('Consumer')).not.toBeInTheDocument();
	});

	it('renders the non-hidden roles', () => {
		renderOpened();

		expect(screen.getByLabelText('Member')).toBeInTheDocument();
		expect(screen.getByLabelText('Editor')).toBeInTheDocument();
	});

	it('disables the defaultRoleName checkbox so it cannot be unchecked', () => {
		renderOpened();

		expect(screen.getByLabelText('Member')).toBeDisabled();
		expect(screen.getByLabelText('Editor')).not.toBeDisabled();
	});
});
