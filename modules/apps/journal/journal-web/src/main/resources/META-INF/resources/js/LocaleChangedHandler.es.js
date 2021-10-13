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

import {AOP} from 'frontend-js-web';

class LocaleChangedHandler {
	constructor({
		contentTitle,
		context,
		defaultLanguageId,
		namespace,
		onDefaultLocaleChangedCallback,
		onLocaleChangedCallback,
	}) {
		this.contentTitle = contentTitle;
		this.defaultLanguageId = defaultLanguageId;
		this.namespace = namespace;
		this.onDefaultLocaleChangedCallback = onDefaultLocaleChangedCallback;
		this.onLocaleChangedCallback = onLocaleChangedCallback;
		this.context = context;

		this.attach();
	}

	attach() {
		this._defaultLocaleChangedHandler = Liferay.after(
			'inputLocalized:defaultLocaleChanged',
			this._onDefaultLocaleChange.bind(this)
		);

		this._localeChangedHandler = Liferay.after(
			'inputLocalized:localeChanged',
			this._onLocaleChange.bind(this)
		);
	}

	detach() {
		this._defaultLocaleChangedHandler.detach();
		this._localeChangedHandler.detach();
	}

	/**
	 * Updates defaultLocale
	 * @param {Event} event
	 */
	_onDefaultLocaleChange(event) {
		if (event.item) {
			this.defaultLanguageId = event.item.getAttribute('data-value');

			if (this.onDefaultLocaleChangedCallback) {
				this.onDefaultLocaleChangedCallback(this.defaultLanguageId);
			}
		}
	}

	/**
	 * Updates description and title values on locale changed
	 * @param {Event} event
	 */
	_onLocaleChange(event) {
		const selectedLanguageId = event.item.getAttribute('data-value');

		this._selectedLanguageId = selectedLanguageId;

		if (selectedLanguageId) {
			this._updateLocalizableInput(
				'descriptionMapAsXML',
				this.defaultLanguageId,
				selectedLanguageId
			);

			this._updateLocalizableInput(
				this.contentTitle,
				this.defaultLanguageId,
				selectedLanguageId
			);

			this._updateLanguageIdInput(selectedLanguageId);
		}

		if (this.onLocaleChangedCallback) {
			this.onLocaleChangedCallback(this.context, selectedLanguageId);
		}
	}

	/**
	 * @private
	 */
	_updateLanguageIdInput(selectedLanguageId) {
		const languageIdInput = document.getElementById(
			this.namespace + 'languageId'
		);

		languageIdInput.value = selectedLanguageId;
	}

	/**
	 * Updates the localized input with the default language's translation
	 * when there is not translation for the selected language
	 * @param {string} name
	 * @param {string} defaultLanguageId
	 * @param {string} selectedLanguageId
	 * @private
	 */
	_updateLocalizableInput(name, defaultLanguageId, selectedLanguageId) {
		const inputComponent = Liferay.component(this.namespace + name);

		if (inputComponent) {
			const inputSelectedValue = inputComponent.getValue(
				selectedLanguageId
			);

			if (inputSelectedValue === '') {
				const inputDefaultValue = inputComponent.getValue(
					defaultLanguageId
				);

				// LPS-92493

				const eventHandler = AOP.before(
					() => AOP.prevent(),
					inputComponent,
					'updateInputLanguage'
				);

				inputComponent.selectFlag(selectedLanguageId);
				inputComponent.updateInput(inputDefaultValue);

				// setInterval declared in ckeditor.jsp is triggering
				// the updateInputLanguage function, so with this
				// we guarantee that this function is not called

				setTimeout(() => {
					eventHandler.detach();
				}, 400);
			}
		}
	}
}

export {LocaleChangedHandler};
export default LocaleChangedHandler;
