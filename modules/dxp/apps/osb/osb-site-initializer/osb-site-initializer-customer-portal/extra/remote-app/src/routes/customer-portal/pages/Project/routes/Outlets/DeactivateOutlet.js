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
import DeactivateKeysTable from '../../../../containers/DeactivateKeysTable';
import {PAGE_TYPES, PRODUCT_TYPES} from '../../../../utils/constants';

const DeactivateOutlet = () => {
	const {accountKey, productId} = useParams();
	const {hasAccessToCurrentProduct, project, sessionId} = useOutletContext();

	const DeactivateComponents = {
		[PAGE_TYPES.dxpDeactivate]: (
			<DeactivateKeysTable
				accountKey={project.accountKey}
				productName={PRODUCT_TYPES.dxp}
				project={project}
				sessionId={sessionId}
			/>
		),
	};

	const currentProduct = DeactivateComponents[`${productId}_deactivate`];

	if (!currentProduct || !hasAccessToCurrentProduct) {
		return <Navigate replace={true} to={`/${accountKey}`} />;
	}

	return currentProduct;
};

export default DeactivateOutlet;
