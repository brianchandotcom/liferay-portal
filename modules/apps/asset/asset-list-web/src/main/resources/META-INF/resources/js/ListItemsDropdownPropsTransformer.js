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

const ACTIONS = {
	editContent(itemData) {
		this.navigate(itemData.editContentURL);
	},

	editDisplayPageTemplate(itemData) {
		this.navigate(itemData.editDisplayPageTemplateURL);
	},

	navigate(url) {
		const openerWindow = Liferay.Util.getTop();

		openerWindow.Liferay.Util.navigate(url);
	},

	viewDisplayPage(itemData) {
		this.navigate(itemData.viewDisplayPageURL);
	},
};

export default function propsTransformer({items, ...props}) {
	return {
		...props,
		items: items.map((item) => {
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
