import Component from 'metal-component';
import debounce from 'metal-debounce';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarLinkPanelDelegateTemplate.soy';
import {BUTTON_TYPES, TARGET_TYPES} from '../../../utils/constants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import templates from './FloatingToolbarLinkPanel.soy';
import {UPDATE_CONFIG_ATTRIBUTES} from '../../../actions/actions.es';

/**
 * FloatingToolbarLinkPanel
 */
class FloatingToolbarLinkPanel extends Component {
	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._handleInputHrefKeyUp = debounce(
			this._handleInputHrefKeyUp.bind(this),
			400
		);
	}

	/**
	 * Callback executed on href keyup
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleInputHrefKeyUp(event) {
		const hrefElement = event.target;

		const config = {
			href: hrefElement.value
		};

		this._updateRowConfig(config);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleSubmit(event) {
		event.preventDefault();

		event.stopPropagation();
	}

	/**
	 * Updates row configuration
	 * @param {object} config Row configuration
	 * @private
	 * @review
	 */
	_updateRowConfig(config) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				config,
				editableId: this.item.editableId,
				fragmentEntryLinkId: this.item.fragmentEntryLinkId,
				type: UPDATE_CONFIG_ATTRIBUTES
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}

	/**
	 * Handle button type option change
	 * @param {Event} event
	 */
	_handleButtonTypeOptionChange(event) {
		const buttonElement = event.delegateTarget;
		const buttonElementValue =
			buttonElement.options[buttonElement.selectedIndex].value;

		let buttonType = this._buttonTypes.find(
			type => type.buttonTypeId === buttonElementValue
		);

		const config = {
			buttonType: buttonType.buttonTypeId
		};

		this._updateRowConfig(config);
	}

	/**
	 * Handle button type option change
	 * @param {Event} event
	 */
	_handleTargetOptionChange(event) {
		const targetElement = event.delegateTarget;

		const config = {
			target: targetElement.options[targetElement.selectedIndex].value
		};

		this._updateRowConfig(config);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarLinkPanel.STATE = {
	/**
	 * @default BUTTON_TYPES
	 * @memberOf FloatingToolbarLinkPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_buttonTypes: Config.array()
		.internal()
		.value(BUTTON_TYPES),

	/**
	 * @default TARGET_TYPES
	 * @memberOf FloatingToolbarLinkPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_targetTypes: Config.array()
		.internal()
		.value(TARGET_TYPES),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object().value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {object}
	 */
	store: Config.object().value(null)
};

Soy.register(FloatingToolbarLinkPanel, templates);

export {FloatingToolbarLinkPanel};
export default FloatingToolbarLinkPanel;
