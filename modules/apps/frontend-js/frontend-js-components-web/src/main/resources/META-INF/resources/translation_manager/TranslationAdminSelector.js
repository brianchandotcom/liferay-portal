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
		notTranslated: Liferay.Language.get('not-translated'),
		translated: Liferay.Language.get('translated'),
	},
	availableLocales = emptyArray,
	defaultLanguageId,
	onActiveLanguageIdsChange = noop,
	onSelectedLanguageIdChange = noop,
	selectedLanguageId: initialSelectedLanguageId,
	showOnlyFlags,
	small = false,
	translations = {},
}) => {
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

	const handleCloseTranslationModal = (activeLanguageIds) => {
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
		);
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

						const localeValue = translations[label];

						return (
							<ClayDropDown.Item
								key={activeLocale.id}
								onClick={() => {
									setSelectedLanguageId(activeLocale.id);
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
};

TranslationAdminSelector.propTypes = {
	activeLanguageIds: PropTypes.arrayOf(PropTypes.string),
	adminMode: PropTypes.bool,
	ariaLabels: PropTypes.shape({
		default: PropTypes.string,
		manageTranslations: PropTypes.string,
		managementToolbar: PropTypes.string,
		notTranslated: PropTypes.string,
		tranlated: PropTypes.string,
	}),
	availableLocales: PropTypes.arrayOf(PropTypes.object).isRequired,
	defaultLanguageId: PropTypes.string.isRequired,
	showOnlyFlags: PropTypes.bool,
	small: PropTypes.bool,
	translations: PropTypes.object,
};

export default TranslationAdminSelector;
