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

import './FloatingToolbarFragmentBackgroundImagePanelDelegateTemplate.soy';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {openImageSelector} from '../../../utils/FragmentsEditorDialogUtils';
import templates from './FloatingToolbarFragmentBackgroundImagePanel.soy';
import {updateEditableValueContentAction} from '../../../actions/updateEditableValue.es';

/**
 * FloatingToolbarFragmentBackgroundImagePanel
 */
class FloatingToolbarFragmentBackgroundImagePanel extends Component {
	/**
	 * Show image selector
	 * @private
	 * @review
	 */
	_handleSelectButtonClick() {
		openImageSelector(image => this._updateFragmentBackgroundImage(image));
	}

	/**
	 * Remove existing image if any
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateFragmentBackgroundImage('');
	}

	/**
	 * Dispatches action to update editableValues with new background image url
	 * @param {string} backgroundImageURL
	 */
	_updateFragmentBackgroundImage(image) {
		this.store.dispatch(
			updateEditableValueContentAction(
				this.item.fragmentEntryLinkId,
				BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
				this.item.editableId,
				image
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
FloatingToolbarFragmentBackgroundImagePanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarFragmentBackgroundImagePanel
	 * @review
	 * @type {!object}
	 */
	item: Config.object().required()
};

const ConnectedFloatingToolbarFragmentBackgroundImagePanel = getConnectedComponent(
	FloatingToolbarFragmentBackgroundImagePanel,
	['defaultSegmentsExperienceId', 'languageId', 'segmentsExperienceId']
);

Soy.register(ConnectedFloatingToolbarFragmentBackgroundImagePanel, templates);

export {
	ConnectedFloatingToolbarFragmentBackgroundImagePanel,
	FloatingToolbarFragmentBackgroundImagePanel
};
export default ConnectedFloatingToolbarFragmentBackgroundImagePanel;
