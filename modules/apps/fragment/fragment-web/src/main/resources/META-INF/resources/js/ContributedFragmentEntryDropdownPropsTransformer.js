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

const ACTIONS = {
	copyContributedEntryToFragmentCollection(itemData, portletNamespace) {
		openSelectionModal({
			id: `${portletNamespace}selectFragmentCollection`,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					document.getElementById(
						`${portletNamespace}contributedEntryKeys`
					).value = itemData.contributedEntryKey;
					document.getElementById(
						`${portletNamespace}fragmentCollectionId`
					).value = selectedItem.id;

					submitForm(
						document.getElementById(
							`${portletNamespace}fragmentEntryFm`
						),
						itemData.copyContributedEntryURL
					);
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-fragment-set'),
			url: itemData.selectFragmentCollectionURL,
		});
	},
};

export default function propsTransformer({
	actions,
	portletNamespace,
	...props
}) {
	const transformAction = (actionItem) => {
		if (actionItem.type === 'group') {
			return {
				...actionItem,
				items: actionItem.items?.map(transformAction),
			};
		}

		return {
			...actionItem,
			onClick(event) {
				const action = actionItem.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action](actionItem.data, portletNamespace);
				}
			},
		};
	};

	return {
		...props,
		actions: (actions || []).map(transformAction),
	};
}
