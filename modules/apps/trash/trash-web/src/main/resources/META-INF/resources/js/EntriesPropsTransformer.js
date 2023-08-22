/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

import openDeleteEntryModal from './openDeleteEntryModal';

const ACTIONS = {
	deleteEntry(itemData) {
		openDeleteEntryModal({
			onDelete: () => {
				submitForm(document.hrefFm, itemData.deleteEntryURL);
			},
		});
	},

	moveEntry(itemData, portletNamespace) {
		openSelectionModal({
			height: '70vh',
			iframeBodyCssClass: '',
			onSelect: (event) => {
				const selectContainerForm = document.getElementById(
					`${portletNamespace}selectContainerForm`
				);

				if (selectContainerForm) {
					const className = selectContainerForm.querySelector(
						`#${portletNamespace}className`
					);

					if (className) {
						className.setAttribute('value', event.classname);
					}

					const classPK = selectContainerForm.querySelector(
						`#${portletNamespace}classPK`
					);

					if (classPK) {
						classPK.setAttribute('value', event.classpk);
					}

					const containerModelId = selectContainerForm.querySelector(
						`#${portletNamespace}containerModelId`
					);

					if (containerModelId) {
						containerModelId.setAttribute(
							'value',
							event.containermodelid
						);
					}

					const redirect = selectContainerForm.querySelector(
						`#${portletNamespace}redirect`
					);

					if (redirect) {
						redirect.setAttribute('value', event.redirect);
					}

					submitForm(selectContainerForm);
				}
			},
			selectEventName: `${portletNamespace}selectContainer`,
			size: 'md',
			title: Liferay.Language.get('select-restore-folder'),
			url: itemData.moveEntryURL,
		});
	},

	restoreEntry(itemData) {
		submitForm(document.hrefFm, itemData.restoreEntryURL);
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
