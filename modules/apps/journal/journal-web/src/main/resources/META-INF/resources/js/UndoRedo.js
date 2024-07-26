/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useCallback, useEffect, useState} from 'react';

const META_FIELD_NAMES = {
	description: 'descriptionMapAsXML',
	friendlyURL: 'friendlyURL',
	title: 'titleMapAsXML',
};

export default function UndoRedo({
	initialDefaultLanguageId,
	initialFields,
	languageId,
	portletNamespace,
}) {
	const [

		// eslint-disable-next-line no-unused-vars
		{defaultLanguageId, history, selectedLanguageId, step},
		setState,
	] = useState({
		defaultLanguageId: initialDefaultLanguageId,
		history: [
			{
				defaultLanguageId: initialDefaultLanguageId,
				descriptionInputValue:
					initialFields[`${META_FIELD_NAMES.description}`][
						`${initialDefaultLanguageId}`
					] || '',
				friendlyURLInputValue:
					initialFields[`${META_FIELD_NAMES.friendlyURL}`][
						`${initialDefaultLanguageId}`
					] || '',
				name: 'Reset',
				selectedLanguageId: languageId,
				titleInputValue:
					initialFields[`${META_FIELD_NAMES.title}`][
						`${initialDefaultLanguageId}`
					] || '',
			},
		],
		selectedLanguageId: languageId,
		step: 0,
	});

	let descriptionInputComponent;
	let friendlyURLInputComponent;
	let titleInputComponent;

	Liferay.componentReady(
		`${portletNamespace}${META_FIELD_NAMES.description}`
	).then((component) => {
		descriptionInputComponent = component;
	});

	Liferay.componentReady(
		`${portletNamespace}${META_FIELD_NAMES.friendlyURL}`
	).then((component) => {
		friendlyURLInputComponent = component;
	});

	Liferay.componentReady(`${portletNamespace}${META_FIELD_NAMES.title}`).then(
		(component) => {
			titleInputComponent = component;
		}
	);

	const handleUndo = () => {
		const newStep = step - 1;
		const nextStep = history[newStep];

		if (nextStep.selectedLanguageId !== selectedLanguageId) {
			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			descriptionInputComponent
				.get('translatedLanguages')
				.values()
				.map((lang) => {
					if (
						!nextStep.descriptionTranslatedLanguages.includes(lang)
					) {
						descriptionInputComponent
							.get('translatedLanguages')
							.remove(lang);
						descriptionInputComponent.removeInputLanguage(lang);
						descriptionInputComponent._updateTranslationStatus(
							selectedLanguageId
						);
					}
				});
			descriptionInputComponent.selectFlag(nextStep.selectedLanguageId);
			friendlyURLInputComponent
				.get('translatedLanguages')
				.values()
				.map((lang) => {
					if (
						!nextStep.friendlyURLTranslatedLanguages.includes(lang)
					) {
						friendlyURLInputComponent
							.get('translatedLanguages')
							.remove(lang);
						friendlyURLInputComponent.removeInputLanguage(lang);
						friendlyURLInputComponent._updateTranslationStatus(
							selectedLanguageId
						);
					}
				});
			friendlyURLInputComponent.selectFlag(nextStep.selectedLanguageId);
			titleInputComponent
				.get('translatedLanguages')
				.values()
				.map((lang) => {
					if (!nextStep.titleTranslatedLanguages.includes(lang)) {
						titleInputComponent
							.get('translatedLanguages')
							.remove(lang);
						titleInputComponent.removeInputLanguage(lang);
						titleInputComponent._updateTranslationStatus(
							selectedLanguageId
						);
					}
				});
			titleInputComponent.selectFlag(nextStep.selectedLanguageId);

			selectedLanguageIdInput.value = nextStep.selectedLanguageId;
		}

		updateMetadataFields(nextStep, newStep);
	};

	const handleRedo = () => {
		const newStep = step + 1;
		const nextStep = history[newStep];

		if (nextStep.selectedLanguageId !== selectedLanguageId) {
			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			selectedLanguageIdInput.value = nextStep.selectedLanguageId;

			nextStep.descriptionTranslatedLanguages.map((lang) => {
				if (
					!descriptionInputComponent
						.get('translatedLanguages')
						.has(lang)
				) {
					descriptionInputComponent
						.get('translatedLanguages')
						.add(lang);
					descriptionInputComponent._updateTranslationStatus(
						nextStep.selectedLanguageId
					);
				}
			});
			descriptionInputComponent.selectFlag(nextStep.selectedLanguageId);
			nextStep.friendlyURLTranslatedLanguages.map((lang) => {
				if (
					!friendlyURLInputComponent
						.get('translatedLanguages')
						.has(lang)
				) {
					friendlyURLInputComponent
						.get('translatedLanguages')
						.add(lang);
					friendlyURLInputComponent._updateTranslationStatus(
						nextStep.selectedLanguageId
					);
				}
			});
			friendlyURLInputComponent.selectFlag(nextStep.selectedLanguageId);
			nextStep.titleTranslatedLanguages.map((lang) => {
				if (!titleInputComponent.get('translatedLanguages').has(lang)) {
					titleInputComponent.get('translatedLanguages').add(lang);
					titleInputComponent._updateTranslationStatus(
						nextStep.selectedLanguageId
					);
				}
			});
			titleInputComponent.selectFlag(nextStep.selectedLanguageId);
		}

		updateMetadataFields(nextStep, newStep);
	};

	const handleStoreState = useCallback(
		({fieldName}) => {
			const defaultLanguageIdInput = document.getElementById(
				`${portletNamespace}defaultLanguageId`
			);

			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			const newHistory = {
				defaultLanguageId: defaultLanguageIdInput.value,
				descriptionInputValue:
					descriptionInputComponent.getValue(selectedLanguageId),
				descriptionTranslatedLanguages: descriptionInputComponent
					.get('translatedLanguages')
					.values(),
				friendlyURLInputValue:
					friendlyURLInputComponent.getValue(selectedLanguageId),
				friendlyURLTranslatedLanguages: friendlyURLInputComponent
					.get('translatedLanguages')
					.values(),
				name: fieldName,
				selectedLanguageId: selectedLanguageIdInput.value,
				titleInputValue:
					titleInputComponent.getValue(selectedLanguageId),
				titleTranslatedLanguages: titleInputComponent
					.get('translatedLanguages')
					.values(),
			};

			setState({
				defaultLanguageId: defaultLanguageIdInput.value,
				history: [...history.slice(0, step + 1), newHistory],
				selectedLanguageId: selectedLanguageIdInput.value,
				step: step + 1,
			});
		},
		[
			descriptionInputComponent,
			friendlyURLInputComponent,
			history,
			portletNamespace,
			selectedLanguageId,
			step,
			titleInputComponent,
		]
	);

	const updateMetadataFields = (step, newStep) => {
		descriptionInputComponent.updateInputLanguage(
			step.descriptionInputValue,
			step.selectedLanguageId
		);

		friendlyURLInputComponent.updateInputLanguage(
			step.friendlyURLInputValue,
			step.selectedLanguageId
		);

		titleInputComponent.updateInputLanguage(
			step.titleInputValue,
			step.selectedLanguageId
		);

		descriptionInputComponent.updateInput(step.descriptionInputValue);

		friendlyURLInputComponent.updateInput(step.friendlyURLInputValue);

		titleInputComponent.updateInput(step.titleInputValue);

		setState({
			defaultLanguageId: step.defaultLanguageId,
			history,
			selectedLanguageId: step.selectedLanguageId,
			step: newStep,
		});
	};

	const resetStoreState = useCallback(
		({fieldName}) => {
			const defaultLanguageIdInput = document.getElementById(
				`${portletNamespace}defaultLanguageId`
			);

			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			const newHistory = {
				defaultLanguageId: defaultLanguageIdInput.value,
				descriptionInputValue: descriptionInputComponent.getValue(
					selectedLanguageIdInput.value
				),
				descriptionTranslatedLanguages: descriptionInputComponent
					.get('translatedLanguages')
					.values(),
				friendlyURLInputValue: friendlyURLInputComponent.getValue(
					selectedLanguageIdInput.value
				),
				friendlyURLTranslatedLanguages: friendlyURLInputComponent
					.get('translatedLanguages')
					.values(),
				name: fieldName,
				selectedLanguageId: selectedLanguageIdInput.value,
				titleInputValue: titleInputComponent.getValue(
					selectedLanguageIdInput.value
				),
				titleTranslatedLanguages: titleInputComponent
					.get('translatedLanguages')
					.values(),
			};

			setState({
				defaultLanguageId: defaultLanguageIdInput.value,
				history: [newHistory],
				selectedLanguageId: selectedLanguageIdInput.value,
				step: 0,
			});
		},
		[
			descriptionInputComponent,
			friendlyURLInputComponent,
			portletNamespace,
			titleInputComponent,
		]
	);

	const localeChangeHandler = useCallback(
		(event) => {
			const fieldName = 'Locale Change';
			const selectedLanguageId = event.item.getAttribute('data-value');
			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			selectedLanguageIdInput.value = selectedLanguageId;

			Liferay.fire('journal:storeState', {fieldName});
		},
		[portletNamespace]
	);

	const defaultLocaleChangeHandler = useCallback(
		(event) => {
			const defaultLanguageIdInput = document.getElementById(
				`${portletNamespace}defaultLanguageId`
			);
			const fieldName = 'Reset';
			const selectedLanguageId = event.item.getAttribute('data-value');
			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			defaultLanguageIdInput.value = selectedLanguageId;

			selectedLanguageIdInput.value = selectedLanguageId;

			resetStoreState({fieldName});
		},
		[portletNamespace, resetStoreState]
	);

	useEffect(() => {
		Liferay.after('journal:localeChanged', localeChangeHandler);
		Liferay.after(
			'journal:defaultLocaleChanged',
			defaultLocaleChangeHandler
		);

		return () => {
			Liferay.detach('journal:localeChanged', localeChangeHandler);
			Liferay.detach(
				'journal:defaultLocaleChanged',
				defaultLocaleChangeHandler
			);
		};
	}, [defaultLocaleChangeHandler, localeChangeHandler]);

	useEffect(() => {
		Liferay.on('journal:storeState', handleStoreState);

		return () => {
			Liferay.detach('journal:storeState', handleStoreState);
		};
	}, [handleStoreState]);

	return (
		<>
			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('undo')}
				className="btn-monospaced"
				disabled={step <= 0}
				displayType="secondary"
				onClick={() => {
					Liferay.fire('journal:undo');
					handleUndo(step - 1);
				}}
				size="sm"
				symbol="undo"
				title={Liferay.Language.get('undo')}
			/>

			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('redo')}
				className="btn-monospaced"
				disabled={!history.length || step === history.length - 1}
				displayType="secondary"
				onClick={() => {
					Liferay.fire('journal:redo');
					handleRedo();
				}}
				size="sm"
				symbol="redo"
				title={Liferay.Language.get('redo')}
			/>
		</>
	);
}
