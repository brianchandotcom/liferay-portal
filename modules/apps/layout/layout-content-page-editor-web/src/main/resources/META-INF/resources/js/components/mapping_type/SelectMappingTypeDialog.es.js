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

/* eslint no-unused-vars: "warn" */

import Component from 'metal-component';
import Soy from 'metal-soy';

import './SelectMappingTypeForm.es';
import {Modal} from 'frontend-js-web';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {HIDE_MAPPING_TYPE_DIALOG} from '../../actions/actions.es';
import templates from './SelectMappingTypeDialog.soy';

/**
 * SelectMappingTypeDialog
 */
class SelectMappingTypeDialog extends Component {
	/**
	 * Change asset type selection dialog visibility.
	 * @private
	 * @review
	 */
	_handleVisibleChanged() {
		this.store.dispatch({
			type: HIDE_MAPPING_TYPE_DIALOG
		});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SelectMappingTypeDialog.STATE = {};

const ConnectedSelectMappingTypeDialog = getConnectedComponent(
	SelectMappingTypeDialog,
	['selectMappingTypeDialogVisible', 'spritemap']
);

Soy.register(ConnectedSelectMappingTypeDialog, templates);

export {ConnectedSelectMappingTypeDialog, SelectMappingTypeDialog};
export default ConnectedSelectMappingTypeDialog;
