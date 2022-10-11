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

export default function PortletConfiguration({
	eventName,
	namespace,
	selectKBObjectURL,
}) {
	const form = document.getElementById(`${namespace}fm`);

	if (form) {
		const selectKBArticleButton = document.getElementById(
			`${namespace}selectKBArticleButton`
		);

		const selectKBArticleButtonClick = () => {
			Liferay.Util.openSelectionModal({
				onSelect: (event) => {
					if (event) {
						const item = JSON.parse(event.value);

						const resourcePrimKey = document.getElementById(
							`${namespace}resourcePrimKey`
						);

						resourcePrimKey.value = item.classPK;

						const configurationKBObject = document.getElementById(
							`${namespace}configurationKBObject`
						);

						configurationKBObject.value = item.title;
					}
				},
				selectEventName: eventName,
				title: Liferay.Language.get('select-article'),
				url: selectKBObjectURL,
			});
		};

		selectKBArticleButton.addEventListener(
			'click',
			selectKBArticleButtonClick
		);

		return {
			dispose() {
				selectKBArticleButton.removeEventListener(
					'click',
					selectKBArticleButtonClick
				);
			},
		};
	}
}
