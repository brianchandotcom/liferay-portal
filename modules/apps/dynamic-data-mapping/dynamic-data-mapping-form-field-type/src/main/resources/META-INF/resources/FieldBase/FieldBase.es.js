import '../components/Tooltip/Tooltip.es';
import 'clay-icon';
import Component from 'metal-component';
import compose from 'dynamic-data-mapping-form-renderer/js/util/compose.es';
import Soy from 'metal-soy';
import templates from './FieldBase.soy.js';
import withDispatch from '../util/withDispatch.es';
import withRepetitionControls from './withRepetitionControls.es';
import {Config} from 'metal-state';
import {getRepeatedIndex} from 'dynamic-data-mapping-form-renderer/js/util/repeatable.es';

class FieldBase extends Component {
	prepareStateForRender(state) {
		const repeatedIndex = getRepeatedIndex(this.name);

		return {
			...state,
			showRepeatableAddButton: this.repeatable,
			showRepeatableRemoveButton: this.repeatable && repeatedIndex > 0
		};
	}
}

FieldBase.STATE = {
	/**
	 * @default input
	 * @memberof FieldBase
	 * @type {?html}
	 */

	contentRenderer: Config.any(),

	/**
	 * @default false
	 * @memberof FieldBase
	 * @type {?boolean}
	 */

	displayErrors: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	id: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	name: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool(),

	/**
	 * @default true
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string()
};

const composed = compose(
	withDispatch,
	withRepetitionControls
)(FieldBase);

Soy.register(composed, templates);

export default composed;
