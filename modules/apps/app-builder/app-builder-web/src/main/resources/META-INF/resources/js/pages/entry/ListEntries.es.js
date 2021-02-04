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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ListView from 'data-engine-js-components-web/js/components/list-view/ListView.es';
import Loading from 'data-engine-js-components-web/js/components/loading/Loading.es';
import useQuery from 'data-engine-js-components-web/js/hooks/useQuery.es';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import useDataListView from '../../hooks/useDataListView.es';
import useEntriesActions from '../../hooks/useEntriesActions.es';
import usePermissions from '../../hooks/usePermissions.es';
import {getLocalizedUserPreferenceValue} from '../../utils/lang.es';
import {buildEntries, getStatusLabel, navigateToEditPage} from './utils.es';

export default function ListEntries({history}) {
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
	} = useDataListView(dataListViewId, dataDefinitionId);

	const formColumns = [
		...columns.map(({value, ...column}) => ({
			...column,
			value: getLocalizedUserPreferenceValue(
				value,
				userLanguageId,
				dataDefinition.defaultLanguageId
			),
		})),
		{
			key: 'status',
			value: Liferay.Language.get('status'),
		},
	];

	const onClickEditPage = () => {
		navigateToEditPage(basePortletURL, {
			backURL: window.location.href,
			languageId: userLanguageId,
		});
	};

	const [query] = useQuery(
		history,
		{
			keywords: '',
			page: 1,
			pageSize: 20,
			sort: '',
		},
		appId
	);

	return (
		<Loading className="loading-wrapper" isLoading={isLoading}>
			<ListView
				actions={actions}
				addButton={() =>
					showFormView &&
					permissions.add && (
						<ClayButtonWithIcon
							className="nav-btn nav-btn-monospaced"
							onClick={onClickEditPage}
							symbol="plus"
							title={Liferay.Language.get('new-entry')}
						/>
					)
				}
				columns={formColumns}
				emptyState={{
					button: () =>
						showFormView &&
						permissions.add && (
							<ClayButton
								displayType="secondary"
								onClick={onClickEditPage}
							>
								{Liferay.Language.get('new-entry')}
							</ClayButton>
						),
					title: Liferay.Language.get('there-are-no-entries-yet'),
				}}
				endpoint={`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`}
				history={history}
				noActionsMessage={Liferay.Language.get(
					'you-do-not-have-the-permission-to-manage-this-entry'
				)}
				queryParams={{dataListViewId}}
				scope={appId}
			>
				{(entry, index) => ({
					...buildEntries({
						dataDefinition,
						fieldNames,
						permissions,
						query,
					})(entry, index),
					status: getStatusLabel(entry.status),
				})}
			</ListView>
		</Loading>
	);
}
