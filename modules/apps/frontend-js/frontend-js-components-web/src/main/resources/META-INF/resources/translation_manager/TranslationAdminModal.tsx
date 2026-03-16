/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import TranslationAdminContent, {Translations} from './TranslationAdminContent';

interface IProps extends Translations {
	onClose: (languageIds: Liferay.Language.Locale[]) => void;
	visible?: boolean;
}

const noop = () => {};

export default function TranslationAdminModal({
	activeLanguageIds: initialActiveLanguageIds = [],
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		notTranslated: Liferay.Language.get('not-translated'),
		translated: Liferay.Language.get('translated'),
	},
	availableLocales = [],
	defaultLanguageId,
	onClose = noop,
	translations,
	visible: initialVisible,
}: IProps) {
	const [activeLanguageIds, setActiveLanguageIds] = useState(
		initialActiveLanguageIds
	);
	const [visible, setVisible] = useState(initialVisible);

	const savedRef = useRef(false);

	const handleModalClose = useCallback(() => {
		setVisible(false);

		if (savedRef.current) {
			onClose([...activeLanguageIds]);
		}
		else {
			setActiveLanguageIds([...initialActiveLanguageIds]);
			onClose([...initialActiveLanguageIds]);
		}
		savedRef.current = false;
	}, [activeLanguageIds, initialActiveLanguageIds, onClose]);

	const handleAddLocale = (localeId: Liferay.Language.Locale) => {
		setActiveLanguageIds([...activeLanguageIds, localeId]);
	};

	const {observer, onClose: closeModal} = useModal({
		onClose: handleModalClose,
	});

	const handleCancel = () => {
		closeModal();
	};

	const handleDone = () => {
		savedRef.current = true;

		closeModal();
	};

	const handleRemoveLocale = (localeId: Liferay.Language.Locale) => {
		const newActiveLanguageIds = [...activeLanguageIds];
		newActiveLanguageIds.splice(activeLanguageIds.indexOf(localeId), 1);
		setActiveLanguageIds(newActiveLanguageIds);
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
						defaultLanguageId={defaultLanguageId}
						onAddLocale={handleAddLocale}
						onCancel={handleCancel}
						onDone={handleDone}
						onRemoveLocale={handleRemoveLocale}
						translations={translations}
					/>
				</ClayModal>
			)}
		</>
	);
}
