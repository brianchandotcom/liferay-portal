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

import {Navigate, useOutletContext, useParams} from 'react-router-dom';
import GenerateNewKey from '../../../../containers/GenerateNewKey';
import {PAGE_TYPES, PRODUCT_TYPES} from '../../../../utils/constants';

const NewProductOutlet = () => {
	const {accountKey, productId} = useParams();
	const {hasAccessToCurrentProduct, project, sessionId} = useOutletContext();

	const newActivationComponents = {
		[PAGE_TYPES.dxpNew]: (
			<GenerateNewKey
				accountKey={project.accountKey}
				productGroupName={PRODUCT_TYPES.dxp}
				sessionId={sessionId}
			/>
		),
		[PAGE_TYPES.portalNew]: (
			<GenerateNewKey
				accountKey={project.accountKey}
				productGroupName={PRODUCT_TYPES.portal}
				sessionId={sessionId}
			/>
		),
	};

	const currentProduct = newActivationComponents[`${productId}_new`];

	if (!currentProduct || !hasAccessToCurrentProduct) {
		return <Navigate replace={true} to={`/${accountKey}`} />;
	}

	return currentProduct;
};

export default NewProductOutlet;
