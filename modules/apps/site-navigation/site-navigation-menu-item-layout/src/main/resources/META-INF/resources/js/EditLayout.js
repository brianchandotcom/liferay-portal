/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const layoutItemRemoveButton = document.getElementById(
		`${namespace}layoutItemRemove`
	);
	const layoutNameInput = document.getElementById(
		`${namespace}layoutNameInput`
	);

	const externalReferenceCodeInput = document.getElementById(
		`${namespace}externalReferenceCode`
	);

	const onLayoutItemRemoveButtonClick = () => {
		layoutNameInput.textContent = Liferay.Language.get('none');
		externalReferenceCodeInput.value = '';

		layoutItemRemoveButton.classList.add('hide');
	};

	layoutItemRemoveButton.addEventListener(
		'click',
		onLayoutItemRemoveButtonClick
	);

	return {
		dispose() {
			layoutItemRemoveButton.removeEventListener(
				'click',
				onLayoutItemRemoveButtonClick
			);
		},
	};
}
