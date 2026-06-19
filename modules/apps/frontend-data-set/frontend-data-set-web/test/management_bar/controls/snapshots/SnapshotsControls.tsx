/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import fetch from 'jest-fetch-mock';

import FrontendDataSetContext from '../../../../src/main/resources/META-INF/resources/FrontendDataSetContext';
import SnapshotsControls from '../../../../src/main/resources/META-INF/resources/management_bar/controls/snapshots/SnapshotsControls';
import ViewsContext from '../../../../src/main/resources/META-INF/resources/views/ViewsContext';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/management_bar/controls/snapshots/shareSnapshotAction',
	() => jest.fn()
);

const mockFDSContext = {
	globalFDSState: {filters: []},
	id: 'testFDS',
	namespace: 'testNamespace_',
	onSnapshotChange: jest.fn(),
	portletId: 'testPortlet',
};

const ownedSnapshot = {erc: 'owned-erc', id: 1, label: 'Owned View'};
const sharedSnapshot = {erc: 'shared-erc', id: 2, label: 'Shared View'};

const renderSnapshotsControls = (
	viewsState: any,
	viewsDispatch: jest.Mock = jest.fn()
) => {
	render(
		<FrontendDataSetContext.Provider value={mockFDSContext as any}>
			<ViewsContext.Provider value={[viewsState, viewsDispatch] as any}>
				<SnapshotsControls />
			</ViewsContext.Provider>
		</FrontendDataSetContext.Provider>
	);

	return {viewsDispatch};
};

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
				snapshotStartupViewERC: null,
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
				snapshotStartupViewERC: null,
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

describe('SnapshotsControls startup view', () => {
	beforeEach(() => {
		fetch.resetMocks();

		(Liferay.ThemeDisplay as any).getUserId = jest.fn(() => '123');
	});

	afterEach(() => {
		delete (Liferay.ThemeDisplay as any).getUserId;
	});

	it('offers "Set as Startup View" for an active view that is not the startup view', async () => {
		renderSnapshotsControls({
			activeSnapshotERC: ownedSnapshot.erc,
			activeView: null,
			defaultSnapshot: {},
			paginationDelta: null,
			snapshotStartupViewERC: null,
			snapshotUpdated: false,
			snapshots: [{headerVisible: false, items: [ownedSnapshot]}],
			sorts: [],
			visibleFieldNames: {},
		});

		await openActionsDropdown();

		expect(
			await screen.findByText('set-as-startup-view')
		).toBeInTheDocument();
	});

	it('offers "Set as Startup View" for a shared active view', async () => {
		renderSnapshotsControls({
			activeSnapshotERC: sharedSnapshot.erc,
			activeView: null,
			defaultSnapshot: {},
			paginationDelta: null,
			snapshotStartupViewERC: null,
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

		await openActionsDropdown();

		expect(
			await screen.findByText('set-as-startup-view')
		).toBeInTheDocument();
	});

	it('hides "Set as Startup View" when the active view is already the startup view', async () => {
		renderSnapshotsControls({
			activeSnapshotERC: ownedSnapshot.erc,
			activeView: null,
			defaultSnapshot: {},
			paginationDelta: null,
			snapshotStartupViewERC: ownedSnapshot.erc,
			snapshotUpdated: false,
			snapshots: [{headerVisible: false, items: [ownedSnapshot]}],
			sorts: [],
			visibleFieldNames: {},
		});

		await openActionsDropdown();

		expect(await screen.findByText('save-view-as')).toBeInTheDocument();
		expect(
			screen.queryByText('set-as-startup-view')
		).not.toBeInTheDocument();
	});

	it('persists the preference and updates state when set as startup', async () => {
		fetch.mockResponseOnce(JSON.stringify({}));

		const {viewsDispatch} = renderSnapshotsControls({
			activeSnapshotERC: ownedSnapshot.erc,
			activeView: null,
			defaultSnapshot: {},
			paginationDelta: null,
			snapshotStartupViewERC: null,
			snapshotUpdated: false,
			snapshots: [{headerVisible: false, items: [ownedSnapshot]}],
			sorts: [],
			visibleFieldNames: {},
		});

		await openActionsDropdown();

		await userEvent.click(screen.getByText('set-as-startup-view'));

		await waitFor(() => expect(fetch).toHaveBeenCalledTimes(1));

		expect(fetch.mock.calls[0][0]).toContain(
			'/o/data-set-admin/snapshot-user-preferences/by-external-reference-code/'
		);
		expect(fetch.mock.calls[0][1]?.method).toBe('PUT');

		await waitFor(() =>
			expect(viewsDispatch).toHaveBeenCalledWith(
				expect.objectContaining({
					type: 'SET_SNAPSHOT_STARTUP_VIEW',
					value: {snapshotStartupViewERC: ownedSnapshot.erc},
				})
			)
		);
	});
});
