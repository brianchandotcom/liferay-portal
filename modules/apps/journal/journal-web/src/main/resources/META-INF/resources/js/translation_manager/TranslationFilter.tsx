/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import React, {LegacyRef, useState} from 'react';

import {TranslationManagerProps} from './Types';
import useTranslationProgress from './useTranslationProgress';

const Trigger = React.forwardRef(
	({children, ...otherProps}, ref: LegacyRef<HTMLButtonElement>) => (
		<button
			{...otherProps}
			className="btn btn-block btn-secondary btn-sm form-control-select"
			ref={ref}
			tabIndex={0}
		>
			{children}
		</button>
	)
);

export default function TranslationFilter({
	defaultLanguageId: initialDefaultLanguageId,
	fields: initialFields,
	locales,
	namespace,
	selectedLanguageId: initialSelectedLanguageId,
}: TranslationManagerProps) {
	const [active, setActive] = useState(false);
	const [selectedKey, setSelectedKey] = useState(() => 'all-fields');

	const {
		defaultLanguageId,
		selectedLanguageId,
		updateTranslations,
	} = useTranslationProgress({
		defaultLanguageId: initialDefaultLanguageId,
		fields: initialFields,
		locales,
		namespace,
		selectedLanguageId: initialSelectedLanguageId,
	});

	const handleSelection = (option: React.Key) => {
		Liferay.fire('inputLocalized:translationFilterChange', {option})
		setSelectedKey(option as string)
	}

	return (
		<>
			<Picker
				active={active}
				as={Trigger}
				disabled={defaultLanguageId === selectedLanguageId}
				id="picker"
				onActiveChange={(active: boolean) => {
					if (active) {
						updateTranslations();
					}

					setActive(active);
				}}
				onSelectionChange={handleSelection}
				selectedKey={selectedKey}
			>
				<Option key="all-fields">
					{Liferay.Language.get('all-fields')}
				</Option>

				<Option key="translated">
					{Liferay.Language.get('translated')}
				</Option>

				<Option key="untranslated">
					{Liferay.Language.get('untranslated')}
				</Option>
			</Picker>
		</>
	);
}
