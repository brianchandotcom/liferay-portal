import {Providers} from '~/apps/get-a-quote/Providers';
import {WebComponent} from '~/shared/WebComponent';
import {GoogleMapsService} from '~/shared/services/google-maps';
import StylesProvider from '~/shared/styles/provider.scss';

import {App} from './App';

const Application = () => (
	<Providers>
		<App />
	</Providers>
);

class Component extends WebComponent {
	constructor() {
		super(Application, StylesProvider);
	}

	connectedCallback() {
		GoogleMapsService.setup(this.getAttribute('GOOGLE_API'));

		super.connectedCallback();
	}
}

const Container = {
	class: Component,
	tag: 'liferay-raylife-get-a-quote',
};

export default Container;
