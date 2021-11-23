import {ApolloProvider} from '@apollo/client';
import React from 'react';
import ReactDOM from 'react-dom';

import {WebComponent} from '~/common/WebComponent';
import ClayProvider from '~/common/providers/ClayProvider';

import SharedStyle from '~/common/styles/global.scss';
import CustomerPortal from '~/routes/customer-portal';
import Onboarding from '~/routes/onboarding';
import apolloClient from './apolloClient';

const CustomerPortalApplication = ({liferaywebdavurl, page, route}) => {
	if (route === 'portal') {
		return <CustomerPortal assetsPath={liferaywebdavurl} page={page} />;
	}

	if (route === 'onboarding') {
		return <Onboarding assetsPath={liferaywebdavurl} />;
	}
};

class CustomerPortalWebComponent extends WebComponent {
	connectedCallback() {
		super.connectedCallback(SharedStyle);

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
			this.mountPoint
		);
	}
}

const ELEMENT_ID = 'liferay-remote-app-customer-portal';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, CustomerPortalWebComponent);
}
