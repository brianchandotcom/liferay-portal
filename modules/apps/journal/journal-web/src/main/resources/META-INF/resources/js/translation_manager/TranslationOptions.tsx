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
	const {observer, onOpenChange, open} = useModal();

	const [dropdownActive, setDropdownActive] = useState(false);

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
			isResetTranslation: true,
			item: document.querySelector<HTMLInputElement>(
				`[data-languageid="${selectedLanguageId}"][data-value="${selectedLanguageId}"]`
			),
		});

		Liferay.fire('inputLocalized:updateTranslationStatus');
	};

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
							disabled={
								selectedLanguageId === defaultLanguageId ||
								!translationProgress?.translatedItems[
									selectedLanguageId
								]
							}
							displayType="unstyled"
							onClick={() => onOpenChange(true)}
							size="sm"
						>
							<ClayIcon className="c-mt-0" symbol="trash" />

							<span className="c-ml-3">
								{Liferay.Language.get('reset-translation')}
							</span>
						</ClayButton>
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{open && (
				<ClayModal observer={observer} status="danger">
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
									onClick={() => onOpenChange(false)}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="danger"
									onClick={() => {
										onOpenChange(false);
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
		</>
	);
}
