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

import 'clay-dropdown';

import 'clay-modal';
import {CompatibilityEventProxy} from 'frontend-js-web';
import {core} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './TranslationManager.soy';

/**
 * TranslationManager
 *
 * This class adds functionallity to manage existing language options, and
 * create new ones.
 * @review
 */
class TranslationManager extends Component {
	/**
	 * @inheritDoc
	 */
	constructor(opt_config, opt_element) {
		super(opt_config, opt_element);

		this.resetEditingLocale_();

		this.startCompatibility_();
	}

	/**
	 * Add a language to the available locales list and set it as the
	 * current editing language.
	 * @param  {MouseEvent} event
	 * @review
	 */
	addLocale(event) {
		const locale = event.data.item;

		if (this.availableLocales.indexOf(locale) === -1) {
			this.availableLocales.push(locale);
		}

		this.availableLocales = this.availableLocales;

		this.editingLocale = locale.id;
	}

	/**
	 * Registers another EventTarget as a bubble target.
	 * @param  {!Object} target YUI component where events will be emited to
	 * @review
	 */
	addTarget(target) {
		this.compatibilityEventProxy_.addTarget(target);
	}

	/**
	 * Change the default language.
	 * @param  {MouseEvent} event
	 * @review
	 */
	changeDefaultLocale(event) {
		const localeId = event.delegateTarget.getAttribute('data-locale-id');

		this.defaultLocale = localeId;

		this.editingLocale = localeId;
	}

	/**
	 * Change current editing language.
	 * @param  {MouseEvent} event
	 * @review
	 */
	changeLocale(event) {
		const localeId = event.delegateTarget.getAttribute('data-locale-id');

		this.editingLocale = localeId;
	}

	/**
	 * Returns a property.
	 * @param  {String} attr Name of the attribute wanted to get
	 * @review
	 */
	get(attr) {
		return this[attr];
	}

	/**
	 * Remove a language from the available locales list and reset the current
	 * editing language to default if removed one was selected.
	 * @param  {MouseEvent} event
	 * @review
	 */
	removeAvailableLocale({delegateTarget}) {
		const {availableLocales} = this;
		const {localeId} = delegateTarget.dataset;

		window.event.stopPropagation();

		this.refs.deleteModal.events = {
			clickButton: ({target}) => {
				if (target.classList.contains('btn-primary')) {
					this.refs.deleteModal.emit('hide');

					this.availableLocales = availableLocales.filter(
						({id}) => id !== localeId
					);

					if (localeId === this.editingLocale) {
						this.resetEditingLocale_();
					}

					this.emit('deleteAvailableLocale', {
						locale: localeId
					});
				}
			}
		};

		this.refs.deleteModal.show();
	}

	/**
	 * Set the current editing locale to the default locale.
	 * @private
	 * @review
	 */
	resetEditingLocale_() {
		this.editingLocale = this.defaultLocale;
	}

	/**
	 * Configuration to emit yui-based events to maintain
	 * backwards compatibility.
	 * @private
	 * @review
	 */
	startCompatibility_() {
		this.destroy = this.dispose;

		this.compatibilityEventProxy_ = new CompatibilityEventProxy({
			host: this,
			namespace: 'translationmanager'
		});
	}
}

/**
 * State definition.
 * @ignore
 * @review
 * @static
 * @type {!Object}
 */
TranslationManager.STATE = {
	/**
	 * List of available languages keys.
	 * @review
	 * @type {Array.<Object>}
	 */
	availableLocales: {
		validator: core.isArray
	},

	/**
	 * Indicates if the default language is editable or not.
	 * @review
	 * @type {Boolean}
	 */
	changeableDefaultLanguage: {
		validator: core.isBoolean
	},

	/**
	 * Default language key.
	 * @review
	 * @type {String}
	 */
	defaultLocale: {
		validator: core.isString
	},

	/**
	 * Current editing language key.
	 * @review
	 * @type {String}
	 */
	editingLocale: {
		validator: core.isString
	},

	/**
	 * Map of all languages
	 * @review
	 * @type {Object}
	 */
	locales: {
		validator: core.isObject
	}
};

Soy.register(TranslationManager, templates);

export default TranslationManager;
