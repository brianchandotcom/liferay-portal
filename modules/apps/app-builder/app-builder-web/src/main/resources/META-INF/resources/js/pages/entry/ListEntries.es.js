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

import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import {Loading} from '../../components/loading/Loading.es';
import useDataListView from '../../hooks/useDataListView.es';
import useEntriesActions from '../../hooks/useEntriesActions.es';
import usePermissions from '../../hooks/usePermissions.es';
import {getLocalizedUserPreferenceValue} from '../../utils/lang.es';
import NoPermissionEntry from './NoPermissionEntry.es';
import {buildEntries, navigateToEditPage} from './utils.es';

export default function ListEntries() {
	const actions = useEntriesActions();
	const permissions = usePermissions();
	const {
		appId,
		basePortletURL,
		dataDefinitionId,
		dataListViewId,
		showFormView,
		userLanguageId,
	} = useContext(AppContext);

	const {
		columns,
		dataDefinition,
		dataListView: {fieldNames},
		isLoading,
	} = useDataListView(dataListViewId, dataDefinitionId, permissions.view);

	const formColumns = columns.map(({value, ...column}) => ({
		...column,
		value: getLocalizedUserPreferenceValue(
			value,
			userLanguageId,
			dataDefinition.defaultLanguageId
		),
	}));

	const portletParams = {
		languageId: userLanguageId,
	};

	if (!permissions.view) {
		return <NoPermissionEntry />;
	}

	return (
		<Loading isLoading={isLoading}>
			<ListView
				actions={actions}
				addButton={() =>
					showFormView &&
					permissions.add && (
						<Button
							className="nav-btn nav-btn-monospaced"
							onClick={() =>
								navigateToEditPage(
									basePortletURL,
									portletParams
								)
							}
							symbol="plus"
							tooltip={Liferay.Language.get('new-entry')}
						/>
					)
				}
				columns={formColumns}
				emptyState={{
					button: () =>
						showFormView &&
						permissions.add && (
							<Button
								displayType="secondary"
								onClick={() =>
									navigateToEditPage(
										basePortletURL,
										portletParams
									)
								}
							>
								{Liferay.Language.get('new-entry')}
							</Button>
						),
					title: Liferay.Language.get('there-are-no-entries-yet'),
				}}
				endpoint={`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`}
				noActionsMessage={Liferay.Language.get(
					'you-do-not-have-the-permission-to-manage-this-entry'
				)}
				queryParams={{dataListViewId}}
				scope={appId}
			>
				{buildEntries({
					dataDefinition,
					fieldNames,
					permissions,
					scope: appId,
				})}
			</ListView>
		</Loading>
	);
}
