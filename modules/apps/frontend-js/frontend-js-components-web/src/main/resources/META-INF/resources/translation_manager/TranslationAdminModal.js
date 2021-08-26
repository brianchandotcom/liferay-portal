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

import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import TranslationAdminContent from './TranslationAdminContent';

const emptyArray = [];
const noop = () => {};

const TranslationAdminModal = ({
	activeLanguageIds: initialActiveLanguageIds = emptyArray,
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		translated: Liferay.Language.get('translated'),
		untranslated: Liferay.Language.get('untranslated'),
	},
	availableLocales = emptyArray,
	defaultLocaleId,
	onClose = noop,
	translations,
	visible: initialVisible,
}) => {
	const [activeLanguageIds, setActiveLanguageIds] = useState(
		initialActiveLanguageIds
	);
	const [lastDeletedLocaleId, setLastDeletedLocaleId] = useState(null);
	const [visible, setVisible] = useState(initialVisible);

	const {observer} = useModal({
		onClose: () => {
			setVisible(false);
			onClose([...activeLanguageIds]);
		},
	});

	const handleAddLocale = (localeId) => {
		setActiveLanguageIds([...activeLanguageIds, localeId]);
		setLastDeletedLocaleId(null);
	};

	const handleClearRestoreLocale = () => {
		setLastDeletedLocaleId(null);
	};

	const handleRemoveLocale = (localeId) => {
		const newActiveLanguageIds = [...activeLanguageIds];
		newActiveLanguageIds.splice(activeLanguageIds.indexOf(localeId), 1);
		setActiveLanguageIds(newActiveLanguageIds);
		setLastDeletedLocaleId(localeId);
	};

	const handleRestoreLocale = () => {
		handleAddLocale(lastDeletedLocaleId);
		setLastDeletedLocaleId(null);
	};

	useEffect(() => {
		setActiveLanguageIds(initialActiveLanguageIds);
	}, [initialActiveLanguageIds]);

	useEffect(() => {
		setVisible(initialVisible);
	}, [initialVisible]);

	return (
		<>
			{visible && (
				<ClayModal observer={observer}>
					<TranslationAdminContent
						activeLanguageIds={activeLanguageIds}
						ariaLabels={ariaLabels}
						availableLocales={availableLocales}
						defaultLocaleId={defaultLocaleId}
						lastDeletedLocaleId={lastDeletedLocaleId}
						onAddLocale={handleAddLocale}
						onClearRestoreLocale={handleClearRestoreLocale}
						onRemoveLocale={handleRemoveLocale}
						onRestoreLocale={handleRestoreLocale}
						translations={translations}
					/>
				</ClayModal>
			)}
		</>
	);
};

TranslationAdminModal.propTypes = {
	activeLanguageIds: PropTypes.arrayOf(PropTypes.string),
	arialLabels: PropTypes.shape({
		default: PropTypes.string,
		manageTranslations: PropTypes.string,
		managementToolbar: PropTypes.string,
		tranlated: PropTypes.string,
		untranslated: PropTypes.string,
	}),
	availableLocales: PropTypes.arrayOf(PropTypes.object).isRequired,
	defaultLocaleId: PropTypes.string.isRequired,
	onActiveLanguageIdsChange: PropTypes.func,
	translations: PropTypes.object,
	visible: PropTypes.bool,
};

export default TranslationAdminModal;
