/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Head, Row, Table, Text} from '@clayui/core';
import {ClayCheckbox, ClaySelect} from '@clayui/form';
import React, {useMemo, useState} from 'react';

export enum ActionId {
	AddCategory = 'ADD_CATEGORY',
	Delete = 'DELETE',
	Download = 'DOWNLOAD',
	Permissions = 'PERMISSIONS',
	Update = 'UPDATE',
	View = 'VIEW',
}

export enum Role {
	Guest = 'Guest',
	OrganizationUser = 'Organization User',
	Owner = 'Owner',
	PowerUser = 'Power User',
	SiteMember = 'Site Member',
	User = 'User',
}

type ActionLabels = {
	[key in ActionId]: string;
};

const ACTION_LABELS: ActionLabels = {
	[ActionId.AddCategory]: Liferay.Language.get('add-category'),
	[ActionId.Delete]: Liferay.Language.get('delete'),
	[ActionId.Download]: Liferay.Language.get('download'),
	[ActionId.Permissions]: Liferay.Language.get('permissions'),
	[ActionId.Update]: Liferay.Language.get('update'),
	[ActionId.View]: Liferay.Language.get('view'),
};

type RoleLabels = {
	[key in Role]: string;
};

const ROLE_LABELS: RoleLabels = {
	[Role.Guest]: Liferay.Language.get('guest'),
	[Role.OrganizationUser]: Liferay.Language.get('organization-members'),
	[Role.PowerUser]: Liferay.Language.get('power-users'),
	[Role.SiteMember]: Liferay.Language.get('site-members'),
	[Role.User]: Liferay.Language.get('user'),
	[Role.Owner]: Liferay.Language.get('owner'),
};
interface PermissionItem {
	actionIds: ActionId[];
	roleName: Role;
}

/**
 * Returns a new permissions object with the particular actionId at
 * a certain index toggled. Called when clicking on a checkbox in the
 * permissions table.
 *
 * @param permissions
 * @param actionId
 * @param index
 * @returns PermissionItem[]
 */
const toggleActionIdAtIndex = (
	permissions: PermissionItem[],
	actionId: ActionId,
	index: number
) => {
	const newPermissions = permissions.slice();

	newPermissions[index].actionIds = permissions[index].actionIds.includes(
		actionId
	)
		? permissions[index].actionIds.filter((item) => item !== actionId)
		: [...permissions[index].actionIds, actionId];

	return newPermissions;
};

/**
 * Returns a new permissions object with all the actionIds enabled
 * for the listed roles, while the rest of the roles have the actionIds
 * disabled. Called when selecting from the 'Viewable By' dropdown.
 *
 * @param permissions
 * @param roles
 * @param actionIdsToEnable
 * @returns PermissionItem[]
 */
const enableActionIdsForRoles = (
	permissions: PermissionItem[],
	roles: Role[],
	actionIdsToEnable: ActionId[]
) => {
	return permissions.map(({actionIds, roleName}) => {
		const actionIdsNotEnabled = actionIdsToEnable.filter(
			(id) => !actionIds.includes(id)
		);

		return {
			actionIds: roles.includes(roleName)
				? [...actionIds, ...actionIdsNotEnabled]
				: actionIds.filter((item) => !actionIdsToEnable.includes(item)),
			roleName,
		};
	});
};

