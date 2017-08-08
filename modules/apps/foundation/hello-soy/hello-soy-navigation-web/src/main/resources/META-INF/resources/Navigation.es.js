import Component from 'metal-component/src/Component';
import Footer from './Footer.es';
import Header from './Header.es';
import Soy from 'metal-soy/src/Soy';
import templates from './Navigation.soy';

class Navigation extends Component {
	constructor(opt_config) {
		super(opt_config);
	}
}

// Register component
Soy.register(Navigation, templates);

export default Navigation;