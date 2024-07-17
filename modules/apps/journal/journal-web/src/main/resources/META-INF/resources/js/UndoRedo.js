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
				descriptionInputComponent:
					initialFields[`${META_FIELD_NAMES.description}`][
						`${initialDefaultLanguageId}`
					] || '',
				friendlyURLInputComponent:
					initialFields[`${META_FIELD_NAMES.friendlyURL}`][
						`${initialDefaultLanguageId}`
					] || '',
				name: 'Reset',
				selectedLanguageId: languageId,
				titleInputComponent:
					initialFields[`${META_FIELD_NAMES.title}`][
						`${initialDefaultLanguageId}`
					] || '',
			},
		],
		selectedLanguageId: languageId,
		step: 0,
	});

	const handleUndo = (newStep) => {
		const descriptionInputComponent = Liferay.component(
			`${portletNamespace}${META_FIELD_NAMES.description}`
		);
		const friendlyURLInputComponent = Liferay.component(
			`${portletNamespace}${META_FIELD_NAMES.friendlyURL}`
		);
		const nextStep = history[newStep];
		const titleInputComponent = Liferay.component(
			`${portletNamespace}${META_FIELD_NAMES.title}`
		);

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

		descriptionInputComponent.updateInputLanguage(
			nextStep.descriptionInputComponent,
			nextStep.selectedLanguageId
		);

		friendlyURLInputComponent.updateInputLanguage(
			nextStep.friendlyURLInputComponent,
			nextStep.selectedLanguageId
		);

		titleInputComponent.updateInputLanguage(
			nextStep.titleInputComponent,
			nextStep.selectedLanguageId
		);

		descriptionInputComponent.updateInput(
			nextStep.descriptionInputComponent
		);

		friendlyURLInputComponent.updateInput(
			nextStep.friendlyURLInputComponent
		);

		titleInputComponent.updateInput(nextStep.titleInputComponent);

		setState({
			defaultLanguageId: nextStep.defaultLanguageId,
			history,
			selectedLanguageId: nextStep.selectedLanguageId,
			step: newStep,
		});
	};

	const handleRedo = (newStep) => {
		const descriptionInputComponent = Liferay.component(
			`${portletNamespace}${META_FIELD_NAMES.description}`
		);
		const friendlyURLInputComponent = Liferay.component(
			`${portletNamespace}${META_FIELD_NAMES.friendlyURL}`
		);
		const nextStep = history[newStep];
		const titleInputComponent = Liferay.component(
			`${portletNamespace}${META_FIELD_NAMES.title}`
		);

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

		descriptionInputComponent.updateInputLanguage(
			nextStep.descriptionInputComponent,
			nextStep.selectedLanguageId
		);

		friendlyURLInputComponent.updateInputLanguage(
			nextStep.friendlyURLInputComponent,
			nextStep.selectedLanguageId
		);

		titleInputComponent.updateInputLanguage(
			nextStep.titleInputComponent,
			nextStep.selectedLanguageId
		);

		descriptionInputComponent.updateInput(
			nextStep.descriptionInputComponent
		);

		friendlyURLInputComponent.updateInput(
			nextStep.friendlyURLInputComponent
		);

		titleInputComponent.updateInput(nextStep.titleInputComponent);

		setState({
			defaultLanguageId: nextStep.defaultLanguageId,
			history,
			selectedLanguageId: nextStep.selectedLanguageId,
			step: newStep,
		});
	};

	const handleStoreState = useCallback(
		({fieldName}) => {
			const defaultLanguageIdInput = document.getElementById(
				`${portletNamespace}defaultLanguageId`
			);

			const descriptionInputComponent = Liferay.componentReady(
				`${portletNamespace}${META_FIELD_NAMES.description}`
			);

			const friendlyURLInputComponent = Liferay.componentReady(
				`${portletNamespace}${META_FIELD_NAMES.friendlyURL}`
			);

			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			const titleInputComponent = Liferay.componentReady(
				`${portletNamespace}${META_FIELD_NAMES.title}`
			);

			Promise.all([
				descriptionInputComponent,
				titleInputComponent,
				friendlyURLInputComponent,
			]).then(
				([
					descriptionInputComponent,
					titleInputComponent,
					friendlyURLInputComponent,
				]) => {
					const newHistory = {
						defaultLanguageId: defaultLanguageIdInput.value,
						descriptionInputComponent:
							descriptionInputComponent.getValue(
								selectedLanguageId
							),
						descriptionTranslatedLanguages:
							descriptionInputComponent
								.get('translatedLanguages')
								.values(),
						friendlyURLInputComponent:
							friendlyURLInputComponent.getValue(
								selectedLanguageId
							),
						friendlyURLTranslatedLanguages:
							friendlyURLInputComponent
								.get('translatedLanguages')
								.values(),
						name: fieldName,
						selectedLanguageId: selectedLanguageIdInput.value,
						titleInputComponent:
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
				}
			);
		},
		[history, portletNamespace, selectedLanguageId, step]
	);

	const resetStoreState = useCallback(
		({fieldName}) => {
			const defaultLanguageIdInput = document.getElementById(
				`${portletNamespace}defaultLanguageId`
			);

			const descriptionInputComponent = Liferay.componentReady(
				`${portletNamespace}${META_FIELD_NAMES.description}`
			);

			const friendlyURLInputComponent = Liferay.componentReady(
				`${portletNamespace}${META_FIELD_NAMES.friendlyURL}`
			);

			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			const titleInputComponent = Liferay.componentReady(
				`${portletNamespace}${META_FIELD_NAMES.title}`
			);

			Promise.all([
				descriptionInputComponent,
				titleInputComponent,
				friendlyURLInputComponent,
			]).then(
				([
					descriptionInputComponent,
					titleInputComponent,
					friendlyURLInputComponent,
				]) => {
					const newHistory = {
						defaultLanguageId: defaultLanguageIdInput.value,
						descriptionInputComponent:
							descriptionInputComponent.getValue(
								selectedLanguageIdInput.value
							),
						descriptionTranslatedLanguages:
							descriptionInputComponent
								.get('translatedLanguages')
								.values(),
						friendlyURLInputComponent:
							friendlyURLInputComponent.getValue(
								selectedLanguageIdInput.value
							),
						friendlyURLTranslatedLanguages:
							friendlyURLInputComponent
								.get('translatedLanguages')
								.values(),
						name: fieldName,
						selectedLanguageId: selectedLanguageIdInput.value,
						titleInputComponent: titleInputComponent.getValue(
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
				}
			);
		},
		[portletNamespace]
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
					handleRedo(step + 1);
				}}
				size="sm"
				symbol="redo"
				title={Liferay.Language.get('redo')}
			/>
		</>
	);
}
