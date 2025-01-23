/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openMarketplaceModal} from '@liferay/layout-js-components-web';

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {

	const marketplaceBadge = document.getElementById(
		`${portletNamespace}marketplaceBadge`
	);

	return {
		...props,
		onClick() {
			openMarketplaceModal({
				component: additionalProps.component,
				location: additionalProps.location,
			});

			Liferay.Util.Session.set(portletNamespace + 'marketplaceButton', 'visited');

			if (marketplaceBadge) {
				marketplaceBadge.remove();
			}
		},
	};
}
