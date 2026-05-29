/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';

import FrontendDataSetContext from '../../../../src/main/resources/META-INF/resources/FrontendDataSetContext';
import SnapshotsControls from '../../../../src/main/resources/META-INF/resources/management_bar/controls/snapshots/SnapshotsControls';
import ViewsContext from '../../../../src/main/resources/META-INF/resources/views/ViewsContext';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/management_bar/controls/snapshots/shareSnapshotAction',
	() => jest.fn()
);

global.Liferay = {
	Language: {
		get: (key: string) => key,
	},
} as any;

const mockFDSContext = {
	globalFDSState: {filters: []},
	id: 'testFDS',
	namespace: 'testNamespace_',
	onSnapshotChange: jest.fn(),
	portletId: 'testPortlet',
};

const ownedSnapshot = {erc: 'owned-erc', id: 1, label: 'Owned View'};
const sharedSnapshot = {erc: 'shared-erc', id: 2, label: 'Shared View'};

const renderSnapshotsControls = (viewsState: any) =>
	render(
		<FrontendDataSetContext.Provider value={mockFDSContext as any}>
			<ViewsContext.Provider value={[viewsState, jest.fn()] as any}>
				<SnapshotsControls />
			</ViewsContext.Provider>
		</FrontendDataSetContext.Provider>
	);

const openActionsDropdown = async () => {
	await userEvent.click(
		screen.getByRole('button', {name: 'show-view-actions'})
	);
};

describe('SnapshotsControls action gating', () => {
	describe('when the active snapshot is owned by the current user', () => {
		beforeEach(() => {
			renderSnapshotsControls({
				activeSnapshotERC: ownedSnapshot.erc,
				activeView: null,
				defaultSnapshot: {},
				paginationDelta: null,
				snapshotUpdated: false,
				snapshots: [{headerVisible: false, items: [ownedSnapshot]}],
				sorts: [],
				visibleFieldNames: {},
			});
		});

		it('shows every owner action plus "Save View As"', async () => {
			await openActionsDropdown();

			expect(await screen.findByText('save-view')).toBeInTheDocument();
			expect(screen.getByText('save-view-as')).toBeInTheDocument();
			expect(screen.getByText('rename-view')).toBeInTheDocument();
			expect(screen.getByText('share-view')).toBeInTheDocument();
			expect(screen.getByText('delete-view')).toBeInTheDocument();
		});
	});

	describe('when the active snapshot is shared with the current user', () => {
		beforeEach(() => {
			renderSnapshotsControls({
				activeSnapshotERC: sharedSnapshot.erc,
				activeView: null,
				defaultSnapshot: {},
				paginationDelta: null,
				snapshotUpdated: false,
				snapshots: [
					{headerVisible: false, items: []},
					{
						headerVisible: true,
						items: [sharedSnapshot],
						label: 'shared-with-me',
					},
				],
				sorts: [],
				visibleFieldNames: {},
			});
		});

		it('only offers "Save View As" and hides every owner action', async () => {
			await openActionsDropdown();

			expect(await screen.findByText('save-view-as')).toBeInTheDocument();
			expect(screen.queryByText('save-view')).not.toBeInTheDocument();
			expect(screen.queryByText('rename-view')).not.toBeInTheDocument();
			expect(screen.queryByText('share-view')).not.toBeInTheDocument();
			expect(screen.queryByText('delete-view')).not.toBeInTheDocument();
		});
	});
});
