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
	 * Aligns the given element to the anchor,
	 * defaulting to BottomRight position and moving to
	 * TopRight if it does not fit.
	 * @param {HTMLElement|null} element
	 * @param {HTMLElement|null} anchor
	 * @param {number} preferredPosition
	 * @param {number} fallbackPosition
	 * @private
	 * @return {number} Selected position
	 * @review
	 */
	static _alignElement(element, anchor, preferredPosition, fallbackPosition) {
		let position = -1;

		if (element && anchor) {
			const suggestedAlign = Align.suggestAlignBestRegion(
				element,
				anchor,
				preferredPosition
			);

			position =
				suggestedAlign.position === preferredPosition
					? preferredPosition
					: fallbackPosition;

			Align.align(element, anchor, position, false);
		}

		return position;
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._defaultButtonClicked = this._defaultButtonClicked.bind(this);
		this._handleWindowResize = this._handleWindowResize.bind(this);
		this._handleWrapperScroll = this._handleWrapperScroll.bind(this);

		window.addEventListener('resize', this._handleWindowResize);

		const wrapper = document.querySelector(
			'.fragment-entry-link-list-wrapper'
		);

		if (wrapper) {
			wrapper.addEventListener('scroll', this._handleWrapperScroll);
		}
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

		const wrapper = document.querySelector(
			'.fragment-entry-link-list-wrapper'
		);

		if (wrapper) {
			wrapper.removeEventListener('scroll', this._handleWrapperScroll);
		}
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		this._align();

		requestAnimationFrame(() => {
			this._align();
		});
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
			} else {
				this.selectedPanelId = panelId;
			}
		}
	}

	/**
	 * Handle panel button click
	 * @param {MouseEvent} event Click event
	 */
	_handlePanelButtonClick(event) {
		const {panelId = null, type} = event.delegateTarget.dataset;

		this.emit('buttonClicked', event, {
			panelId,
			type
		});
	}

	/**
	 * @private
	 * @review
	 */
	_handleWindowResize() {
		this._align();
	}

	/**
	 * @private
	 * @review
	 */
	_handleWrapperScroll() {
		this._align();
	}

	/**
	 * Aligns the FloatingToolbar to the anchorElement
	 * @private
	 * @review
	 */
	_align() {
		let panelPosition = {
			fallback: Align.TopRight,
			preferred: Align.BottomRight
		};

		const languageDirection =
			Liferay.Language.direction[Liferay.ThemeDisplay.getLanguageId()];

		if (languageDirection === 'rtl') {
			panelPosition = {
				fallback: Align.TopLeft,
				preferred: Align.BottomLeft
			};
		}

		requestAnimationFrame(() => {
			FloatingToolbar._alignElement(
				this.refs.buttons,
				this.anchorElement,
				panelPosition.preferred,
				panelPosition.fallback
			);

			requestAnimationFrame(() => {
				this._alignPanel(panelPosition);
			});
		});
	}

	/**
	 * Aligns the FloatingToolbar panel to the buttons
	 * @param {{ fallback: string, preferred: string }} panelPosition
	 * @private
	 * @review
	 */
	_alignPanel(panelPosition) {
		FloatingToolbar._alignElement(
			this.refs.panel,
			this.refs.buttons,
			panelPosition.preferred,
			panelPosition.fallback
		);
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
	_selectedPanel: Config.object()
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
	anchorElement: Config.instanceOf(HTMLElement).required(),

	/**
	 * List of available buttons.
	 * @default undefined
	 * @instance
	 * @memberOf FloatingToolbar
	 * @review
	 * @type {object[]}
	 */
	buttons: Config.arrayOf(
		Config.shapeOf({
			icon: Config.string(),
			id: Config.string(),
			panelId: Config.string(),
			title: Config.string(),
			type: Config.string()
		})
	).required(),

	/**
	 * If true, once a panel has been selected it cannot be changed
	 * until selectedPanelId is set manually to null.
	 * @default false
	 * @instance
	 * @memberof FloatingToolbar
	 * @review
	 * @type {boolean}
	 */
	fixSelectedPanel: Config.bool().value(false),

	/**
	 * Selected panel ID.
	 * @default null
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {string|null}
	 */
	selectedPanelId: Config.string()
		.internal()
		.value(null)
};

const ConnectedFloatingToolbar = getConnectedComponent(FloatingToolbar, [
	'spritemap'
]);

Soy.register(ConnectedFloatingToolbar, templates);

export {ConnectedFloatingToolbar, FloatingToolbar};
export default ConnectedFloatingToolbar;
