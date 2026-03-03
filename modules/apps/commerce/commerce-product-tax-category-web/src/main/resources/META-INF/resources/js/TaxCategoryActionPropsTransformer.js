/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-components-web';

const ACTIONS = {
	delete(itemData) {
		openConfirmModal({
			message: itemData.confirmationMessage,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					Liferay.Util.navigate(itemData.deleteURL);
				}
			},
		});
	},
};

export default function TaxCategoryActionPropsTransformer({items, ...props}) {
	return {
		...props,
		items: items?.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data);
					}
				},
			};
		}),
	};
}
