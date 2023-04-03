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

import {openModal, openSelectionModal, postForm} from 'frontend-js-web';

const ACTIONS = {
	assignSiteRolesSegmentsEntry(
		{itemSelectorURL, segmentsEntryId},
		portletNamespace
	) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('add'),
			multiple: true,
			onSelect(selectedItems) {
				if (selectedItems) {
					const form = document.getElementById(
						`${portletNamespace}updateSegmentsEntrySiteRolesFm`
					);

					const data = {
						segmentsEntryId,
						siteRoleIds: selectedItems.map((item) => item.value),
					};

					if (form) {
						postForm(form, {data});
					}
				}
			},
			selectEventName: `${portletNamespace}assignSiteRoles"`,
			title: Liferay.Language.get('assign-site-roles'),
			url: itemSelectorURL,
		});
	},

	deleteSegmentEntry({deleteSegmentEntryURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, deleteSegmentEntryURL);
		}
	},

	permissionsSegmentsEntry({permissionsSegmentsEntryURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsSegmentsEntryURL,
		});
	},

	viewMembersSegmentsEntry({viewMembersSegmentsEntryURL}) {
		openModal({
			title: Liferay.Language.get('view-members'),
			url: viewMembersSegmentsEntryURL,
		});
	},
};

export default function propsTransformer({
	actions,
	items,
	portletNamespace,
	...props
}) {
	const updateItem = (item) => {
		const newItem = {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action]?.(item.data, portletNamespace);
				}
			},
		};

		if (Array.isArray(item.items)) {
			newItem.items = item.items.map(updateItem);
		}

		return newItem;
	};

	return {
		...props,
		actions: actions?.map(updateItem),
		items: items?.map(updateItem),
	};
}
