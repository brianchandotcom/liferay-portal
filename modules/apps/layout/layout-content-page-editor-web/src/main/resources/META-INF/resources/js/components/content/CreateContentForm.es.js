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

import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {
	HIDE_CREATE_CONTENT_DIALOG,
	UPDATE_LAST_SAVE_DATE
} from '../../actions/actions.es';
import templates from './CreateContentForm.soy';

/**
 * CreateContentForm
 */
class CreateContentForm extends PortletBase {
	/**
	 * Close asset type selection dialog
	 * @private
	 * @review
	 */
	_handleCancelButtonClick() {
		this.store.dispatch({
			type: HIDE_CREATE_CONTENT_DIALOG
		});
	}

	/**
	 * Sends mapped content to the server and closes this dialog.
	 * @private
	 * @review
	 */
	_handleSubmitButtonClick() {
		this.store
			.dispatch({
				type: HIDE_CREATE_CONTENT_DIALOG
			})
			.dispatch({
				lastSaveDate: new Date(),
				type: UPDATE_LAST_SAVE_DATE
			});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
CreateContentForm.STATE = {};

const ConnectedCreateContentForm = getConnectedComponent(CreateContentForm, [
	'portletNamespace',
	'savingChanges'
]);

Soy.register(ConnectedCreateContentForm, templates);

export {ConnectedCreateContentForm, CreateContentForm};
export default ConnectedCreateContentForm;
