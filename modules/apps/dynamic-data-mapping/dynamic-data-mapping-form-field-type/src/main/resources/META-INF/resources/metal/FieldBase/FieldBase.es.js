import 'clay-icon';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './FieldBase.soy.js';

class FieldBase extends Component {
	static STATE = {

		/**
		 * @default input
		 * @instance
		 * @memberof FieldBase
		 * @type {?html}
		 */

		contentRenderer: Config.any(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(bool|undefined)}
		 */

		required: Config.bool(),

		/**
		 * @default true
		 * @instance
		 * @memberof FieldBase
		 * @type {?(bool|undefined)}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string().required()
	};
}

Soy.register(FieldBase, templates);

export default FieldBase;