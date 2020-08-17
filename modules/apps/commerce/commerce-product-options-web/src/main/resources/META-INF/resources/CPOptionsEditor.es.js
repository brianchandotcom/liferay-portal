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

import './CPOptionDetail.es';

import './CPOptionList.es';

import './CPOptionValuesEditor.es';

import './CPOptionValueDetail.es';

import './CPOptionValueList.es';

import {fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './CPOptionsEditor.soy';

/**
 * CPOptionsEditor
 *
 */

class CPOptionsEditor extends Component {
	created() {
		this.loadOptions();
		this._handleKeyUpForModal = this._handleKeyUpForModal.bind(this);
	}

	_handleAddOption() {
		this._newOptionName = '';
		this._currentOption = '0';
	}

	loadOptions() {
		var url = new URL(this.optionsURL);

		fetch(url)
			.then((response) => response.json())
			.then((jsonResponse) => {
				this._options = jsonResponse;

				if (this._options && this._options.length > 0) {
					if (!this._currentOption) {
						this._currentOption = this._options[0].cpOptionId;
					}
				}
				else if (this._options && this._options.length == 0) {
					this._newOptionName = '';
					this._currentOption = '0';
				}
			});
	}

	_handleOptionSelected(cpOptionId) {
		this._currentOption = cpOptionId;
	}

	_handleOptionSaved(event) {
		this._currentOption = event.cpOptionId;

		this.loadOptions();

		if (event.success) {
			this._showNotification(this.successMessage, 'success');
		}
		else {
			this._showNotification(event.message, 'danger');
		}
	}

	_handleoptionDeleted(_event) {
		this._currentOption = null;

		this.loadOptions();
	}

	_handleCancelEditing(_event) {
		this._currentOption = null;

		this.loadOptions();
	}

	_handleNameChange(newName) {
		if (this._currentOption == '0') {
			this._newOptionName = newName;
		}
		else {
			this._newOptionName = '';
		}
	}

	_handleKeyUpForModal(evt) {
		if (evt.code === 'Escape') {
			this._handleCloseValueEditor();
		}
	}

	_handleEditValues(cpOptionId) {
		this._currentOption = cpOptionId;
		this._showValues = true;

		document.addEventListener('keyup', this._handleKeyUpForModal);
	}

	_handleCloseValueEditor() {
		this._showValues = false;

		document.removeEventListener('keyup', this._handleKeyUpForModal);
	}

	_showNotification(message, type) {
		AUI().use('liferay-notification', () => {
			new Liferay.Notification({
				closeable: true,
				delay: {
					hide: 5000,
					show: 0,
				},
				duration: 500,
				message,
				render: true,
				title: '',
				type,
			});
		});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPOptionsEditor.STATE = {
	_newOptionName: Config.string().value(''),
	_options: Config.array().value([]),
	namespace: Config.string().required(),
	optionURL: Config.string().required(),
	optionValueURL: Config.string().required(),
	optionValuesURL: Config.string().required(),
	optionsURL: Config.string().required(),
	pathThemeImages: Config.string().required(),
	successMessage: Config.string().required(),
};

// Register component

Soy.register(CPOptionsEditor, templates);

export default CPOptionsEditor;
