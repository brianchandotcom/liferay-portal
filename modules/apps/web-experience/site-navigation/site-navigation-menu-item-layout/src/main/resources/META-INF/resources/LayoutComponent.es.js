import {dom} from 'metal-dom';
import Soy from 'metal-soy';

import SelectLayout from 'layout-item-selector-web/js/SelectLayout.es';

import templates from './LayoutComponent.soy';

/**
 * Layout Component
 *
 */
class LayoutComponent extends SelectLayout {}

Soy.register(LayoutComponent, templates);

export { LayoutComponent }
export default LayoutComponent;