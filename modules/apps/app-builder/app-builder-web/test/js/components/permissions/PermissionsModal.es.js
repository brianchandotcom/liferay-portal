/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import PermissionsModal from '../../../../src/main/resources/META-INF/resources/js/components/permissions/PermissionsModal.es';

const ACTIONS = [
	{
		key: 'ADD',
		sortable: false,
		value: Liferay.Language.get('add'),
	},
	{
		key: 'VIEW',
		sortable: false,
		value: Liferay.Language.get('view'),
	},
];

const PERMISSIONS = {
	items: [
		{
			actionIds: ['ADD', 'VIEW'],
			roleName: 'Administrator',
		},
		{
			actionIds: ['VIEW'],
			roleName: 'Power User',
		},
	],
};

const ROLES = {
	items: [
		{id: 1, name: 'Administrator', roleType: 'regular'},
		{id: 2, name: 'Power User', roleType: 'regular'},
	],
};

describe('PermissionsModal', () => {
	beforeAll(() => {
		jest.useFakeTimers();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	afterEach(() => {
		jest.clearAllTimers();
		jest.restoreAllMocks();
		cleanup();
	});

	it('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(ROLES));

		fetch.mockResponseOnce(JSON.stringify(PERMISSIONS));

		const {getAllByRole, getByText} = render(
			<PermissionsModal
				actions={ACTIONS}
				endpoint={'/permissions'}
				isOpen={true}
				onClose={() => {}}
				rolesFilter={() => true}
				title={'title'}
			/>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		getByText('title');
		getByText('role');
		getByText('add');
		getByText('view');
		getByText('Administrator');
		getByText('Power User');

		expect(getAllByRole('button').length).toBe(4);
	});

	it('renders empty element when not open', async () => {
		const {queryAllByRole, queryByText} = render(
			<PermissionsModal
				actions={ACTIONS}
				endpoint={'/permissions'}
				isDisabled={() => false}
				isOpen={false}
				onClose={() => {}}
				onSave={() => Promise.resolve()}
				rolesFilter={() => true}
				title={'title'}
			/>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('title')).toBeNull();
		expect(queryByText('role')).toBeNull();
		expect(queryByText('add')).toBeNull();
		expect(queryByText('view')).toBeNull();
		expect(queryByText('Administrator')).toBeNull();
		expect(queryByText('Power User')).toBeNull();
		expect(queryAllByRole('button').length).toBe(0);
	});

	it('save and cancel buttons are called', async () => {
		const onCloseCallback = jest.fn();
		const onSaveCallback = jest.fn();

		fetch.mockResponseOnce(JSON.stringify(ROLES));

		fetch.mockResponseOnce(JSON.stringify(PERMISSIONS));

		const {getAllByRole} = render(
			<PermissionsModal
				actions={ACTIONS}
				endpoint={'/permissions'}
				isDisabled={() => false}
				isOpen={true}
				onClose={onCloseCallback}
				onSave={onSaveCallback}
				rolesFilter={() => true}
				title={'title'}
			/>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		fetch.mockResponseOnce(JSON.stringify({}));

		const saveButton = getAllByRole('button')[3];

		act(() => {
			saveButton.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		expect(onSaveCallback.mock.calls.length).toBe(1);
		expect(onSaveCallback.mock.calls[0][0]).toEqual(PERMISSIONS.items);

		const cancelButton = getAllByRole('button')[2];

		act(() => {
			cancelButton.dispatchEvent(
				new MouseEvent('click', {bubbles: true})
			);
		});

		expect(onCloseCallback.mock.calls.length).toBe(1);
	});

	it('adds permission to a role', async () => {
		const onSaveCallback = jest.fn();

		fetch.mockResponseOnce(JSON.stringify(ROLES));

		fetch.mockResponseOnce(JSON.stringify(PERMISSIONS));

		const {getAllByRole} = render(
			<PermissionsModal
				actions={ACTIONS}
				endpoint={'/permissions'}
				isDisabled={() => false}
				isOpen={true}
				onClose={() => {}}
				onSave={onSaveCallback}
				rolesFilter={() => true}
				title={'title'}
			/>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const powerUserAddPermissionCheckbox = document.querySelector(
			'table > tbody > tr:nth-child(2) > td:nth-child(2) > input[type=checkbox]'
		);

		act(() => {
			powerUserAddPermissionCheckbox.dispatchEvent(
				new MouseEvent('click', {bubbles: true})
			);
		});

		expect(powerUserAddPermissionCheckbox.checked).toBe(true);

		fetch.mockResponseOnce(JSON.stringify({}));

		const saveButton = getAllByRole('button')[3];

		act(() => {
			saveButton.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		const powerUserPermissions = onSaveCallback.mock.calls[0][0][1];

		expect(onSaveCallback.mock.calls.length).toBe(1);
		expect(powerUserPermissions.actionIds).toContain('ADD');
	});

	it('removes permission to a role', async () => {
		const onSaveCallback = jest.fn();

		fetch.mockResponseOnce(JSON.stringify(ROLES));

		fetch.mockResponseOnce(JSON.stringify(PERMISSIONS));

		const {getAllByRole} = render(
			<PermissionsModal
				actions={ACTIONS}
				endpoint={'/permissions'}
				isDisabled={() => false}
				isOpen={true}
				onClose={() => {}}
				onSave={onSaveCallback}
				rolesFilter={() => true}
				title={'title'}
			/>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const powerUserViewPermissionCheckbox = document.querySelector(
			'table > tbody > tr:nth-child(2) > td:nth-child(3) > input[type=checkbox]'
		);

		act(() => {
			powerUserViewPermissionCheckbox.dispatchEvent(
				new MouseEvent('click', {bubbles: true})
			);
		});

		expect(powerUserViewPermissionCheckbox.checked).toBe(false);

		fetch.mockResponseOnce(JSON.stringify({}));

		const saveButton = getAllByRole('button')[3];

		act(() => {
			saveButton.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		const powerUserPermissions = onSaveCallback.mock.calls[0][0][1];

		expect(onSaveCallback.mock.calls.length).toBe(1);
		expect(powerUserPermissions.actionIds.length).toBe(0);
	});
});
