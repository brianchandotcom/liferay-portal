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

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteArticles = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-version'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form, itemData?.deleteArticlesURL);
			}
		}
	};

	const expireArticles = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-expire-the-selected-version'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form, itemData?.expireArticlesURL);
			}
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item.data;

			const action = data?.action;

			if (action === 'deleteArticles') {
				deleteArticles(data);
			}
			else if (action === 'expireArticles') {
				expireArticles(data);
			}
		},
	};
}
