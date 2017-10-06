import {dom} from 'metal-dom';
import Soy from 'metal-soy';

import SelectLayout from 'layout-item-selector-web/js/SelectLayout.es';

import templates from './NavigationMenuTree.soy';

/**
 * NavigationMenuTree
 *
 */
class NavigationMenuTree extends SelectLayout {}

NavigationMenuTree.STATE = {
};

Soy.register(NavigationMenuTree, templates);

export { NavigationMenuTree }
export default NavigationMenuTree;