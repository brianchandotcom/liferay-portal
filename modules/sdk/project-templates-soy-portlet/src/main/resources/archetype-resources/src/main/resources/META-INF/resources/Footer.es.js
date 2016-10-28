import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import templates from './Footer.soy';

/**
 * Footer Component
 */
class Footer extends Component {}

// Register component
Soy.register(Footer, templates);

export default Footer;