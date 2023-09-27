/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import React, {useEffect, useMemo, useState} from 'react';

import {Locale, Translations} from './TranslationAdminContent';
import TranslationAdminModal from './TranslationAdminModal';

interface IProps extends Translations {
	adminMode?: boolean;
	onActiveLanguageIdsChange?: (languageIds: string[]) => void;
	onSelectedLanguageIdChange?: (languageId: string) => void;
	selectedLanguageId: string;
	showOnlyFlags?: boolean;
	small?: boolean;
}

// These variables are defined here, out of the component, to avoid
// unexpected re-renders

const noop = () => {};

export default function TranslationAdminSelector({
	activeLanguageIds: initialActiveLanguageIds = [],
	adminMode,
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		notTranslated: Liferay.Language.get('not-translated'),
		translated: Liferay.Language.get('translated'),
	},
	availableLocales = [],
	defaultLanguageId,
	onActiveLanguageIdsChange = noop,
	onSelectedLanguageIdChange = noop,
	selectedLanguageId: initialSelectedLanguageId,
	showOnlyFlags,
	small = false,
	translations = {},
}: IProps) {
	const [activeLanguageIds, setActiveLanguageIds] = useState(
		initialActiveLanguageIds
	);
	const [selectedLanguageId, setSelectedLanguageId] = useState(
		initialSelectedLanguageId
	);
	const [selectorDropdownActive, setSelectorDropdownActive] = useState(false);
	const [translationModalVisible, setTranslationModalVisible] = useState(
		false
	);

	const handleCloseTranslationModal = (activeLanguageIds: string[]) => {
		setActiveLanguageIds(activeLanguageIds);

		if (!activeLanguageIds.includes(selectedLanguageId)) {
			setSelectedLanguageId(defaultLanguageId);
		}

		setTranslationModalVisible(false);
	};

	const activeLocales = useMemo(
		() =>
			availableLocales.filter((availableLocale) =>
				activeLanguageIds.includes(availableLocale.id)
			),
		[availableLocales, activeLanguageIds]
	);

	const selectedLocale = useMemo(() => {
		const id = selectedLanguageId ?? defaultLanguageId;

		return availableLocales.find(
			(availableLocale) => availableLocale.id === id
		) as Locale;
	}, [availableLocales, defaultLanguageId, selectedLanguageId]);

	useEffect(() => {
		onActiveLanguageIdsChange(activeLanguageIds);
	}, [activeLanguageIds, onActiveLanguageIdsChange]);

	useEffect(() => {
		onSelectedLanguageIdChange(selectedLanguageId);
	}, [selectedLanguageId, onSelectedLanguageIdChange]);

	useEffect(() => {
		setActiveLanguageIds(initialActiveLanguageIds);
	}, [initialActiveLanguageIds]);

	useEffect(() => {
		setSelectedLanguageId(initialSelectedLanguageId);
	}, [initialSelectedLanguageId]);

	return (
		<>
			<TranslationAdminModal
				activeLanguageIds={activeLanguageIds}
				ariaLabels={ariaLabels}
				availableLocales={availableLocales}
				defaultLanguageId={defaultLanguageId}
				onClose={handleCloseTranslationModal}
				translations={translations}
				visible={translationModalVisible}
			/>

			<ClayDropDown
				active={selectorDropdownActive}
				onActiveChange={setSelectorDropdownActive}
				trigger={
					<ClayButton
						displayType="secondary"
						monospaced
						small={small}
						title={Liferay.Language.get(
							'select-translation-language'
						)}
					>
						<span className="inline-item">
							<ClayIcon symbol={selectedLocale.symbol} />
						</span>

						<span className="btn-section">
							{selectedLocale.label}
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{activeLocales.map((activeLocale) => {
						const label = activeLocale.label;

						const isDefaultLocale =
							activeLocale.id === defaultLanguageId;

						const localeValue = translations[activeLocale.id];

						return (
							<ClayDropDown.Item
								key={activeLocale.id}
								onClick={() => {
									setSelectedLanguageId(activeLocale.id);
									setSelectorDropdownActive(false);
								}}
							>
								<ClayLayout.ContentRow containerElement="span">
									<ClayLayout.ContentCol
										containerElement="span"
										expand
									>
										<ClayLayout.ContentSection>
											<ClayIcon
												className="inline-item inline-item-before"
												symbol={activeLocale.symbol}
											/>

											{label}
										</ClayLayout.ContentSection>
									</ClayLayout.ContentCol>

									{!showOnlyFlags && (
										<ClayLayout.ContentCol containerElement="span">
											<ClayLayout.ContentSection>
												<ClayLabel
													displayType={
														isDefaultLocale
															? 'info'
															: localeValue
															? 'success'
															: 'warning'
													}
												>
													{isDefaultLocale
														? ariaLabels.default
														: localeValue
														? ariaLabels.translated
														: ariaLabels.notTranslated}
												</ClayLabel>
											</ClayLayout.ContentSection>
										</ClayLayout.ContentCol>
									)}
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						);
					})}

					{adminMode && (
						<>
							<ClayDropDown.Divider />
							<ClayDropDown.Item
								data-testid="translation-modal-trigger"
								onClick={() => setTranslationModalVisible(true)}
							>
								<ClayLayout.ContentRow containerElement="span">
									<ClayLayout.ContentCol
										containerElement="span"
										expand
									>
										<ClayLayout.ContentSection>
											<ClayIcon
												className="inline-item inline-item-before"
												symbol="automatic-translate"
											/>

											{ariaLabels.manageTranslations}
										</ClayLayout.ContentSection>
									</ClayLayout.ContentCol>
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						</>
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}
