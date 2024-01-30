/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import React, {useMemo} from 'react';

const Locale = ({children, editingLocale, locale, onLocaleClicked}) => (
	<ClayTabs.Item
		active={editingLocale === locale.id}
		onClick={() => onLocaleClicked && onLocaleClicked(locale)}
	>
		<ClayIcon className="inline-item-before" symbol={locale.icon} />

		<span className="inline-item-before">{locale.label}</span>

		{children}
	</ClayTabs.Item>
);

export default function LocalesList({
	availableLocales,
	changeableDefaultLanguage,
	defaultLocale,
	editingLocale,
	onLocaleClicked,
	onLocaleRemoved,
}) {
	const sortedLocales = useMemo(() => {
		const availableLocalesArray = Array.from(availableLocales.values());

		if (
			!availableLocalesArray.some((locale) => locale.id === defaultLocale)
		) {
			return availableLocalesArray;
		}

		return [
			availableLocalesArray.find((locale) => locale.id === defaultLocale),
			...availableLocalesArray.filter(
				(locale) => locale.id !== defaultLocale
			),
		];
	}, [availableLocales, defaultLocale]);

	return (
		<>
			{sortedLocales.map((locale) => (
				<Locale
					editingLocale={editingLocale}
					key={locale.id}
					locale={locale}
					onLocaleClicked={() =>
						onLocaleClicked && onLocaleClicked(locale)
					}
				>
					{(changeableDefaultLanguage &&
						defaultLocale === locale.id) ||
						(locale.id !== defaultLocale && (
							<ClayButton
								displayType="unstyled"
								onClick={() =>
									onLocaleRemoved && onLocaleRemoved(locale)
								}
								small
							>
								<ClayIcon symbol="times-small" />
							</ClayButton>
						))}
				</Locale>
			))}
		</>
	);
}
