import '../components/Tooltip/Tooltip.es';
import 'clay-icon';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './FieldBase.soy.js';
import {Config} from 'metal-state';

class FieldBase extends Component {}

FieldBase.STATE = {
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

	repeatable: Config.bool(),

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

	spritemap: Config.string().required(),

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

	tooltip: Config.string()
};

Soy.register(FieldBase, templates);

export default FieldBase;
