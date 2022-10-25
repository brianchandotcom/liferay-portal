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

import ReactDOM from 'react-dom';

import ClayIconProvider from './common/context/ClayIconProvider';

import './common/styles/index.scss';
import {GoogleMapsService} from './common/services/google-maps/google-maps';
import NewApplicationAutoContextProvider from './routes/applications/context/NewApplicationAutoContextProvider';
import ApplicationDetails from './routes/applications/pages/ApplicationDetails';
import Applications from './routes/applications/pages/Applications';
import ApplicationsTable from './routes/applications/pages/ApplicationsTable';
import NewApplication from './routes/applications/pages/NewApplication';
import Claims from './routes/claims/pages/Claims';
import ProductPerformance from './routes/dashboard/ProductPerformance';
import RecentApplications from './routes/dashboard/pages/RecentApplications';
import WhatsNewModal from './routes/dashboard/pages/SettingsModals';
import Policies from './routes/policies/pages/Policies';
import PoliciesTable from './routes/policies/pages/PoliciesTable';
import PolicyDetails from './routes/policies/pages/PolicyDetails';
import Reports from './routes/reports/pages/Reports';

type Props = {
	route: any;
};

const DirectToCustomer: React.FC<Props> = ({route}) => {
	const SearchParams = new URLSearchParams(window.location.search);

	const routeEntry = SearchParams.get('raylife_dev_application') || route;

	if (routeEntry === 'recent-applications') {
		return <RecentApplications />;
	}

	if (routeEntry === 'product-performance') {
		return <ProductPerformance />;
	}

	if (routeEntry === 'applications') {
		return <Applications />;
	}

	if (routeEntry === 'applications-table') {
		return <ApplicationsTable />;
	}

	if (routeEntry === 'policies') {
		return <Policies />;
	}

	if (routeEntry === 'policies-table') {
		return <PoliciesTable />;
	}

	if (routeEntry === 'claims') {
		return <Claims />;
	}

	if (routeEntry === 'reports') {
		return <Reports />;
	}

	if (routeEntry === 'new-application') {
		return (
			<NewApplicationAutoContextProvider>
				<NewApplication />
			</NewApplicationAutoContextProvider>
		);
	}

	if (routeEntry === 'whats-new-modal') {
		return <WhatsNewModal />;
	}

	if (routeEntry === 'application-details') {
		return <ApplicationDetails />;
	}

	if (routeEntry === 'policy-details') {
		return <PolicyDetails />;
	}

	return <></>;
};

class WebComponent extends HTMLElement {
	connectedCallback() {
		const properties = {
			googleplaceskey: this.getAttribute('googleplaceskey'),
			route: this.getAttribute('route'),
		};

		if (properties.googleplaceskey) {
			GoogleMapsService.setup(properties.googleplaceskey);
		}

		ReactDOM.render(
			<ClayIconProvider>
				<DirectToCustomer route={properties.route} />
			</ClayIconProvider>,
			this
		);
	}
}

const ELEMENT_ID = 'liferay-remote-app-raylife-ap';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
