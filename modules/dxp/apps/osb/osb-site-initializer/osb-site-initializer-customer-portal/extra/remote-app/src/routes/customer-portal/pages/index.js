import {useContext} from 'react';
import {AppContext} from '../context';
import {pages} from '../utils/constants';
import ActivationKeys from './ActivationKeys';
import Home from './Home';
import Overview from './Overview';

const Pages = () => {
	const [{page, project, userAccount}] = useContext(AppContext);

	if (page === pages.OVERVIEW) {
		if (userAccount) {
			return <Overview userAccount={userAccount} />;
		}

		return <ActivationKeys.Skeleton />;
	}

	if (page === pages.ENTERPRISE_SEARCH) {
		if (project) {
			return (
				<ActivationKeys.EnterpriseSearch
					accountKey={project.accountKey}
				/>
			);
		}

		return <ActivationKeys.Skeleton />;
	}

	if (page === pages.COMMERCE) {
		if (userAccount) {
			return <ActivationKeys.Commerce accountKey={project.accountKey} />;
		}

		return <ActivationKeys.Skeleton />;
	}

	if (userAccount) {
		return <Home userAccount={userAccount} />;
	}

	return <Home.Skeleton />;
};

export default Pages;
