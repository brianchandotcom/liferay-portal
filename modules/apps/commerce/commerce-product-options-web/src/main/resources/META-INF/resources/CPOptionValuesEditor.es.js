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

import {fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './CPOptionValuesEditor.soy';

/**
 * CPOptionValuesEditor
 *
 */

class CPOptionValuesEditor extends Component {
	constructor(opt_config, opt_parentElement) {
		super(opt_config, opt_parentElement);

		this.on('showChanged', this._handleShowChange);
		this.on('cpOptionIdChanged', this._handleShowChange);
	}

	_handleClose() {
		this.emit('close');
	}

	_handleAddOptionValue() {
		this._newOptionValueName = '';
		this._currentOptionValue = '0';
	}

	loadOptionValues() {
		if (!this.cpOptionId || this.cpOptionId === '0') {
			this._newOptionValueName = '';
			this._currentOptionValue = '0';

			return;
		}

		var url = new URL(this.optionValuesURL);

		url.searchParams.append(this.namespace + 'cpOptionId', this.cpOptionId);

		fetch(url.toString())
			.then((response) => response.json())
			.then((jsonResponse) => {
				this._optionValues = jsonResponse;

				if (this._optionValues && this._optionValues.length > 0) {
					if (!this._currentOptionValue) {
						this._currentOptionValue = this._optionValues[0].cpOptionValueId;
					}
				}
				else if (
					this._optionValues &&
					this._optionValues.length == 0
				) {
					this._newOptionValueName = '';
					this._currentOptionValue = '0';
				}
			});
	}

	_handleShowChange(_event) {
		this.loadOptionValues();
	}

	_handleOptionValueSelected(cpOptionValueId) {
		this._currentOptionValue = cpOptionValueId;
	}

	_handleOptionValueSaved(event) {
		this._currentOptionValue = event.cpOptionValueId;

		this.loadOptionValues();

		if (event.success) {
			this._showNotification(this.successMessage, 'success');
		}
		else {
			this._showNotification(event.message, 'danger');
		}
	}

	_handleOptionValueDeleted(_event) {
		this._currentOptionValue = null;

		this.loadOptionValues();
	}

	_handleCancelEditing(_event) {
		this._currentOptionValue = null;

		this.loadOptionValues();
	}

	_handleNameChange(newName) {
		if (this._currentOptionValue == '0') {
			this._newOptionValueName = newName;
		}
		else {
			this._newOptionValueName = '';
		}
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

CPOptionValuesEditor.STATE = {
	_newOptionValueName: Config.string().value(''),
	_optionValues: Config.array().value([]),
	cpOptionId: Config.string().required(),
	namespace: Config.string().required(),
	optionValueURL: Config.string().required(),
	optionValuesURL: Config.string().required(),
	pathThemeImages: Config.string().required(),
	show: Config.bool().value(false),
	successMessage: Config.string().required(),
};

// Register component

Soy.register(CPOptionValuesEditor, templates);

export default CPOptionValuesEditor;
