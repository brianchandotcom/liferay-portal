/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
