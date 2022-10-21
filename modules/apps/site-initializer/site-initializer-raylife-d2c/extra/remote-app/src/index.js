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

import React from 'react';
import ReactDOM from 'react-dom';

import ClayIconProvider from './common/context/ClayIconProvider';
import {GoogleMapsService} from './common/services/google-maps';
import './common/styles/index.scss';
import ApplicationContextProvider from './common/context/ApplicationPropertiesProvider';
import {getGuestPermissionToken} from './common/services/token';
import {Liferay} from './common/utils/liferay';
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

const giveGuestAuthorization = async () => {
	const token = await getGuestPermissionToken();

	sessionStorage.setItem(
		'raylife-guest-permission-token',
		token?.access_token
	);
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

		if (!Liferay.ThemeDisplay.getUserName()) {
			giveGuestAuthorization();
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
