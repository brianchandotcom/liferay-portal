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
import React, {useContext, useEffect, useRef, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import useDeployApp from '../../hooks/useDeployApp.es';
import isClickOutside from '../../utils/clickOutside.es';
import {confirmDelete} from '../../utils/client.es';
import {fromNow} from '../../utils/time.es';
import {concatValues} from '../../utils/utils.es';
import NewAppPopover from './NewAppPopover.es';
import {DEPLOYMENT_ACTION, DEPLOYMENT_TYPES, STATUSES} from './constants.es';

export default ({
	history,
	match: {
		params: {dataDefinitionId, objectType},
		url,
	},
}) => {
	const {getStandaloneURL, userId} = useContext(AppContext);
	const addButtonRef = useRef();
	const emptyStateButtonRef = useRef();
	const popoverRef = useRef();

	const {deployApp, undeployApp} = useDeployApp();

	const [alignElement, setAlignElement] = useState(addButtonRef.current);
	const [isPopoverVisible, setPopoverVisible] = useState(false);

	const onClickAddButton = ({currentTarget}) => {
		setAlignElement(currentTarget);

		if (isPopoverVisible && alignElement !== currentTarget) {
			return;
		}

		setPopoverVisible(!isPopoverVisible);
	};

	const onCancel = () => setPopoverVisible(false);

	useEffect(() => {
		const handler = ({target}) => {
			const isOutside = isClickOutside(
				target,
				addButtonRef.current,
				emptyStateButtonRef.current,
				popoverRef.current
			);

			if (isOutside) {
				setPopoverVisible(false);
			}
		};

		window.addEventListener('click', handler);

		return () => window.removeEventListener('click', handler);
	}, [addButtonRef, emptyStateButtonRef, popoverRef]);

	const ACTIONS = [
		{
			action: (item) =>
				item.active ? undeployApp(item) : deployApp(item),
			name: (item) =>
				item.active
					? DEPLOYMENT_ACTION.undeploy
					: DEPLOYMENT_ACTION.deploy,
		},
		{
			action: (item) =>
				Promise.resolve(
					window.open(getStandaloneURL(item.id), '_blank')
				),
			name: Liferay.Language.get('open-standalone-app'),
			show: (item) =>
				item.appDeployments.some(
					(deployment) => deployment.type === 'standalone'
				),
		},
		{
			action: confirmDelete('/o/app-builder/v1.0/apps/'),
			name: Liferay.Language.get('delete'),
		},
	];

	let COLUMNS = [
		{
			key: 'name',
			sortable: true,
			value: Liferay.Language.get('name'),
		},
		{
			key: 'type',
			value: Liferay.Language.get('deployed-as'),
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
		{
			key: 'status',
			value: Liferay.Language.get('status'),
		},
	];

	let ENDPOINT = `/o/app-builder/v1.0/apps`;
	let buttonProps;

	const FILTERS = [
		{
			items: [
				{label: DEPLOYMENT_TYPES.productMenu, value: 'productMenu'},
				{label: DEPLOYMENT_TYPES.standalone, value: 'standalone'},
				{label: DEPLOYMENT_TYPES.widget, value: 'widget'},
			],
			key: 'deploymentTypes',
			multiple: true,
			name: 'deployment-type',
		},
		{
			items: [
				{label: STATUSES.active, value: 'true'},
				{label: STATUSES.inactive, value: 'false'},
			],
			key: 'active',
			name: 'status',
		},
		{
			items: [{label: Liferay.Language.get('me'), value: userId}],
			key: 'userIds',
			multiple: true,
			name: 'author',
		},
	];

	if (dataDefinitionId) {
		ENDPOINT = `/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`;
		buttonProps = {href: `${url}/deploy`};
	}
	else {
		const [firstColumn, ...otherColumns] = COLUMNS;

		COLUMNS = [
			firstColumn,
			{
				key: 'dataDefinitionName',
				value: Liferay.Language.get('object'),
			},
			...otherColumns,
		];
		buttonProps = {
			onClick: (currentTarget) => onClickAddButton(currentTarget),
		};
	}

	const EMPTY_STATE = {
		button: () => (
			<Button
				{...buttonProps}
				displayType="secondary"
				ref={emptyStateButtonRef}
			>
				{Liferay.Language.get('new-app')}
			</Button>
		),
		description: Liferay.Language.get(
			'select-the-form-and-table-view-you-want-and-deploy-your-app-as-a-widget-standalone-or-place-it-in-the-product-menu'
		),
		title: Liferay.Language.get('there-are-no-apps-yet'),
	};

	return (
		<>
			<ListView
				actions={ACTIONS}
				addButton={() => (
					<Button
						className="nav-btn nav-btn-monospaced"
						ref={addButtonRef}
						symbol="plus"
						tooltip={Liferay.Language.get('new-app')}
						{...buttonProps}
					/>
				)}
				columns={COLUMNS}
				emptyState={EMPTY_STATE}
				endpoint={ENDPOINT}
				filters={!dataDefinitionId ? FILTERS : []}
			>
				{(item) => ({
					...item,
					dateCreated: fromNow(item.dateCreated),
					dateModified: fromNow(item.dateModified),
					name: dataDefinitionId ? (
						<Link
							to={`/${objectType}/${dataDefinitionId}/apps/${item.id}`}
						>
							{item.name.en_US}
						</Link>
					) : (
						item.name.en_US
					),
					nameText: item.name.en_US,
					status: (
						<ClayLabel
							displayType={item.active ? 'success' : 'secondary'}
						>
							{STATUSES[item.active ? 'active' : 'inactive']}
						</ClayLabel>
					),
					type: concatValues(
						item.appDeployments.map(
							({type}) => DEPLOYMENT_TYPES[type]
						)
					),
				})}
			</ListView>

			{!dataDefinitionId && (
				<NewAppPopover
					alignElement={alignElement}
					history={history}
					onCancel={onCancel}
					ref={popoverRef}
					setVisible={setPopoverVisible}
					visible={isPopoverVisible}
				/>
			)}
		</>
	);
};
