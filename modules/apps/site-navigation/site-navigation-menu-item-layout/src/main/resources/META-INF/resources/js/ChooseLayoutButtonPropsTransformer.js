/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getPortletNamespace, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {eventName, itemSelectorURL},
	portletNamespace,
	...props
}) {
	const url = new URL(itemSelectorURL);

	return {
		...props,
		onClick() {
			const groupIdInput = document.getElementById(
				`${portletNamespace}groupId`
			);

			const layoutItemRemoveButton = document.getElementById(
				`${portletNamespace}layoutItemRemove`
			);

			const layoutNameInput = document.getElementById(
				`${portletNamespace}layoutNameInput`
			);

			const layoutUuidInput = document.getElementById(
				`${portletNamespace}layoutUuid`
			);

			const privateLayoutInput = document.getElementById(
				`${portletNamespace}privateLayout`
			);

			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('select'),
				height: '70vh',
				multiple: true,
				onSelect: (selectedItem) => {
					if (selectedItem) {
						groupIdInput.value = selectedItem.groupId;
						layoutUuidInput.value = selectedItem.id;
						layoutNameInput.textContent = selectedItem.name;
						privateLayoutInput.value = selectedItem.privateLayout;

						url.searchParams.set(
							`${getPortletNamespace(
								Liferay.PortletKeys.ITEM_SELECTOR
							)}layoutUuid`,
							selectedItem.id
						);

						layoutItemRemoveButton.classList.remove('hide');
					}
				},
				selectEventName: eventName,
				size: 'md',
				title: Liferay.Language.get('select-layout'),
				url: url.href,
			});
		},
	};
}
