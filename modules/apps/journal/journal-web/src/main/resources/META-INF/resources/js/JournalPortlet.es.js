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

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {delegate, on} from 'metal-dom';
import {EventHandler} from 'metal-events';
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

		this._eventHandler.add(
			delegate(
				buttonRow,
				'click',
				'button',
				this._updateAction.bind(this)
			)
		);

		const form = this._getInputByName(this.ns('fm1'));

		this._eventHandler.add(
			on(form, 'submit', this._onFormSubmit.bind(this))
		);

		const resetValuesButton = this._getInputByName(
			this.ns('resetValuesButton')
		);

		if (resetValuesButton) {
			this._eventHandler.add(
				on(
					resetValuesButton,
					'click',
					this._resetValuesDDMStructure.bind(this)
				)
			);
		}

		this._localeChangedHandler = new LocaleChangedHandler({
			callback: this._onLocaleChanged,
			contentTitle: this.contentTitle,
			context: this,
			defaultLanguageId: this.defaultLanguageId,
			namespace: this.namespace,
		});

		this._selectedLanguageId = this.defaultLanguageId;

		this._setupSidebar();
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this._eventHandler = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this._eventHandler.removeAllListeners();
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
	_onFormSubmit(event) {
		event.preventDefault();

		const actionInput = this._getInputByName(ACTION_INPUT_NAME);

		const actionName = actionInput.value;

		this._saveArticle(actionName);
	}

	/**
	 * @private
	 */
	_onLocaleChanged(context, languageId) {
		const {availableLocales} = context;

		if (!availableLocales.includes(languageId)) {
			availableLocales.push(languageId);
		}
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
		const articleId = this._getInputByName('articleId').value;

		if (actionName === 'publish') {
			const workflowActionInput = this._getInputByName('workflowAction');

			workflowActionInput.value = Liferay.Workflow.ACTION_PUBLISH;

			actionName = null;
		}

		if (!actionName) {
			const classNameId = this._getInputByName('classNameId').value;

			if (classNameId > 0) {
				actionName = articleId
					? '/journal/update_data_engine_default_values'
					: '/journal/add_data_engine_default_values';
			}
			else {
				actionName = articleId
					? '/journal/update_article'
					: '/journal/add_article';
			}
		}

		this._setActionName(actionName);

		if (!articleId) {
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
			this._eventHandler.add(
				on(contextualSidebarButton, 'click', () => {
					contextualSidebarContainer.classList.toggle(
						SIDEBAR_VISIBLE_CLASS
					);
				})
			);
		}
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
}

JournalPortlet.STATE = {
	_selectedLanguageId: Config.internal().string(),
	availableLocales: Config.array(),
	contentTitle: Config.string(),
	defaultLanguageId: Config.string(),
};

export {JournalPortlet};
export default JournalPortlet;
