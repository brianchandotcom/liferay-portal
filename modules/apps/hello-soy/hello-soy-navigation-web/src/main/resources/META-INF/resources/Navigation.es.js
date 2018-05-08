import Component from 'metal-component/src/Component';
import Footer from 'hello-soy-web/Footer.soy';
import Header from 'hello-soy-web/Header.soy';
import Soy from 'metal-soy/src/Soy';
import templates from './Navigation.soy';

class Navigation extends Component {}

// Register component
Soy.register(Navigation, templates);

export default Navigation;