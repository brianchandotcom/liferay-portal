/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import '../css/DataSets.scss';
import {FDS_DEFAULT_PROPS} from './utils/constants';

export interface IDataSet {
	actions: {
		delete: {
			href: string;
			method: string;
		};
		update: {
			href: string;
			method: string;
		};
	};
	additionalAPIURLParameters?: string;
	creationActionsOrder?: string;
	defaultItemsPerPage: number;
	defaultVisualizationMode?: string;
	description?: string;
	externalReferenceCode: string;
	filtersOrder?: string;
	id: string;
	itemActionsOrder?: string;
	label: string;
	listOfItemsPerPage: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
	sortsOrder?: string;
	tableSectionsOrder?: string;
}

const SystemDataSets = ({
	namespace,
}: {
	namespace: string;
}) => {
	const views = [
		{
			contentRenderer: 'table',
			name: 'table',
			schema: {
				fields: [
					{
						actionId: 'edit',
						contentRenderer: 'actionLink',
						fieldName: 'label',
						label: Liferay.Language.get('name'),
						sortable: true,
					},
					{
						fieldName: 'restApplication',
						label: Liferay.Language.get('rest-application'),
						sortable: true,
					},
					{
						fieldName: 'restSchema',
						label: Liferay.Language.get('rest-schema'),
						sortable: true,
					},
					{
						fieldName: 'restEndpoint',
						label: Liferay.Language.get('rest-endpoint'),
						sortable: true,
					},
					{
						fieldName: 'status',
						label: Liferay.Language.get('status'),
						sortable: true,
					},
					{
						contentRenderer: 'dateTime',
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified-date'),
						sortable: true,
					},
				],
			},
		},
	];

	return (
		<div className="data-sets">
			<FrontendDataSet
				{...FDS_DEFAULT_PROPS}
				emptyState={{
					description: Liferay.Language.get(
						'start-creating-one-to-show-your-data'
					),
					image: '/states/empty_state.svg',
					title: Liferay.Language.get('no-system-data-sets-created'),
				}}
				id={`${namespace}DataSets`}
				itemsActions={[]}
				sorts={[{direction: 'desc', key: 'dateCreated'}]}
				views={views}
			/>
		</div>
	);
};

export default SystemDataSets;
