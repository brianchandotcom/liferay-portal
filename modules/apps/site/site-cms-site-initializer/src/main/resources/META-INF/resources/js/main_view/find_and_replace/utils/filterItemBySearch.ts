/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReplaceItem} from '../contexts/FindAndReplaceContext';

export function filterItemsBySearch(
	items: ReplaceItem[] | null,
	search: string
) {
	return (items ?? []).filter(({fields}) =>
		fields.some(({value, value_i18n}) => {
			if (value?.includes(search)) {
				return true;
			}

			if (!value_i18n) {
				return false;
			}

			return Object.values(value_i18n).some((translation) =>
				translation?.includes(search)
			);
		})
	);
}
