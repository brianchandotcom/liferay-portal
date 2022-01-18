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

import Layout from '../components/Layout';
import {useCustomerPortal} from '../context';
import {pages} from '../utils/constants';
import ActivationKeys from './ActivationKeys';
import DXPCloud from './DXPCloud';
import Home from './Home';
import Overview from './Overview';
import TeamMembers from './TeamMembers';

const Pages = () => {
	const [
		{page, project, sessionId, subscriptionGroups, userAccount},
	] = useCustomerPortal();

	const PageLayout = {
		[pages.COMMERCE]: {
			Component: (
				<ActivationKeys.Commerce
					accountKey={project?.accountKey}
					sessionId={sessionId}
				/>
			),
			Skeleton: <ActivationKeys.Skeleton />,
		},
		[pages.DXP_CLOUD]: {
			Component: (
				<DXPCloud
					project={project}
					subscriptionGroups={subscriptionGroups}
					userAccount={userAccount}
				/>
			),
			Skeleton: <ActivationKeys.Skeleton />,
		},
		[pages.ENTERPRISE_SEARCH]: {
			Component: (
				<ActivationKeys.EnterpriseSearch
					accountKey={project?.accountKey}
					sessionId={sessionId}
				/>
			),
			Skeleton: <ActivationKeys.Skeleton />,
		},
		[pages.HOME]: {
			Component: <Home userAccount={userAccount} />,
			Skeleton: <Home.Skeleton />,
		},
		[pages.OVERVIEW]: {
			Component: (
				<Overview
					project={project}
					subscriptionGroups={subscriptionGroups}
				/>
			),
			Skeleton: <Overview.Skeleton />,
		},
		[pages.TEAM_MEMBERS]: {
			Component: <TeamMembers project={project} />,
			Skeleton: <ActivationKeys.Skeleton />,
		},
	};

	if (
		((project && subscriptionGroups && sessionId) || page === pages.HOME) &&
		userAccount
	) {
		return (
			<Layout
				hasProjectContact={page === pages.OVERVIEW}
				hasQuickLinks={
					page !== pages.TEAM_MEMBERS && page !== pages.HOME
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
