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
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './field_types/ColorPaletteField.es';

import './field_types/CheckboxField.es';

import './field_types/ItemSelectorField.es';

import './field_types/SelectField.es';

import './field_types/TextField.es';

import './FloatingToolbarFragmentConfigurationPanelDelegateTemplate.soy';
import {updateFragmentConfigurationAction} from '../../../actions/updateEditableValue.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {deleteIn, setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarFragmentConfigurationPanel.soy';

/**
 * FloatingToolbarFragmentConfigurationPanel
 */
class FloatingToolbarFragmentConfigurationPanel extends Component {
	/**
	 * Handles Configuration change
	 * @private
	 * @review
	 */
	_handleChangeConfiguration(event) {
		const {name: fieldName, value: fieldValue} = event;

		let nextConfigurationValues = setIn(
			this.item.configurationValues,
			[fieldName],
			fieldValue
		);

		Object.keys(nextConfigurationValues).forEach(key => {
			if (
				nextConfigurationValues[key] ===
				this.item.defaultConfigurationValues[key]
			) {
				nextConfigurationValues = deleteIn(nextConfigurationValues, [
					key
				]);
			}
		});

		this._sendConfiguration(nextConfigurationValues);
	}

	/**
	 * Handles Restore button click
	 * @private
	 * @review
	 */
	_handleRestoreButtonClick() {
		this._sendConfiguration({});
	}

	/**
	 * Update editableValues with new configuration
	 * @param {object} configurationValues
	 * @private
	 * @review
	 */
	_sendConfiguration(configurationValues) {
		this.store.dispatch(
			updateFragmentConfigurationAction(
				this.item.fragmentEntryLinkId,
				configurationValues
			)
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarFragmentConfigurationPanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object().value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarFragmentConfigurationPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required()
};

const ConnectedFloatingToolbarFragmentConfigurationPanel = getConnectedComponent(
	FloatingToolbarFragmentConfigurationPanel,
	['defaultSegmentsExperienceId', 'segmentsExperienceId', 'spritemap']
);

Soy.register(ConnectedFloatingToolbarFragmentConfigurationPanel, templates);

export {
	ConnectedFloatingToolbarFragmentConfigurationPanel,
	FloatingToolbarFragmentConfigurationPanel
};
export default ConnectedFloatingToolbarFragmentConfigurationPanel;
