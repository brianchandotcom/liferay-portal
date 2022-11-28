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
	addScope: ({url}) => {
		submitForm(document.hrefFm, url);
	},
	openScopeSelector: ({eventName, id, url}, portletNamespace) => {
		openSelectionModal({
			id,
			onSelect(selectedItem) {
				let groupId = 0;

				if (selectedItem.value) {
					const itemValue = JSON.parse(selectedItem.value);

					groupId = itemValue.groupId;
				}
				else {
					groupId = selectedItem.groupid;
				}

				const form = document.getElementById(`${portletNamespace}fm`);

				Liferay.Util.postForm(form, {
					data: {
						cmd: 'add-scope',
						groupId,
					},
				});
			},
			selectEventName: eventName,
			title: Liferay.Language.get('scope'),
			url,
		});
	},
};

export default function propsTransformer({items, portletNamespace, ...props}) {
	const updateItem = (item) => {
		return {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action]?.(item.data, portletNamespace);
				}
			},
		};
	};

	return {
		...props,
		items: items?.map(updateItem),
	};
}
