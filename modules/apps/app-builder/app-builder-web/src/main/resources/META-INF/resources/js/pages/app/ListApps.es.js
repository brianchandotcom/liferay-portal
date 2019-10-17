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

import ClayLabel from '@clayui/label';
import moment from 'moment';
import {Link} from 'react-router-dom';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import {confirmDelete, updateItem} from '../../utils/client.es';

const DEPLOYMENT_ACTION = {
	deploy: Liferay.Language.get('deploy'),
	undeploy: Liferay.Language.get('undeploy')
};

const DEPLOYMENT_STATUS = {
	deployed: Liferay.Language.get('deployed'),
	undeployed: Liferay.Language.get('undeployed')
};

const DEPLOYMENT_TYPES = {
	productMenu: Liferay.Language.get('product-menu'),
	standalone: Liferay.Language.get('standalone'),
	widget: Liferay.Language.get('widget')
};

const concatTypes = types => {
	return types.reduce((acc, cur, index) => {
		if (index < types.length - 2) {
			return `${acc + DEPLOYMENT_TYPES[cur]}, `;
		}

		if (index == types.length - 2) {
			return `${acc + DEPLOYMENT_TYPES[cur]} ${Liferay.Language.get(
				'and'
			).toLowerCase()} `;
		}

		return acc + DEPLOYMENT_TYPES[cur];
	}, '');
};

const isDeployed = item => item.status.toLowerCase() === 'deployed';

const COLUMNS = [
	{
		key: 'name',
		sortable: true,
		value: Liferay.Language.get('name')
	},
	{
		key: 'type',
		value: Liferay.Language.get('deployed-as')
	},
	{
		key: 'dateCreated',
		sortable: true,
		value: Liferay.Language.get('create-date')
	},
	{
		asc: false,
		key: 'dateModified',
		sortable: true,
		value: Liferay.Language.get('modified-date')
	},
	{
		key: 'statusLabel',
		value: Liferay.Language.get('status')
	}
];

export default ({
	match: {
		params: {dataDefinitionId},
		url
	}
}) => {
	const {getStandaloneURL} = useContext(AppContext);

	const ACTIONS = [
		{
			action: item =>
				new Promise((resolve, reject) => {
					const deploymentAction = isDeployed(item)
						? 'undeploy'
						: 'deploy';

					updateItem(
						`/o/app-builder/v1.0/apps/${item.id}/deployment`,
						{},
						{deploymentAction}
					)
						.then(() => resolve(true))
						.catch(error => reject(error));
				}),
			name: item =>
				isDeployed(item)
					? DEPLOYMENT_ACTION.undeploy
					: DEPLOYMENT_ACTION.deploy
		},
		{
			action: item =>
				Promise.resolve(
					window.open(getStandaloneURL(item.id), '_blank')
				),
			name: Liferay.Language.get('open-standalone-app'),
			show: item =>
				item.appDeployments.some(
					deployment => deployment.type === 'standalone'
				)
		},
		{
			action: confirmDelete('/o/app-builder/v1.0/apps/'),
			name: Liferay.Language.get('delete')
		}
	];

	const EMPTY_STATE = {
		button: () => (
			<Button displayType="secondary" href={`${url}/deploy`}>
				{Liferay.Language.get('new-app')}
			</Button>
		),
		description: Liferay.Language.get(
			'select-the-form-and-table-view-you-want-and-deploy-your-app-as-a-widget-standalone-or-place-it-in-the-product-menu'
		),
		title: Liferay.Language.get('there-are-no-apps-yet')
	};

	return (
		<ListView
			actions={ACTIONS}
			addButton={() => (
				<Button
					className="nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
					href={`${url}/deploy`}
					symbol="plus"
					tooltip={Liferay.Language.get('new-app')}
				/>
			)}
			columns={COLUMNS}
			emptyState={EMPTY_STATE}
			endpoint={`/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`}
		>
			{item => ({
				...item,
				dateCreated: moment(item.dateCreated).fromNow(),
				dateModified: moment(item.dateModified).fromNow(),
				name: (
					<Link
						to={`/custom-object/${dataDefinitionId}/apps/${item.id}`}
					>
						{item.name.en_US}
					</Link>
				),
				statusLabel: (
					<ClayLabel
						displayType={isDeployed(item) ? 'success' : 'secondary'}
					>
						{DEPLOYMENT_STATUS[item.status.toLowerCase()]}
					</ClayLabel>
				),
				type: concatTypes(
					item.appDeployments.map(deployment => deployment.type)
				)
			})}
		</ListView>
	);
};
