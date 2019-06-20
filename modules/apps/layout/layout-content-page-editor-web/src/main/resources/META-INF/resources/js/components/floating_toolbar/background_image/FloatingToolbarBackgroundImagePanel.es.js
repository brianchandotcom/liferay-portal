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

import './FloatingToolbarBackgroundImagePanelDelegateTemplate.soy';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {openImageSelector} from '../../../utils/FragmentsEditorDialogUtils';
import templates from './FloatingToolbarBackgroundImagePanel.soy';
import {UPDATE_ROW_CONFIG} from '../../../actions/actions.es';

/**
 * FloatingToolbarBackgroundImagePanel
 */
class FloatingToolbarBackgroundImagePanel extends Component {
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
FloatingToolbarBackgroundImagePanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarBackgroundImagePanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required()
};

const ConnectedFloatingToolbarBackgroundImagePanel = getConnectedComponent(
	FloatingToolbarBackgroundImagePanel,
	['imageSelectorURL', 'portletNamespace']
);

Soy.register(ConnectedFloatingToolbarBackgroundImagePanel, templates);

export {
	ConnectedFloatingToolbarBackgroundImagePanel,
	FloatingToolbarBackgroundImagePanel
};
export default ConnectedFloatingToolbarBackgroundImagePanel;
