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
import templates from './FloatingToolbarLayoutBackgroundImagePanel.soy';
import {UPDATE_ROW_CONFIG} from '../../../actions/actions.es';

/**
 * FloatingToolbarLayoutBackgroundImagePanel
 */
class FloatingToolbarLayoutBackgroundImagePanel extends Component {
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
FloatingToolbarLayoutBackgroundImagePanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required()
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
