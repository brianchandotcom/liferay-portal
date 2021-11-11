export class WebComponent extends HTMLElement {
	constructor() {
		super();
		this.styleSass = document.createElement('style');
		this.mountPoint = document.createElement('div');

		this.attachShadow({mode: 'open'});
	}

	connectedCallback(StylesProvider) {
		this.styleSass.textContent = StylesProvider;
		this.shadowRoot.appendChild(this.styleSass);
		this.shadowRoot.appendChild(this.mountPoint);
	}
}
