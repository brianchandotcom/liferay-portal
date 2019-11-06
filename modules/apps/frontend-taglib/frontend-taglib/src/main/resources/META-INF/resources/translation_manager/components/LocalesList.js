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
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import React from 'react';

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
	onLocaleRemoved
}) {
	return (
		<>
			{Array.from(availableLocales.values()).map(locale => (
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
