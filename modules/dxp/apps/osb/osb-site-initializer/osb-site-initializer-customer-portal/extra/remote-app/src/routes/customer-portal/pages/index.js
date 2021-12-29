import Layout from '../components/Layout';
import {useCustomerPortal} from '../context';
import {pages} from '../utils/constants';
import ActivationKeys from './ActivationKeys';
import DXPCloud from './DXPCloud';
import Home from './Home';
import Overview from './Overview';
import TeamMembers from './TeamMembers';

const Pages = () => {
	const [{page, project, sessionId, userAccount}] = useCustomerPortal();

	const PageSkeletons = {
		[pages.COMMERCE]: <ActivationKeys.Skeleton />,
		[pages.DXP_CLOUD]: <ActivationKeys.Skeleton />,
		[pages.ENTERPRISE_SEARCH]: <ActivationKeys.Skeleton />,
		[pages.HOME]: <Home.Skeleton />,
		[pages.OVERVIEW]: <div>Overview Skeleton</div>,
		[pages.TEAM_MEMBERS]: <ActivationKeys.Skeleton />,
	};
	const PageComponent = {
		[pages.COMMERCE]: (
			<ActivationKeys.Commerce
				accountKey={project?.accountKey}
				sessionId={sessionId}
			/>
		),
		[pages.DXP_CLOUD]: <DXPCloud />,
		[pages.ENTERPRISE_SEARCH]: (
			<ActivationKeys.EnterpriseSearch
				accountKey={project?.accountKey}
				sessionId={sessionId}
			/>
		),
		[pages.HOME]: <Home userAccount={userAccount} />,
		[pages.OVERVIEW]: (
			<Overview project={project} userAccount={userAccount} />
		),
		[pages.TEAM_MEMBERS]: <TeamMembers />,
	};

	if ((project || page === pages.HOME) && userAccount && sessionId) {
		return (
			<Layout
				hasProjectContacts={page === pages.OVERVIEW}
				hasQuickLinks={page !== pages.TEAM_MEMBERS}
			>
				{PageComponent[page]}
			</Layout>
		);
	}

	return PageSkeletons[page];
};

export default Pages;
