/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openLayoutSelectionModal} from './ChooseLayoutButtonPropsTransformer';

export default function ({eventName, itemSelectorURL, namespace}) {
	const itemInput = document.getElementById(`${namespace}itemInput`);

	const onItemInputClick = () => {
		openLayoutSelectionModal(namespace, itemSelectorURL, eventName);
	};

	itemInput.addEventListener('click', onItemInputClick);

	return {
		dispose() {
			itemInput.removeEventListener('click', onItemInputClick);
		},
	};
}
