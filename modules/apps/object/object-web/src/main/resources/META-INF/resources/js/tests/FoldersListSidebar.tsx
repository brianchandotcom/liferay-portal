/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import FoldersListSideBar from '../components/ViewObjectDefinitions/FoldersListSidebar';

const uncategorizedFolder = {
	actions: {},
	dateCreated: '2023-08-07T14:42:21Z',
	dateModified: '2023-08-07T14:42:21Z',
	externalReferenceCode: 'uncategorized',
	id: 1010,
	label: {en_US: 'Uncategorized'},
	name: 'Uncategorized',
};

const ticketFolder = {
	actions: {},
	dateCreated: '2023-08-07T14:45:00Z',
	dateModified: '2023-08-07T14:45:00Z',
	externalReferenceCode: 'ticketERC',
	id: 2020,
	label: {en_US: 'Ticket System'},
	name: 'TicketName',
};

describe('The FoldersListSidebar component should', () => {
	it('render all the folders created', () => {
		render(
			<FoldersListSideBar
				foldersList={[uncategorizedFolder, ticketFolder]}
				selectedFolder={uncategorizedFolder}
				setSelectedFolder={() => {}}
				setShowModal={() => {}}
			></FoldersListSideBar>
		);

		expect(screen.getAllByRole('listitem')).toHaveLength(2);

		expect(screen.getByText('Uncategorized')).toBeInTheDocument();

		expect(screen.getByText('Ticket System')).toBeInTheDocument();
	});
});
