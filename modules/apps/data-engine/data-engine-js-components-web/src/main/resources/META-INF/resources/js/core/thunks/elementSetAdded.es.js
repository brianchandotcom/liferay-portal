/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {makeFetch} from '../../utils/fetch.es';

export function elementSetAdded({
	definitionURL,
	editingLanguageId,
	elementSetId,
	indexes,
	namespace,
}) {
	return async (dispatch) => {
		const {pages} = await makeFetch({
			method: 'GET',
			url: `${definitionURL}?ddmStructureId=${elementSetId}&languageId=${editingLanguageId}&portletNamespace=${namespace}`,
		});

		dispatch({
			payload: {
				elementSetPages: pages,
				indexes,
			},
			type: 'element_set_add',
		});
	};
}
