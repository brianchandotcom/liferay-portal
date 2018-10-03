import 'clay-icon';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './Tooltip.soy.js';
import {Align} from 'metal-position';

import '../FormPortal/index.es';

const POSITIONS = ['top', 'left', 'right', 'bottom'];

class Tooltip extends Component {
	static STATE = {

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

		spritemap: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Tooltip
		 * @type {?(string|undefined)}
		 */

		text: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Tooltip
		 * @type {?(string|undefined)}
		 */

		showContent: Config.bool()
	};

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
		const suggestedPosition = Align.align(element, tooltipTarget, Align.Right);

		this.position = POSITIONS[suggestedPosition];
	}
}

Soy.register(Tooltip, templates);

export default Tooltip;