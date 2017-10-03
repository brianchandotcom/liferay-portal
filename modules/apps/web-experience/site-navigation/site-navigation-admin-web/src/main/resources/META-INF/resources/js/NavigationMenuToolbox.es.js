import Component from 'metal-component';
import Soy from 'metal-soy';

import 'metal-dropdown';

import templates from './NavigationMenuToolbox.soy';

/**
 * NavigationMenuToolbox
 *
 */
class NavigationMenuToolbox extends Component {}

Soy.register(NavigationMenuToolbox, templates);

export { NavigationMenuToolbox }
export default NavigationMenuToolbox;