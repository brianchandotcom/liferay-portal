import {ApolloProvider} from '@apollo/client';
import React from 'react';
import ReactDOM from 'react-dom';

import './common/styles/global.scss';
import apolloClient from './apolloClient';
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
		ReactDOM.render(
			<ClayProvider>
				<ApolloProvider client={apolloClient}>
					<CustomerPortalApplication
						liferaywebdavurl={super.getAttribute(
							'liferaywebdavurl'
						)}
						page={super.getAttribute('page')}
						route={super.getAttribute('route')}
					/>
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
