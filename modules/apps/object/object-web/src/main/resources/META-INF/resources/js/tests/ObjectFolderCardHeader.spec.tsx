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

const defaultFolderHTTPMethods = {
	objectDefinitionActions: {
		create: {href: '', method: 'POST'},
	},
	objectFolderActions: {
		get: {href: '', method: 'GET'},
		permissions: {href: '', method: 'PATCH'},
	},
};

const ticketFolderHTTPMethods = {
	objectDefinitionActions: {
		create: {href: '', method: 'POST'},
	},
	objectFolderActions: {
		delete: {href: '', method: 'DELETE'},
		get: {href: '', method: 'GET'},
		permissions: {href: '', method: 'PATCH'},
		update: {href: '', method: 'PUT'},
	},
};

describe('The ObjectFolderCardHeader component should', () => {
	it('render all object folder actions', () => {
		render(
			<ObjectFolderCardHeader
				externalReferenceCode="ticket"
				items={
					getObjectFolderActions({
						actions: {
							objectDefinitionActions:
								ticketFolderHTTPMethods.objectDefinitionActions,
							objectFolderActions:
								ticketFolderHTTPMethods.objectFolderActions,
						},
						baseResourceURL: '',
						importObjectDefinitionURL: '',
						objectFolderExternalReferenceCode: '',
						objectFolderId: 1,
						objectFolderPermissionsURL: '',
						portletNamespace: '',
						setModalImportProperties: () => {},
						setShowModal: () => {},
					}) as IItem[]
				}
				label={{en_US: 'Ticket'}}
				modelBuilderURL=""
			></ObjectFolderCardHeader>
		);

		userEvent.click(
			screen.getByRole('button', {name: 'object-folder-actions'})
		);

		const menuItem = screen.getAllByRole('menuitem');

		expect(menuItem).toHaveLength(5);

		expect(menuItem[0]).toHaveAttribute('value', 'editObjectFolder');

		expect(menuItem[1]).toHaveAttribute('value', 'exportObjectFolder');

		expect(menuItem[2]).toHaveAttribute('value', 'importObjectDefinition');

		expect(menuItem[3]).toHaveAttribute('value', 'objectFolderPermissions');

		expect(menuItem[4]).toHaveAttribute('value', 'deleteObjectFolder');
	});

	it('not render delete and edit object folder actions on default object folder', () => {
		render(
			<ObjectFolderCardHeader
				externalReferenceCode="default"
				items={
					getObjectFolderActions({
						actions: {
							objectDefinitionActions:
								defaultFolderHTTPMethods.objectDefinitionActions,
							objectFolderActions:
								defaultFolderHTTPMethods.objectFolderActions,
						},
						baseResourceURL: '',
						importObjectDefinitionURL: '',
						objectFolderExternalReferenceCode: '',
						objectFolderId: 2,
						objectFolderPermissionsURL: '',
						portletNamespace: '',
						setModalImportProperties: () => {},
						setShowModal: () => {},
					}) as IItem[]
				}
				label={{en_US: 'Default'}}
				modelBuilderURL=""
			></ObjectFolderCardHeader>
		);

		userEvent.click(
			screen.getByRole('button', {name: 'object-folder-actions'})
		);

		const menuItem = screen.getAllByRole('menuitem');

		expect(menuItem).toHaveLength(3);

		expect(menuItem[0]).toHaveAttribute('value', 'exportObjectFolder');

		expect(menuItem[1]).toHaveAttribute('value', 'importObjectDefinition');

		expect(menuItem[2]).toHaveAttribute('value', 'objectFolderPermissions');
	});
});
