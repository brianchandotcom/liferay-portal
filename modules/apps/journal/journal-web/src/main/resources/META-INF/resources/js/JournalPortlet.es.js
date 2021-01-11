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

import {PortletBase, delegate, openToast} from 'frontend-js-web';
import {Config} from 'metal-state';

import {LocaleChangedHandler} from './LocaleChangedHandler.es';

const ACTION_INPUT_NAME = 'javax-portlet-action';

const BUTTON_ROW_CLASS = '.journal-article-button-row';

const SIDEBAR_VISIBLE_CLASS = 'contextual-sidebar-visible';

/**
 * JournalPortlet
 *
 * @abstract
 * @extends {PortletBase}
 */
class JournalPortlet extends PortletBase {

	/**
	 * @inheritDoc
	 */
	attached() {
		const buttonRow = this.one(BUTTON_ROW_CLASS);

		this._buttonClickUpdateAction = delegate(
			buttonRow,
			'click',
			'button',
			this._updateAction
		);

		const form = this._getInputByName(this.ns('fm1'));

		form.addEventListener('submit', this._onFormSubmit);

		const resetValuesButton = this._getInputByName(
			this.ns('resetValuesButton')
		);

		if (resetValuesButton) {
			resetValuesButton.addEventListener(
				'click',
				this._resetValuesDDMStructure
			);
		}

		this._localeChangedHandler = new LocaleChangedHandler({
			contentTitle: this.contentTitle,
			context: this,
			defaultLanguageId: this.defaultLanguageId,
			namespace: this.namespace,
			onDefaultLocaleChangedCallback: (languageId) => {
				this.defaultLanguageId = languageId;
			},
			onLocaleChangedCallback: this._onLocaleChanged,
		});

		this._selectedLanguageId = this.defaultLanguageId;

		this._setupSidebar();
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this._onContextualSidebarButtonClick = this._onContextualSidebarButtonClick.bind(
			this
		);
		this._onFormSubmit = this._onFormSubmit.bind(this);
		this._onLocaleChanged = this._onLocaleChanged.bind(this);
		this._resetValuesDDMStructure = this._resetValuesDDMStructure.bind(
			this
		);
		this._saveArticle = this._saveArticle.bind(this);
		this._updateAction = this._updateAction.bind(this);
		this._validTitle = this._validTitle.bind(this);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this._buttonClickUpdateAction.dispose();

		const form = this._getInputByName(this.ns('fm1'));

		if (form) {
			form.removeEventListener('submit', this._onFormSubmit);
		}

		const resetValuesButton = this._getInputByName(
			this.ns('resetValuesButton')
		);

		if (resetValuesButton) {
			resetValuesButton.removeEventListener(
				'click',
				this._resetValuesDDMStructure
			);
		}

		const contextualSidebarButton = document.getElementById(
			this.ns('contextualSidebarButton')
		);

		if (contextualSidebarButton) {
			contextualSidebarButton.removeEventListener(
				'click',
				this._onContextualSidebarButtonClick
			);
		}

		this._localeChangedHandler.detachLocaleChangedEventListener();
	}

	/**
	 * Clean the input if the language is not considered translated when
	 * submitting the form
	 * @param {string} name of the input
	 */
	_cleanInputIfNeeded(name) {
		const inputComponent = Liferay.component(this.ns(name));
		const translatedLanguages = inputComponent.get('translatedLanguages');

		if (
			!translatedLanguages.has(this._selectedLanguageId) &&
			this._selectedLanguageId !== this.defaultLanguageId
		) {
			inputComponent.updateInput('');

			const form = Liferay.Form.get(this.ns('fm1'));

			form.removeRule(this.ns(name), 'required');
		}
	}

	/**
	 * Query an input by its name
	 * @param {string} name
	 * @private
	 */
	_getInputByName(name) {
		return document.getElementById(this.ns(name));
	}

	/**
	 * @private
	 */
	_onContextualSidebarButtonClick() {
		const contextualSidebarContainer = document.getElementById(
			this.ns('contextualSidebarContainer')
		);

		if (contextualSidebarContainer) {
			contextualSidebarContainer.classList.toggle(SIDEBAR_VISIBLE_CLASS);
		}
	}

