/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	debounce,
	fetch,
	navigate,
	openConfirmModal,
	openToast,
	sub,
} from 'frontend-js-web';

import {LocaleChangedHandler} from './LocaleChangedHandler.es';

const AUTO_SAVE_DELAY = 1500;

export default function _JournalPortlet({
	articleId: initialArticleId,
	autoSaveDraftEnabled,
	availableLocales: initialAvailableLocales,
	classNameId,
	contentTitle,
	defaultLanguageId: initialDefaultLanguageId,
	displayDate,
	hasSavePermission,
	namespace,
}) {
	const formId = `${namespace}fm1`;

	const actionInput = document.getElementById(
		`${namespace}javax-portlet-action`
	);
	const availableLocalesInput = document.getElementById(
		`${namespace}availableLocales`
	);
	const contextualSidebarButton = document.getElementById(
		`${namespace}contextualSidebarButton`
	);
	const contextualSidebarContainer = document.getElementById(
		`${namespace}contextualSidebarContainer`
	);
	const form = document.getElementById(formId);
	const formDateInput = document.getElementById(`${namespace}formDate`);
	const publishButton = document.getElementById(`${namespace}publishButton`);
	const resetValuesButton = document.getElementById(
		`${namespace}resetValuesButton`
	);
	const saveButton = document.getElementById(`${namespace}saveButton`);

	const availableLocales = [
		...initialAvailableLocales,
		initialDefaultLanguageId,
	];

	availableLocalesInput.value = availableLocales;

	let articleId = initialArticleId;
	let defaultLanguageId = initialDefaultLanguageId;
	let selectedLanguageId = initialDefaultLanguageId;

	const publishingLock = getLock('publishing', {
		lockedIndicator: document.getElementById(
			`${namespace}savingChangesIndicator`
		),
		triggerElements: [publishButton, resetValuesButton, saveButton],
		unlockedIndicator: document.getElementById(
			`${namespace}changesSavedIndicator`
		),
	});

	const editingDefaultValues = classNameId && classNameId !== '0';

	if (editingDefaultValues) {
		const getInput = (inputName) =>
			document.getElementById(`${namespace}${inputName}`);

		const resetInput = (inputName) => {
			const input = getInput(inputName);

			if (input && !displayDate) {
				input.value = '';
			}
		};

		resetInput('displayDate');
		resetInput('displayDateAmPm');
		resetInput('displayDateDay');
		resetInput('displayDateHour');
		resetInput('displayDateMinute');
		resetInput('displayDateMonth');
		resetInput('displayDateTime');
		resetInput('displayDateYear');

		const displayDateInput = getInput('displayDate');

		if (displayDateInput) {
			displayDateInput.addEventListener('change', (event) => {
				if (!event.target.value) {
					getInput('displayDateDay').value = '';
					getInput('displayDateMonth').value = '';
					getInput('displayDateYear').value = '';
				}
			});
		}
	}

	const handleContextualSidebarButton = () => {
		contextualSidebarContainer?.classList.toggle(
			'contextual-sidebar-visible'
		);
	};

	const isContextualSidebarOpen = () =>
		contextualSidebarContainer.classList.contains(
			'contextual-sidebar-visible'
		);

	const updateContextualSidebarAriaAttributes = () => {
		const isOpen = isContextualSidebarOpen();

		const title = isOpen
			? Liferay.Language.get('close-configuration-panel')
			: Liferay.Language.get('open-configuration-panel');

		contextualSidebarButton.setAttribute('aria-label', title);
		contextualSidebarButton.setAttribute('aria-selected', isOpen);
		contextualSidebarButton.setAttribute('title', title);
	};

	const handleContextualSidebarButtonClick = () => {
		handleContextualSidebarButton();

		updateContextualSidebarAriaAttributes();

		if (isContextualSidebarOpen()) {
			contextualSidebarContainer.focus();
		}
	};

	const handleDDMFormError = (error) => {
		publishingLock.unlock();
		console.error(error);

		const workflowActionInput = document.getElementById(
			`${namespace}workflowAction`
		);

		workflowActionInput.value = Liferay.Workflow.ACTION_SAVE_DRAFT;

		const titleInputComponent = Liferay.component(
			`${namespace}titleMapAsXML`
		);

		if (!titleInputComponent?.getValue(defaultLanguageId)) {
			showAlert(
				sub(
					Liferay.Language.get(
						'please-enter-a-valid-title-for-the-default-language-x'
					),
					defaultLanguageId.replaceAll('_', '-')
				)
			);
		}
	};

	const handleDDMFormValid = (
		{redirectOnSave, showErrors} = {
			redirectOnSave: false,
			showErrors: false,
		}
	) => {
		const titleInputComponent = Liferay.component(
			`${namespace}titleMapAsXML`
		);

		if (
			titleInputComponent?.getValue(defaultLanguageId) ||
			editingDefaultValues
		) {
			if (!articleId) {
				const newArticleIdInput = document.getElementById(
					`${namespace}newArticleId`
				);

				articleId = newArticleIdInput.value || '';
			}

			const articleIdInput = document.getElementById(
				`${namespace}articleId`
			);

			articleIdInput.value = articleId;

			availableLocalesInput.value = availableLocales;

			if (autoSaveDraftEnabled) {
				submitAsyncForm(form, {redirectOnSave});
			}
			else {
				form.submit();
			}
		}
		else {
			if (showErrors) {
				showAlert(
					sub(
						Liferay.Language.get(
							'please-enter-a-valid-title-for-the-default-language-x'
						),
						defaultLanguageId.replaceAll('_', '-')
					)
				);
			}

			publishingLock.unlock();
		}
	};

	const handlePublishButtonClick = (event) => {
		publishingLock.lock();

		document
			.querySelectorAll('.journal-alert-container')
			.forEach((alertElement) => {
				alertElement.parentElement.removeChild(alertElement);
			});

		const workflowActionInput = document.getElementById(
			`${namespace}workflowAction`
		);

		if (event.currentTarget.dataset.actionname === 'publish') {
			workflowActionInput.value = Liferay.Workflow.ACTION_PUBLISH;
		}

		if (editingDefaultValues) {
			Liferay.component(`${namespace}dataEngineLayoutRenderer`)
				.reactComponentRef.current.getFields()
				.forEach((field) => {
					field.required = false;
				});

			actionInput.value = articleId
				? '/journal/update_data_engine_default_values'
				: '/journal/add_data_engine_default_values';
		}
		else {
			actionInput.value = articleId
				? '/journal/update_article'
				: '/journal/add_article';
		}

		const descriptionInputComponent = Liferay.component(
			`${namespace}descriptionMapAsXML`
		);
		const titleInputComponent = Liferay.component(
			`${namespace}titleMapAsXML`
		);

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
						`${namespace}${inputComponent.get('id')}`,
						'required'
					);
				}
			}
		);
	};

	const handleResetValuesButtonClick = (event) => {
		publishingLock.lock();

		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-reset-the-default-values'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					if (editingDefaultValues) {
						actionInput.value = articleId
							? '/journal/update_data_engine_default_values'
							: '/journal/add_data_engine_default_values';
					}

					submitForm(
						document.hrefFm,
						event.currentTarget.dataset.url
					);
				}
				else {
					publishingLock.unlock();
				}
			},
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
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	};

	const submitAsyncForm = (
		formElement,
		{redirectOnSave} = {redirectOnSave: false}
	) => {
		if (autoSaveDraftEnabled) {
			formDateInput.value = Date.now().toString();
		}

		return fetch(formElement.action, {
			body: new FormData(formElement),
			method: formElement.method,
		})
			.then((response) => {
				if (redirectOnSave) {
					navigate(
						response.redirected && response.url
							? response.url
							: window.location.href
					);
				}
				else {
					if (!articleId && response.url) {
						const key = `${namespace}articleId`;
						const url = new URL(response.url);

						if (url.searchParams.has(key)) {
							articleId = url.searchParams.get(key);
						}
					}

					publishingLock.unlock();
				}
			})
			.catch((error) => {
				console.error(error);
				publishingLock.unlock();
			});
	};

	const eventHandlers = [
		attachListener(
			contextualSidebarButton,
			'click',
			handleContextualSidebarButtonClick
		),
		attachListener(publishButton, 'click', handlePublishButtonClick),
		attachListener(saveButton, 'click', handlePublishButtonClick),
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
					availableLocalesInput.value = availableLocales;
				}

				selectedLanguageId = languageId;
			},
		}),

		Liferay.on('ddmFormError', handleDDMFormError),
		Liferay.on('ddmFormValid', () =>
			handleDDMFormValid({
				redirectOnSave: true,
				showErrors: true,
			})
		),
	];

	if (
		autoSaveDraftEnabled &&
		hasSavePermission &&
		(!classNameId || classNameId === '0')
	) {
		eventHandlers.push(
			attachFormChangeListener(
				form,
				() => {
					return !publishingLock.isLocked();
				},
				(mutationRecord) => {
					if (publishingLock.isLocked()) {
						return false;
					}

					return [
						mutationRecord.target,
						...mutationRecord.addedNodes,
						...mutationRecord.removedNodes,
					].some(
						(node) =>
							node.name &&
							node.name.startsWith(namespace) &&
							node.name !== `${namespace}languageId`
					);
				},
				() => {
					if (publishingLock.isLocked()) {
						return;
					}

					publishingLock.lock();

					actionInput.value = articleId
						? '/journal/update_article'
						: '/journal/add_article';

					handleDDMFormValid({
						redirectOnSave: false,
						showErrors: false,
					});
				}
			)
		);
	}

	if (window.innerWidth > Liferay.BREAKPOINTS.PHONE) {
		handleContextualSidebarButton();
	}

	updateContextualSidebarAriaAttributes();

	return {
		dispose() {
			eventHandlers.forEach((eventHandler) => {
				eventHandler.detach();
			});
		},
	};
}

