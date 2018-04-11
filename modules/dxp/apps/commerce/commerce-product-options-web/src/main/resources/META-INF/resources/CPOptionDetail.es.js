import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import {globalEval} from 'metal-dom';

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

		let optionDetail = this.refs['option-detail'];

		var url = new URL(this.optionURL);

		url.searchParams.append(this.namespace + 'cpOptionId', cpOptionId);

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

				var title = optionDetail.querySelector('#' + instance.namespace + 'title');

				if (title) {
					title.addEventListener(
						'keyup',
						(event) => {
							var target = event.target;

							instance.emit('titleChange', target.value);
						}
					);
				}
			}
		);
	}

	_handleCPOptionChange(event) {
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
		if (confirm('Are you sure to delete?')) {
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

CPOptionDetail.STATE = {
	cpOptionId: Config.string().required(),
	namespace: Config.string().required(),
	optionURL: Config.string().required(),
	pathThemeImages: Config.string().required()
};

// Register component

Soy.register(CPOptionDetail, templates);

export default CPOptionDetail;