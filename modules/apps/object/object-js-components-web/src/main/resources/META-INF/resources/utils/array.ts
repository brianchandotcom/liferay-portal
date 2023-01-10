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

import {getLocalizableLabel, stringIncludesQuery} from './string';

/**
 * Filter an Array by checking if the String includes the query
 */

interface FilterArrayByQueryProps<T> {
	array: T[];
	creationLanguageId?: Locale;
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
				creationLanguageId!,
				localizedLabels
			);

			if (!label) {
				label = localizedLabels[
					((item as unknown) as LocalizedObject<Locale>)
						.defaultLanguageId as Locale
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
