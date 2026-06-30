/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import FrontendDataSetContext from '../../../src/main/resources/META-INF/resources/FrontendDataSetContext';
import BulkActions from '../../../src/main/resources/META-INF/resources/management_bar/controls/BulkActions';
import {IBulkActionItem} from '../../../src/main/resources/META-INF/resources/utils/types';

const baseContext = {
	actionParameterName: undefined,
	allItemsSelectedActive: false,
	apiURL: '/o/test/items',
	formId: undefined,
	formName: undefined,
	globalFDSState: {filters: [], search: {query: ''}},
	loadData: () => {},
	namespace: undefined,
	onBulkActionItemClick: jest.fn(),
	searchParam: undefined,
	showBulkActionsManagementBar: true,
	showBulkActionsManagementBarActions: true,
	showInfoPanel: false,
	sidePanelId: undefined,
};

const renderBulkActions = (bulkActions: IBulkActionItem[]) =>
	render(
		<FrontendDataSetContext.Provider value={baseContext as any}>
			<BulkActions
				bulkActions={bulkActions}
				handleSelectAll={() => {}}
				items={[{id: 1}, {id: 2}]}
				onClear={() => {}}
				pageSelectedItemsValue={[1]}
				selectedItems={[{id: 1}]}
				selectedItemsKey="id"
				selectedItemsValue={[1]}
				total={2}
			/>
		</FrontendDataSetContext.Provider>
	);

describe('BulkActions className propagation', () => {
	it('applies data.className to the highlighted bulk action button', () => {
		const bulkActions: IBulkActionItem[] = [
			{
				className: 'text-danger',
				data: {
					highlighted: true,
					id: 'delete',
				},
				icon: 'trash',
				label: 'Delete',
			},
		];

		renderBulkActions(bulkActions);

		expect(screen.getByRole('button', {name: /delete/i})).toHaveClass(
			'text-danger'
		);
	});

	it('applies data.className to the overflow dropdown item', async () => {
		const bulkActions: IBulkActionItem[] = [
			{
				className: 'text-danger',
				data: {id: 'delete'},
				icon: 'trash',
				label: 'Delete',
			},
		];

		renderBulkActions(bulkActions);

		await userEvent.click(screen.getByRole('button', {name: /actions/i}));

		expect(screen.getByRole('menuitem', {name: /delete/i})).toHaveClass(
			'text-danger'
		);
	});

	it('does not apply data.className to the highlighted bulk action button when it is disabled', () => {
		const bulkActions: IBulkActionItem[] = [
			{
				className: 'text-danger',
				data: {
					disabled: true,
					highlighted: true,
					id: 'delete',
				},
				icon: 'trash',
				label: 'Delete',
			},
		];

		renderBulkActions(bulkActions);

		const button = screen.getByRole('button', {name: /delete/i});

		expect(button).toBeDisabled();
		expect(button).not.toHaveClass('text-danger');
	});

	it('does not apply data.className to the overflow dropdown item when it is disabled', async () => {
		const bulkActions: IBulkActionItem[] = [
			{
				className: 'text-danger',
				data: {disabled: true, id: 'delete'},
				icon: 'trash',
				label: 'Delete',
			},
		];

		renderBulkActions(bulkActions);

		await userEvent.click(screen.getByRole('button', {name: /actions/i}));

		expect(screen.getByRole('menuitem', {name: /delete/i})).not.toHaveClass(
			'text-danger'
		);
	});
});
