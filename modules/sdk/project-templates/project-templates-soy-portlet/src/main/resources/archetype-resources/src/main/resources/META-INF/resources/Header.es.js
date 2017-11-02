import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import templates from './Header.soy';

/**
 * Header Component
 */
class Header extends Component {}

// Register component
Soy.register(Header, templates);

export default Header;