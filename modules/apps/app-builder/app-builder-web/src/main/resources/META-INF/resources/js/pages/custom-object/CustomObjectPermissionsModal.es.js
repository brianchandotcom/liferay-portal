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
import React, {useState, useEffect} from 'react';

import ManagementToolbar from '../../components/management-toolbar/ManagementToolbar.es';
import SearchInput from '../../components/management-toolbar/search/SearchInput.es';
import Table from '../../components/table/Table.es';
import {getItem, updateItem} from '../../utils/client.es';

const ADD_DATA_RECORD = 'ADD_DATA_RECORD';
const DELETE_DATA_RECORD = 'DELETE_DATA_RECORD';
const UPDATE_DATA_RECORD = 'UPDATE_DATA_RECORD';
const VIEW_DATA_RECORD = 'VIEW_DATA_RECORD';
const ACTIONS = [
	ADD_DATA_RECORD,
	DELETE_DATA_RECORD,
	UPDATE_DATA_RECORD,
	VIEW_DATA_RECORD
];

const COLUMNS = [
	{
		key: 'name',
		sortable: false,
		value: Liferay.Language.get('role')
	},
	{
		key: ADD_DATA_RECORD,
		sortable: false,
		value: Liferay.Language.get('add-entry')
	},
	{
		key: DELETE_DATA_RECORD,
		sortable: false,
		value: Liferay.Language.get('delete')
	},
	{
		key: UPDATE_DATA_RECORD,
		sortable: false,
		value: Liferay.Language.get('update')
	},
	{
		key: VIEW_DATA_RECORD,
		sortable: false,
		value: Liferay.Language.get('view')
	}
];

export default ({dataDefinitionId, onClose}) => {
	const {observer} = useModal({
		onClose
	});

	const [isLoading, setLoading] = useState(true);
	const [dataRecordCollectionId, setDataRecordCollectionId] = useState(null);
	const [permissions, setPermissions] = useState([]);
	const [roles, setRoles] = useState([]);
	const [searchText, setSearchText] = useState('');

	useEffect(() => {
		setLoading(true);
		setDataRecordCollectionId(null);
		setPermissions([]);
		setRoles([]);
		setSearchText('');

		if (dataDefinitionId) {
			Promise.all([
				getItem('/o/headless-admin-user/v1.0/roles'),
				getItem(
					`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-record-collection`
				)
			])
				.then(([{items: roles = []}, {id: dataRecordCollectionId}]) => {
					roles = roles.filter(
						({name, roleType}) =>
							name !== 'Administrator' &&
							name !== 'Site Administrator' &&
							name !== 'Site Owner' &&
							roleType !== 'organization'
					);

					setRoles(roles);
					setDataRecordCollectionId(dataRecordCollectionId);

					const roleNames = roles.map(({name}) => name);

					return getItem(
						`/o/data-engine/v2.0/data-record-collections/${dataRecordCollectionId}/data-model-permissions`,
						{roleNames}
					);
				})
				.then(({items: permissions = []}) => {
					setPermissions(permissions);
					setLoading(false);
				})
				.catch(_ => setLoading(false));
		}
	}, [dataDefinitionId]);

	if (!dataDefinitionId) {
		return <></>;
	}

	if (isLoading) {
		return <></>;
	}

	const isChecked = (roleName, actionId) =>
		permissions.some(
			({actionIds, roleName: name}) =>
				name === roleName && actionIds.includes(actionId)
		);

	const onSave = () => {
		updateItem(
			`/o/data-engine/v2.0/data-record-collections/${dataRecordCollectionId}/data-model-permissions`,
			permissions
		).then(() => onClose());
	};

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

		setPermissions(newPermissions);
	};

	const filteredRoles = roles
		.filter(({name}) => new RegExp(searchText, 'ig').test(name))
		.map(({name, roleType}) => {
			let item = {
				name: (
					<>
						<ClayIcon
							symbol={roleType === 'site' ? 'sites' : 'user'}
						/>{' '}
						{name}
					</>
				)
			};

			ACTIONS.forEach(action => {
				item = {
					...item,
					[action]: (
						<input
							checked={isChecked(name, action)}
							name={action}
							onClick={() => togglePermission(name, action)}
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
				{Liferay.Language.get('permissions')}
			</ClayModal.Header>
			<ClayModal.Body>
				<ManagementToolbar>
					<SearchInput
						onChange={searchText => setSearchText(searchText)}
						searchText={searchText}
					/>
				</ManagementToolbar>

				<Table align="center" columns={COLUMNS} items={filteredRoles} />
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
						<ClayButton onClick={() => onSave()}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};
