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
	activeLocales: initialActiveLocales = emptyArray,
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
	const [activeLocales, setActiveLocales] = useState(initialActiveLocales);
	const [lastDeletedLocaleId, setLastDeletedLocaleId] = useState(null);
	const [visible, setVisible] = useState(initialVisible);

	const {observer} = useModal({
		onClose: () => {
			setVisible(false);
			onClose([...activeLocales]);
		},
	});

	const handleAddLocale = (localeId) => {
		setActiveLocales([...activeLocales, localeId]);
		setLastDeletedLocaleId(null);
	};

	const handleClearRestoreLocale = () => {
		setLastDeletedLocaleId(null);
	};

	const handleRemoveLocale = (localeId) => {
		const newActiveLocales = [...activeLocales];
		newActiveLocales.splice(activeLocales.indexOf(localeId), 1);
		setActiveLocales(newActiveLocales);
		setLastDeletedLocaleId(localeId);
	};

	const handleRestoreLocale = () => {
		handleAddLocale(lastDeletedLocaleId);
		setLastDeletedLocaleId(null);
	};

	useEffect(() => {
		setActiveLocales(initialActiveLocales);
	}, [initialActiveLocales]);

	useEffect(() => {
		setVisible(initialVisible);
	}, [initialVisible]);

	return (
		<>
			{visible && (
				<ClayModal observer={observer}>
					<TranslationAdminContent
						activeLocales={activeLocales}
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
	activeLocales: PropTypes.arrayOf(PropTypes.string),
	arialLabels: PropTypes.shape({
		default: PropTypes.string,
		manageTranslations: PropTypes.string,
		managementToolbar: PropTypes.string,
		tranlated: PropTypes.string,
		untranslated: PropTypes.string,
	}),
	availableLocales: PropTypes.arrayOf(PropTypes.object).isRequired,
	defaultLocaleId: PropTypes.string.isRequired,
	onActiveLocalesChange: PropTypes.func,
	translations: PropTypes.object,
	visible: PropTypes.bool,
};

export default TranslationAdminModal;
