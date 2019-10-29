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
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {delegate, on} from 'metal-dom';
import {EventHandler} from 'metal-events';

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

		this._localeChangedHandler = Liferay.after(
			'inputLocalized:localeChanged',
			this._onLocaleChange.bind(this)
		);

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
		this._localeChangedHandler.detach();
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
	 * Updates description and title values on locale changed
	 * @param {Event} event
	 */
	_onLocaleChange(event) {
		const defaultLanguageId = themeDisplay.getDefaultLanguageId();
		const selectedLanguageId = event.item.getAttribute('data-value');

		if (selectedLanguageId) {
			this._updateLocalizableInput(
				'descriptionMapAsXML',
				defaultLanguageId,
				selectedLanguageId
			);

			this._updateLocalizableInput(
				'titleMapAsXML',
				defaultLanguageId,
				selectedLanguageId
			);

			this._updateLanguageIdInput(selectedLanguageId);
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
					? '/journal/update_ddm_structure_default_values'
					: '/journal/add_ddm_structure_default_values';
			} else {
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

	/**
	 * @private
	 */
	_updateLanguageIdInput(selectedLanguageId) {
		const languageIdInput = document.getElementById(this.ns('languageId'));

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
		const inputComponent = Liferay.component(this.ns(name));

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

				eventHandler.detach();
			}
		}
	}
}

export {JournalPortlet};
export default JournalPortlet;
