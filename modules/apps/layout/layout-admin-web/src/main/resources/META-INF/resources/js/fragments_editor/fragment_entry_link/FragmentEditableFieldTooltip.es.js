import {Align} from 'metal-position';
import Component from 'metal-component';
import {Config} from 'metal-state';
import debounce from 'metal-debounce';
import dom from 'metal-dom';
import Soy from 'metal-soy';

import templates from './FragmentEditableFieldTooltip.soy';

class FragmentEditableFieldTooltip extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		this._handleDocumentClick = this._handleDocumentClick.bind(this);

		this._handleWindowResize = debounce(
			this._handleWindowResize.bind(this),
			100
		);

		this._windowResizeHandler = dom.on(
			window,
			'resize',
			this._handleWindowResize
		);

		this._documentClickHandler = dom.on(
			document.body,
			'click',
			this._handleDocumentClick
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	disposed() {
		if (this._documentClickHandler) {
			this._documentClickHandler.removeListener();
			this._documentClickHandler = null;
		}

		if (this._windowResizeHandler) {
			this._windowResizeHandler.removeListener();
			this._windowResizeHandler = null;
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	rendered() {
		this._alignTooltip();
	}

	/**
	 * Align tooltip position acording to editable field
	 * @private
	 * @review
	 */

	_alignTooltip() {
		Align.align(
			this.refs.tooltip,
			this.alignElement,
			Align.Top
		);
	}

	/**
	 * Handle a button click
	 */

	_handleButtonClick(event) {
		const button = event.delegateTarget;
		const buttonId = button.dataset.tooltipButtonId;

		this.emit(
			'buttonClick',
			{buttonId}
		);
	}

	/**
	 * Hide tooltip on document click when it is outside the tooltip
	 * @param {MouseEvent} event
	 */

	_handleDocumentClick(event) {
		if (
			this.refs.tooltip &&
			!this.refs.tooltip.contains(event.target) &&
			!this.alignElement.contains(event.target)
		) {
			this.emit('outsideTooltipClick');
		}
	}

	/**
	 * Handle window resize event
	 * @private
	 * @review
	 */

	_handleWindowResize() {
		this._alignTooltip();
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentEditableFieldTooltip.STATE = {

	/**
	 * Reference element used for aligning the tooltip
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableFieldTooltip
	 * @review
	 * @type {HTMLElement}
	 */

	alignElement: Config.object().required(),

	/**
	 * List of buttons rendered inside the tooltip
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableFieldTooltip
	 * @review
	 * @type {!Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */

	buttons: Config.arrayOf(
		Config.shapeOf(
			{
				id: Config.string().required(),
				label: Config.string().required()
			}
		)
	)
};

Soy.register(FragmentEditableFieldTooltip, templates);

export {FragmentEditableFieldTooltip};
export default FragmentEditableFieldTooltip;