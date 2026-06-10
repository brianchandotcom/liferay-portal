/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {useEffect, useState} from 'react';

import {fixLocaleKeys} from '../../ListTypeDefinition/utils';

export function useListTypeEntries(
	listTypeDefinitionId?: number
): ListTypeEntry[] | undefined {
	const [listTypeEntries, setListTypeEntries] = useState<ListTypeEntry[]>();

	useEffect(() => {
		let discardResults = false;

		setListTypeEntries(undefined);

		if (listTypeDefinitionId) {
			API.getListTypeDefinitionListTypeEntries(listTypeDefinitionId).then(
				(items) => {
					if (!discardResults) {
						setListTypeEntries(
							items.map((item) => ({
								...item,
								name_i18n: fixLocaleKeys(item.name_i18n),
							}))
						);
					}
				}
			);
		}

		return () => {
			discardResults = true;
		};
	}, [listTypeDefinitionId]);

	return listTypeEntries;
}
