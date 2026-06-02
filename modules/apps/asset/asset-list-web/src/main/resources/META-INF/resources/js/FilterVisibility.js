/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const assetSelector = document.getElementById(namespace + 'anyAssetType');
	const assetWrapper = document.getElementById(
		namespace + 'assetFilterBuilderWrapper'
	);
	const collectionWrapper = document.getElementById(
		namespace + 'collectionFilterBuilderWrapper'
	);

	if (!assetSelector || !assetWrapper || !collectionWrapper) {
		return;
	}

	const updateVisibility = () => {
		const selectedOption =
			assetSelector.options[assetSelector.selectedIndex];

		const isSingleCMSType =
			!!selectedOption && selectedOption.dataset.cms === 'true';

		const isMultiSelection = assetSelector.value === 'false';

		const showCollection = isSingleCMSType || isMultiSelection;

		collectionWrapper.classList.toggle('hide', !showCollection);
		assetWrapper.classList.toggle('hide', showCollection);
	};

	updateVisibility();

	Liferay.on(namespace + 'sourceChange', updateVisibility);

	return {
		destroy() {
			Liferay.detach(namespace + 'sourceChange', updateVisibility);
		},
	};
}
