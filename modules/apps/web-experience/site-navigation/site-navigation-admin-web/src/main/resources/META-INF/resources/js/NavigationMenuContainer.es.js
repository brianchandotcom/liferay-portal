import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './NavigationMenuContainer.soy';

/**
 * NavigationMenuContainer
 *
 */
class NavigationMenuContainer extends Component {}

Soy.register(NavigationMenuContainer, templates);

export { NavigationMenuContainer }
export default NavigationMenuContainer;