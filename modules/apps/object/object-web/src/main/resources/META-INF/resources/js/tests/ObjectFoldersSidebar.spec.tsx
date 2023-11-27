/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import ObjectFoldersSideBar from '../components/ViewObjectDefinitions/ObjectFoldersSidebar';

const ticketObjectFolder = {
	actions: {get: {href: '', method: 'GET'}},
	dateCreated: '2023-08-07T14:45:00Z',
	dateModified: '2023-08-07T14:45:00Z',
	externalReferenceCode: 'ticket',
	id: 1,
	label: {en_US: 'Ticket'},
	name: 'Ticket',
	objectFolderItems: [],
};

const uncategorizedObjectFolder = {
	actions: {get: {href: '', method: 'GET'}},
	dateCreated: '2023-08-07T14:42:21Z',
	dateModified: '2023-08-07T14:42:21Z',
	externalReferenceCode: 'uncategorized',
	id: 2,
	label: {en_US: 'Uncategorized'},
	name: 'Uncategorized',
	objectFolderItems: [],
};

const objectFoldersRequestInfo = {
	actions: {create: {href: '', method: 'POST'}},
	items: [ticketObjectFolder, uncategorizedObjectFolder],
};

describe('The ObjectFoldersSidebar component should', () => {
	it('render all created object folders', () => {
		render(
			<ObjectFoldersSideBar
				baseResourceURL=""
				objectDefinitionsActions={{create: {href: '', method: 'POST'}}}
				objectFoldersRequestInfo={objectFoldersRequestInfo}
				selectedObjectFolder={uncategorizedObjectFolder}
				setModalImportObjectDefinitionInfo={() => {}}
				setSelectedObjectFolder={() => {}}
				setShowModal={() => {}}
			></ObjectFoldersSideBar>
		);

		expect(screen.getAllByRole('listitem')).toHaveLength(2);

		expect(screen.getByText('Ticket')).toBeInTheDocument();

		expect(screen.getByText('Uncategorized')).toBeInTheDocument();
	});
});
