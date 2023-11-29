/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

const TriggerLabel = React.forwardRef(
	(
		{selectedItem, ...otherProps}: {selectedItem: Language},
		ref: React.LegacyRef<HTMLButtonElement>
	) => {
		return (
			<button
				{...otherProps}
				className="btn btn-block btn-secondary btn-sm form-control-select"
				ref={ref}
			>
				<span className="inline-item-before">
					<ClayIcon symbol={selectedItem.icon} />
				</span>

				{selectedItem.label}
			</button>
		);
	}
);
export interface Language {
	icon: string;
	id: Liferay.Language.Locale;
	label: Liferay.Language.Locale;
	translations: number;
}

export interface Translations {
	[key: string]: Record<Liferay.Language.Locale, string>;
}

interface Props {

	/**
	 * Current default language
	 */
	defaultLanguageId: Liferay.Language.Locale;

	/**
	 * List of languages to allow localization for
	 */
	languages: Language[];

	/**
	 * Callback that gets called when a selected language gets changed
	 */
	onSelectedLanguageChange: (item: any) => void;

	/**
	 * Currently selected language
	 */
	selectedLanguageId: Liferay.Language.Locale;

	/**
	 * Translations provided to the component to be used and modified by it
	 */
	translations: Translations;
}

export default function TranslationSelector({
	defaultLanguageId,
	languages,
	onSelectedLanguageChange,
	selectedLanguageId,
	translations,
}: Props) {
	const selectorId = useId();

	const getSelectedLanguage = (id: React.Key) =>
		languages.find((language) => language.id === id)!;

	const [selectedLanguage, setSelectedLanguage] = useState<Language>(
		getSelectedLanguage(selectedLanguageId)!
	);

	const items = translations
		? languages.map((language) => ({
				...language,
				translations: Object.values(translations).filter(
					(translation) => translation[language.id]
				).length,
		  }))
		: languages;

	return (
		<Picker
			as={TriggerLabel}
			id={selectorId}
			items={items}
			onSelectionChange={(key: React.Key) => {
				onSelectedLanguageChange(key);
				setSelectedLanguage(getSelectedLanguage(key));
			}}
			selectedItem={selectedLanguage}
			selectedKey={selectedLanguage.id}
		>
			{(item) => (
				<Option key={item.id} textValue={item.label}>
					<ClayLayout.ContentRow containerElement="span">
						<ClayLayout.ContentCol containerElement="span" expand>
							<ClayLayout.ContentSection>
								<ClayIcon
									className="inline-item-before"
									symbol={item.icon}
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
