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
						<select
							className="form-control"
							disabled={true}
							id="site"
							value={1}
						>
							<option value={1}>
								{Liferay.Language.get('all-sites')}
							</option>
						</select>
					</div>
				</div>
			)}
		</div>
	);
};
