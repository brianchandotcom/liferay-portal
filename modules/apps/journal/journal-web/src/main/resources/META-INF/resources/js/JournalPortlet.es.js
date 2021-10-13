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

import {delegate, fetch, navigate, openToast} from 'frontend-js-web';

import {LocaleChangedHandler} from './LocaleChangedHandler.es';

export default function _JournalPortlet({
	articleId: initialArticleId,
	availableLocales: initialAvailableLocales,
	classNameId,
	contentTitle,
	defaultLanguageId: initialDefaultLanguageId,
	namespace,
}) {
	const formId = `${namespace}fm1`;

	const actionInput = document.getElementById(
		`${namespace}javax-portlet-action`
	);
	const buttonRow = document.querySelector('.journal-article-button-row');
	const contextualSidebarButton = document.getElementById(
		`${namespace}contextualSidebarButton`
	);
	const contextualSidebarContainer = document.getElementById(
		`${namespace}contextualSidebarContainer`
	);
	const resetValuesButton = document.getElementById(
		`${namespace}resetValuesButton`
	);

	const availableLocales = [...initialAvailableLocales];

	let articleId = initialArticleId;
	let defaultLanguageId = initialDefaultLanguageId;
	let selectedLanguageId = initialDefaultLanguageId;

	const handleContextualSidebarButtonClick = () => {
		contextualSidebarContainer?.classList.toggle(
			'contextual-sidebar-visible'
		);
	};

	const handleDDMFormError = (error) => {
		buttonRow.disabled = false;
		console.error(error);
	};

	const handleDDMFormValid = () => {
		const titleInputComponent = Liferay.component(
			`${namespace}titleMapAsXML`
		);

		const isValidTitle =
			(classNameId && classNameId !== '0') ||
			titleInputComponent.getValue(defaultLanguageId);

		if (isValidTitle) {
			if (actionInput.value === 'publish') {
				const workflowActionInput = document.getElementById(
					`${namespace}workflowAction`
				);

				workflowActionInput.value = Liferay.Workflow.ACTION_PUBLISH;

				if (classNameId && classNameId !== '0') {
					actionInput.value = articleId
						? '/journal/update_data_engine_default_values'
						: '/journal/add_data_engine_default_values';
				}
				else {
					actionInput.value = articleId
						? '/journal/update_article'
						: '/journal/add_article';
				}
			}

			if (!articleId) {
				const articleIdInput = document.getElementById(
					`${namespace}articleId`
				);
				const newArticleIdInput = document.getElementById(
					`${namespace}newArticleId`
				);

				articleId = newArticleIdInput.value;
				articleIdInput.value = articleId;
			}

			const availableLocalesInput = document.getElementById(
				`${namespace}availableLocales`
			);
			const descriptionInputComponent = Liferay.component(
				`${namespace}descriptionMapAsXML`
			);

			availableLocalesInput.value = availableLocales;

			[titleInputComponent, descriptionInputComponent].forEach(
				(inputComponent) => {
					const translatedLanguages = inputComponent.get(
						'translatedLanguages'
					);

					if (
						!translatedLanguages.has(selectedLanguageId) &&
						selectedLanguageId !== defaultLanguageId
					) {
						inputComponent.updateInput('');

						Liferay.Form.get(formId).removeRule(
							inputComponent.get('id'),
							'required'
						);
					}
				}
			);

			submitAsyncForm(document.getElementById(formId));
		}
		else {
			buttonRow.disabled = false;

			showAlert(
				Liferay.Util.sub(
					Liferay.Language.get(
						'please-enter-a-valid-title-for-the-default-language-x'
					),
					defaultLanguageId.replace('_', '-')
				)
			);
		}
	};

	const handleResetValuesButtonClick = () => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-reset-the-default-values'
				)
			)
		) {
			buttonRow.disabled = true;
			document.hrefFm.action = resetValuesButton.dataset.url;
			submitAsyncForm(document.hrefFm);
		}
	};

	const handleRowButtonClick = (event) => {
		document
			.querySelectorAll('.journal-alert-container')
			.forEach((alertElement) => {
				alertElement.parentElement.removeChild(alertElement);
			});

		actionInput.value =
			event.delegateTarget.dataset.actionname || actionInput.value;

		requestAnimationFrame(() => {
			buttonRow.disabled = true;
		});
	};

	const showAlert = (message) => {
		const articleContentWrapper = document.querySelector(
			'.article-content-content'
		);

		const alertContainer = document.createElement('div');

		alertContainer.classList.add('journal-alert-container');
		articleContentWrapper.prepend(alertContainer);

		openToast({
			autoClose: false,
			container: alertContainer,
			message,
			onClose: () => alertContainer.remove(),
			type: 'danger',
		});
	};

	const submitAsyncForm = (formElement) => {
		return fetch(formElement.action, {
			body: new FormData(formElement),
			method: formElement.method,
		})
			.then((response) => {
				navigate(
					response.redirected && response.url
						? response.url
						: window.location.href
				);
			})
			.catch((error) => {
				console.error(error);
				buttonRow.disabled = false;
			});
	};

	const eventHandlers = [
		attachListener(
			contextualSidebarButton,
			'click',
			handleContextualSidebarButtonClick
		),
		attachListener(
			resetValuesButton,
			'click',
			handleResetValuesButtonClick
		),

		new LocaleChangedHandler({
			contentTitle,
			defaultLanguageId,
			namespace,
			onDefaultLocaleChangedCallback: (languageId) => {
				defaultLanguageId = languageId;
			},
			onLocaleChangedCallback: (_context, languageId) => {
				if (!availableLocales.includes(languageId)) {
					availableLocales.push(languageId);
				}

				selectedLanguageId = languageId;
			},
		}),

		attachDelegateListener(
			buttonRow,
			'click',
			'button',
			handleRowButtonClick
		),

		Liferay.on('ddmFormError', handleDDMFormError),
		Liferay.on('ddmFormValid', handleDDMFormValid),
	];

	if (window.innerWidth > Liferay.BREAKPOINTS.PHONE) {
		handleContextualSidebarButtonClick();
	}

	return {
		dispose() {
			eventHandlers.forEach((eventHandler) => {
				eventHandler.detach();
			});
		},
	};
}

function attachDelegateListener(element, eventType, selector, callback) {
	const eventHandler = delegate(element, eventType, selector, callback);

	return {
		detach() {
			eventHandler.dispose();
		},
	};
}

function attachListener(element, eventType, callback) {
	element?.addEventListener(eventType, callback);

	return {
		detach() {
			element?.removeEventListener(eventType, callback);
		},
	};
}
