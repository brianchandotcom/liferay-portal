/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {removeEmptyRows as removeEmptyRowsUtil} from '../../utils/FormSupport.es';
import {removeField} from '../../utils/fieldSupport';
import {formatRules} from '../../utils/rulesSupport';

export function handleFieldDeleted(
	props,
	state,
	{activePage, editRule = true, fieldName, removeEmptyRows = true}
) {
	const {pages} = state;

	if (activePage === undefined) {
		activePage = state.activePage;
	}

	const newPages = pages.map((page, pageIndex) => {
		if (activePage === pageIndex) {
			const pagesWithFieldRemoved = removeField(
				props,
				pages,
				fieldName,
				removeEmptyRows
			);

			return {
				...page,
				rows: removeEmptyRows
					? removeEmptyRowsUtil(pagesWithFieldRemoved, pageIndex)
					: pagesWithFieldRemoved[pageIndex].rows,
			};
		}

		return page;
	});

	return {
		focusedField: {},
		pages: newPages,
		rules: editRule ? formatRules(newPages, state.rules) : state.rules,
	};
}

export default handleFieldDeleted;
