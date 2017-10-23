import {Component} from 'metal-component';
import Soy from 'metal-soy';

import componentTemplates from './URLComponent.soy';
import typeTemplates from './URLType.soy';

/**
 * URL Component
 *
 */
class URLComponent extends Component {}

Soy.register(URLComponent, componentTemplates);

export { URLComponent }
export default URLComponent;