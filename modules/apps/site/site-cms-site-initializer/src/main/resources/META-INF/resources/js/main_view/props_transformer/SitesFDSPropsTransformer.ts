/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';

import connectSitesAction from './actions/connectSitesAction';
import SiteRenderer from './cell_renderers/SiteRenderer';
import addOnClickToCreationMenuItems from './utils/addOnClickToCreationMenuItems';

const ACTIONS = {
	connectSites: connectSitesAction,
};

export default function SitesFDSPropsTransformer({
	creationMenu,
	...otherProps
}: {
	creationMenu: any;
	otherProps: any;
}) {
	return {
		...otherProps,
		creationMenu: {
			...creationMenu,
			primaryItems: addOnClickToCreationMenuItems(
				creationMenu.primaryItems,
				ACTIONS
			),
		},
		customRenderers: {
			tableCell: [
				{
					component: SiteRenderer,
					name: 'siteTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
	};
}
