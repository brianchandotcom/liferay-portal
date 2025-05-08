/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {ViewDashboardContext} from '../ViewDashboardContext';
import {FilterDropdown} from './FilterDropdown';

type AvailableLocales = Exclude<
	Liferay.Language.Locale,
	'zh_Hans_CN' | 'zh_Hant_TW' | 'zh_TW'
>;

const localizations: Record<AvailableLocales, string> = {
	ar_SA: Liferay.Language.get('language.ar'),
	ca_ES: Liferay.Language.get('language.ca'),
	de_DE: Liferay.Language.get('language.de'),
	en_US: Liferay.Language.get('language.en'),
	es_ES: Liferay.Language.get('language.es'),
	fi_FI: Liferay.Language.get('language.fi'),
	fr_FR: Liferay.Language.get('language.fr'),
	hu_HU: Liferay.Language.get('language.hu'),
	ja_JP: Liferay.Language.get('language.ja'),
	nl_NL: Liferay.Language.get('language.nl'),
	pt_BR: Liferay.Language.get('language.pt_BR'),
	sv_SE: Liferay.Language.get('language.sv'),
	zh_CN: Liferay.Language.get('language.zh_CN'),
};

/**
 * Must update the code below to iterate through the collection
 * of languages used by the assets. Expect to be represented as
 * an array.
 */
const availableLanguages = Object.entries(localizations).map(
	([locale, translation]) => ({
		label: translation,
		value: locale,
	})
);

const languages = [
	{
		label: Liferay.Language.get('all-languages'),
		value: 'all',
	},
	...availableLanguages,
];

const LanguagesDropdown: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	className,
}) => {
	const {
		changeLanguageDropdown,
		filters: {languageId},
	} = useContext(ViewDashboardContext);

	return (
		<FilterDropdown
			active={languageId}
			borderless={false}
			className={className}
			filterByValue="languages"
			icon="automatic-translate"
			items={languages}
			onSelectItem={(language) => changeLanguageDropdown(language.value)}
			triggerLabel={
				languages.find(({value}) => value === languageId)?.label ?? ''
			}
		/>
	);
};

export {LanguagesDropdown};
