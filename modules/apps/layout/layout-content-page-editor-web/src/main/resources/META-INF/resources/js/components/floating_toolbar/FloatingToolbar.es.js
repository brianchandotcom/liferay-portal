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
	 * @param {string} selectedPanelId
	 * @return {string}
	 * @review
	 */
	syncSelectedPanelId(selectedPanelId) {
		this._selectedPanel = this.panels.find(
			panel => panel.panelId === selectedPanelId
		);

		return selectedPanelId;
	}

	/**
	 * Handle panel button click
	 * @param {MouseEvent} event Click event
	 */
	_handlePanelButtonClick(event) {
		const {panelId = null} = event.delegateTarget.dataset;

		if (this.selectedPanelId === panelId) {
			this.selectedPanelId = null;
		}
		else {
			this.selectedPanelId = panelId;
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
			Align.align(
				this.element,
				this.anchorElement,
				Align.BottomRight,
				false
			);
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
	 * Selected panel
	 * @default null
	 * @instance
	 * @memberof FloatingToolbar
	 * @review
	 * @type {object|null}
	 */
	_selectedPanel: Config
		.object()
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
		.required(),

	/**
	 * Selected panel ID.
	 * @default null
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {string|null}
	 */
	selectedPanelId: Config
		.string()
		.internal()
		.value(null)
};

const ConnectedFloatingToolbar = getConnectedComponent(
	FloatingToolbar,
	['spritemap']
);

Soy.register(ConnectedFloatingToolbar, templates);

export {ConnectedFloatingToolbar, FloatingToolbar};
export default ConnectedFloatingToolbar;