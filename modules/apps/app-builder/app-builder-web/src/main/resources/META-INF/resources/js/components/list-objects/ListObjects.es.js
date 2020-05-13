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

import React, {useEffect} from 'react';
import {Link} from 'react-router-dom';

import ListView from '../../components/list-view/ListView.es';
import PermissionsModal from '../../components/permissions/PermissionsModal.es';
import {ACTIONS} from '../../pages/entry/PermissionsContext.es';
import {getItem, updateItem} from '../../utils/client.es';
import {fromNow} from '../../utils/time.es';

const COLUMNS = [
	{
		key: 'name',
		sortable: true,
		value: Liferay.Language.get('name'),
	},
	{
		key: 'dateCreated',
		sortable: true,
		value: Liferay.Language.get('create-date'),
	},
	{
		asc: false,
		key: 'dateModified',
		sortable: true,
		value: Liferay.Language.get('modified-date'),
	},
];

export default ({
	customObjectPermissionsModalState,
	listViewProps,
	objectType,
	setCustomObjectPermissionsModalState,
}) => {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	const rolesFilter = ({name, roleType}) =>
		name !== 'Administrator' &&
		name !== 'Guest' &&
		name !== 'Owner' &&
		roleType !== 'organization' &&
		roleType !== 'site';

	const {dataDefinitionId} = customObjectPermissionsModalState;

	useEffect(() => {
		if (!dataDefinitionId) {
			return;
		}

		getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-record-collection`
		).then(({id: dataRecordCollectionId}) => {
			setCustomObjectPermissionsModalState((prevState) => ({
				...prevState,
				endpoint: `/o/data-engine/v2.0/data-record-collections/${dataRecordCollectionId}/permissions`,
			}));
		});
	}, [dataDefinitionId, setCustomObjectPermissionsModalState]);

	return (
		<>
			<ListView columns={COLUMNS} {...listViewProps}>
				{(item) => ({
					...item,
					dateCreated: fromNow(item.dateCreated),
					dateModified: fromNow(item.dateModified),
					name: (
						<Link to={`/${objectType}/${item.id}/form-views`}>
							{item.name[defaultLanguageId]}
						</Link>
					),
				})}
			</ListView>

			<PermissionsModal
				actions={[
					{
						key: ACTIONS.ADD_DATA_RECORD,
						sortable: false,
						value: Liferay.Language.get('add-entry'),
					},
					{
						key: ACTIONS.DELETE_DATA_RECORD,
						sortable: false,
						value: Liferay.Language.get('delete-entry'),
					},
					{
						key: ACTIONS.UPDATE_DATA_RECORD,
						sortable: false,
						value: Liferay.Language.get('update-entry'),
					},
					{
						key: ACTIONS.VIEW_DATA_RECORD,
						sortable: false,
						value: Liferay.Language.get('view-entries'),
					},
				]}
				endpoint={customObjectPermissionsModalState.endpoint}
				isOpen={dataDefinitionId !== null}
				onClose={() =>
					setCustomObjectPermissionsModalState({
						dataDefinitionId: null,
						endpoint: null,
					})
				}
				onSave={(permissions) => {
					const dataDefinitionPermissions = [];

					Object.values(permissions).forEach(
						({actionIds, roleName}) => {
							if (actionIds.length > 0) {
								dataDefinitionPermissions.push({
									actionIds: [ACTIONS.VIEW],
									roleName,
								});
							}
						}
					);

					return updateItem(
						`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/permissions`,
						dataDefinitionPermissions
					);
				}}
				rolesFilter={rolesFilter}
				title={Liferay.Language.get('app-permissions')}
			/>
		</>
	);
};
