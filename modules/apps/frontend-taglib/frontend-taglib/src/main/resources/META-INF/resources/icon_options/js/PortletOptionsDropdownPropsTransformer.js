/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const dropdownClass = `${portletNamespace}portlet-options`;

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

							const callback = Liferay.Util.getPortletConfigurationIconAction(
								action
							);

							if (callback) {
								callback(event, item.data);
							}
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
			className: `${dropdownClass} portlet-options-dropdown`,
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
