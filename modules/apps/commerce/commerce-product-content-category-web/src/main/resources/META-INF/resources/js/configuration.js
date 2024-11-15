/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const assetCategoryContent = document.getElementById(
		`${namespace}assetCategoryContent`
	);
	const inputAssetCategoryExternalReferenceCode = document.getElementById(
		`${namespace}preferencesAssetCategoryExternalReferenceCode`
	);
	const useAssetCategory = document.getElementById(
		`${namespace}useAssetCategory`
	);

	useAssetCategory.addEventListener('change', (event) => {
		if (event.target.checked) {
			assetCategoryContent.classList.remove('hide');

			inputAssetCategoryExternalReferenceCode.removeAttribute('disabled');
		}
		else {
			assetCategoryContent.classList.add('hide');

			inputAssetCategoryExternalReferenceCode.setAttribute(
				'disabled',
				'true'
			);
		}
	});
}
