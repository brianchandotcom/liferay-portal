/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';

import {CommerceReturnItemPicklistDataRenderer} from './CommerceReturnItemPicklistDataRenderer';
import {CommerceReturnItemReceivedDataRenderer} from './CommerceReturnItemReceivedDataRenderer';
import {CommerceReturnItemStatusDataRenderer} from './CommerceReturnItemStatusDataRenderer';
import CommerceStatusDataRenderer from './CommerceStatusDataRenderer';

const CommerceReturnItemResource = CommerceServiceProvider.ReturnItemAPI();

export default function propsTransformer({...otherProps}) {
	return {
		...otherProps,
		customDataRenderers: {
			commerceReturnItemPicklistDataRenderer:
				CommerceReturnItemPicklistDataRenderer,
			commerceReturnItemReceivedDataRenderer:
				CommerceReturnItemReceivedDataRenderer,
			commerceReturnItemStatusDataRenderer:
				CommerceReturnItemStatusDataRenderer,
			commerceStatusDataRenderer: CommerceStatusDataRenderer,
		},
		onActionDropdownItemClick: ({
			action: {
				data: {id: actionId},
			},
			itemData: {id: itemId},
		}) => {
			if (actionId === 'removeReturnItem') {
				CommerceReturnItemResource.deleteItemById(itemId)
					.then(() => {
						window.location.reload();
					})
					.catch(() => {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							type: 'danger',
						});
					});
			}
		},
	};
}
