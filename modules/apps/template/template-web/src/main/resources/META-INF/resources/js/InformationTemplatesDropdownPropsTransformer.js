/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

import openDeleteTemplateModal from './modal/openDeleteTemplateModal';

const ACTIONS = {
	deleteTemplateEntry({deleteTemplateEntryURL}) {
		openDeleteTemplateModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onDelete: () => {
				submitForm(document.hrefFm, deleteTemplateEntryURL);
			},
		});
	},

	permissionsTemplateEntry({permissionsTemplateEntryURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsTemplateEntryURL,
		});
	},
};

export default function propsTransformer({items, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](child.data);
							}
						},
					};
				}),
			};
		}),
	};
}
