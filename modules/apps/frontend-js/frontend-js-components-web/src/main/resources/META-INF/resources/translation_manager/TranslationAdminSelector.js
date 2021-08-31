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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import TranslationAdminModal from './TranslationAdminModal';

// These variables are defined here, out of the component, to avoid
// unexpected re-renders

const emptyArray = [];
const noop = () => {};

const TranslationAdminSelector = ({
	activeLanguageIds: initialActiveLanguageIds = emptyArray,
	adminMode,
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		translated: Liferay.Language.get('translated'),
		untranslated: Liferay.Language.get('untranslated'),
	},
	availableLocales = emptyArray,
	defaultLocaleId,
	onActiveLanguageIdsChange = noop,
	selectedLocaleId: initialSelectedLocaleId,
	showOnlyFlags,
	small = false,
	translations,
}) => {
	const [activeLanguageIds, setActiveLanguageIds] = useState(
		initialActiveLanguageIds
	);
	const [selectedLocaleId, setSelectedLocaleId] = useState(
		initialSelectedLocaleId
	);
	const [selectorDropdownActive, setSelectorDropdownActive] = useState(false);
	const [translationModalVisible, setTranslationModalVisible] = useState(
		false
	);

	const handleCloseTranslationModal = (activeLanguageIds) => {
		setActiveLanguageIds(activeLanguageIds);
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
		const id = selectedLocaleId ?? defaultLocaleId;

		return availableLocales.find(
			(availableLocale) => availableLocale.id === id
		);
	}, [availableLocales, defaultLocaleId, selectedLocaleId]);

	useEffect(() => {
		onActiveLanguageIdsChange(activeLanguageIds);
	}, [activeLanguageIds, onActiveLanguageIdsChange]);

	useEffect(() => {
		setActiveLanguageIds(initialActiveLanguageIds);
	}, [initialActiveLanguageIds]);

	useEffect(() => {
		setSelectedLocaleId(initialSelectedLocaleId);
	}, [initialSelectedLocaleId]);

	return (
		<>
			<TranslationAdminModal
				activeLanguageIds={activeLanguageIds}
				ariaLabels={ariaLabels}
				availableLocales={availableLocales}
				defaultLocaleId={defaultLocaleId}
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
							activeLocale.id === defaultLocaleId;

						const localeValue = translations[label];

						return (
							<ClayDropDown.Item
								key={activeLocale.id}
								onClick={() => {
									setSelectedLocaleId(activeLocale.id);
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
														: ariaLabels.untranslated}
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
};

TranslationAdminSelector.propTypes = {
	activeLanguageIds: PropTypes.arrayOf(PropTypes.string),
	adminMode: PropTypes.bool,
	ariaLabels: PropTypes.shape({
		default: PropTypes.string,
		manageTranslations: PropTypes.string,
		managementToolbar: PropTypes.string,
		tranlated: PropTypes.string,
		untranslated: PropTypes.string,
	}),
	availableLocales: PropTypes.arrayOf(PropTypes.object).isRequired,
	defaultLocaleId: PropTypes.string.isRequired,
	showOnlyFlags: PropTypes.bool,
	small: PropTypes.bool,
	translations: PropTypes.object,
};

export default TranslationAdminSelector;
