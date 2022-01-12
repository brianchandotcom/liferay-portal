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

import {ApolloProvider} from '@apollo/client';
import React from 'react';
import ReactDOM from 'react-dom';

import './common/styles/global.scss';
import apolloClient from './apolloClient';
import ApplicationContextProvider from './common/context/ApplicationPropertiesProvider';
import ClayProvider from './common/providers/ClayProvider';
import CustomerPortal from './routes/customer-portal';
import Onboarding from './routes/onboarding';

const CustomerPortalApplication = ({liferaywebdavurl, page, route}) => {
	if (route === 'portal') {
		return <CustomerPortal assetsPath={liferaywebdavurl} page={page} />;
	}

	if (route === 'onboarding') {
		return <Onboarding assetsPath={liferaywebdavurl} />;
	}
};

class CustomerPortalWebComponent extends HTMLElement {
	connectedCallback() {
		const properties = {
			createSupportRequest: super.getAttribute('create-support-request'),
			licenseKeyDownloadURL: super.getAttribute(
				'license-key-download-url'
			),
			liferaywebdavurl: super.getAttribute('liferaywebdavurl'),
			oktaSessionURL: super.getAttribute('okta-session-url'),
			page: super.getAttribute('page'),
			route: super.getAttribute('route'),
			supportLink: super.getAttribute('support-link'),
		};
		ReactDOM.render(
			<ClayProvider>
				<ApolloProvider client={apolloClient}>
					<ApplicationContextProvider properties={properties}>
						<CustomerPortalApplication
							liferaywebdavurl={properties.liferaywebdavurl}
							page={properties.page}
							route={properties.route}
						/>
					</ApplicationContextProvider>
				</ApolloProvider>
			</ClayProvider>,
			this
		);
	}
}

const ELEMENT_ID = 'liferay-remote-app-customer-portal';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, CustomerPortalWebComponent);
}
