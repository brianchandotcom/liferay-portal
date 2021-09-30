import React from 'react';
import ReactDOM from 'react-dom';

import GetAQuote from '~/apps/get-a-quote/pages/GetAQuote';
import QuoteComparison from '~/apps/quote-comparison/pages/QuoteComparison';
import SelectedQuote from '~/apps/selected-quote/pages/SelectedQuote';
import {WebComponent} from '~/shared/WebComponent';
import ClayIconProvider from '~/shared/context/ClayIconProvider';
import {GoogleMapsService} from '~/shared/services/google-maps';
import SharedStyle from '~/shared/styles/index.scss';

const DirectToCustomer = ({application}) => {
	const SearchParams = new URLSearchParams(window.location.search);

	const route = SearchParams.get('raylife_dev_application') || application;

	if (route === 'quote-comparison') {
		return <QuoteComparison />;
	}

	if (route === 'get-a-quote') {
		return <GetAQuote />;
	}

	if (route === 'selected-quote') {
		return <SelectedQuote />;
	}
};

class DirectToCustomerWebComponent extends WebComponent {
	connectedCallback() {
		super.connectedCallback(SharedStyle);

		const GOOGLE_PLACES_KEY = this.getAttribute('GOOGLE_PLACES_KEY');

		if (GOOGLE_PLACES_KEY) {
			GoogleMapsService.setup(GOOGLE_PLACES_KEY);
		}

		ReactDOM.render(
			<ClayIconProvider>
				<DirectToCustomer
					application={super.getAttribute('application')}
				/>
			</ClayIconProvider>,
			this.mountPoint
		);
	}
}

const ELEMENT_ID = 'liferay-direct-to-customer';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, DirectToCustomerWebComponent);
}
