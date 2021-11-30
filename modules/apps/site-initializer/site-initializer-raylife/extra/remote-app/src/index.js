import React from 'react';
import ReactDOM from 'react-dom';

import ClayIconProvider from './common/context/ClayIconProvider';
import {GoogleMapsService} from './common/services/google-maps';
import './common/styles/index.scss';
import ApplicationContextProvider from './common/context/ApplicationPropertiesProvider';
import GetAQuote from './routes/get-a-quote/pages/GetAQuote';
import QuoteComparison from './routes/quote-comparison/pages/QuoteComparison';
import SelectedQuote from './routes/selected-quote/pages/SelectedQuote';

const DirectToCustomer = ({route}) => {
	const SearchParams = new URLSearchParams(window.location.search);

	const routeEntry = SearchParams.get('raylife_dev_application') || route;

	if (routeEntry === 'quote-comparison') {
		return <QuoteComparison />;
	}

	if (routeEntry === 'get-a-quote') {
		return <GetAQuote />;
	}

	if (routeEntry === 'selected-quote') {
		return <SelectedQuote />;
	}
};

class DirectToCustomerWebComponent extends HTMLElement {
	connectedCallback() {
		const properties = {
			applicationsfoldername: this.getAttribute('applicationsfoldername'),
			googleplaceskey: this.getAttribute('googleplaceskey'),
			route: this.getAttribute('route'),
		};

		if (properties.googleplaceskey) {
			GoogleMapsService.setup(properties.googleplaceskey);
		}

		ReactDOM.render(
			<ClayIconProvider>
				<ApplicationContextProvider properties={properties}>
					<DirectToCustomer route={properties.route} />
				</ApplicationContextProvider>
			</ClayIconProvider>,
			this
		);
	}
}

const ELEMENT_ID = 'liferay-remote-app-raylife';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, DirectToCustomerWebComponent);
}
