/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useMemo} from 'react';
import {Outlet, useOutletContext, useParams} from 'react-router-dom';
import {PAGE_TYPES, PRODUCT_TYPES} from '../../../../utils/constants';

const ProductsMiddlewareOutlet = () => {
	const {
		activationComponents,
		project,
		sessionId,
		subscriptionGroups,
		userAccount,
	} = useOutletContext();
	const {productId} = useParams();

	const hasAccessToCurrentProduct = useMemo(() => {
		const [pageKey] =
			Object.entries(PAGE_TYPES)?.find(
				([, pageValue]) => pageValue === productId
			) || [];

		if (pageKey) {
			const productName = PRODUCT_TYPES[pageKey];

			return subscriptionGroups?.some(
				(subscriptionGroup) => subscriptionGroup.name === productName
			);
		}

		return false;
	}, [productId, subscriptionGroups]);

	return (
		<Outlet
			context={{
				activationComponents,
				hasAccessToCurrentProduct,
				project,
				sessionId,
				subscriptionGroups,
				userAccount,
			}}
		/>
	);
};

export default ProductsMiddlewareOutlet;
