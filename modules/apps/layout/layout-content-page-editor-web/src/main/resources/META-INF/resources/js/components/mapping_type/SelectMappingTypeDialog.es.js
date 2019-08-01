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

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import Component from 'metal-component';
import Soy from 'metal-soy';

import {HIDE_MAPPING_TYPE_DIALOG} from '../../actions/actions.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './SelectMappingTypeDialog.soy';
import './SelectMappingTypeForm.es';

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
			type: HIDE_MAPPING_TYPE_DIALOG,
			value: false
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
