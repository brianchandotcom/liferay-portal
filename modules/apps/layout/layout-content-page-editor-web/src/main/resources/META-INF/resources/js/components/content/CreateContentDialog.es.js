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

import {Config} from 'metal-state';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import Component from 'metal-component';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './CreateContentDialog.soy';
import './CreateContentForm.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';

/**
 * CreateContentDialog
 */
class CreateContentDialog extends Component {
	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = setIn(
			state,
			['_stepLabel'],
			Liferay.Util.sub(
				Liferay.Language.get('step-x-of-x'),
				state._step,
				'2'
			)
		);

		nextState = setIn(
			nextState,
			['_stepName'],
			Liferay.Language.get('details')
		);

		return nextState;
	}

	/**
	 * Close asset type selection dialog
	 * @private
	 * @review
	 */
	_handleCancelButtonClick() {
		this.onCancelButtonClick();
	}

	/**
	 * Sends mapped content to the server and closes this dialog.
	 * @private
	 * @review
	 */
	_handleSubmitButtonClick() {
		this.onSubmitButtonClick();
	}

	/**
	 * Control form validation state
	 * @private
	 * @review
	 */
	_handleFormValidated(event) {
		this._valid = event.valid;
	}

	/**
	 * Change asset type selection dialog visibility.
	 * @private
	 * @review
	 */
	_handleVisibleChanged() {
		this.onVisibilityChange();
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
CreateContentDialog.STATE = {
	/**
	 * Current dialog step
	 * @default 1
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {number}
	 */
	_step: Config.number().value(1),

	/**
	 * Is form valid
	 * @default false
	 * @instance
	 * @memberOf CreateContentForm
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_valid: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof CreateContentDialog
	 * @review
	 * @type {function}
	 */
	onCancelButtonClick: Config.func().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof CreateContentDialog
	 * @review
	 * @type {function}
	 */
	onSubmitButtonClick: Config.func().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof CreateContentDialog
	 * @review
	 * @type {function}
	 */
	onVisibilityChange: Config.func().required()
};

const ConnectedCreateContentDialog = getConnectedComponent(
	CreateContentDialog,
	['savingChanges', 'spritemap']
);

Soy.register(ConnectedCreateContentDialog, templates);

export {ConnectedCreateContentDialog, CreateContentDialog};
export default ConnectedCreateContentDialog;
