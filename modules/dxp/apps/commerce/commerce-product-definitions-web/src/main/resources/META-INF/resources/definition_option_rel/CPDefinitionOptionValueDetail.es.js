import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import {globalEval} from 'metal-dom';

import templates from './CPDefinitionOptionValueDetail.soy';

/**
 * CPDefinitionOptionValueDetail
 *
 */

class CPDefinitionOptionValueDetail extends Component {

	constructor(opt_config, opt_parentElement) {
		super(opt_config, opt_parentElement);

		this.on('cpDefinitionOptionValueRelIdChanged', this._handleCPDefinitionOptionValueChange);
	}

	rendered() {
		this.loadOptionValueDetail(this.cpDefinitionOptionValueRelId);
	}

	loadOptionValueDetail(cpDefinitionOptionValueRelId) {
		var instance = this;

		let optionValueDetail = this.element.querySelector('.option-value-detail');

		var url = new URL(this.cpDefinitionOptionValueRelURL);

		url.searchParams.append(this.namespace + 'cpDefinitionOptionValueRelId', cpDefinitionOptionValueRelId);

		fetch(
			url,
			{
				credentials: 'include',
				method: 'GET'
			}
		).then(
			response => response.text()
		).then(
			(text) => {
				optionValueDetail.innerHTML = text;

				globalEval.runScriptsInElement(optionValueDetail);

				var name = optionValueDetail.querySelector('#' + instance.namespace + 'optionValueName');

				if (name) {
					name.addEventListener(
						'keyup',
						(event) => {
							var target = event.target;

							instance.emit('nameChange', target.value);
						}
					);
				}
			}
		);
	}

	_handleCPDefinitionOptionValueChange(event) {
		this.loadOptionValueDetail(event.newVal);
	}

	_handleSaveOptionValue() {
		var instance = this;

		AUI().use(
			'aui-base',
			'aui-form-validator',
			'liferay-form',
			(A) => {
				var hasErrors = false;

				let form = instance.element.querySelector('.option-value-detail form');

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
					instance._saveOptionValue();
				}
			}
		);
	}

	_handleCancel() {
		this.emit('cancel');
	}

	_handleDeleteOptionValue() {
		if (confirm('Are you sure to delte?')) {
			this._deleteOptionValue();
		}
	}

	_deleteOptionValue() {
		let form = this.element.querySelector('.option-value-detail form');

		var formData = new FormData(form);

		formData.set(this.namespace + 'cmd', 'delete');

		fetch(
			form.action,
			{
				body: formData,
				credentials: 'include',
				method: 'POST'
			}
		).then(
			response => response.json()
		).then(
			(jsonResponse) => {
				this.emit('optionValueDelated', jsonResponse);
			}
		);
	}

	_saveOptionValue() {
		let form = this.element.querySelector('.option-value-detail form');

		var formData = new FormData(form);

		formData.set(this.namespace + 'cpDefinitionOptionRelId', this.cpDefinitionOptionRelId);

		fetch(
			form.action,
			{
				body: formData,
				credentials: 'include',
				method: 'POST'
			}
		).then(
			response => response.json()
		).then(
			(jsonResponse) => {
				this.emit('optionValueSaved', jsonResponse);
			}
		);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPDefinitionOptionValueDetail.STATE = {
	cpDefinitionOptionRelId: Config.string().required(),
	cpDefinitionOptionValueRelId: Config.string().required(),
	cpDefinitionOptionValueRelURL: Config.string().required(),
	namespace: Config.string().required(),
	pathThemeImages: Config.string().required()
};

// Register component

Soy.register(CPDefinitionOptionValueDetail, templates);

export default CPDefinitionOptionValueDetail;