/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, navigate, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {selectAssetTypeURL, viewAssetTypeURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'openAssetTypesSelector') {
				openSelectionModal({
					onSelect: (selectedItem) => {
						if (selectedItem) {
							const uri = addParams(
								`${portletNamespace}className=${selectedItem.value}`,
								viewAssetTypeURL
							);

							navigate(uri);
						}
					},
					selectEventName: `${portletNamespace}selectAssetType`,
					title: Liferay.Language.get('select-asset-type'),
					url: selectAssetTypeURL,
				});
			}
		},
	};
}