function attachFormChangeListener(
	form,
	accentChangeEvent,
	acceptMutationRecord,
	callback
) {
	const handleChange = debounce(() => {
		callback();
	}, AUTO_SAVE_DELAY);

	const mutationObserver = new MutationObserver((mutationRecords) => {
		const observedMutationRecords = mutationRecords
			.filter((mutationRecord) => {
				if (mutationRecord.type === 'attributes') {
					return (
						mutationRecord.oldValue !== null &&
						mutationRecord.target.value.trim() !==
							mutationRecord.oldValue.trim()
					);
				}
				else if (mutationRecord.type === 'childList') {
					return [
						...mutationRecord.addedNodes,
						...mutationRecord.removedNodes,
					].some((node) => node.name);
				}
			})
			.filter((mutationRecord) => acceptMutationRecord(mutationRecord));

		if (observedMutationRecords.length) {
			handleChange();
		}
	});

	mutationObserver.observe(form, {
		attributeFilter: ['value'],
		attributeOldValue: true,
		attributes: true,
		childList: true,
		subtree: true,
	});

	const handleFormChange = (event) => {
		if (accentChangeEvent(event)) {
			handleChange();
		}
	};

	form.addEventListener('change', handleFormChange);

	return {
		detach() {
			mutationObserver.disconnect();
			form.removeEventListener('change', handleFormChange);
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

function getLock(name, {lockedIndicator, triggerElements, unlockedIndicator}) {
	let locked = false;

	const toggle = (nextValue) => {
		if (nextValue === locked) {
			throw new Error(
				`${name} is already ${locked ? 'locked' : 'unlocked'}`
			);
		}

		locked = nextValue;

		requestAnimationFrame(() => {
			triggerElements.forEach((triggerElement) => {
				if (triggerElement) {
					triggerElement.disabled = locked;
				}
			});

			if (locked) {
				lockedIndicator?.classList.replace('d-none', 'd-flex');
				unlockedIndicator?.classList.replace('d-flex', 'd-none');
			}
			else {
				lockedIndicator?.classList.replace('d-flex', 'd-none');
				unlockedIndicator?.classList.replace('d-none', 'd-flex');
			}
		});
	};

	return {
		isLocked: () => locked,
		lock: () => toggle(true),
		unlock: () => toggle(false),
	};
}
