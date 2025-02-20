/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-components-web';

const AdminCatalogAPI = CommerceServiceProvider.AdminCatalogAPI('v1');

export default function propsTransformer({...otherProps}) {
	return {
		...otherProps,
		onActionDropdownItemClick: ({
			action: {
				data: {id: actionId},
			},
			itemData: {id: optionId},
			loadData: refresh,
		}) => {
			if (actionId === 'delete') {
				AdminCatalogAPI.deleteOptionById(optionId)
					.then(() => {
						openToast({
							message: Liferay.Language.get(
								'your-request-completed-successfully'
							),
							type: 'success',
						});
						refresh();
					})
					.catch((error) => {
						openToast({
							message:
								error.message ||
								Liferay.Language.get(
									'an-option-cannot-be-deleted-if-it-is-being-used-by-one-or-more-products'
								),
							type: 'danger',
						});
					});
			}
		},
	};
}
