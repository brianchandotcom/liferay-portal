/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createPortletURL, navigate, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onCreationMenuItemClick: (event, {item}) => {
			if (item?.data?.action === 'openAICreateImage') {
				if (!item?.data?.isAICreatorOpenAIAPIKey) {
					Liferay.componentReady(
						`${portletNamespace}ItemSelectorRepositoryEntryBrowserConfigureAIModal`
					).then((configureAIModal) => {
						configureAIModal.open();
					});
				}
				else {
					openSelectionModal({
						height: '70vh',
						onSelect: ({selectedItems}) => {
							if (selectedItems) {
								setTimeout(() => {
									navigate(window.location.href);
								}, 300);
							}
						},
						selectEventName: `${portletNamespace}selectAIImages`,
						size: 'lg',
						title: Liferay.Language.get('create-ai-image'),
						url: createPortletURL(item?.data?.aiCreatorURL, {
							selectEventName: `${portletNamespace}selectAIImages`,
						}).toString(),
					});
				}
			}
		},
	};
}
