/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';

import formatActionURL from '../../common/utils/formatActionURL';
import FilePreviewerModalContent from '../modal/FilePreviewerModalContent';
import deleteItemAction from './actions/deleteItemAction';
import shareAction from './actions/shareAction';
import AssetRenderer from './cell_renderers/AssetRenderer';

export default function HomeRecentAssetsFDSPropsTransformer({
	additionalProps,
	itemsActions = [],
	...otherProps
}: {
	additionalProps: {
		autocompleteURL: string;
		cmsGroupId?: number;
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
					component: AssetRenderer,
					name: 'assetRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'download') {
				return {
					...action,
					isVisible: (item: any) =>
						Boolean(item?.embedded?.file?.link?.href),
				};
			}
			else if (
				action?.data?.id === 'export-for-translation' ||
				action?.data?.id === 'import-translation' ||
				action?.data?.id === 'view-content'
			) {
				return {
					...action,
					isVisible: (item: any) => Boolean(!item?.embedded?.file),
				};
			}
			else if (action?.data?.id === 'view-file') {
				return {
					...action,
					isVisible: (item: any) => Boolean(item?.embedded?.file),
				};
			}

			return action;
		}),
		async onActionDropdownItemClick({
			action,
			event,
			itemData,
			loadData,
		}: {
			action: any;
			event: Event;
			itemData: any;
			loadData: () => {};
		}) {
			if (action?.data?.id === 'delete') {
				await deleteItemAction(itemData, loadData);
			}

			if (
				action?.data?.id === 'export-for-translation' ||
				action?.data?.id === 'import-translation'
			) {
				event?.preventDefault();

				openModal({
					size: 'full-screen',
					title: action.label,
					url: formatActionURL(itemData, action.href),
				});
			}
			else if (action?.data?.id === 'view-content') {
				event?.preventDefault();

				openModal({
					size: 'full-screen',
					title: itemData.embedded.title,
					url: formatActionURL(itemData, action.href),
				});
			}
			else if (action?.data?.id === 'view-file') {
				openModal({
					containerProps: {
						className: '',
					},
					contentComponent: () =>
						FilePreviewerModalContent({
							file: itemData.embedded.file,
							headerName: itemData.embedded.title,
						}),
					size: 'full-screen',
				});
			}
			else if (action?.data?.id === 'share') {
				const {autocompleteURL, collaboratorURLs} = additionalProps;

				shareAction({
					autocompleteURL,
					collaboratorURL: collaboratorURLs[itemData.entryClassName],
					creator: itemData.embedded.creator,
					itemId: itemData.embedded.id,
					title: itemData.embedded?.title,
				});
			}
		},
	};
}
