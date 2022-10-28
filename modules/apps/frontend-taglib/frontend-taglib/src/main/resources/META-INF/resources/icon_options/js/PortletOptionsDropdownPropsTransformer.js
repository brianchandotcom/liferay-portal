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

import {openModal} from 'frontend-js-web';

const ACTIONS = {
	openDialog(itemData) {
		openModal({
			onClose: () => {
				Liferay.Portlet.refresh(`#p_p_id_${itemData.portletId}_`);
			},
			title: itemData.title,
			url: itemData.url,
		});
	},

	send(itemData) {
		submitForm(document.hrefFm, itemData.url);
	},
};

export default function propsTransformer({
	items,
	portletNamespace,
	...otherProps
}) {
	const dropdownClass = `${portletNamespace}portlet-options portlet-options-dropdown`;

	const handleDropdownMenuOpen = (event) => {
		const portlet = event.target.closest('.portlet');

		if (portlet) {
			portlet.classList.add('focus');
		}

		const listener = (event) => {
			if (!event.target.closest(`.${dropdownClass}`)) {
				portlet.classList.remove('focus');

				document.removeEventListener('mousedown', listener);
				document.removeEventListener('touchstart', listener);
			}
		};

		document.addEventListener('mousedown', listener);
		document.addEventListener('touchstart', listener);
	};

	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						const globalAction = item.data?.globalAction;

						if (globalAction) {
							event.preventDefault();

							Liferay.__PORTLET_CONFIGURATION_ICON_ACTIONS__?.[
								action
							]?.();
						}
						else {
							event.preventDefault();

							ACTIONS[action](item.data);
						}
					}
				},
			};
		}),
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
