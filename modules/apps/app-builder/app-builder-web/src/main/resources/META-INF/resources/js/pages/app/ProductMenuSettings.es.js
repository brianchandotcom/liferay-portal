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

import {ClaySelect} from '@clayui/form';
import React, {useContext, useEffect, useState} from 'react';

import {getItem} from '../../utils/client.es';
import EditAppContext, {UPDATE_SETTINGS} from './EditAppContext.es';

const SCOPES = [
	{
		label: Liferay.Language.get('control-panel'),
		value: ['control_panel']
	},
	{
		label: Liferay.Language.get('site-menu'),
		value: ['site_administration.content']
	},
	{
		label: Liferay.Language.get('control-panel-and-site-menu'),
		value: ['control_panel', 'site_administration.content']
	}
];

export default () => {
	const {
		dispatch,
		state: {
			app: {appDeployments}
		}
	} = useContext(EditAppContext);

	const [sites, setSites] = useState([]);

	useEffect(() => {
		getItem('/o/headless-admin-user/v1.0/my-user-account/sites').then(
			({items: sites = []}) => setSites(sites)
		);
	}, []);

	const onScopeChange = event => {
		const scope = event.target.value;

		dispatch({
			deploymentType: 'productMenu',
			settings: {scope: scope.split(',')},
			type: UPDATE_SETTINGS
		});
	};

	const {
		settings: {scope}
	} = appDeployments.find(
		appDeployment => appDeployment.type === 'productMenu'
	);

	return (
		<div className="autofit-row pl-4 pr-4">
			<div className="autofit-col-expand">
				<div className="form-group">
					<label htmlFor="scope">
						{Liferay.Language.get('place-it-in-the')}
					</label>
					<select
						className="form-control"
						id="scope"
						onChange={onScopeChange}
						value={scope}
					>
						{SCOPES.map(({label, value}, index) => (
							<option key={index} value={value}>
								{label}
							</option>
						))}
					</select>
				</div>
			</div>

			{scope.includes('site_administration.content') && (
				<div className="col-md-6">
					<div className="form-group">
						<label htmlFor="site">
							{Liferay.Language.get('site')}
						</label>

						<ClaySelect
							aria-label={Liferay.Language.get('select-site')}
							id="site"
						>
							<option key={0} value={0}>
								{Liferay.Language.get('all-sites')}
							</option>

							{sites.map(({id, name}) => (
								<ClaySelect.Option
									key={id}
									label={name}
									value={id}
								/>
							))}
						</ClaySelect>
					</div>
				</div>
			)}
		</div>
	);
};
