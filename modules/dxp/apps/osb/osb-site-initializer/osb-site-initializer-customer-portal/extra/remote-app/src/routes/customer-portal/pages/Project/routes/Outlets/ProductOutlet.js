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
import {Navigate, Outlet, useOutletContext} from 'react-router-dom';
import {useCustomerPortal} from '../../../../context';

const ProductOutlet = ({product}) => {
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();
	const [{project, subscriptionGroups}] = useCustomerPortal();

	const hasProduct = useMemo(
		() => !!subscriptionGroups?.find(({name}) => name === product),
		[product, subscriptionGroups]
	);

	if (!hasProduct) {
		return <Navigate replace={true} to={`/${project?.accountKey}`} />;
	}

	return (
		<Outlet
			context={{
				setHasQuickLinksPanel,
				setHasSideMenu,
			}}
		/>
	);
};

export default ProductOutlet;
