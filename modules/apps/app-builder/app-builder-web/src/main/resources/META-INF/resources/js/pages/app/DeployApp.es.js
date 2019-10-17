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

import React from 'react';

import ProductMenuSettings from './ProductMenuSettings.es';
import Settings from './Settings.es';
import WidgetSettings from './WidgetSettings.es';

const Divider = () => {
	return (
		<div className="autofit-row pl-2 pr-2 mb-4">
			<div className="col-md-12">
				<h4 className="card-divider"></h4>
			</div>
		</div>
	);
};

export default () => {
	return (
		<>
			<div className="autofit-row pl-4 pr-4 mb-4">
				<div className="autofit-col-expand">
					<h2>{`${Liferay.Language.get('deploy-as')}...`}</h2>
				</div>
			</div>

			<Settings
				deploymentType="widget"
				settings={WidgetSettings}
				subtitle={Liferay.Language.get('deploy-a-widget')}
				title={Liferay.Language.get('widget')}
			/>

			<Divider />

			<Settings
				deploymentType="standalone"
				subtitle={Liferay.Language.get(
					'deploy-a-standalone-app-with-a-direct-link'
				)}
				title={Liferay.Language.get('standalone')}
			/>

			<Divider />

			<Settings
				deploymentType="productMenu"
				settings={ProductMenuSettings}
				subtitle={Liferay.Language.get(
					'deploy-to-the-control-panel-or-a-site-menu'
				)}
				title={Liferay.Language.get('product-menu')}
			/>

			<Divider />
		</>
	);
};
