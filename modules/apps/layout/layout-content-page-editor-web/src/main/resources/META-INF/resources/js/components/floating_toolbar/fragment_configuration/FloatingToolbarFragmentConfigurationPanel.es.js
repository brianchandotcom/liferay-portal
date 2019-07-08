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
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FloatingToolbarFragmentConfigurationPanelDelegateTemplate.soy';
import './field_types/Select.soy';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {getSelectData} from './field_types/Select.es';
import {prefixSegmentsExperienceId} from '../../../utils/prefixSegmentsExperienceId.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarFragmentConfigurationPanel.soy';
import {updateEditableValueAction} from '../../../actions/updateEditableValue.es';

const GET_DATA_FUNCTIONS = {
	select: getSelectData
};

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
		const fieldType = event.delegateTarget.dataset.fieldType;

		const fieldData = GET_DATA_FUNCTIONS[fieldType](event);

		const nextConfigurationValues = setIn(
			this.item.configurationValues,
			[fieldData.fieldSetName, fieldData.fieldName],
			fieldData.fieldValue
		);

		this._sendConfiguration(nextConfigurationValues);
	}

	/**
	 * Handles Restore button click
	 * @private
	 * @review
	 */
	_handleRestoreButtonClick() {
		this._sendConfiguration(this.item.defaultConfigurationValues);
	}

	/**
	 * Update editableValues with new configuration
	 * @param {object} configurationValues
	 * @private
	 * @review
	 */
	_sendConfiguration(configurationValues) {
		const defaultSegmentsExperienceId = prefixSegmentsExperienceId(
			this.defaultSegmentsExperienceId
		);
		const segmentsExperienceId = prefixSegmentsExperienceId(
			this.segmentsExperienceId
		);

		this.store.dispatch(
			updateEditableValueAction({
				fragmentEntryLinkId: this.item.fragmentEntryLinkId,
				editableValueContent: configurationValues,
				processor: FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				segmentsExperienceId:
					segmentsExperienceId || defaultSegmentsExperienceId
			})
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
