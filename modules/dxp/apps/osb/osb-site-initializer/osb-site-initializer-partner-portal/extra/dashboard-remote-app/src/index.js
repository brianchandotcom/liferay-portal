/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAlert from '@clayui/alert';
import React from 'react';
import ReactDOM from 'react-dom';
import DealsChart from './routes/deals-chart';
import LevelChart from './routes/level-chart';
import MDFRequestChart from './routes/mdf-request-chart';
import RenewalsChart from './routes/renewals-chart';
import RevenueChart from './routes/revenue-chart';

const ELEMENT_ID = 'liferay-dashboard-remote-app-partner-portal';

const DashboardPartnerPortalApp = ({route}) => {
	if (route === 'deals-chart') {
		return <DealsChart />;
	}

	if (route === 'level-chart') {
		return <LevelChart />;
	}

	if (route === 'mdf-requests-chart') {
		return <MDFRequestChart />;
	}

	if (route === 'renewals-chart') {
		return <RenewalsChart />;
	}

	if (route === 'revenue-chart') {
		return <RevenueChart />;
	}

	return (
		<ClayAlert displayType="danger" title="Error:">
			Route not defined
		</ClayAlert>
	);
};

class DashboardPartnerPortalWebComponent extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(
			<DashboardPartnerPortalApp route={super.getAttribute('route')} />,
			this
		);
	}
}

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, DashboardPartnerPortalWebComponent);
}
