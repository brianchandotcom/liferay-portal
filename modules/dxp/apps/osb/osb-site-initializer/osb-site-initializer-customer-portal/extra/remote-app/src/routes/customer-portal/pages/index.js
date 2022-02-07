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

import {useCustomerPortal} from '../context';
import Layout from '../layouts/BaseLayout';
import {PAGE_TYPES} from '../utils/constants';
import ActivationKeys from './ActivationKeys';
import DXP from './DXP';
import DXPCloud from './DXPCloud';
import Home from './Home';
import Overview from './Overview';
import TeamMembers from './TeamMembers';

const Pages = () => {
	const [
		{page, project, sessionId, subscriptionGroups, userAccount},
	] = useCustomerPortal();

	const PageLayout = {
		[PAGE_TYPES.commerce]: {
			Component: (
				<ActivationKeys.Commerce
					accountKey={project?.accountKey}
					sessionId={sessionId}
				/>
			),
			Skeleton: <ActivationKeys.Skeleton />,
		},
		[PAGE_TYPES.dxp]: {
			Component: <DXP project={project} sessionId={sessionId} />,
			Skeleton: <ActivationKeys.Skeleton />,
		},
		[PAGE_TYPES.dxpCloud]: {
			Component: (
				<DXPCloud
					project={project}
					sessionId={sessionId}
					subscriptionGroups={subscriptionGroups}
					userAccount={userAccount}
				/>
			),
			Skeleton: <ActivationKeys.Skeleton />,
		},
		[PAGE_TYPES.enterpriseSearch]: {
			Component: (
				<ActivationKeys.EnterpriseSearch
					accountKey={project?.accountKey}
					sessionId={sessionId}
				/>
			),
			Skeleton: <ActivationKeys.Skeleton />,
		},
		[PAGE_TYPES.home]: {
			Component: <Home userAccount={userAccount} />,
			Skeleton: <Home.Skeleton />,
		},
		[PAGE_TYPES.overview]: {
			Component: (
				<Overview
					project={project}
					subscriptionGroups={subscriptionGroups}
				/>
			),
			Skeleton: <Overview.Skeleton />,
		},
		[PAGE_TYPES.teamMembers]: {
			Component: <TeamMembers project={project} sessionId={sessionId} />,
			Skeleton: <ActivationKeys.Skeleton />,
		},
	};

	if (
		((project && subscriptionGroups && sessionId) ||
			page === PAGE_TYPES.home) &&
		userAccount
	) {
		return (
			<Layout
				hasProjectContact={page === PAGE_TYPES.overview}
				hasQuickLinks={
					page !== PAGE_TYPES.teamMembers && page !== PAGE_TYPES.home
				}
				project={project}
			>
				{PageLayout[page].Component}
			</Layout>
		);
	}

	return PageLayout[page].Skeleton;
};

export default Pages;
