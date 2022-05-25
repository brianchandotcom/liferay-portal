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

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const {faviconFileEntryTitleValue} = additionalProps;

			const faviconFileEntryId = document.getElementById(
				`${portletNamespace}faviconFileEntryId`
			);

			const faviconFileEntryTitle = document.getElementById(
				`${portletNamespace}faviconFileEntryTitle`
			);

			if (faviconFileEntryId && faviconFileEntryTitle) {
				faviconFileEntryId.value = '0';
				faviconFileEntryTitle.innerHTML = faviconFileEntryTitleValue;
			}
		},
	};
}
