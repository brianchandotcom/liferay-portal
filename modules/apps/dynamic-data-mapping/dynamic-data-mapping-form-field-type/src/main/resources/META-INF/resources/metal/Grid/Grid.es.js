import '../FieldBase/FieldBase.es';
import './GridRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Grid.soy.js';
import {Config} from 'metal-state';

class Grid extends Component {}

Grid.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Select
	 * @type {?array<object>}
	 */

	columns: Config.arrayOf(
		Config.shapeOf({
			label: Config.string(),
			value: Config.string()
		})
	).value([
		{
			label: 'col1',
			value: 'fieldId'
		}
	]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Grid
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Grid
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @instance
	 * @memberof Grid
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Select
	 * @type {?array<object>}
	 */

	rows: Config.arrayOf(
		Config.shapeOf({
			label: Config.string(),
			value: Config.string()
		})
	).value([
		{
			label: 'row',
			value: 'jehf'
		}
	]),

	/**
	 * @default true
	 * @instance
	 * @memberof Grid
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('grid')
};

Soy.register(Grid, templates);

export default Grid;
