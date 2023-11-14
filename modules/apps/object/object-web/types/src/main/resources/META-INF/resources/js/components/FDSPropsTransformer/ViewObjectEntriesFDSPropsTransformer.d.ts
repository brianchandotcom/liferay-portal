/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MultiselectPicklistDataRenderer from './FDSDataRenderers/MultiselectPicklistDataRenderer';
import StatusDataRenderer from './FDSDataRenderers/StatusDataRenderer';
export default function ViewObjectEntriesFDSPropsTransformer({
	...otherProps
}: {
	[x: string]: any;
}): {
	customDataRenderers: {
		multiselectPicklistDataRenderer: typeof MultiselectPicklistDataRenderer;
		statusDataRenderer: typeof StatusDataRenderer;
	};
	onActionDropdownItemClick({
		action,
		itemData,
	}: {
		action: {
			data: {
				id: string;
			};
		};
		itemData: any;
	}): void;
};
