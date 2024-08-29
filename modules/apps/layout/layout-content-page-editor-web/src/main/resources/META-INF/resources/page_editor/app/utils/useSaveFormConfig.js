/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';

import {useUpdateItemLocalConfig} from '../contexts/LocalConfigContext';
import {useDispatch} from '../contexts/StoreContext';
import updateFormItemConfig from '../thunks/updateFormItemConfig';

export function useSaveFormConfig(item) {
	const dispatch = useDispatch();
	const updateItemLocalConfig = useUpdateItemLocalConfig();

	return useCallback(
		(nextConfig, fields) => {
			const isMapping = Boolean(nextConfig.classNameId);

			if (isMapping) {
				updateItemLocalConfig(item.itemId, {
					loading: true,
					showMessagePreview: false,
				});
			}

			return dispatch(
				updateFormItemConfig({
					fields,
					itemConfig: nextConfig,
					itemIds: [item.itemId],
				})
			).then(() =>
				updateItemLocalConfig(item.itemId, {
					loading: false,
				})
			);
		},
		[dispatch, item.itemId, updateItemLocalConfig]
	);
}
