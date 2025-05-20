/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceFrontendUtils} from 'commerce-frontend-js';

const AddOrderButtonPropsTransformer = ({additionalProps, ...props}) => ({
	...props,
	onClick: async (event) => {
		event.preventDefault();

		return CommerceFrontendUtils.createCommerceCart({
			orderDetailURL: additionalProps.orderDetailURL,
		});
	},
});

export default AddOrderButtonPropsTransformer;
