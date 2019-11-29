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

import ToggleSwitch from '../../components/toggle-switch/ToggleSwitch.es';
import EditAppContext, {
	ADD_DEPLOYMENT,
	REMOVE_DEPLOYMENT
} from './EditAppContext.es';

export default ({deploymentType, settings = () => <></>, subtitle, title}) => {
	const {
		dispatch,
		state: {
			app: {appDeployments}
		}
	} = useContext(EditAppContext);

	const checked = appDeployments.some(
		appDeployment => appDeployment.type === deploymentType
	);

	return (
		<>
			<div className="autofit-row mb-3 pl-4 pr-4">
				<div className="autofit-col-expand">
					<section className="autofit-section">
						<h3>{title}</h3>
						<p className="list-group-subtext">
							<small>{subtitle}</small>
						</p>
					</section>
				</div>

				<div className="autofit-col right">
					<ToggleSwitch
						checked={checked}
						onChange={checked => {
							if (checked) {
								dispatch({
									deploymentType,
									type: ADD_DEPLOYMENT
								});
							}
							else {
								dispatch({
									deploymentType,
									type: REMOVE_DEPLOYMENT
								});
							}
						}}
					/>
				</div>
			</div>

			{checked && settings()}
		</>
	);
};
