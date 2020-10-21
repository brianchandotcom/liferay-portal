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

import ClayLayout from '@clayui/layout';
import React, {useContext} from 'react';

import ToggleSwitch from '../../../../components/toggle-switch/ToggleSwitch.es';
import EditAppContext, {
	ADD_DEPLOYMENT,
	REMOVE_DEPLOYMENT,
} from '../EditAppContext.es';

export default ({
	deploymentType,
	tip = () => null,
	settings = () => null,
	subtitle,
	title,
}) => {
	const {
		dispatch,
		state: {
			app: {appDeployments},
		},
	} = useContext(EditAppContext);

	const checked = appDeployments.some(
		(appDeployment) => appDeployment.type === deploymentType
	);

	return (
		<>
			<ClayLayout.ContentRow className="justify-content-between mb-3 pl-4 pr-4">
				<ClayLayout.ContentCol>
					<ClayLayout.ContentSection containerElement="section">
						<h3>
							{title}

							{tip()}
						</h3>

						<p className="list-group-subtext">
							<small>{subtitle}</small>
						</p>
					</ClayLayout.ContentSection>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol className="right">
					<ToggleSwitch
						checked={checked}
						onChange={(checked) => {
							if (checked) {
								dispatch({
									deploymentType,
									type: ADD_DEPLOYMENT,
								});
							}
							else {
								dispatch({
									deploymentType,
									type: REMOVE_DEPLOYMENT,
								});
							}
						}}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{checked && settings()}
		</>
	);
};
