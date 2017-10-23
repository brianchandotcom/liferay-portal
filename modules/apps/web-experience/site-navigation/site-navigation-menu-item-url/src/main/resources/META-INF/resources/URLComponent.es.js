import {Component} from 'metal-component';
import Soy from 'metal-soy';

import templates from './URLComponent.soy';

/**
 * URL Component
 *
 */
class URLComponent extends Component {}

Soy.register(URLComponent, templates);

export { URLComponent }
export default URLComponent;