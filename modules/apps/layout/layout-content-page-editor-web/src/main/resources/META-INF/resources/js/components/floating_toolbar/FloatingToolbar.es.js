import Component from 'metal-component';
import Soy from 'metal-soy';
import {Align} from 'metal-position';
import {Config} from 'metal-state';

import {CLEAR_ACTIVE_ITEM} from '../../actions/actions.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {shouldClearFocus} from '../../utils/FragmentsEditorUpdateUtils.es';
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
		this._defaultButtonClicked = this._defaultButtonClicked.bind(this);
		this._handleWindowResize = this._handleWindowResize.bind(this);

		window.addEventListener('resize', this._handleWindowResize);
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.addListener('buttonClicked', this._defaultButtonClicked, true);
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
		this._selectedPanel = this.buttons.find(
			button => button.panelId === selectedPanelId
		);

		return selectedPanelId;
	}

	/**
	 * Select or deselect panel. Default handler for button clicked event.
	 * @param {Event} event
	 * @param {Object} data
	 * @private
	 */
	_defaultButtonClicked(event, data) {
		const {panelId} = data;

		if (!event.defaultPrevented) {
			if (this.selectedPanelId === panelId) {
				this.selectedPanelId = null;
			}
			else {
				this.selectedPanelId = panelId;
			}
		}
	}

	_handleFloatingToolbarFocusOut() {
		requestAnimationFrame(
			() => {
				if (shouldClearFocus(this.anchorElement)) {
					this.store.dispatchAction(
						CLEAR_ACTIVE_ITEM
					);
				}
			}
		);
	}

	/**
	 * Handle panel button click
	 * @param {MouseEvent} event Click event
	 */
	_handlePanelButtonClick(event) {
		const {panelId = null, type} = event.delegateTarget.dataset;

		this.emit(
			'buttonClicked',
			event,
			{
				panelId,
				type
			}
		);
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
	 * List of available buttons.
	 * @default undefined
	 * @instance
	 * @memberOf FloatingToolbar
	 * @review
	 * @type {object[]}
	 */
	buttons: Config
		.arrayOf(
			Config.shapeOf(
				{
					icon: Config.string(),
					id: Config.string(),
					panelId: Config.string(),
					title: Config.string(),
					type: Config.string()
				}
			)
		)
		.required(),

	/**
	 * If true, once a panel has been selected it cannot be changed
	 * until selectedPanelId is set manually to null.
	 * @default false
	 * @instance
	 * @memberof FloatingToolbar
	 * @review
	 * @type {boolean}
	 */
	fixSelectedPanel: Config
		.bool()
		.value(false),

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