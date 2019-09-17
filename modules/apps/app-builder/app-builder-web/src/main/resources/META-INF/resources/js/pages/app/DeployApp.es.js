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

import React, {useState} from 'react';

export default ({appName, onSettingsChange, settings}) => {
	const [state, setState] = useState({
		productMenu: settings.deploymentTypes.includes('productMenu'),
		standalone: settings.deploymentTypes.includes('standalone'),
		widget: settings.deploymentTypes.includes('widget')
	});

	const onSwitchToggle = type => {
		const {deploymentTypes: types} = settings;

		onSettingsChange({
			...settings,
			deploymentTypes: types.includes(type)
				? types.filter(deploymentType => deploymentType !== type)
				: types.concat(type)
		});

		setState(prevState => ({
			...prevState,
			[type]: !prevState[type]
		}));
	};

	const {productMenu: isProductMenu} = state;

	return (
		<>
			<div className="autofit-row pl-4 pr-4 mb-4">
				<div className="autofit-col-expand">
					<h2>{`${Liferay.Language.get('deploy-as')}...`}</h2>
				</div>
			</div>

			<div className="autofit-row pl-4 pr-4 mb-4">
				<div className="autofit-col-expand">
					<section className="autofit-section">
						<p className="list-group-title">
							<h3>{Liferay.Language.get('product-menu')}</h3>
						</p>
						<p className="list-group-subtext">
							<small>
								{Liferay.Language.get(
									'deploy-it-in-the-control-panel-or-in-a-specific-site'
								)}
							</small>
						</p>
					</section>
				</div>

				<div className="autofit-col right">
					<label className="toggle-switch">
						<input
							checked={isProductMenu}
							className="toggle-switch-check"
							onChange={() => onSwitchToggle('productMenu')}
							type="checkbox"
						/>
						<span aria-hidden="true" className="toggle-switch-bar">
							<span className="toggle-switch-handle"></span>
						</span>
					</label>
				</div>
			</div>

			{isProductMenu && (
				<>
					<div className="autofit-row pl-4 pr-4">
						<div className="autofit-col-expand">
							<div className="form-group">
								<label htmlFor="productMenuName">
									{Liferay.Language.get('name-in-the-menu')}
								</label>
								<input
									className="form-control"
									disabled={true}
									id="productMenuName"
									placeholder={Liferay.Language.get(
										'untitled-app'
									)}
									type="text"
									value={appName}
								/>
							</div>
						</div>
					</div>

					<div className="autofit-row pl-4 pr-4">
						<div className="autofit-col-expand">
							<div className="form-group">
								<label htmlFor="selectPlacement">
									{Liferay.Language.get('place-it-on')}
								</label>
								<select
									className="form-control"
									disabled={true}
									id="selectPlacement"
									value={3}
								>
									<option value={1}>
										{Liferay.Language.get('control-panel')}
									</option>
									<option value={2}>
										{Liferay.Language.get('site-level')}
									</option>
									<option value={3}>
										{Liferay.Language.get(
											'control-panel-and-site-level'
										)}
									</option>
								</select>
							</div>
						</div>
						<div className="col-md-6">
							<div className="form-group">
								<label htmlFor="selectSites">
									{Liferay.Language.get('site-level')}
								</label>
								<select
									className="form-control"
									disabled={true}
									id="selectSites"
									value={1}
								>
									<option value={1}>
										{Liferay.Language.get('all-sites')}
									</option>
								</select>
							</div>
						</div>
					</div>
				</>
			)}

			<div className="autofit-row pl-2 pr-2">
				<div className="col-md-12">
					<h4 className="card-divider"></h4>
				</div>
			</div>
		</>
	);
};
