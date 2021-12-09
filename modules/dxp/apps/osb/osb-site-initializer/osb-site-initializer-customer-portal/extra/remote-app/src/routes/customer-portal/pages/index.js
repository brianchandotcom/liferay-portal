/* eslint-disable no-unused-vars */
import {useContext} from 'react';
import {AppContext} from '../context';
import ActivationKeys from './ActivationKeys';
import Home from './Home';
import Overview from './Overview';

const Pages = () => {
	const [{page, project, userAccount}] = useContext(AppContext);

	if (page === 'overview') {
		if (project) {
			return <ActivationKeys.EnterpriseSearch accountKey={project.accountKey} />;
		}

		return <ActivationKeys.Skeleton />;
	}

	if (page === 'enterprise_search') {
		if (project) {
			return <ActivationKeys.EnterpriseSearch accountKey={project.accountKey} />;
		}

		return <ActivationKeys.Skeleton />;
	}

	if (page === 'commerce') {
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
