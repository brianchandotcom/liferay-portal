/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-components-web';
import {getPortletNamespace} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {eventName, itemSelectorURL},
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			openLayoutSelectionModal(
				portletNamespace,
				itemSelectorURL,
				eventName
			);
		},
	};
}

export function openLayoutSelectionModal(
	portletNamespace,
	itemSelectorURL,
	eventName
) {
	const url = new URL(itemSelectorURL);

	const externalReferenceCodeInput = document.getElementById(
		`${portletNamespace}externalReferenceCode`
	);

	const itemInput = document.getElementById(`${portletNamespace}itemInput`);

	const privateLayoutInput = document.getElementById(
		`${portletNamespace}privateLayout`
	);
	openSelectionModal({
		buttonAddLabel: Liferay.Language.get('select'),
		height: '70vh',
		multiple: true,
		onSelect: (selectedItem) => {
			if (selectedItem) {
				externalReferenceCodeInput.value =
					selectedItem.externalReferenceCode;
				itemInput.value = selectedItem.name;
				privateLayoutInput.value = selectedItem.privateLayout;

				url.searchParams.set(
					`${getPortletNamespace(
						Liferay.PortletKeys.ITEM_SELECTOR
					)}externalReferenceCode`,
					selectedItem.externalReferenceCode
				);
			}
		},
		selectEventName: eventName,
		size: 'md',
		title: Liferay.Language.get('select-layout'),
		url: url.href,
	});
}
