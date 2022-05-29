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
import AppContextProvider from './common/context/AppPropertiesProvider';
import ClayProvider from './common/providers/ClayProvider';
import CustomerPortal from './routes/customer-portal';
import Onboarding from './routes/onboarding';

const CustomerPortalApplication = ({route}) => {
	if (route === 'portal') {
		return <CustomerPortal />;
	}

	if (route === 'onboarding') {
		return <Onboarding />;
	}
};

class CustomerPortalWebComponent extends HTMLElement {
	constructor() {
		super();
	}

	connectedCallback() {
		const properties = {
			articleAccountSupportURL: super.getAttribute(
				'article-account-support-url'
			),
			articleDeployingActivationKeysURL: super.getAttribute(
				'article-deploying-activation-keys-url'
			),
			gravatarAPI: super.getAttribute('gravatar-api'),
			liferayWebDAV: super.getAttribute('liferaywebdavurl'),
			oktaSessionAPI: super.getAttribute('okta-session-api'),
			page: super.getAttribute('page'),
			provisioningServerAPI: super.getAttribute(
				'provisioning-server-api'
			),
			route: super.getAttribute('route'),
			submitSupportTicketURL: super.getAttribute(
				'submit-support-ticket-url'
			),
		};
		ReactDOM.render(
			<ClayProvider>
				<ApolloProvider client={apolloClient}>
					<AppContextProvider properties={properties}>
						<CustomerPortalApplication route={properties.route} />
					</AppContextProvider>
				</ApolloProvider>
			</ClayProvider>,
			this
		);
	}

	disconnectedCallback() {
		ReactDOM.unmountComponentAtNode(this);
	}
}

const ELEMENT_ID = 'liferay-remote-app-customer-portal';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, CustomerPortalWebComponent);
}
