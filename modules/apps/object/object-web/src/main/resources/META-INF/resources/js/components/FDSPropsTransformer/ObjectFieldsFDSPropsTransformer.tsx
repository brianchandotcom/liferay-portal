/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SourceDataRenderer from './FDSDataRenderers/SourceDataRenderer';

export default function ObjectFieldsFDSPropsTransformer({...otherProps}) {
	return {
		...otherProps,
		customDataRenderers: {
			objectFieldSourceDataRenderer: SourceDataRenderer,
		},
		onActionDropdownItemClick({
			action,
			itemData,
		}: {
			action: {data: {id: string}};
			itemData: {id: string};
		}) {
			if (action.data.id === 'deleteObjectField') {
				Liferay.fire('deleteObjectField', {itemData});
			}
		},
	};
}
