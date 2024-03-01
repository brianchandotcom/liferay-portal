/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {TranslationProgress} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {getAllLocalizableFields} from './TranslationsWrapper';

type Field = Record<Liferay.Language.Locale, string>;
interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Record<string, Field>;
	getLocalizableFields: () => void;
	selectedLanguageId: Liferay.Language.Locale;
	translationProgress: TranslationProgress | null;
}

export default function TranslationOptions({
	defaultLanguageId,
	fields: initialFields,
	getLocalizableFields,
	selectedLanguageId,
	translationProgress,
}: Props) {
	const {
		observer: resetTranslationObserver,
		onOpenChange: onOpenChangeResetTranslation,
		open: openResetTranslation,
	} = useModal();

	const {
		observer: markTranslatedObserver,
		onOpenChange: onOpenChangeMarkAsTranslated,
		open: openMarkTranslated,
	} = useModal();

	const [dropdownActive, setDropdownActive] = useState(false);

	const markAsTranslatedHandler = () => {
		Liferay.fire('inputLocalized:markAsTranslated', {selectedLanguageId});
	};

	const resetButtonHandler = () => {
		const fields = getAllLocalizableFields(initialFields);

		const defaultFields = [
			'descriptionMapAsXML',
			'friendlyURL',
			'titleMapAsXML',
		];

		Object.keys(fields)
			.flatMap((fieldName) => {
				if (!defaultFields.includes(fieldName)) {
					return [];
				}

				return Array.from(
					document.querySelectorAll<HTMLInputElement>(
						`[type="hidden"][data-field-name="${fieldName}"]`
					)
				).filter(
					(input) => input.dataset.languageid === selectedLanguageId
				);
			})
			.map((input) => {
				input.remove();
			});

		Liferay.fire('inputLocalized:resetTranslations', {
			defaultLanguageId,
		});

		Liferay.fire('inputLocalized:localeChanged', {
			item: document.querySelector<HTMLInputElement>(
				`[data-languageid="${selectedLanguageId}"][data-value="${selectedLanguageId}"]`
			),
		});

		Liferay.fire('inputLocalized:updateTranslationStatus');
	};

	const disabledResetButton =
		selectedLanguageId === defaultLanguageId ||
		(translationProgress &&
			!translationProgress.translatedItems[selectedLanguageId]) ||
		(translationProgress &&
			translationProgress.translatedItems[selectedLanguageId] === 0);

	const disabledMarkTranslatedButton =
		selectedLanguageId === defaultLanguageId ||
		(translationProgress &&
			translationProgress.translatedItems[selectedLanguageId] ===
				translationProgress.totalItems);

	return (
		<>
			<ClayDropDown
				active={dropdownActive}
				onActiveChange={(active: boolean) => {
					if (active) {
						getLocalizableFields();
					}

					setDropdownActive(active);
				}}
				trigger={
					<ClayButton
						aria-label={Liferay.Language.get('translation-options')}
						className="px-2"
						displayType="secondary"
						size="sm"
						title={Liferay.Language.get('translation-options')}
						type="button"
					>
						<ClayIcon symbol="ellipsis-v" />
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Item>
						<ClayButton
							className="font-weight-normal text-secondary"
							disabled={!!disabledResetButton}
							displayType="unstyled"
							onClick={() => onOpenChangeResetTranslation(true)}
							size="sm"
						>
							<ClayIcon className="c-mt-0" symbol="trash" />

							<span className="c-ml-3">
								{Liferay.Language.get('reset-translation')}
							</span>
						</ClayButton>
					</ClayDropDown.Item>

					<ClayDropDown.Item>
						<ClayButton
							className="font-weight-normal text-secondary"
							disabled={!!disabledMarkTranslatedButton}
							displayType="unstyled"
							onClick={() => onOpenChangeMarkAsTranslated(true)}
							size="sm"
						>
							<ClayIcon
								className="c-mt-0"
								symbol="question-circle-full"
							/>

							<span className="c-ml-3">
								{Liferay.Language.get('mark-as-translated')}
							</span>
						</ClayButton>
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{openResetTranslation && (
				<ClayModal observer={resetTranslationObserver} status="danger">
					<ClayModal.Header>
						{sub(
							Liferay.Language.get('delete-x-translation'),
							selectedLanguageId
						)}
					</ClayModal.Header>

					<ClayModal.Body>
						<p>
							<strong>{selectedLanguageId}&nbsp;</strong>

							{Liferay.Language.get(
								'translation-will-be-deleted-and-content-fields-will-be-set-to-default-language'
							)}
						</p>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={() =>
										onOpenChangeResetTranslation(false)
									}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="danger"
									onClick={() => {
										onOpenChangeResetTranslation(false);
										resetButtonHandler();
									}}
								>
									{Liferay.Language.get('delete')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
			{openMarkTranslated && (
				<ClayModal observer={markTranslatedObserver} status="info">
					<ClayModal.Header>
						{sub(
							Liferay.Language.get('mark-x-as-translated'),
							selectedLanguageId
						)}
					</ClayModal.Header>

					<ClayModal.Body>
						<p>
							{sub(
								Liferay.Language.get(
									'mark-as-translated-for-x-language'
								),
								selectedLanguageId
							)}
						</p>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={() =>
										onOpenChangeMarkAsTranslated(false)
									}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="info"
									onClick={() => {
										onOpenChangeMarkAsTranslated(false);
										markAsTranslatedHandler();
									}}
								>
									{Liferay.Language.get('mark-as-translated')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
}
