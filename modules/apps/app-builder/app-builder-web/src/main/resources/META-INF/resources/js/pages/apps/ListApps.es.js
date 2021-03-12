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
import ClayLabel from '@clayui/label';
import ListView from 'data-engine-js-components-web/js/components/list-view/ListView.es';
import {confirmDelete} from 'data-engine-js-components-web/js/utils/client.es';
import {concatValues} from 'data-engine-js-components-web/js/utils/utils.es';
import {compile} from 'path-to-regexp';
import React, {useContext} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import useBackUrl from '../../hooks/useBackUrl.es';
import useDeployApp from '../../hooks/useDeployApp.es';
import {getLocalizedValue} from '../../utils/lang.es';
import {fromNow} from '../../utils/time.es';
import {
	COLUMNS,
	DEPLOYMENT_ACTION,
	DEPLOYMENT_TYPES,
	STATUSES,
} from './constants.es';

const queryFields = [
	'active',
	'appDeployments',
	'dataDefinitionId',
	'dateCreated',
	'dateModified',
	'id',
	'name',
].join(',');

export const Actions = (validateMissingRequiredFieldsModal) => {
	const {getStandaloneURL} = useContext(AppContext);
	const {deployApp, undeployApp} = useDeployApp();

	return [
		{
			action: (app) => {
				if (app.active) {
					return undeployApp(app);
				}

				if (validateMissingRequiredFieldsModal) {
					return validateMissingRequiredFieldsModal(app).then(
						(callback) => callback ?? deployApp(app)
					);
				}

				return deployApp(app);
			},
			name: ({active}) =>
				DEPLOYMENT_ACTION[active ? 'undeploy' : 'deploy'],
			show: ({appDeployments}) => appDeployments.length > 0,
		},
		{
			action: ({id}) =>
				Promise.resolve(window.open(getStandaloneURL(id), '_blank')),
			name: Liferay.Language.get('open-standalone-app'),
			show: ({active, appDeployments}) =>
				active &&
				appDeployments.some(({type}) => type === 'standalone'),
		},
		{
			action: confirmDelete('/o/app-builder/v1.0/apps/'),
			name: Liferay.Language.get('delete'),
		},
	];
};

const ListApps = ({
	defaultLanguageId,
	editPath = [
		`/:objectType/:dataDefinitionId(\\d+)/apps/deploy`,
		`/:objectType/:dataDefinitionId(\\d+)/apps/:appId(\\d+)`,
	],
	listViewProps = {},
	match: {
		params: {dataDefinitionId, objectType},
	},
	history,
}) => {
	const {scope} = useContext(AppContext);
	const withBackUrl = useBackUrl();
	const newAppLink = compile(editPath[0])({dataDefinitionId, objectType});

	const ADD_BUTTON = () => (
		<Link to={newAppLink}>
			<ClayButtonWithIcon
				className="nav-btn nav-btn-monospaced"
				symbol="plus"
				title={Liferay.Language.get('new-app')}
			/>
		</Link>
	);

	const EMPTY_STATE = {
		button: () => (
			<Link to={newAppLink}>
				<ClayButton displayType="secondary">
					{Liferay.Language.get('new-app')}
				</ClayButton>
			</Link>
		),
		description: Liferay.Language.get(
			'select-the-form-and-table-view-you-want-and-deploy-your-app-as-a-widget-standalone-or-place-it-in-the-product-menu'
		),
		title: Liferay.Language.get('there-are-no-apps-yet'),
	};

	const ENDPOINT = `/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps?scope=${scope}&fields=${queryFields}`;

	const getEditAppUrl = ({dataDefinitionId, id}) => {
		return withBackUrl(
			compile(editPath[1])({
				appId: id,
				dataDefinitionId,
				objectType,
			})
		);
	};

	return (
		<ListView
			actions={Actions()}
			addButton={ADD_BUTTON}
			columns={COLUMNS}
			emptyState={EMPTY_STATE}
			endpoint={ENDPOINT}
			history={history}
			{...listViewProps}
		>
			{(app) => {
				const appName = getLocalizedValue(defaultLanguageId, app.name);

				return {
					...app,
					appName,
					dateCreated: fromNow(app.dateCreated),
					dateModified: fromNow(app.dateModified),
					name: <Link to={getEditAppUrl(app)}>{appName}</Link>,
					status: (
						<ClayLabel
							displayType={app.active ? 'success' : 'secondary'}
						>
							{STATUSES[app.active ? 'active' : 'inactive']}
						</ClayLabel>
					),
					type: concatValues(
						app.appDeployments.map(
							({type}) => DEPLOYMENT_TYPES[type]
						)
					),
				};
			}}
		</ListView>
	);
};

export default ListApps;
