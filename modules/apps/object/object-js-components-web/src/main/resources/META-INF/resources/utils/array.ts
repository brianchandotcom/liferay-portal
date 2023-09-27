/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel, stringIncludesQuery} from './string';

/**
 * Filter an Array by checking if the String includes the query
 */

interface FilterArrayByQueryProps<T> {
	array: T[];
	creationLanguageId?: Liferay.Language.Locale;
	query: string;
	str: string;
}

type LocalizedObject<T> = {
	[key: string]: T;
};

export function filterArrayByQuery<T>({
	array,
	creationLanguageId,
	query,
	str,
}: FilterArrayByQueryProps<T>) {
	return array.filter((item) => {
		if (str === 'label') {
			const localizedValue = ((item as unknown) as LocalizedObject<
				LocalizedValue<string>
			>)[str];

			const localizedLabels = localizedValue as LocalizedValue<string>;

			let label = getLocalizableLabel(
				creationLanguageId as Liferay.Language.Locale,
				localizedLabels
			);

			if (!label) {
				label = localizedLabels[
					((item as unknown) as LocalizedObject<
						Liferay.Language.Locale
					>).defaultLanguageId as Liferay.Language.Locale
				] as string;
			}

			return stringIncludesQuery(label, query);
		}

		const comparisonString = ((item as unknown) as LocalizedObject<string>)[
			str
		];

		return stringIncludesQuery(comparisonString, query);
	});
}
