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
			Component: <DXPCloud />,
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
			Component: <TeamMembers />,
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
