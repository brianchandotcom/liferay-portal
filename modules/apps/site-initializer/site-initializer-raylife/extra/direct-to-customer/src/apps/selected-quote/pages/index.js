import {WebComponent} from '~/shared/WebComponent';
import ClayIconProvider from '~/shared/context/ClayIconProvider';
import StylesProvider from '~/shared/styles/provider.scss';

import {App} from './App';

const Application = () => (
	<ClayIconProvider>
		<App />
	</ClayIconProvider>
);

class Component extends WebComponent {
	constructor() {
		super(Application, StylesProvider);
	}
}

const Container = {
	class: Component,
	tag: 'liferay-raylife-selected-quote',
};

export default Container;
