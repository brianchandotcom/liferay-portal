/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ObjectFolderCardHeader from '../components/ViewObjectDefinitions/ObjectFolderCardHeader';
import {getObjectFolderActions} from '../components/ViewObjectDefinitions/objectDefinitionUtil';

describe('The ObjectFolderCardHeader component should', () => {
	it('render all object folder actions', () => {
		render(
			<ObjectFolderCardHeader
				externalReferenceCode="ticket"
				items={
					getObjectFolderActions(1, '', () => {}, {
						delete: {href: '', method: 'DELETE'},
						get: {href: 'GET', method: ''},
						permissions: {href: 'PATCH', method: ''},
						update: {href: '', method: 'PUT'},
					}) as IItem[]
				}
				label={{en_US: 'Ticket'}}
				modelBuilderURL=""
			></ObjectFolderCardHeader>
		);

		userEvent.click(screen.getByRole('button', {name: 'folder-actions'}));

		expect(screen.getAllByRole('menuitem')).toHaveLength(3);

		expect(screen.getByText('delete-folder')).toBeInTheDocument();

		expect(screen.getByText('edit-label-and-erc')).toBeInTheDocument();

		expect(screen.getByText('folder-permissions')).toBeInTheDocument();
	});

	it('not render delete and edit object folder actions on uncategorized object folder', () => {
		render(
			<ObjectFolderCardHeader
				externalReferenceCode="uncategorized"
				items={
					getObjectFolderActions(2, '', () => {}, {
						get: {href: 'GET', method: ''},
						permissions: {href: 'PATCH', method: ''},
					}) as IItem[]
				}
				label={{en_US: 'Uncategorized'}}
				modelBuilderURL=""
			></ObjectFolderCardHeader>
		);

		userEvent.click(screen.getByRole('button', {name: 'folder-actions'}));

		expect(screen.getAllByRole('menuitem')).toHaveLength(1);

		expect(screen.getByText('folder-permissions')).toBeInTheDocument();
	});
});
