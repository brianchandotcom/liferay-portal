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

import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppNavigationBar} from '../../App.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import ListView from '../../components/list-view/ListView.es';
import PermissionsModal from '../../components/permissions/PermissionsModal.es';
import {ACTIONS} from '../../pages/entry/PermissionsContext.es';
import {getItem, updateItem} from '../../utils/client.es';
import {getLocalizedValue} from '../../utils/lang.es';
import {fromNow} from '../../utils/time.es';

const columns = [
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

export default ({history, listViewProps = {}, objectType}) => {
	const [
		objectPermissionsModalState,
		setObjectPermissionsModalState,
	] = useState({
		dataDefinitionId: null,
		endpoint: null,
	});

	const {actions: listViewActions = []} = listViewProps;

	const actions = [
		{
			action: ({id}) =>
				Promise.resolve(
					history.push(`/${objectType}/${id}/form-views`)
				),
			name: Liferay.Language.get('form-views'),
		},
		{
			action: ({id}) =>
				Promise.resolve(
					history.push(`/${objectType}/${id}/table-views`)
				),
			name: Liferay.Language.get('table-views'),
		},
		{
			action: ({id}) =>
				Promise.resolve(history.push(`/${objectType}/${id}/apps`)),
			name: Liferay.Language.get('apps'),
		},
		{
			name: 'divider',
		},
		{
			action: ({id}) =>
				Promise.resolve(
					setObjectPermissionsModalState((prevState) => ({
						...prevState,
						dataDefinitionId: id,
					}))
				),
			name: Liferay.Language.get('app-permissions'),
		},
		...listViewActions,
	];

	const rolesFilter = ({name, roleType}) =>
		name !== 'Administrator' &&
		name !== 'Guest' &&
		name !== 'Owner' &&
		roleType !== 'organization' &&
		roleType !== 'site';

	const {dataDefinitionId} = objectPermissionsModalState;

	useEffect(() => {
		if (!dataDefinitionId) {
			return;
		}

		getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-record-collection`
		).then(({id: dataRecordCollectionId}) => {
			setObjectPermissionsModalState((prevState) => ({
				...prevState,
				endpoint: `/o/data-engine/v2.0/data-record-collections/${dataRecordCollectionId}/permissions`,
			}));
		});
	}, [dataDefinitionId, setObjectPermissionsModalState]);

	return (
		<>
			<ControlMenu
				title={Liferay.Language.get(
					'javax.portlet.title.com_liferay_app_builder_web_internal_portlet_ObjectsPortlet'
				)}
			/>

			<AppNavigationBar />

			<ListView {...listViewProps} actions={actions} columns={columns}>
				{(item) => {
					const {
						dateCreated,
						dateModified,
						defaultLanguageId,
						id,
						name,
					} = item;

					return {
						...item,
						dateCreated: fromNow(dateCreated),
						dateModified: fromNow(dateModified),
						name: (
							<Link to={`/${objectType}/${id}/form-views`}>
								{getLocalizedValue(defaultLanguageId, name)}
							</Link>
						),
					};
				}}
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
				endpoint={objectPermissionsModalState.endpoint}
				isOpen={dataDefinitionId !== null}
				onClose={() =>
					setObjectPermissionsModalState({
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
