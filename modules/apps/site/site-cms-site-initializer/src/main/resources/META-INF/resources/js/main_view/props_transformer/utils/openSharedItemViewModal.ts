/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openCMSModal} from '../../../common/utils/openCMSModal';
import AssetNavigationModalContent from '../../modal/asset_navigation_view/AssetNavigationModalContent';

let _additionalProps: any = null;

export function setSharedWithMeAdditionalProps(props: any) {
	_additionalProps = props;
}

export function transformSharedItem(item: any) {
	return {
		...item,
		embedded: {
			file: item.file ?? undefined,
			id: item.classPK,
			title: item.title,
		},
		entryClassName: item.className,
	};
}

export function openSharedItemViewModal(itemData: any) {
	if (!_additionalProps) {
		return;
	}

	openCMSModal({
		contentComponent: () =>
			AssetNavigationModalContent({
				additionalProps: _additionalProps,
				contentViewURL: _additionalProps.contentViewURL,
				currentIndex: 0,
				items: [transformSharedItem(itemData)],
				showInfoPanel: false,
			}),
		size: 'full-screen',
	});
}
