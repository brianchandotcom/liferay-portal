import Component from 'metal-component';
import { Config } from 'metal-state';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import { CancellablePromise } from 'metal-promise';
import { async, core } from 'metal';

import templates from './CPOptionValueDetail.soy';

/**
 * CPOptionValueDetail
 *
 */
class CPOptionValueDetail extends Component {

	constructor(opt_config, opt_parentElement) {
		super(opt_config, opt_parentElement)

		this.on('cpOptionValueIdChanged', this._handleCPOptionValueChange);
	}

	rendered() {
		this.loadOptionValueDetail(this.cpOptionValueId);
	}

	loadOptionValueDetail(cpOptionValueId) {
		var that = this;

		let optionValueDetail = this.element.querySelector('.option-value-detail');

		var url = new URL(this.optionValueURL);

		url.searchParams.append(this.namespace + "cpOptionValueId", cpOptionValueId);

		var promise = fetch(url, {
				credentials: 'include',
				method: 'GET'
			})
			.then(response => response.text())
			.then((text) => {

			optionValueDetail.innerHTML = text;

				globalEval.runScriptsInElement(optionValueDetail);

				var title = optionValueDetail.querySelector('#' + that.namespace + 'optionValueTitle');

				if (title) {
					title.addEventListener(
						'keyup',
						function(event) {
							var target = event.target;

							that.emit('titleChange', target.value);
						});
				}
			});
	}

	_handleCPOptionValueChange(event) {
		this.loadOptionValueDetail(event.newVal);

	}

	_handleSaveOptionValue() {
		var that = this;

		AUI().use(
			'aui-base', 'aui-form-validator', 'liferay-form',
			function(A) {
				var hasErrors = false;

				let form = that.element.querySelector('.option-value-detail form');

				var liferayForm = Liferay.Form.get(form.getAttribute("id"));

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
					that._saveOptionValue();
				}
			}
		);
	}

	_handleCancel() {
		this.emit('cancel');
	}

	_handleDeleteOptionValue() {
		if (confirm("Are you sure to delete?")) {
			this._deleteOptionValue();
		}
	}

	_deleteOptionValue() {
		let form = this.element.querySelector('.option-value-detail form');

		var formData = new FormData(form);

		formData.set(this.namespace + 'cmd', 'delete');

		var promise = fetch(form.action, {
				body: formData,
				credentials: 'include',
				method: 'POST',
			})
			.then(response => response.json())
			.then((jsonResponse) => {
					this.emit('optionValueDelated', jsonResponse);
			});
	}

	_saveOptionValue() {
		let form = this.element.querySelector('.option-value-detail form');

		var formData = new FormData(form);

		formData.set(this.namespace + 'cpOptionId', this.cpOptionId);

		var promise = fetch(form.action, {
				body: formData,
				credentials: 'include',
				method: 'POST',
			})
				.then(response => response.json())
		.then((jsonResponse) => {
				this.emit('optionValueSaved', jsonResponse);
		});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
CPOptionValueDetail.STATE = {
	cpOptionId: Config.string().required(),
	cpOptionValueId: Config.string().required(),
	namespace: Config.string().required(),
	optionValueURL: Config.string().required(),
	pathThemeImages: Config.string().required()
};

// Register component
Soy.register(CPOptionValueDetail, templates);

export default CPOptionValueDetail;