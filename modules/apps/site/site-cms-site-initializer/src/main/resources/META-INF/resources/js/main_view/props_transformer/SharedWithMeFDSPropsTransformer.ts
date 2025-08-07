/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';

import shareAction from './actions/shareAction';
import AuthorRenderer from './cell_renderers/AuthorRenderer';
import SharedItemRenderer from './cell_renderers/SharedItemRenderer';

const OBJECT_ENTRY_FOLDER_CLASSNAME =
	'com.liferay.object.model.ObjectEntryFolder';

export default function SharedWithMeFDSPropsTransformer({
	additionalProps,
	itemsActions = [],
	...otherProps
}: {
	additionalProps: {
		autocompleteURL: string;
		collaboratorURLs: Record<string, string>;
	};
	itemsActions?: any[];
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
					component: SharedItemRenderer,
					name: 'sharedItemTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'actionLink') {
				return {
					...action,
					data: {
						...action.data,
						disableHeader: false,
						size: 'full-screen',
						title: 'View',
					},
					isVisible: () => false,
				};
			}
			else if (action?.data?.id === 'actionLinkEdit') {
				return {
					...action,
					isVisible: (item: any) =>
						Boolean(
							item?.className !== OBJECT_ENTRY_FOLDER_CLASSNAME &&
								item?.actionIds?.includes('UPDATE')
						),
				};
			}
			else if (action?.data?.id === 'share') {
				return {
					...action,
					isVisible: (item: any) => Boolean(item?.shareable),
				};
			}

			return action;
		}),
		onActionDropdownItemClick: ({
			action,
			itemData,
		}: {
			action: any;
			itemData: any;
		}) => {
			if (action?.data?.id === 'share') {
				const {autocompleteURL, collaboratorURLs} = additionalProps;

				shareAction({
					autocompleteURL,
					collaboratorURL: collaboratorURLs[itemData.className],
					creator: itemData.creator,
					itemId: itemData.classPK,
					title: itemData?.title,
				});
			}
		},
	};
}
