/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
