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
import {useOutletContext, useParams} from 'react-router-dom';
import {PAGE_TYPES, PRODUCT_TYPES} from '../../../../utils/constants';

const ProductsOutlet = () => {
	const {productId} = useParams();
	const {activationComponents, subscriptionGroups} = useOutletContext();

	const currentProduct = activationComponents[productId];

	const hasAccessToCurrentProduct = useMemo(() => {
		const [pageKey] =
			Object.entries(PAGE_TYPES).find((page) => page[1] === productId) ||
			[];

		if (pageKey) {
			const productName = PRODUCT_TYPES[pageKey];

			return subscriptionGroups.some(
				(subscriptionGroup) => subscriptionGroup.name === productName
			);
		}

		return false;
	}, [productId, subscriptionGroups]);

	if (!currentProduct || !hasAccessToCurrentProduct) {
		return <h3>Page not found</h3>;
	}

	return currentProduct;
};

export default ProductsOutlet;