	/**
	 * @private
	 */
	_onFormSubmit(event) {
		event.preventDefault();

		if (!this._validTitle()) {
			this._showAlert(
				Liferay.Util.sub(
					Liferay.Language.get(
						'please-enter-a-valid-title-for-the-default-language-x'
					),
					this.defaultLanguageId.replace('_', '-')
				)
			);

			event.stopImmediatePropagation();

			return;
		}

		const actionInput = this._getInputByName(ACTION_INPUT_NAME);

		const actionName = actionInput.value;

		this._saveArticle(actionName);
	}

	/**
	 * @private
	 */
	_onLocaleChanged({availableLocales}, languageId) {
		if (!availableLocales.includes(languageId)) {
			availableLocales.push(languageId);
		}

		this._selectedLanguageId = languageId;
	}

	/**
	 * @private
	 */
	_resetValuesDDMStructure(event) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-reset-the-default-values'
				)
			)
		) {
			const button = event.currentTarget;

			submitForm(document.hrefFm, button.dataset.url);
		}
	}

	/**
	 * Prepare action and articleId inputs to submit form
	 * @param {string} actionName
	 */
	_saveArticle(actionName) {
		if (actionName === 'publish') {
			const workflowActionInput = this._getInputByName('workflowAction');

			workflowActionInput.value = Liferay.Workflow.ACTION_PUBLISH;

			actionName = null;
		}

		if (!actionName) {
			if (this.classNameId && this.classNameId !== '0') {
				actionName = this.articleId
					? '/journal/update_data_engine_default_values'
					: '/journal/add_data_engine_default_values';
			}
			else {
				actionName = this.articleId
					? '/journal/update_article'
					: '/journal/add_article';
			}
		}

		this._setActionName(actionName);

		this._setAvailableLocales(this.availableLocales);

		if (!this.articleId) {
			const articleIdInput = this._getInputByName('articleId');

			const newArticleIdInput = this._getInputByName('newArticleId');

			articleIdInput.value = newArticleIdInput.value;
		}

		const form = this._getInputByName(this.ns('fm1'));

		this._cleanInputIfNeeded('titleMapAsXML');
		this._cleanInputIfNeeded('descriptionMapAsXML');

		submitForm(form);
	}

	/**
	 * Set the action name in the corresponding input
	 * @param {string} actionName
	 */
	_setActionName(actionName) {
		const actionInput = this._getInputByName(ACTION_INPUT_NAME);

		actionInput.value = actionName;
	}

	_setAvailableLocales(availableLocales) {
		const availableLocalesInput = this._getInputByName(
			this.ns('availableLocales')
		);

		availableLocalesInput.value = availableLocales;
	}

	/**
	 * @private
	 */
	_setupSidebar() {
		const contextualSidebarButton = document.getElementById(
			this.ns('contextualSidebarButton')
		);

		const contextualSidebarContainer = document.getElementById(
			this.ns('contextualSidebarContainer')
		);

		if (
			contextualSidebarContainer &&
			window.innerWidth > Liferay.BREAKPOINTS.PHONE
		) {
			contextualSidebarContainer.classList.add(SIDEBAR_VISIBLE_CLASS);
		}

		if (contextualSidebarButton) {
			contextualSidebarButton.addEventListener(
				'click',
				this._onContextualSidebarButtonClick
			);
		}
	}

	/**
	 * @private
	 */
	_showAlert(message) {
		let alertContainer = document.querySelector('.journal-alert-container');

		if (!alertContainer) {
			alertContainer = document.createElement('div');

			const content = document.querySelector('.article-content-content');

			content.prepend(alertContainer);
		}

		openToast({
			autoClose: false,
			container: alertContainer,
			message,
			type: 'danger',
		});
	}

	/**
	 * Change the portlet action based on the button clicked
	 * @param {Event} event
	 * @private
	 */
	_updateAction(event) {
		const button = event.delegateTarget;

		const actionName = button.dataset.actionname;

		if (actionName) {
			this._setActionName(actionName);
		}
	}

	/**
	 * @private
	 */
	_validTitle() {
		if (!this.classNameId || this.classNameId === '0') {
			const inputComponent = Liferay.component(this.ns('titleMapAsXML'));

			const value = inputComponent.getValue(this.defaultLanguageId);

			if (!value) {
				return false;
			}
		}

		return true;
	}
}

JournalPortlet.STATE = {
	_selectedLanguageId: Config.internal().string(),
	articleId: Config.string(),
	availableLocales: Config.array(),
	classNameId: Config.string(),
	contentTitle: Config.string(),
	defaultLanguageId: Config.string(),
};

export {JournalPortlet};
export default JournalPortlet;
