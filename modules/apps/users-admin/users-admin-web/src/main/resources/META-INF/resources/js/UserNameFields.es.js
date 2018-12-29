import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import {Config} from 'metal-state';

class UserNameFields extends PortletBase {

	/**
	 * @inheritDoc
	 */
	attached() {
		this.eventHandler_.add(
			dom.on(this.selectNode, 'change', this._handleSelectChange.bind(this))
		);
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();

		this.formDataCache_ = {};
		this.loadingAnimationMarkupText_ = `<div class="loading-animation" id="${this.portletNamespace}loadingUserNameFields"></div>`;
		this.maxLengthsCache_ = {};
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Updates the user name fields to display the appropriate fields for the
	 * given locale.
	 * @param {string} languageId The language id to retrieve user name fields
	 * for.
	 */
	updateUserNameFields(languageId) {
		this._setUp();

		this._getURL(languageId)
			.then(fetch)
			.then((response) => response.text())
			.then(this._insertUserNameFields.bind(this))
			.then(this._cleanUp.bind(this))
			.catch(this._handleError.bind(this));
	}

	/**
	 * Caches the values and maxLength attribute values from the current
	 * username fields.
	 * @private
	 */
	_cacheData() {
		for (const [name, value] of new FormData(this.formNode)) {
			const field = this.userNameFieldsNode.querySelector('#' + name);

			if (field) {
				this.formDataCache_[name] = value;

				if (field.hasAttribute('maxLength')) {
					this.maxLengthsCache_[name] = field.getAttribute('maxLength');
				}
			}
		}
	}

	/**
	 * Inserts a loading indicator into the dom before the user name fields,
	 * then hides the user name fields.
	 * @private
	 */
	_createLoadingIndicator() {
		this.userNameFieldsNode.insertAdjacentHTML(
			'beforebegin',
			this.loadingAnimationMarkupText_
		);

		dom.addClasses(this.userNameFieldsNode, 'hide');
	}

	_cleanUp() {
		this._removeLoadingIndicator();
		this._populateData();
	}

	/**
	 * Returns a promise containing the URL to be used to retrieve the user name
	 * fields.
	 * @param {string} languageId The language id to be set on the URL.
	 * @return {Promise} A promise to be resolved with the constructed URL
	 * @async
	 * @private
	 */
	_getURL(languageId) {
		return new Promise((resolve) => {
			AUI().use(
				'liferay-portlet-url',
				(A) => {
					const url = Liferay.PortletURL.createURL(this.baseURL);

					url.setParameter('languageId', languageId);

					resolve(url);
				}
			);
		});
	}

	/**
	 * Logs any error in the promise chain and removes the loading indicator.
	 * @param {Error} error The error object
	 * @private
	 */
	_handleError(error) {
		console.error(error);

		this._removeLoadingIndicator();
	}

	/**
	 * Handles the change event when selecting a new language.
	 * @param {Event} event The event object.
	 * @private
	 */
	_handleSelectChange(event) {
		this.updateUserNameFields(event.currentTarget.value);
	}

	/**
	 * Replaces the HTML of the user name fields with the given HTML.
	 * @param {string} markupText The markup text used to create and insert the
	 * new user name fields.
	 * @private
	 */
	_insertUserNameFields(markupText) {
		const temp = document.implementation.createHTMLDocument();

		temp.body.innerHTML = markupText;

		const newUserNameFields = temp.getElementById(`${this.portletNamespace}userNameFields`);

		if (newUserNameFields) {
			this.userNameFieldsNode.innerHTML = newUserNameFields.innerHTML;
		}
	}

	/**
	 * Sets the values and max length attributes of the current user name fields
	 * with the data cached in this._cacheData.
	 * @private
	 */
	_populateData() {
		for (const [name, value] of Object.entries(this.formDataCache_)) {
			const newField = this.userNameFieldsNode.querySelector('#' + name);

			if (newField) {
				newField.value = value;

				if (this.maxLengthsCache_.hasOwnProperty(name)) {
					newField.setAttribute('maxLength', this.maxLengthsCache_[name]);
				}
			}
		}
	}

	/**
	 * Removes the loading indicator from the dom and shows the user name
	 * fields.
	 * @private
	 */
	_removeLoadingIndicator() {
		dom.exitDocument(this.one('#loadingUserNameFields'));

		dom.removeClasses(this.userNameFieldsNode, 'hide');
	}

	_setUp() {
		this._cacheData();
		this._createLoadingIndicator();
	}
}

UserNameFields.STATE = {
	baseURL: Config.required().string().writeOnce(),
	formNode: Config.required().setter(dom.toElement).writeOnce(),
	selectNode: Config.required().setter(dom.toElement).writeOnce(),
	userNameFieldsNode: Config.required().setter(dom.toElement).writeOnce()
};

export default UserNameFields;