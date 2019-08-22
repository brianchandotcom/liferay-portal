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
import './MapContentForm.es';
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
		this.dispose();
	}

	/**
	 * Sends mapped content to the server and closes this dialog.
	 * @private
	 * @review
	 */
	_handleSubmitButtonClick() {
		if (this._step === 1) {
			this._step = 2;
		} else {
			const ddmFormValuesElement = document.createElement('input');

			ddmFormValuesElement.setAttribute(
				'name',
				`${this.portletNamespace}ddmFormValues`
			);
			ddmFormValuesElement.setAttribute('type', 'hidden');
			ddmFormValuesElement.setAttribute(
				'value',
				this.refs.modal.refs.mapContentForm.getSerializedFields()
			);

			const ddmStructureIdElement = document.createElement('input');

			ddmStructureIdElement.setAttribute(
				'name',
				`${this.portletNamespace}ddmStructureId`
			);
			ddmStructureIdElement.setAttribute('type', 'hidden');
			ddmStructureIdElement.setAttribute('value', this._ddmStructure.id);

			const titleElement = document.createElement('input');

			titleElement.setAttribute('name', `${this.portletNamespace}title`);
			titleElement.setAttribute('type', 'hidden');
			titleElement.setAttribute('value', this._title);

			document.hrefFm.appendChild(ddmFormValuesElement);
			document.hrefFm.appendChild(ddmStructureIdElement);
			document.hrefFm.appendChild(titleElement);

			submitForm(document.hrefFm, this.addStucturedContentURL);
		}
	}

	/**
	 * Control form validation state
	 * @private
	 * @review
	 */
	_handleFormValidated(event) {
		this._valid = event.valid;
	}

	_handleStructureChange(event) {
		this._ddmStructure = event.ddmStructure;
	}

	_handleTitleChange(event) {
		this._title = event.title;
	}

	/**
	 * Change asset type selection dialog visibility.
	 * @private
	 * @review
	 */
	_handleVisibleChanged() {
		if (!this.visible) {
			this.dispose();
		}

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
	 * Selected DDM structure
	 * @default 1
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {
	 * 	id: string,
	 * 	label: string
	 * }
	 */
	_ddmStructure: Config.shapeOf({
		id: Config.string().required(),
		label: Config.string().required()
	}),

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
	 * Current content title
	 * @default 1
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {string}
	 */
	_title: Config.string().value(''),

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
	['addStucturedContentURL', 'portletNamespace', 'savingChanges', 'spritemap']
);

Soy.register(ConnectedCreateContentDialog, templates);

export {ConnectedCreateContentDialog, CreateContentDialog};
export default ConnectedCreateContentDialog;
