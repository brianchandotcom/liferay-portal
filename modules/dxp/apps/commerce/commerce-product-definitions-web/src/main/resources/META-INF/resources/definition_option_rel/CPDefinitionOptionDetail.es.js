import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import {globalEval} from 'metal-dom';

import templates from './CPDefinitionOptionDetail.soy';

/**
 * CPDefinitionOptionDetail
 *
 */

class CPDefinitionOptionDetail extends Component {

	created() {
		this.on('cpDefinitionOptionRelIdChanged', this._handleCPDefinitionOptionChange);
	}

	rendered() {
		this.loadOptionDetail(this.cpDefinitionOptionRelId);
	}

	loadOptionDetail(cpDefinitionOptionRelId) {
		var instance = this;

		let optionDetail = this.refs['option-detail'];

		var url = new URL(this.optionURL);

		url.searchParams.append(this.namespace + 'cpDefinitionOptionRelId', cpDefinitionOptionRelId);

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
				optionDetail.innerHTML = text;

				globalEval.runScriptsInElement(optionDetail);
			}
		);
	}

	_handleCPDefinitionOptionChange(event) {
		this.loadOptionDetail(event.newVal);
	}

	_handleSaveOption() {
		var instance = this;

		AUI().use(
			'aui-base',
			'aui-form-validator',
			'liferay-form',
			(A) => {
				var hasErrors = false;

				let form = instance.element.querySelector('.option-detail form');

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
			}
		);
	}

	_handleCancel() {
		this.emit('cancel');
	}

	_handleDeleteOption() {
		if (confirm('Are you sure to delte?')) {
			this._deleteOption();
		}
	}

	_deleteOption() {
		let form = this.element.querySelector('.option-detail form');

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
				this.emit('optionDeleted', jsonResponse);
			}
		);
	}

	_saveOption() {
		let form = this.element.querySelector('.option-detail form');

		var formData = new FormData(form);

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
				this.emit('optionSaved', jsonResponse);
			}
		);
	}

}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPDefinitionOptionDetail.STATE = {
	cpDefinitionOptionRelId: Config.string().required(),
	namespace: Config.string().required(),
	optionURL: Config.string().required(),
	pathThemeImages: Config.string().required()
};

// Register component

Soy.register(CPDefinitionOptionDetail, templates);

export default CPDefinitionOptionDetail;