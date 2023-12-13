/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {Ref, useEffect, useMemo, useState} from 'react';

import useId from '../hooks/useId';
import {Locale, Translations} from './TranslationAdminContent';
import TranslationAdminModal from './TranslationAdminModal';
import TranslationAdminStatusLabel from './TranslationAdminStatusLabel';

const DISPLAY_TYPE = {
	default: 'default',
	horizontal: 'horizontal',
} as const;

type DisplayType = 'default' | 'horizontal';

interface IProps extends Translations {
	adminMode?: boolean;
	displayType?: DisplayType;
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

interface TriggerButtonProps {
	displayType: DisplayType;
	selectedItem: Locale;
	small: boolean;
}

// These variables are defined here, out of the component, to avoid
// unexpected re-renders

const noop = () => {};

const TriggerButton = React.forwardRef(
	(
		{displayType, selectedItem, small, ...props}: TriggerButtonProps,
		ref: Ref<HTMLButtonElement>
	) => {
		const ariaLabelButton = sub(
			Liferay.Language.get('select-a-language.-current-language-x'),
			selectedItem.displayName
		);

		return Liferay.FeatureFlags['LPS-114700'] &&
			displayType === DISPLAY_TYPE.horizontal ? (
			<ClayButton
				{...props}
				aria-label={ariaLabelButton}
				className="btn-block form-control-select"
				displayType="secondary"
				ref={ref}
				size="sm"
			>
				<span className="inline-item-before">
					<ClayIcon symbol={selectedItem.symbol} />
				</span>

				<span aria-hidden="true">{selectedItem.label}</span>
			</ClayButton>
		) : (
			<ClayButton
				{...props}
				aria-label={ariaLabelButton}
				displayType="secondary"
				monospaced
				ref={ref}
				small={small}
				title={Liferay.Language.get('select-language')}
			>
				<span className="inline-item">
					<ClayIcon symbol={selectedItem.symbol} />
				</span>

				<span aria-hidden="true" className="btn-section">
					{selectedItem.label}
				</span>
			</ClayButton>
		);
	}
);

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
	const selectorId = useId();
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

	if (Liferay.FeatureFlags['LPS-114700'] && !adminMode) {
		return (
			<Picker
				as={TriggerButton}
				displayType={displayType}
				id={selectorId}
				items={activeLocales}
				onSelectionChange={(id: React.Key) => {
					setSelectedLanguageId(id as Liferay.Language.Locale);
				}}
				selectedItem={activeLocales.find(
					(locale) => locale.id === selectedLanguageId
				)}
				selectedKey={selectedLanguageId}
			>
				{(item) => (
					<Option key={item.id} textValue={item.label}>
						<ClayLayout.ContentRow containerElement="span">
							<ClayLayout.ContentCol
								containerElement="span"
								expand
							>
								<ClayLayout.ContentSection>
									<ClayIcon
										className="inline-item-before"
										symbol={item.symbol}
									/>

									{item.label}
								</ClayLayout.ContentSection>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</Option>
				)}
			</Picker>
		);
	}

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
					<TriggerButton
						displayType={displayType}
						selectedItem={selectedLocale}
						small={small}
					/>
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
