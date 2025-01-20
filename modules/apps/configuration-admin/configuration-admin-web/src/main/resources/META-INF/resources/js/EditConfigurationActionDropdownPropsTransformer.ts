/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export default function propsTransformer({items, ...props}: {items: any}) {
	return {
		...props,
		items: items.map((item: any) => {
			const newItem = {
				...item,
				onClick() {
					const action = item.data?.action;

					if (action === 'delete') {

						// @ts-ignore

						submitForm(

							// @ts-ignore

							document.hrefFm,
							item.data?.deleteConfigActionURL
						);
					}
					else if (action === 'customMenuItem') {
						openModal({
							url: item.data?.url,
						});
					}
				},
			};

			if (item.data?.action === 'customMenuItem') {
				newItem['aria-haspopup'] = 'dialog';
			}

			return newItem;
		}),
	};
}
