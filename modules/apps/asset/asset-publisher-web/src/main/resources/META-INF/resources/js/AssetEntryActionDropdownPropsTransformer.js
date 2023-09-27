/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

const ACTIONS = {
	assetEntryAction: ({assetEntryActionTitle, assetEntryActionURL}) => {
		openModal({
			title: assetEntryActionTitle,
			url: assetEntryActionURL,
		});
	},
};

export default function propsTransformer({
	actions,
	items,
	portletNamespace,
	...props
}) {
	const dropdownClass = `${portletNamespace}asset-entry-options`;

	const handleDropdownMenuOpen = (event) => {
		const portlet = event.target.closest('.portlet');

		if (portlet) {
			portlet.classList.add('focus');
		}

		const listener = (event) => {
			if (!event.target.closest(`.${dropdownClass}`)) {
				if (portlet) {
					portlet.classList.remove('focus');
				}

				document.removeEventListener('mousedown', listener);
				document.removeEventListener('touchstart', listener);
			}
		};

		document.addEventListener('mousedown', listener);
		document.addEventListener('touchstart', listener);
	};

	const updateItem = (item) => {
		const newItem = {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action]?.(item.data);
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
		menuProps: {
			className: dropdownClass,
		},
		onClick: (event) => {
			handleDropdownMenuOpen(event);
		},
		onKeyDown: (event) => {
			if (event.key === 'Enter' || event.key === 'ArrowDown') {
				handleDropdownMenuOpen(event);
			}
		},
	};
}
