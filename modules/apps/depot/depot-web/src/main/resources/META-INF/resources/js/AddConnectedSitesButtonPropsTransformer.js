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

import {openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const {currentURL, itemSelectorURL} = additionalProps;

			openSelectionModal({
				id: `${portletNamespace}selectSite`,
				onSelect: (event) => {
					const toGroupIdInput = document.getElementById(
						`${portletNamespace}toGroupId`
					);

					toGroupIdInput.value = event.groupid;

					const redirectInput = document.getElementById(
						`${portletNamespace}redirect`
					);

					redirectInput.value = currentURL;

					submitForm(toGroupIdInput.form);
				},
				selectEventName: `${portletNamespace}selectSite`,
				title: Liferay.Language.get('select-site'),
				url: `${itemSelectorURL}`,
			});
		},
	};
}
