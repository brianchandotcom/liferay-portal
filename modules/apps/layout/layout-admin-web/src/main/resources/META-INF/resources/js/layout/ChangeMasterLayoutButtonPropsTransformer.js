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
				masterLayoutPlid: getSelectedMasterLayoutPlid(portletNamespace),
			});

			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('done'),
				iframeBodyCssClass: '',
				multiple: true,
				onSelect(selectedItem) {
					if (selectedItem) {
						const editMasterLayoutButton = document.getElementById(
							`${portletNamespace}editMasterLayoutButton`
						);

						const masterLayoutName = document.getElementById(
							`${portletNamespace}masterLayoutName`
						);

						const masterLayoutPlid = document.getElementById(
							`${portletNamespace}masterLayoutPlid`
						);

						const oldMasterLayoutPlid = masterLayoutPlid.value;

						const themeContainer = document.getElementById(
							`${portletNamespace}themeContainer`
						);

						masterLayoutName.innerHTML = selectedItem.name;

						masterLayoutPlid.value = selectedItem.plid;

						if (masterLayoutPlid.value === '0') {
							themeContainer.classList.remove('hide');
						}
						else {
							themeContainer.classList.add('hide');
						}

						if (
							masterLayoutPlid.value === oldMasterLayoutPlid &&
							masterLayoutPlid.value !== '0'
						) {
							editMasterLayoutButton.classList.remove('hide');
						}
						else {
							editMasterLayoutButton.classList.add('hide');
						}
					}
				},
				selectEventName: `${portletNamespace}selectMasterLayout`,
				title: Liferay.Language.get('select-master'),
				url: renderURL.toString(),
			});
		},
	};
}

function getSelectedMasterLayoutPlid(portletNamespace) {
	const masterLayoutPlidInput = document.getElementById(
		`${portletNamespace}masterLayoutPlid`
	);

	return masterLayoutPlidInput ? masterLayoutPlidInput.value : 0;
}
