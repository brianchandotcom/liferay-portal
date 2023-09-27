/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactDOM from 'react-dom';

import ClayIconProvider from './common/context/ClayIconProvider';

import './common/styles/index.scss';
import NotificationSidebar from './common/components/notification-sidebar/index';
import {GoogleMapsService} from './common/services/google-maps/google-maps';
import NewApplicationAutoContextProvider from './routes/applications/context/NewApplicationAutoContextProvider';
import ApplicationDetails from './routes/applications/pages/ApplicationDetails';
import Applications from './routes/applications/pages/Applications';
import ApplicationsTable from './routes/applications/pages/ApplicationsTable';
import NewApplication from './routes/applications/pages/NewApplication';
import Claims from './routes/claims/pages/Claims';
import ClaimDetails from './routes/claims/pages/ClaimsDetails';
import ClaimsTable from './routes/claims/pages/ClaimsTable';
import ProductPerformance from './routes/dashboard/ProductPerformance';
import RecentApplications from './routes/dashboard/pages/RecentApplications';
import WhatsNewModal from './routes/dashboard/pages/SettingsModals';
import Policies from './routes/policies/pages/Policies';
import PoliciesTable from './routes/policies/pages/PoliciesTable';
import PolicyDetails from './routes/policies/pages/PolicyDetails';
import Reports from './routes/reports/pages/Reports';

export type RaylifeComponentsType = {
	[key: string]: JSX.Element;
};

const NoRouteSelected = () => (
	<div className="raylife-app">No route selected</div>
);

const RaylifeComponents: RaylifeComponentsType = {
	'application-details': <ApplicationDetails />,
	'applications': <Applications />,
	'applications-table': <ApplicationsTable />,
	'claim-details': <ClaimDetails />,
	'claims': <Claims />,
	'claims-table': <ClaimsTable />,
	'new-application': (
		<NewApplicationAutoContextProvider>
			<NewApplication />
		</NewApplicationAutoContextProvider>
	),
	'notification-sidebar': <NotificationSidebar />,
	'policies': <Policies />,
	'policies-table': <PoliciesTable />,
	'policy-details': <PolicyDetails />,
	'product-performance': <ProductPerformance />,
	'recent-applications': <RecentApplications />,
	'reports': <Reports />,
	'whats-new-modal': <WhatsNewModal />,
};

type Props = {
	route: any;
};

const DirectToCustomer: React.FC<Props> = ({route}) =>
	RaylifeComponents[route] ?? <NoRouteSelected />;

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
