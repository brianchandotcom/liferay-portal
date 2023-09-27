/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import TranslationAdminContent, {Translations} from './TranslationAdminContent';

interface IProps extends Translations {
	onClose: (languageIds: string[]) => void;
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

	const handleAddLocale = (localeId: string) => {
		setActiveLanguageIds([...activeLanguageIds, localeId]);
	};

	const handleCancel = () => {
		setVisible(false);
		setActiveLanguageIds([...initialActiveLanguageIds]);
		onClose([...initialActiveLanguageIds]);
	};

	const handleDone = () => {
		setVisible(false);
		onClose([...activeLanguageIds]);
	};

	const handleRemoveLocale = (localeId: string) => {
		const newActiveLanguageIds = [...activeLanguageIds];
		newActiveLanguageIds.splice(activeLanguageIds.indexOf(localeId), 1);
		setActiveLanguageIds(newActiveLanguageIds);
	};

	const {observer} = useModal({
		onClose: handleCancel,
	});

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
