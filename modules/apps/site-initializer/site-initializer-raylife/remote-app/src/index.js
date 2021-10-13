import React from 'react';
import ReactDOM from 'react-dom';

import {WebComponent} from '~/common/WebComponent';
import ClayIconProvider from '~/common/context/ClayIconProvider';
import {GoogleMapsService} from '~/common/services/google-maps';
import SharedStyle from '~/common/styles/index.scss';
import GetAQuote from '~/routes/get-a-quote/pages/GetAQuote';
import QuoteComparison from '~/routes/quote-comparison/pages/QuoteComparison';
import SelectedQuote from '~/routes/selected-quote/pages/SelectedQuote';

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

const ELEMENT_ID = 'liferay-raylife-d2c';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, DirectToCustomerWebComponent);
}
