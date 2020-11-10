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

import {fetch, runScriptsInElement} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './CPOptionDetail.soy';

/**
 * CPOptionDetail
 *
 */

class CPOptionDetail extends Component {
	created() {
		this.on('cpOptionIdChanged', this._handleCPOptionChange);
	}

	rendered() {
		this.loadOptionDetail(this.cpOptionId);
	}

	loadOptionDetail(cpOptionId) {
		var instance = this;

		const optionDetail = this.refs['option-detail'];

		var url = new URL(this.optionURL);

		url.searchParams.append(this.namespace + 'cpOptionId', cpOptionId);

		fetch(url)
			.then((response) => response.text())
			.then((text) => {
				optionDetail.innerHTML = text;

				runScriptsInElement(optionDetail);

				var name = optionDetail.querySelector(
					'#' + instance.namespace + 'name'
				);

				if (name) {
					name.addEventListener('keyup', (event) => {
						var target = event.target;

						instance.emit('nameChange', target.value);
					});
				}
			});
	}

	_handleCPOptionChange(event) {
		this.loadOptionDetail(event.newVal);
	}

	_handleSaveOption() {
		var instance = this;

		AUI().use('aui-base', 'aui-form-validator', 'liferay-form', (A) => {
			var hasErrors = false;

			const form = instance.element.querySelector('.option-detail form');

			var liferayForm = Liferay.Form.get(form.getAttribute('id'));

			if (liferayForm) {
				var validator = liferayForm.formValidator;

				if (A.instanceOf(validator, A.FormValidator)) {
					validator.validate();

					hasErrors = validator.hasErrors();

					if (hasErrors) {
						validator.focusInvalidField();
					}
				}
			}

			if (!hasErrors) {
				instance._saveOption();
			}
		});
	}

	_handleCancel() {
		this.emit('cancel');
	}

	_handleDeleteOption() {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-option'
				)
			)
		) {
			this._deleteOption();
		}
	}

	_deleteOption() {
		const form = this.element.querySelector('.option-detail form');

		form.querySelector('[name=' + this.namespace + 'cmd]').value = 'delete';

		var formData = new FormData(form);

		fetch(form.action, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((jsonResponse) => {
				this.emit('optionDeleted', jsonResponse);
			});
	}

	_saveOption() {
		const form = this.element.querySelector('.option-detail form');

		var formData = new FormData(form);

		fetch(form.action, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((jsonResponse) => {
				this.emit('optionSaved', jsonResponse);
			});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPOptionDetail.STATE = {
	cpOptionId: Config.string().required(),
	namespace: Config.string().required(),
	optionURL: Config.string().required(),
	pathThemeImages: Config.string().required(),
};

// Register component

Soy.register(CPOptionDetail, templates);

export default CPOptionDetail;
