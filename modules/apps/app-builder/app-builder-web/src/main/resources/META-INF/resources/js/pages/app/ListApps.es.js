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
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import {Context} from '@clayui/modal';
import moment from 'moment';
import React, {useContext} from 'react';
import {Link} from 'react-router-dom';

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

const deploy = (item, deploy, resolve, reject) => {
	updateItem(
		`/o/app-builder/v1.0/apps/${item.id}/deployment`,
		{},
		{deploymentAction: deploy ? 'deploy' : 'undeploy'}
	)
		.then(() => resolve(true))
		.catch(error => reject(error));
};

const isDeployed = status => status === 'deployed';

const showUndeployModal = (item, resolve, reject, undeployModalContext) => {
	const [state, dispatch] = undeployModalContext;

	dispatch({
		payload: {
			body: (
				<>
					<p>{Liferay.Language.get('undeploy-warning')}</p>
					<ClayList>
						<ClayList.Header>
							{Liferay.Language.get('app')}
						</ClayList.Header>
						<ClayList.Item flex>
							<ClayList.ItemField expand>
								<span>
									<b>{Liferay.Language.get('name')}:</b>{' '}
									{item.nameText}
								</span>
								<span>
									<b>
										{Liferay.Language.get('deployed-as')}:
									</b>{' '}
									{concatTypes(
										item.appDeployments.map(
											deployment => deployment.type
										)
									)}
								</span>
								<span>
									<b>
										{Liferay.Language.get('modified-date')}:
									</b>{' '}
									{item.dateModified}
								</span>
							</ClayList.ItemField>
						</ClayList.Item>
					</ClayList>
				</>
			),
			footer: [
				<></>,
				<></>,
				<ClayButton.Group key={0} spaced>
					<ClayButton
						displayType="secondary"
						key={1}
						onClick={state.onClose}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
					<ClayButton
						key={2}
						onClick={() => {
							state.onClose();
							deploy(item, false, resolve, reject);
						}}
					>
						{DEPLOYMENT_ACTION.undeploy}
					</ClayButton>
				</ClayButton.Group>
			],
			header: DEPLOYMENT_ACTION.undeploy,
			size: 'lg',
			status: 'warning'
		},
		type: 1
	});
};

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
		key: 'status',
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
	const undeployModalContext = useContext(Context);

	const ACTIONS = [
		{
			action: item =>
				new Promise((resolve, reject) => {
					if (isDeployed(item.statusText)) {
						showUndeployModal(
							item,
							resolve,
							reject,
							undeployModalContext
						);
					}
					else {
						deploy(item, true, resolve, reject);
					}
				}),
			name: item =>
				isDeployed(item.statusText)
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
				nameText: item.name.en_US,
				status: (
					<ClayLabel
						displayType={
							isDeployed(item.status.toLowerCase())
								? 'success'
								: 'secondary'
						}
					>
						{DEPLOYMENT_STATUS[item.status.toLowerCase()]}
					</ClayLabel>
				),
				statusText: item.status.toLowerCase(),
				type: concatTypes(
					item.appDeployments.map(deployment => deployment.type)
				)
			})}
		</ListView>
	);
};
