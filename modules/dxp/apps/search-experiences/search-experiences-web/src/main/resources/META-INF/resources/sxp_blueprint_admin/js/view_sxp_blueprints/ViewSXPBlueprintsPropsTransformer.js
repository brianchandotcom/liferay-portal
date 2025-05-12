/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	download,
	updateCollectionProvider,
} from '../utils/fdsPropsTransformerActions';
import {DEFAULT_HEADERS} from '../utils/fetch/fetch_data';
import CollectionProviderTableCell from './CollectionProviderTableCell';

export default function propsTransformer({itemsActions, ...otherProps}) {
	const collectionProviderCellRenderer = {
		component: CollectionProviderTableCell,
		name: 'collectionProviderCellRenderer',
		type: 'internal',
	};

	return {
		...otherProps,
		customRenderers: {
			tableCell: [collectionProviderCellRenderer],
		},
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'disableAsACollectionProvider') {
				return {
					...action,
					isVisible: (item) =>
						Liferay.FeatureFlags['LPS-129412'] &&
						!!item.configuration.generalConfiguration
							.collectionProvider,
				};
			}
			else if (action?.data?.id === 'enableAsACollectionProvider') {
				return {
					...action,
					isVisible: (item) =>
						Liferay.FeatureFlags['LPS-129412'] &&
						!item.configuration.generalConfiguration
							.collectionProvider,
				};
			}

			return action;
		}),
		onActionDropdownItemClick({action, itemData, loadData}) {
			if (action.data.id === 'disableAsACollectionProvider') {
				updateCollectionProvider({
					collectionProvider: false,
					itemData,
					loadData,
				});
			}
			else if (action.data.id === 'enableAsACollectionProvider') {
				updateCollectionProvider({
					collectionProvider: true,
					itemData,
					loadData,
				});
			}
			else if (action.data.id === 'export') {
				download(
					`/o/search-experiences-rest/v1.0/sxp-blueprints/${itemData.id}/export`,
					{headers: DEFAULT_HEADERS, method: 'GET'},
					itemData.title
				);
			}
		},
	};
}
