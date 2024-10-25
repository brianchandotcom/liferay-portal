/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';

const AdminCatalogResource = CommerceServiceProvider.AdminCatalogAPI('v1');

const propsTransformer = ({additionalProps: {specificationId}, ...props}) => ({
	...props,
	onActionDropdownItemClick: ({
		action: {
			data: {id: actionId},
		},
		itemData: {listTypeDefinitionId},
		loadData: refresh,
	}) => {
		if (actionId === 'removePicklistRelation') {
			AdminCatalogResource.deleteSpecificationListTypeDefinition(
				specificationId,
				listTypeDefinitionId
			)
				.then(() => {
					refresh();

					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						type: 'success',
					});
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
});

export default propsTransformer;
