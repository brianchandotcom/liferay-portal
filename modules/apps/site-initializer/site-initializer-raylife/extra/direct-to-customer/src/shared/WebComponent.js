import React from 'react';
import ReactDOM from 'react-dom';

export class WebComponent extends HTMLElement {
	constructor(MyApp, StylesProvider) {
		super();
		this.MyApp = MyApp;
		this.StylesProvider = StylesProvider;
		this.styleSass = document.createElement('style');
		this.mountPoint = document.createElement('div');

		this.attachShadow({mode: 'open'});
	}

	connectedCallback() {
		this.styleSass.textContent = this.StylesProvider;
		this.shadowRoot.appendChild(this.styleSass);
		this.shadowRoot.appendChild(this.mountPoint);

		const MyApp = this.MyApp;

		ReactDOM.render(<MyApp />, this.mountPoint);
	}
}
