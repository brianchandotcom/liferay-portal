/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useCallback, useEffect, useState} from 'react';

const META_FIELD_NAMES = {
	description: 'descriptionMapAsXML',
	friendlyURL: 'friendlyURL',
	title: 'titleMapAsXML',
};

const METADATA_FIELD_NAME_HISTORY = {
	[`${META_FIELD_NAMES.description}Editor`]:
		Liferay.Language.get('description'),
	[META_FIELD_NAMES.friendlyURL]: Liferay.Language.get('friendly-url'),
	[META_FIELD_NAMES.title]: Liferay.Language.get('title'),
};

export default function UndoRedo({
	initialDefaultLanguageId,
	initialFields,
	languageId,
	portletNamespace,
}) {
	const [active, setActive] = useState(false);

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

	const handleUndo = (newStep) => {
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

			Liferay.fire('journal:updateSelectedLanguage', {
				item: document.querySelector(
					`[data-languageid="${nextStep.selectedLanguageId}"][data-value="${nextStep.selectedLanguageId}"]`
				),
			});
		}

		updateMetadataFields(nextStep, newStep);
		Liferay.fire('inputLocalized:updateTranslationStatus');
	};

	const handleRedo = (newStep) => {
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

			Liferay.fire('journal:updateSelectedLanguage', {
				item: document.querySelector(
					`[data-languageid="${nextStep.selectedLanguageId}"][data-value="${nextStep.selectedLanguageId}"]`
				),
			});
		}

		updateMetadataFields(nextStep, newStep);
		Liferay.fire('inputLocalized:updateTranslationStatus');
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
			const selectedLanguageId = event.item.getAttribute('data-value');
			const selectedLanguageIdInput = document.getElementById(
				`${portletNamespace}languageId`
			);

			selectedLanguageIdInput.value = selectedLanguageId;

			Liferay.fire('journal:storeState', {
				fieldName: Liferay.Language.get('change-language'),
			});
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

	const handleUpdateFriendlyURL = useCallback(
		({friendlyURL}) => {
			const newHistory = history.map((step) => {
				if (step.titleInputValue) {
					step.friendlyURLInputValue = friendlyURL;
				}

				return step;
			});
			setState((prevState) => {
				return {
					...prevState,
					history: newHistory,
				};
			});
		},
		[history]
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

	useEffect(() => {
		Liferay.on('journal:update-friendly-url', handleUpdateFriendlyURL);

		return () => {
			Liferay.detach(
				'journal:update-friendly-url',
				handleUpdateFriendlyURL
			);
		};
	}, [handleUpdateFriendlyURL]);

	return (
		<div className="d-flex">
			{Liferay.FeatureFlags['LPD-36053'] ? (
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
						disabled={
							!history.length || step === history.length - 1
						}
						displayType="secondary"
						onClick={() => {
							Liferay.fire('journal:redo');
							handleRedo(step + 1);
						}}
						size="sm"
						symbol="redo"
						title={Liferay.Language.get('redo')}
					/>

					<ClayDropDown
						active={active}
						alignmentPosition={Align.BottomRight}
						className="ml-2"
						onActiveChange={setActive}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('history')}
								aria-pressed={active}
								disabled={history.length <= 1}
								displayType="secondary"
								size="sm"
								symbol="time"
								title={Liferay.Language.get('history')}
							/>
						}
					>
						<ClayDropDown.ItemList>
							{history
								.map((item, index) => {
									return (
										index > 0 && (
											<ClayDropDown.Item
												disabled={step === index}
												key={index}
												onClick={() => {
													if (index < step) {
														handleUndo(index);
													}
													else {
														handleRedo(index);
													}

													Liferay.fire(
														'journal:goto',
														{
															step: index,
														}
													);

													setActive(false);
												}}
												symbolRight={
													step === index
														? 'check'
														: ''
												}
											>
												{METADATA_FIELD_NAME_HISTORY[
													item.name
												] || item.name}
											</ClayDropDown.Item>
										)
									);
								})
								.reverse()}

							<ClayDropDown.Divider />

							<ClayDropDown.Item
								disabled={step <= 0}
								onClick={() => {
									handleUndo(0);
									Liferay.fire('journal:goto', {
										step: 0,
									});
									setActive(false);
								}}
							>
								{Liferay.Language.get('undo-all')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</>
			) : (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('undo-all')}
					className="btn-monospaced"
					disabled={history.length <= 1}
					displayType="secondary"
					onClick={() => {
						handleUndo(0);
						Liferay.fire('journal:goto', {
							step: 0,
						});
					}}
					size="sm"
					symbol="time"
					title={Liferay.Language.get('undo-all')}
				/>
			)}
		</div>
	);
}
