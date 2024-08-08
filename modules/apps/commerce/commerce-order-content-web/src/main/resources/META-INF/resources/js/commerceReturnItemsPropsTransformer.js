/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CommerceCurrencyDataRenderer from './CommerceCurrencyDataRenderer';
import CommerceReturnItemStatusDataRenderer from './CommerceReturnItemStatusDataRenderer';

function handleRefreshPage() {
	Liferay.detach('fds-display-updated', handleRefreshPage);

	window.top.location.reload();
}

function commerceReturnItemsPropsTransformer({...props}) {
	return {
		...props,
		customDataRenderers: {
			commerceCurrencyDataRenderer: CommerceCurrencyDataRenderer,
			commerceReturnItemStatusDataRenderer:
				CommerceReturnItemStatusDataRenderer,
		},
		onActionDropdownItemClick: ({
			action: {
				data: {id},
			},
		}) => {
			if (id === 'delete') {
				Liferay.on('fds-display-updated', handleRefreshPage);
			}
		},
	};
}

export default commerceReturnItemsPropsTransformer;