function PermissionsTable({

	/*
	 * Classname to apply extra styles to the table.
	 */
	className,

	/*
	 * Default group role that is between Guest and Owner, featured
	 * in the Viewable By options and should be included as one of the roles
	 * in defaultPermissions (example: organization member, power user,
	 * site member).
	 */
	defaultGroupRole = Role.SiteMember,

	/*
	 * Default state for permissions.
	 */
	defaultPermissions = [
		{
			actionIds: [ActionId.View],
			roleName: Role.Guest,
		},
		{
			actionIds: [ActionId.View],
			roleName: Role.SiteMember,
		},
	],

	/*
	 * Checkboxes to be disabled for the guest role.
	 */
	guestUnsupportedActions = [ActionId.Update],

	/*
	 * Callback for when permissions gets changed.
	 */
	onChange = () => {},

	/*
	 * Actions to be listed as the row/column entries.
	 */
	supportedActions = [
		ActionId.Delete,
		ActionId.Permissions,
		ActionId.Update,
		ActionId.View,
	],
	...otherProps
}: {
	className: string;
	defaultGroupRole: Role;
	defaultPermissions: PermissionItem[];
	guestUnsupportedActions: ActionId[];
	onChange: Function;
	supportedActions: ActionId[];
}) {
	const [permissions, setPermissions] =
		useState<PermissionItem[]>(defaultPermissions);
	const [viewableBy, setViewableBy] = useState<Role>(
		defaultPermissions.every(({actionIds}: {actionIds: ActionId[]}) =>
			actionIds.includes(ActionId.View)
		) // Guest -> all permissions enabled
			? Role.Guest
			: defaultPermissions.every(
						({actionIds}: {actionIds: ActionId[]}) =>
							!actionIds.includes(ActionId.View)
				  ) // Owner -> no permissions enabled
				? Role.Owner
				: defaultGroupRole // Group -> group permissions enabled
	);

	const allRoles: Role[] = useMemo(() => {
		return defaultPermissions.map(({roleName}) => roleName);
	}, [defaultPermissions]);

	const viewableByOptions = [
		{
			label: `${Liferay.Language.get('anyone')} (${Liferay.Util.sub(Liferay.Language.get('x-role'), ROLE_LABELS[Role.Guest])})`,
			value: Role.Guest,
		},
		{
			label: ROLE_LABELS[defaultGroupRole],
			value: defaultGroupRole,
		},
		{
			label: ROLE_LABELS[Role.Owner],
			value: Role.Owner,
		},
	];

	const _handleChangePermissions = (newPermissions: PermissionItem[]) => {
		setPermissions(newPermissions);
		onChange(newPermissions);
	};

	const _handleChangeAction = (index: number, action: ActionId) => {
		_handleChangePermissions(
			toggleActionIdAtIndex(permissions, action, index)
		);
	};

	const _handleChangeViewableBy = (event: any) => {
		setViewableBy(event.target.value as Role);

		const actionIdsToUpdate = supportedActions.includes(ActionId.Download)
			? [ActionId.View, ActionId.Download]
			: [ActionId.View];

		const rolesToEnable =
			event.target.value === Role.Guest
				? allRoles
				: event.target.value === Role.Owner
					? []
					: [defaultGroupRole];

		_handleChangePermissions(
			enableActionIdsForRoles(
				permissions,
				rolesToEnable,
				actionIdsToUpdate
			)
		);
	};

	const _isActionChecked = (action: ActionId, index: number) => {
		return permissions[index].actionIds.includes(action);
	};

	const _isActionDisabled = (action: ActionId, role: Role) => {
		if (action === ActionId.View) {
			return true;
		}

		if (role === Role.Guest) {
			return guestUnsupportedActions.includes(action);
		}

		return false;
	};

	return (
		<>
			<p>
				<label htmlFor="viewableBy">
					{supportedActions.includes(ActionId.Download)
						? Liferay.Language.get('viewable-and-downloadable-by')
						: Liferay.Language.get('viewable-by')}
				</label>

				<ClaySelect
					aria-label={Liferay.Language.get('viewable-by')}
					id="viewableBy"
					onChange={_handleChangeViewableBy}
					value={viewableBy}
				>
					{viewableByOptions.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</p>

			<Table
				className={className}
				columnsVisibility={false}
				striped={false}
				{...otherProps}
			>
				<Head
					items={[
						{
							id: 'role',
							name: Liferay.Language.get('role'),
						},
						...supportedActions.map((action: ActionId) => ({
							id: action,
							name: ACTION_LABELS[action] || action,
						})),
					]}
				>
					{(column) => (
						<Cell key={`action_header_${column.id}`}>
							{column.name}
						</Cell>
					)}
				</Head>

				<Body>
					{permissions.map((item: PermissionItem, index) => (
						<Row key={item.roleName}>
							<Cell>
								<Text size={3} weight="semi-bold">
									{item.roleName}
								</Text>
							</Cell>

							{supportedActions.map((action: ActionId) => (
								<Cell
									key={`${item.roleName.replace(' ', '')}-${action}`}
								>
									<ClayCheckbox
										aria-label={Liferay.Util.sub(
											'give-x-permission-to-users-with-the-x-role',
											[
												ACTION_LABELS[action],
												item.roleName,
											]
										)}
										checked={_isActionChecked(
											action,
											index
										)}
										disabled={_isActionDisabled(
											action,
											item.roleName
										)}
										onChange={() =>
											_handleChangeAction(index, action)
										}
									/>
								</Cell>
							))}
						</Row>
					))}
				</Body>
			</Table>
		</>
	);
}

export default PermissionsTable;
