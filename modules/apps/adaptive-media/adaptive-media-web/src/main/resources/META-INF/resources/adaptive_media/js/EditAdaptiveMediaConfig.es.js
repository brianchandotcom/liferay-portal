import core from 'metal';
import dom from 'metal-dom';
import { EventHandler } from 'metal-events';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * EditAdaptiveMediaConfig
 *
 * @abstract
 * @extends {PortletBase}
 */

class EditAdaptiveMediaConfig extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
		this.validInputKeyCodes_ = [8, 9, 13, 38, 40, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57];
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		let idOptions = this.one('#idOptions');

		if (idOptions) {
			this.eventHandler_.add(
				dom.delegate(
					idOptions,
					'change',
					'input[type="radio"]',
					(event) => this.onChangeUuidOptions_(event.delegateTarget)
				)
			);
		}

		let nameInput = this.one('#name');

		if (nameInput) {
			this.eventHandler_.add(nameInput.addEventListener('input', (event) => {
				this.updateUuid();
			}));
		}

		this.nameInput = nameInput;

		let maxWidthInput = this.one('#maxWidth');
		let maxHeightInput = this.one('#maxHeight');

		if (maxWidthInput) {
			this.eventHandler_.add(maxWidthInput.addEventListener('keydown', (event) => {
				this.handleKeyDown_(event);
			}));

			this.eventHandler_.add(maxWidthInput.addEventListener('input', (event) => {
				this.validateDimensions_(true);
			}));
		}

		if (maxHeightInput) {
			this.eventHandler_.add(maxHeightInput.addEventListener('keydown', (event) => {
				this.handleKeyDown_(event);
			}));

			this.eventHandler_.add(maxHeightInput.addEventListener('input', (event) => {
				this.validateDimensions_(true);
			}));
		}

		this.maxWidthInput = maxWidthInput;

		this.maxHeightInput = maxHeightInput;

		this.newUuidInput = this.one('#newUuid');

		let saveButton = this.one('button[type=submit]');

		this.eventHandler_.add(saveButton.addEventListener('click', (event) => {
			this.onSubmitForm_(event);
		}));
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Updates the uuid identifier based on the "name" field
	 * if the "Automatic" option is selected
	 */
	updateUuid() {
		let newUuidInput = this.newUuidInput;

		let uuidEmpty = !newUuidInput.value;

		if (this.isAutomaticUuid_() && (uuidEmpty || this._originalUuidChanged)) {
			newUuidInput.value = Liferay.Util.normalizeFriendlyURL(this.nameInput.value);
		}

		this._originalUuidChanged = true;
	}

	/**
	 * Prevents from introducing letters in the input field.
	 *
	 * @param {KeyboardEvent} event The keyboard event.
	 */
	handleKeyDown_(event) {
		let code = event.keyCode || event.charCode;

		if (this.validInputKeyCodes_.indexOf(code) == -1) {
			event.preventDefault();
		}
	}

	/**
	 * Returns if the "Automatic" check is selected
	 * @return {Boolean} whether the "Automatic" radiobutton is checked or not.
	 */
	isAutomaticUuid_() {
		return this.one('input:checked', '#idOptions').value === 'true';
	}

	/**
	 * Checks if the uuid identifier has a custom
	 * value or it has to be generated automatically.
	 *
	 * @protected
	 */
	onChangeUuidOptions_() {
		let newUuidInput = this.newUuidInput;

		if (this.isAutomaticUuid_()) {
			this._lastCustomUuuid = newUuidInput.value;

			this.updateUuid(this.nameInput.value);

			newUuidInput.setAttribute('disabled', true);
		}
		else {
			newUuidInput.value = this._lastCustomUuuid || newUuidInput.value;

			newUuidInput.removeAttribute('disabled');
		}
	}

	/**
	 * Checks if there are form errors before
	 * submitting the AMI.
	 *
	 * @param {Event} event The event that
	 * triggered the submit action.
	 * @protected
	 */
	onSubmitForm_(event) {
		this.validateDimensions_(false);

		let form = Liferay.Form.get(this.ns('fm'));

		form.formValidator.validate();

		if (form.formValidator.hasErrors()) {
			event.preventDefault();
		}
		else {
			submitForm(form.form);
		}
	}

	/**
	 * Checks if max-widht or max-height has a value.
	 *
	 * @param  {Boolean} validateFields whether the dimensions values
	 * have to be validated or not.
	 *
	 * @protected
	 */
	validateDimensions_(validateFields) {
		let form = Liferay.Form.get(this.ns('fm'));

		let nsMaxWidth = this.ns('maxWidth');
		let nsMaxHeight = this.ns('maxHeight');

		let inputErrorMessage = Liferay.Language.get('at-least-one-value-is-required');
		let STR_BLANK = ' ';

		if (this.maxWidthInput.value || this.maxHeightInput.value) {
			form.removeRule(nsMaxWidth, 'required');
			form.removeRule(nsMaxHeight, 'required');
		}
		else {
			form.addRule(nsMaxWidth, 'required', inputErrorMessage);
			form.addRule(nsMaxHeight, 'required', STR_BLANK);

			if (validateFields) {
				form.formValidator.validateField(nsMaxWidth);
				form.formValidator.validateField(nsMaxHeight);
			}
		}
	}
}

/**
 * EditAdaptiveMediaConfig State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
EditAdaptiveMediaConfig.STATE = {
	/**
	 * Node where errors will be rendered.
	 * @instance
	 * @memberof EditAdaptiveMediaConfig
	 * @type {String}
	 */
	errorNode: {
		validator: core.isString,
		value: '.error-wrapper'
	}
};

export default EditAdaptiveMediaConfig;