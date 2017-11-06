import {dom} from 'metal-dom';
import Soy from 'metal-soy';

import SelectLayout from 'layout-item-selector-web/js/SelectLayout.es';

import componentTemplates from './LayoutComponent.soy';
import typeTemplates from './LayoutType.soy';

/**
 * Layout Component
 *
 */
class LayoutComponent extends SelectLayout {}

Soy.register(LayoutComponent, componentTemplates);

export { LayoutComponent }
export default LayoutComponent;