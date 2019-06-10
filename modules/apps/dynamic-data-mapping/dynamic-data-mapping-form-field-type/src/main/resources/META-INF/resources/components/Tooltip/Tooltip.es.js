import '../FormPortal/FormPortal.es';
import 'clay-icon';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Tooltip.soy.js';
import {Align} from 'metal-position';
import {Config} from 'metal-state';

const POSITIONS = ['top', 'left', 'right', 'bottom'];

class Tooltip extends Component {
	created() {
		this._handleTooltipHovered = this._handleTooltipHovered.bind(this);
		this._handleTooltipRendered = this._handleTooltipRendered.bind(this);
	}

	_handleTooltipHovered() {
		this.showContent = true;
	}

	_handleTooltipLeaved() {
		this.showContent = false;
	}

	_handleTooltipRendered() {
		const {tooltipSource, tooltipTarget} = this.refs;
		const {element} = tooltipSource;
		const suggestedPosition = Align.align(
			element,
			tooltipTarget,
			Align.Right
		);

		this.position = POSITIONS[suggestedPosition];
	}
}

Soy.register(Tooltip, templates);

Tooltip.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	icon: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(number|undefined)}
	 */

	position: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	showContent: Config.bool(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	text: Config.string().required()
};

export default Tooltip;
