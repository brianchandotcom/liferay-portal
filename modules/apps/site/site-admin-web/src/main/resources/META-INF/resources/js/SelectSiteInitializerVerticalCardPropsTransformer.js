/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export default function SelectSiteInitializerVerticalCardPropsTransformer({
	portletNamespace: namespace,
	...otherProps
}) {
	const openModalAddSite = (event) => {
		openModal({
			disableAutoClose: true,
			height: '60vh',
			id: `${namespace}addSiteDialog`,
			size: 'md',
			title: Liferay.Language.get('add-site'),
			url: event.currentTarget.dataset.addSiteUrl,
		});
	};

	return {
		...otherProps,
		onClick: openModalAddSite,
		onKeyDown: (event) => {
			if (event.key === 'Enter') {
				openModalAddSite(event);
			}
		},
	};
}
