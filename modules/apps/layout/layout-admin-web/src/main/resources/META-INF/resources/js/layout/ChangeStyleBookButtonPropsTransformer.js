/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {createRenderURL, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const {url} = additionalProps;

			const renderURL = createRenderURL(url, {
				styleBookEntryId: getSelectedStyleBookEntryId(portletNamespace),
			});

			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('done'),
				iframeBodyCssClass: '',
				multiple: true,
				onSelect(selectedItem) {
					if (selectedItem) {
						const styleBookName = document.getElementById(
							`${portletNamespace}styleBookName`
						);

						styleBookName.innerHTML = selectedItem.name;

						const styleBookEntryId = document.getElementById(
							`${portletNamespace}styleBookEntryId`
						);

						styleBookEntryId.value = selectedItem.stylebookentryid;
					}
				},
				selectEventName: `${portletNamespace}selectStyleBook`,
				title: Liferay.Language.get('select-style-book'),
				url: renderURL.toString(),
			});
		},
	};
}

function getSelectedStyleBookEntryId(portletNamespace) {
	const styleBookEntryIdInput = document.getElementById(
		`${portletNamespace}styleBookEntryId`
	);

	return styleBookEntryIdInput ? styleBookEntryIdInput.value : 0;
}
