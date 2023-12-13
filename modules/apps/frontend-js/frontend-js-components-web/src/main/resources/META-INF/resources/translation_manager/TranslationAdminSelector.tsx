/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React, {useEffect, useMemo, useState} from 'react';

import {Locale, Translations} from './TranslationAdminContent';
import TranslationAdminModal from './TranslationAdminModal';
import TranslationAdminStatusLabel from './TranslationAdminStatusLabel';

const DISPLAY_TYPE = {
	default: 'default',
	horizontal: 'horizontal',
} as const;

interface IProps extends Translations {
	adminMode?: boolean;
	displayType?: 'default' | 'horizontal';
	onActiveLanguageIdsChange?: (
		languageIds: Liferay.Language.Locale[]
	) => void;
	onSelectedLanguageIdChange?: (languageId: Liferay.Language.Locale) => void;
	selectedLanguageId: Liferay.Language.Locale;
	showOnlyFlags?: boolean;
	small?: boolean;
	translationProgress?: TranslationProgress | null;
}

export interface TranslationProgress {
	totalItems: number;
	translatedItems: Record<string, number>;
}

// These variables are defined here, out of the component, to avoid
// unexpected re-renders

const noop = () => {};

export default function TranslationAdminSelector({
	activeLanguageIds: initialActiveLanguageIds = [],
	adminMode,
	ariaLabels = {
		manageTranslations: Liferay.Language.get('manage-translations'),
	},
	availableLocales = [],
	defaultLanguageId,
	displayType = DISPLAY_TYPE.default,
	onActiveLanguageIdsChange = noop,
	onSelectedLanguageIdChange = noop,
	selectedLanguageId: initialSelectedLanguageId,
	showOnlyFlags,
	small = false,
	translations = null,
	translationProgress = null,
}: IProps) {
	const [activeLanguageIds, setActiveLanguageIds] = useState<
		Liferay.Language.Locale[]
	>(initialActiveLanguageIds);
	const [selectedLanguageId, setSelectedLanguageId] = useState<
		Liferay.Language.Locale
	>(initialSelectedLanguageId);
	const [selectorDropdownActive, setSelectorDropdownActive] = useState(false);
	const [translationModalVisible, setTranslationModalVisible] = useState(
		false
	);

	const handleCloseTranslationModal = (
		activeLanguageIds: Liferay.Language.Locale[]
	) => {
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
					Liferay.FeatureFlags['LPS-114700'] &&
					displayType === DISPLAY_TYPE.horizontal ? (
						<ClayButton
							className="btn-block form-control-select"
							displayType="secondary"
							size="sm"
						>
							<span className="inline-item-before">
								<ClayIcon symbol={selectedLocale.symbol} />
							</span>

							{selectedLocale.label}
						</ClayButton>
					) : (
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
					)
				}
			>
				<ClayDropDown.ItemList>
					{activeLocales.map(({displayName, id, label, symbol}) => {
						return (
							<ClayDropDown.Item
								key={id}
								onClick={() => {
									setSelectedLanguageId(id);
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
												symbol={symbol}
											/>

											<span>{label}</span>
										</ClayLayout.ContentSection>
									</ClayLayout.ContentCol>

									{!showOnlyFlags && (
										<TranslationAdminStatusLabel
											defaultLanguageId={
												defaultLanguageId
											}
											labels={{
												default: ariaLabels.default,
												notTranslated:
													ariaLabels.notTranslated,
												translated:
													ariaLabels.translated,
											}}
											languageId={id}
											languageName={displayName}
											localeValue={
												translations
													? translations[id]
													: null
											}
											translationProgress={
												translationProgress
											}
										/>
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
