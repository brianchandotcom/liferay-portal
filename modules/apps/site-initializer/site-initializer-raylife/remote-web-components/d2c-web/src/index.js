import React from 'react';
import ReactDOM from 'react-dom';

import {App} from './App';
import {Providers} from './Providers';
import {GoogleMapsService} from './services/google-maps';
import StylesProvider from './styles/provider.scss';

const TAG_NAME = 'd2c-web';

class WebComponent extends HTMLElement {
	constructor() {
		super();

		GoogleMapsService.setup();

		this.styleSass = document.createElement('style');
		this.mountPoint = document.createElement('div');

		this.attachShadow({mode: 'open'});
	}

	connectedCallback() {
		this.styleSass.textContent = StylesProvider;
		this.shadowRoot.appendChild(this.styleSass);
		this.shadowRoot.appendChild(this.mountPoint);

		ReactDOM.render(
			<Providers>
				<App />
			</Providers>,
			this.mountPoint
		);
	}
}

if (!customElements.get(TAG_NAME)) {
	customElements.define(TAG_NAME, WebComponent);
}
