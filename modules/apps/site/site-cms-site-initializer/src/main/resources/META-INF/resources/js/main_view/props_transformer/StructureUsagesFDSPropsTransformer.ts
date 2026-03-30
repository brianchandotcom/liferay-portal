/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';

import StatusLabel from '../../common/components/StatusLabel';
import {getScopeExternalReferenceCode} from '../../common/utils/getScopeExternalReferenceCode';
import AuthorRenderer from './cell_renderers/AuthorRenderer';
import SpaceRendererWithCache from './cell_renderers/SpaceRendererWithCache';
import TypeRenderer from './cell_renderers/TypeRenderer';

export default function StructureUsagesFDSPropsTransformer({
	...otherProps
}: {
	otherProps: any;
}) {
	return {
		...otherProps,
		customRenderers: {
			tableCell: [
				{
					component: AuthorRenderer,
					name: 'authorTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) =>
						SpaceRendererWithCache({
							scopeKey: itemData.embedded.scopeKey,
							spaceExternalReferenceCode:
								getScopeExternalReferenceCode(itemData),
						}),
					name: 'spaceTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: TypeRenderer,
					name: 'typeTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({value}) => StatusLabel(value),
					name: 'statusTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		hideManagementBarInEmptyState: true,
	};
}
