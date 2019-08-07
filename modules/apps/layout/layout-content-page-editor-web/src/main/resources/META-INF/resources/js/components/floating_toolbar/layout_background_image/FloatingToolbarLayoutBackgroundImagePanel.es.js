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

import './FloatingToolbarLayoutBackgroundImagePanelDelegateTemplate.soy';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {openImageSelector} from '../../../utils/FragmentsEditorDialogUtils';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarLayoutBackgroundImagePanel.soy';
import {UPDATE_ROW_CONFIG} from '../../../actions/actions.es';

const IMAGE_SOURCE_TYPE_IDS = {
	content: 'content_mapping',
	selection: 'manual_selection'
};

/**
 * FloatingToolbarLayoutBackgroundImagePanel
 */
class FloatingToolbarLayoutBackgroundImagePanel extends Component {
	/**
	 * @return {Array<{id: string, label: string}>} Image source types
	 * @private
	 * @static
	 * @review
	 */
	static getImageSourceTypes() {
		return [
			{
				id: IMAGE_SOURCE_TYPE_IDS.selection,
				label: Liferay.Language.get('manual-selection')
			},
			{
				id: IMAGE_SOURCE_TYPE_IDS.content,
				label: Liferay.Language.get('content-mapping')
			}
		];
	}

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		nextState = setIn(
			nextState,
			['_imageSourceTypeIds'],
			IMAGE_SOURCE_TYPE_IDS
		);

		nextState = setIn(
			nextState,
			['_imageSourceTypes'],
			FloatingToolbarLayoutBackgroundImagePanel.getImageSourceTypes()
		);

		return nextState;
	}

	/**
	 * @inheritdoc
	 * @param {boolean} firstRender
	 * @review
	 */
	rendered(firstRender) {
		if (firstRender) {
			this._selectedImageSourceTypeId =
				this.item.config.classNameId || this.item.config.mappedField
				? IMAGE_SOURCE_TYPE_IDS.content
				: IMAGE_SOURCE_TYPE_IDS.selection;
		}
	}

	/**
	 * Show image selector
	 * @private
	 * @review
	 */
	_handleSelectButtonClick() {
		openImageSelector({
			callback: url => this._updateRowBackgroundImage(url),
			imageSelectorURL: this.imageSelectorURL,
			portletNamespace: this.portletNamespace
		});
	}

	/**
	 * Remove existing image if any
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateRowBackgroundImage('');
	}

	/**
	 * @private
	 * @review
	 */
	_handleImageSourceTypeSelect(event) {
		this._selectedImageSourceTypeId = event.delegateTarget.value;
	}

	/**
	 * Updates row image
	 * @param {string} backgroundImage Row image
	 * @private
	 * @review
	 */
	_updateRowBackgroundImage(backgroundImage) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				config: {
					backgroundImage
				},
				rowId: this.itemId,
				type: UPDATE_ROW_CONFIG
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarLayoutBackgroundImagePanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {string}
	 */
	_selectedImageSourceTypeId: Config.oneOf(
		Object.values(IMAGE_SOURCE_TYPE_IDS)
	).internal()
};

const ConnectedFloatingToolbarLayoutBackgroundImagePanel = getConnectedComponent(
	FloatingToolbarLayoutBackgroundImagePanel,
	['imageSelectorURL', 'portletNamespace']
);

Soy.register(ConnectedFloatingToolbarLayoutBackgroundImagePanel, templates);

export {
	ConnectedFloatingToolbarLayoutBackgroundImagePanel,
	FloatingToolbarLayoutBackgroundImagePanel
};
export default ConnectedFloatingToolbarLayoutBackgroundImagePanel;
