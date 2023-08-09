/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import CardHeader from '../components/ViewObjectDefinitions/CardHeader';
import {getFolderActions} from '../components/ViewObjectDefinitions/objectDefinitionUtil';

describe('The CardHeader component should', () => {
	it('render all the folder actions', () => {
		render(
			<CardHeader
				externalReferenceCode="ticketERC"
				items={
					getFolderActions(1010, '', () => {}, {
						delete: {href: '', method: 'DELETE'},
						get: {href: 'GET', method: ''},
						permissions: {href: 'PATCH', method: ''},
						update: {href: '', method: 'PUT'},
					}) as IItem[]
				}
				label={{en_US: 'Ticket'}}
			></CardHeader>
		);

		userEvent.click(screen.getByRole('button', {name: 'folder-actions'}));

		expect(screen.getAllByRole('menuitem')).toHaveLength(4);

		expect(screen.getByText('edit-label-and-erc')).toBeInTheDocument();

		expect(screen.getByText('import-object')).toBeInTheDocument();

		expect(screen.getByText('folder-permissions')).toBeInTheDocument();

		expect(screen.getByText('delete-folder')).toBeInTheDocument();
	});

	it('not render delete and edit folder actions on uncategorized folder', () => {
		render(
			<CardHeader
				externalReferenceCode="uncategorized"
				items={
					getFolderActions(1010, '', () => {}, {
						get: {href: 'GET', method: ''},
						permissions: {href: 'PATCH', method: ''},
					}) as IItem[]
				}
				label={{en_US: 'Uncategorized'}}
			></CardHeader>
		);

		userEvent.click(screen.getByRole('button', {name: 'folder-actions'}));

		expect(screen.getAllByRole('menuitem')).toHaveLength(2);

		expect(screen.getByText('import-object')).toBeInTheDocument();

		expect(screen.getByText('folder-permissions')).toBeInTheDocument();
	});
});
