import Component from 'metal-component';
import Soy from 'metal-soy';
import {Align} from 'metal-position';
import {Config} from 'metal-state';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FloatingToolbar.soy';

/**
 * FloatingToolbar
 */
class FloatingToolbar extends Component {

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._handleWindowResize = this._handleWindowResize.bind(this);

		window.addEventListener('resize', this._handleWindowResize);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		window.removeEventListener('resize', this._handleWindowResize);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		this._align();

		requestAnimationFrame(
			() => {
				this._align();
			}
		);
	}

	/**
	 * Handle button click
	 * @param {MouseEvent} event Click event
	 */
	_handleButtonClick(event) {
		const {panelId = null} = event.delegateTarget.dataset;

		if (this._selectedPanelId === panelId) {
			this._selectedPanelId = null;
		}
		else {
			this._selectedPanelId = panelId;
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleWindowResize() {
		this._align();
	}

	/**
	 * Aligns the floating panel to the anchorElement
	 * @private
	 * @review
	 */
	_align() {
		if (this.element && this.anchorElement) {
			Align.align(this.element, this.anchorElement, Align.BottomRight);
		}
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbar.STATE = {

	/**
	 * Selected panel ID.
	 * @default null
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {string|null}
	 */
	_selectedPanelId: Config
		.string()
		.internal()
		.value(null),

	/**
	 * Element where the floating toolbar is positioned with
	 * @default undefined
	 * @instance
	 * @memberof FloatingToolbar
	 * @review
	 * @type {HTMLElement}
	 */
	anchorElement: Config
		.instanceOf(HTMLElement)
		.required(),

	/**
	 * List of available panels.
	 * @default undefined
	 * @instance
	 * @memberOf FloatingToolbar
	 * @review
	 * @type {object[]}
	 */
	panels: Config
		.arrayOf(
			Config.shapeOf(
				{
					icon: Config.string(),
					panelId: Config.string(),
					title: Config.string()
				}
			)
		)
		.required()
};

const ConnectedFloatingToolbar = getConnectedComponent(
	FloatingToolbar,
	['spritemap']
);

Soy.register(ConnectedFloatingToolbar, templates);

export {ConnectedFloatingToolbar, FloatingToolbar};
export default ConnectedFloatingToolbar;