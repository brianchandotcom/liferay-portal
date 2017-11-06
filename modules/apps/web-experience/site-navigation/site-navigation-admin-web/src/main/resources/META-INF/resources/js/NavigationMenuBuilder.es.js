import Component from 'metal-component';
import Soy from 'metal-soy';

import './NavigationMenuContainer.es';
import './NavigationMenuToolbox.es';

import templates from './NavigationMenuBuilder.soy';

/**
 * NavigationMenuBuilder
 *
 */
class NavigationMenuBuilder extends Component {}

Soy.register(NavigationMenuBuilder, templates);

export { NavigationMenuBuilder }
export default NavigationMenuBuilder;