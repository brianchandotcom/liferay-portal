/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';

import formatActionURL from '../../common/utils/formatActionURL';
import FilePreviewerModalContent from '../modal/FilePreviewerModalContent';
import AssetRenderer from './cell_renderers/AssetRenderer';

export default function HomeRecentAssetsFDSPropsTransformer({
	itemsActions = [],
	...otherProps
}: {
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
			else if (action?.data?.id === 'view-content') {
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
		onActionDropdownItemClick: ({
			action,
			event,
			itemData,
		}: {
			action: any;
			event: Event;
			itemData: any;
		}) => {
			if (action?.data?.id === 'view-content') {
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
		},
	};
}
