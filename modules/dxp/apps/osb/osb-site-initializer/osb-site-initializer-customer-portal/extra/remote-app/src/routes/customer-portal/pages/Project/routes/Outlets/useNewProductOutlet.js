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

import {useOutletContext, useParams} from 'react-router-dom';
import GenerateNewDXPKey from '../../../../containers/GenerateNewDXPKey';
import {PAGE_TYPES} from '../../../../utils/constants';

const NewProductOutlet = () => {
	const {productId} = useParams();
	const {hasAccessToCurrentProduct, project, sessionId} = useOutletContext();

	const newActivationComponents = {
		[PAGE_TYPES.dxpNew]: (
			<GenerateNewDXPKey
				accountKey={project.accountKey}
				sessionId={sessionId}
			/>
		),
	};

	const currentProduct = newActivationComponents[`${productId}_new`];

	if (!currentProduct || !hasAccessToCurrentProduct) {
		return <h3>Page not found</h3>;
	}

	return currentProduct;
};

export default NewProductOutlet;
