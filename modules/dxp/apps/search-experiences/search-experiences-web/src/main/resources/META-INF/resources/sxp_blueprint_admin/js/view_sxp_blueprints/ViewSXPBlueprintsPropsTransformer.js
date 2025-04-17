/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {download} from '../shared/fdsPropsTransformerActions';
import {DEFAULT_HEADERS} from '../utils/fetch/fetch_data';
import {SubtypeTableCell, TypeTableCell} from './TypeTableCell';

export default function propsTransformer({...otherProps}) {
	const subtypeCellRenderer = {
		component: SubtypeTableCell,
		name: 'subtypeCellRenderer',
		type: 'internal',
	};
	const typeCellRenderer = {
		component: TypeTableCell,
		name: 'typeCellRenderer',
		type: 'internal',
	};

	return {
		...otherProps,
		customRenderers: {
			tableCell: [typeCellRenderer, subtypeCellRenderer],
		},
		onActionDropdownItemClick({action, itemData}) {
			if (action.data.id === 'export') {
				download(
					`/o/search-experiences-rest/v1.0/sxp-blueprints/${itemData.id}/export`,
					{headers: DEFAULT_HEADERS, method: 'GET'},
					itemData.title
				);
			}
		},
	};
}
