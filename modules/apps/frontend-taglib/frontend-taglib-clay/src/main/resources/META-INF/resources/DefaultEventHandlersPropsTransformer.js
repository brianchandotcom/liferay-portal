/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function propsTransformer({
	actions,
	defaultEventHandler,
	items,
	...otherProps
}) {
	const itemsMap = (item) => {
		return {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					Liferay.componentReady(defaultEventHandler).then(
						(eventHandler) => {
							eventHandler[action](item.data);
						}
					);
				}
			},
		};
	};

	return {
		...otherProps,
		actions: actions?.map(itemsMap),
		items: items?.map(itemsMap),
	};
}
