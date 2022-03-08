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

import {useEffect} from 'react';
import {Outlet, useNavigate, useOutletContext} from 'react-router-dom';
import {PAGE_TYPES, PRODUCT_TYPES} from '../../../../utils/constants';
import ActivationKeys from '../../ActivationKeys';
import AnalyticsCloud from '../../AnalyticsCloud';
import DXP from '../../DXP';
import DXPCloud from '../../DXPCloud';

const ACTIVATION_ROOT_ROUTER = 'activation';

const ActivationOutlet = () => {
	const navigate = useNavigate();

	const {
		getCurrentPage,
		project,
		sessionId,
		subscriptionGroups,
		userAccount,
	} = useOutletContext();

	useEffect(() => {
		if (getCurrentPage() === ACTIVATION_ROOT_ROUTER) {
			const firstSubscriptionName = subscriptionGroups[0].name;

			const [productKey] = Object.entries(PRODUCT_TYPES).find(
				([, productName]) => productName === firstSubscriptionName
			);

			const pageToRedirect = PAGE_TYPES[productKey];

			navigate(pageToRedirect);
		}
	}, [getCurrentPage, navigate, subscriptionGroups]);

	const activationComponents = {
		[PAGE_TYPES.analyticsCloud]: (
			<AnalyticsCloud
				accountKey={project?.accountKey}
				project={project}
				sessionId={sessionId}
				subscriptionGroups={subscriptionGroups}
				userAccount={userAccount}
			/>
		),
		[PAGE_TYPES.commerce]: (
			<ActivationKeys.Commerce
				accountKey={project?.accountKey}
				sessionId={sessionId}
			/>
		),
		[PAGE_TYPES.dxp]: <DXP project={project} sessionId={sessionId} />,
		[PAGE_TYPES.dxpCloud]: (
			<DXPCloud
				project={project}
				sessionId={sessionId}
				subscriptionGroups={subscriptionGroups}
				userAccount={userAccount}
			/>
		),
		[PAGE_TYPES.enterpriseSearch]: (
			<ActivationKeys.EnterpriseSearch
				accountKey={project?.accountKey}
				sessionId={sessionId}
			/>
		),
	};

	return (
		<Outlet
			context={{
				activationComponents,
				project,
				sessionId,
				subscriptionGroups,
				userAccount,
			}}
		/>
	);
};

export default ActivationOutlet;
