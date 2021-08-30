import React from 'react';
import ReactDOM from 'react-dom';
import StyleProvider from './index.scss';
import App from './App';

const customElementName = 'liferay-cra';

class WebComponent extends HTMLElement {
	constructor() {
		super();
		this.attachShadow({mode: 'open'});
	}

	connectedCallback() {
		ReactDOM.render(
			<>
				<style type="text/css">{StyleProvider}</style>

				<React.StrictMode>
					<App />
				</React.StrictMode>
			</>,

			this.shadowRoot
		);
	}
}

if (!customElements.get(customElementName)) {
	customElements.define(customElementName, WebComponent);
}
