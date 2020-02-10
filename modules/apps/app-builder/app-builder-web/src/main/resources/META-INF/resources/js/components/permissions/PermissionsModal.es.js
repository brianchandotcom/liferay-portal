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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {SearchInput} from 'data-engine-taglib';
import React, {useEffect, useState} from 'react';

import ManagementToolbar from '../../components/management-toolbar/ManagementToolbar.es';
import Table from '../../components/table/Table.es';
import {getItem, updateItem} from '../../utils/client.es';

export default ({
	actions,
	endpoint,
	isDisabled = () => false,
	isOpen,
	onClose,
	onSave = () => Promise.resolve(),
	rolesFilter = () => true,
	title
}) => {
	const {observer} = useModal({
		onClose
	});

	const columns = [
		{
			key: 'name',
			sortable: false,
			value: Liferay.Language.get('role')
		},
		...actions
	];

	const [state, setState] = useState({
		isLoading: true,
		permissions: [],
		roles: [],
		searchText: ''
	});

	useEffect(() => {
		if (!isOpen || !endpoint) {
			return;
		}

		setState({
			isLoading: true,
			permissions: [],
			roles: [],
			searchText: ''
		});

		getItem('/o/headless-admin-user/v1.0/roles')
			.then(({items: roles = []}) => {
				roles = roles.filter(rolesFilter);

				setState(prevState => ({
					...prevState,
					roles
				}));

				const roleNames = roles.map(({name}) => name);

				return getItem(endpoint, {roleNames});
			})
			.then(({items: permissions = []}) => {
				setState(prevState => ({
					...prevState,
					isLoading: false,
					permissions
				}));
			})
			.catch(_ =>
				setState(prevState => ({
					...prevState,
					isLoading: false
				}))
			);
	}, [endpoint, isOpen, rolesFilter]);

	const {isLoading, permissions, roles, searchText} = state;

	if (!isOpen || isLoading) {
		return <></>;
	}

	const isChecked = (roleName, actionId) =>
		permissions.some(
			({actionIds, roleName: name}) =>
				name === roleName && actionIds.includes(actionId)
		);

	const handleOnSave = () =>
		Promise.all([
			updateItem(endpoint, permissions),
			onSave(permissions)
		]).then(() => onClose());

	const togglePermission = (roleName, actionId) => {
		const exists = permissions.some(
			({roleName: name}) => roleName === name
		);

		let newPermissions = exists
			? permissions
			: permissions.concat({actionIds: [], roleName});

		newPermissions = newPermissions.map(permission => {
			if (permission.roleName !== roleName) {
				return permission;
			}

			const {actionIds} = permission;

			return {
				...permission,
				actionIds: actionIds.includes(actionId)
					? actionIds.filter(id => id !== actionId)
					: actionIds.concat(actionId)
			};
		});

		setState(prevState => ({
			...prevState,
			permissions: newPermissions
		}));
	};

	const filteredRoles = roles
		.filter(({name}) => new RegExp(searchText, 'ig').test(name))
		.map(({name}) => {
			let item = {
				name: (
					<>
						<ClayIcon symbol="user" /> {name}
					</>
				)
			};

			actions.forEach(({key}) => {
				item = {
					...item,
					[key]: (
						<input
							checked={isChecked(name, key)}
							disabled={isDisabled(name, key)}
							name={key}
							onClick={() => togglePermission(name, key)}
							type="checkbox"
						/>
					)
				};
			});

			return item;
		});

	return (
		<ClayModal observer={observer} size="full-screen">
			<ClayModal.Header>
				{title || Liferay.Language.get('permissions')}
			</ClayModal.Header>
			<ClayModal.Body>
				<ManagementToolbar>
					<SearchInput
						onChange={searchText =>
							setState(prevState => ({
								...prevState,
								searchText
							}))
						}
						searchText={searchText}
					/>
				</ManagementToolbar>

				<Table align="center" columns={columns} items={filteredRoles} />
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton onClick={() => handleOnSave()}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};
