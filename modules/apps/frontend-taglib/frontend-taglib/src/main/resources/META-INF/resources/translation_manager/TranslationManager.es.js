import 'frontend-js-web/liferay/compat/dropdown/Dropdown.es';
import CompatibilityEventProxy from 'frontend-js-web/liferay/CompatibilityEventProxy.es'
import Component from 'metal-component';
import Soy from 'metal-soy';
import { core } from 'metal';

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
		let localeId = event.delegateTarget.getAttribute('data-locale-id');

		if (this.availableLocales.indexOf(localeId) === -1) {
			this.availableLocales.push(localeId);
		}

		this.availableLocales = this.availableLocales;

		this.editingLocale = localeId;
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
		let localeId = event.delegateTarget.getAttribute('data-locale-id');

		this.defaultLocale = localeId;

		this.editingLocale = localeId;
	}

	/**
	 * Change current editing language.
	 * @param  {MouseEvent} event
	 * @review
	 */
	changeLocale(event) {
		let localeId = event.delegateTarget.getAttribute('data-locale-id');

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
	removeAvailableLocale(event) {
		let localeId = event.delegateTarget.getAttribute('data-locale-id');

		let localePosition = this.availableLocales.indexOf(localeId);

		this.availableLocales.splice(localePosition, 1);

		this.availableLocales = this.availableLocales;

		if (localeId === this.editingLocale) {
			this.resetEditingLocale_();
		}

		this.emit(
			'deleteAvailableLocale',
			{
				locale: localeId
			}
		);
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

		this.compatibilityEventProxy_ = new CompatibilityEventProxy(
			{
				host: this,
				namespace: 'translationmanager'
			}
		);
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
	 * Current editing language key.
	 * @review
	 * @type {String}
	 */
	editingLocale: {
		validator: core.isString
	},

	/**
	 * List of available languages keys.
	 * @review
	 * @type {Array.<String>}
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