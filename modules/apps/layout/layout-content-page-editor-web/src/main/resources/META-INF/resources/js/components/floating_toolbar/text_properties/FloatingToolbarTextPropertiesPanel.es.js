import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import '../common/FloatingToolbarColorPicker.es';
import './FloatingToolbarTextPropertiesPanelDelegateTemplate.soy';
import {
	EDITABLE_FIELD_CONFIG_KEYS,
	TEXT_ALIGNMENT_OPTIONS,
	TEXT_STYLES
} from '../../../utils/constants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarTextPropertiesPanel.soy';
import {UPDATE_CONFIG_ATTRIBUTES} from '../../../actions/actions.es';

/**
 * FloatingToolbarTextPropertiesPanel
 */
class FloatingToolbarTextPropertiesPanel extends Component {
	/**
	 * Updates fragment configuration
	 * @param {object} config Configuration
	 * @private
	 * @review
	 */
	_updateFragmentConfig(config) {
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
	 * Handle text alignment option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleTextAlignmentOptionChange(event) {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.textAlignment]:
				event.delegateTarget.value
		});
	}

	/**
	 * Handle text color option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleTextColorButtonClick(event) {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.textColor]: event.color
		});
	}

	/**
	 * Handle text style option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleTextStyleOptionChange(event) {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.textStyle]: event.delegateTarget.value
		});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarTextPropertiesPanel.STATE = {
	/**
	 * @default TEXT_ALIGNMENT_OPTIONS
	 * @memberOf FloatingToolbarTextPropertiesPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_textAlignmentOptions: Config.array()
		.internal()
		.value(TEXT_ALIGNMENT_OPTIONS),

	/**
	 * @default TEXT_STYLES
	 * @memberOf FloatingToolbarTextPropertiesPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_textStyles: Config.array()
		.internal()
		.value(TEXT_STYLES),

	/**
	 * @default undefined
	 * @memberOf FloatingToolbarTextPropertiesPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarTextPropertiesPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarTextPropertiesPanel
	 * @review
	 * @type {object}
	 */
	store: Config.object().value(null)
};

const ConnectedFloatingToolbarTextPropertiesPanel = getConnectedComponent(
	FloatingToolbarTextPropertiesPanel,
	['themeColorsCssClasses']
);

Soy.register(ConnectedFloatingToolbarTextPropertiesPanel, templates);

export {
	ConnectedFloatingToolbarTextPropertiesPanel,
	FloatingToolbarTextPropertiesPanel
};
export default FloatingToolbarTextPropertiesPanel;
