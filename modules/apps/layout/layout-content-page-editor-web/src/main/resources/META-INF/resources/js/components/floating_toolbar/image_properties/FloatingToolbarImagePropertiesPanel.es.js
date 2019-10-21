/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarImagePropertiesPanelDelegateTemplate.soy';
import {UPDATE_CONFIG_ATTRIBUTES} from '../../../actions/actions.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {
	EDITABLE_FIELD_CONFIG_KEYS,
	TARGET_TYPES
} from '../../../utils/constants';
import templates from './FloatingToolbarImagePropertiesPanel.soy';

/**
 * FloatingToolbarImagePropertiesPanel
 */
class FloatingToolbarImagePropertiesPanel extends Component {
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
	 * Handle alt text change
	 * @private
	 * @review
	 */
	_handleAltTextInputChange() {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.alt]: window.event.delegateTarget.value
		});
	}

	/**
	 * Handle select image button change
	 * @private
	 * @review
	 */
	_handleClearImageButtonClick() {
		this.emit('clearEditor');
	}

	/**
	 * Handle image link change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleImageLinkInputChange(event) {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.imageLink]: event.delegateTarget.value
		});
	}

	/**
	 * Handle image target option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleImageTargetOptionChange(event) {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.imageTarget]: event.delegateTarget.value
		});
	}

	/**
	 * Handle select image button change
	 * @private
	 * @review
	 */
	_handleSelectImageButtonClick() {
		this.emit('createProcessor');
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarImagePropertiesPanel.STATE = {
	/**
	 * @default TARGET_TYPES
	 * @memberOf FloatingToolbarImagePropertiesPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_imageTargetOptions: Config.array()
		.internal()
		.value(TARGET_TYPES),

	/**
	 * @default undefined
	 * @memberOf FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {object}
	 */
	store: Config.object().value(null)
};

const ConnectedFloatingToolbarImagePropertiesPanel = getConnectedComponent(
	FloatingToolbarImagePropertiesPanel,
	['imageSelectorURL', 'portletNamespace']
);

Soy.register(ConnectedFloatingToolbarImagePropertiesPanel, templates);

export {
	ConnectedFloatingToolbarImagePropertiesPanel,
	FloatingToolbarImagePropertiesPanel
};
export default FloatingToolbarImagePropertiesPanel;
