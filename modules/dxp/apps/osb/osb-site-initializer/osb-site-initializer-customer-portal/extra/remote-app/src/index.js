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
import './common/styles/global.scss';
import {ClayIconSpriteContext} from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ReactDOM from 'react-dom';
import {AppPropertiesContext} from './common/contexts/AppPropertiesContext';
import useApollo from './common/hooks/useApollo';
import useGlobalNetworkIndicator from './common/hooks/useGlobalNetworkIndicator';
import getIconSpriteMap from './common/utils/getIconSpriteMap';
import CustomerPortal from './routes/customer-portal';
import Home from './routes/home';
import Onboarding from './routes/onboarding';

const ELEMENT_ID = 'liferay-remote-app-customer-portal';

const AppRoutes = {
	home: <Home />,
	onboarding: <Onboarding />,
	portal: <CustomerPortal />,
};

const CustomerPortalApp = ({apis, route, ...properties}) => {
	const {client, networkStatus} = useApollo(
		apis.provisioningServerAPI,
		apis.oktaSessionAPI
	);
	useGlobalNetworkIndicator(networkStatus);

	if (!client) {
		return <ClayLoadingIndicator />;
	}

	return (
		<ApolloProvider client={client}>
			<AppPropertiesContext.Provider
				value={{
					...properties,
					...apis,
					client,
				}}
			>
				{AppRoutes[route]}
			</AppPropertiesContext.Provider>
		</ApolloProvider>
	);
};

class CustomerPortalWebComponent extends HTMLElement {
	connectedCallback() {
		const properties = {
			articleAccountSupportURL: super.getAttribute(
				'article-account-support-url'
			),
			articleDeployingActivationKeysURL: super.getAttribute(
				'article-deploying-activation-keys-url'
			),
			importDate: super.getAttribute('import-date'),
			liferayWebDAV: super.getAttribute('liferaywebdavurl'),
			submitSupportTicketURL: super.getAttribute(
				'submit-support-ticket-url'
			),
		};

		const apis = {
			gravatarAPI: super.getAttribute('gravatar-api'),
			oktaSessionAPI: super.getAttribute('okta-session-api'),
			provisioningServerAPI: super.getAttribute(
				'provisioning-server-api'
			),
		};

		ReactDOM.render(
			<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
				<CustomerPortalApp
					{...properties}
					apis={apis}
					route={super.getAttribute('route')}
				/>
			</ClayIconSpriteContext.Provider>,
			this
		);
	}
}

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, CustomerPortalWebComponent);
}
