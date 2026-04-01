/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';

import SpaceService from '../../../common/services/SpaceService';
import {ViewDashboardContext, initialLanguage} from '../ViewDashboardContext';
import {FilterDropdown} from './FilterDropdown';

type AvailableLocales = Exclude<
	Liferay.Language.Locale,
	'zh_Hans_CN' | 'zh_Hant_TW' | 'zh_TW'
>;

export const localizations: Record<AvailableLocales, string> = {
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

const initialLanguages = [initialLanguage, ...availableLanguages];

const LanguagesDropdown: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	className,
}) => {
	const {
		changeLanguage,
		filters: {language, space},
	} = useContext(ViewDashboardContext);

	const [languages, setLanguages] = useState(initialLanguages);
	const [dropdownActive, setDropdownActive] = useState(false);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (space.value === 'all') {
			setLanguages(initialLanguages);

			return;
		}

		const fetchSpaceLanguages = async () => {
			setLoading(true);

			try {
				const {settings} = await SpaceService.getSpace(
					space.externalReferenceCode as string
				);

				if (settings?.availableLanguageIds) {
					const availableLanguageIds =
						settings.availableLanguageIds.map((languageId) =>
							languageId.replace('-', '_')
						);

					const filteredLanguages = availableLanguages.filter(
						({value}) => availableLanguageIds.includes(value)
					);

					setLanguages([initialLanguage, ...filteredLanguages]);
				}
				else {
					setLanguages(initialLanguages);
				}
			}
			catch (error) {
				console.error(error);

				setLanguages(initialLanguages);
			}
			finally {
				setLoading(false);
			}
		};

		fetchSpaceLanguages();
	}, [space.value, space.externalReferenceCode]);

	return (
		<FilterDropdown
			active={dropdownActive}
			borderless={false}
			className={className}
			filterByValue="languages"
			icon="automatic-translate"
			items={languages}
			loading={loading}
			onActiveChange={() => setDropdownActive((prevState) => !prevState)}
			onSearch={(value) => {
				setLanguages(
					value
						? languages.filter(({label}) =>
								label
									.toLowerCase()
									.includes(value.toLowerCase())
							)
						: languages
				);
			}}
			onSelectItem={(item) => {
				changeLanguage(item);

				setDropdownActive(false);
			}}
			selectedItem={language}
			title={Liferay.Language.get('filter-by-languages')}
		/>
	);
};

export {LanguagesDropdown};
