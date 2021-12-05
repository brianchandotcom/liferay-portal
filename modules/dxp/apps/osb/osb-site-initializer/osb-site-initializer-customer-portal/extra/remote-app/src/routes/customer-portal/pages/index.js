import {useContext} from 'react';
import {AppContext} from '../context';
import Commerce from './Commerce';
import EnterpriseSearch from './EnterpriseSearch';
import Home from './Home';
import Overview from './Overview';

const Pages = () => {
	const [{page, project, userAccount}] = useContext(AppContext);

	if (page === 'overview') {
		if (userAccount) {
			return <Overview userAccount={userAccount} />;
		}

		return <div>Overview Skeleton</div>;
	}

	if (page === 'enterprise_search') {
		if (userAccount) {
			return <EnterpriseSearch accountKey={project.accountKey} />;
		}

		return <EnterpriseSearch.Skeleton />;
	}
	if (page === 'commerce') {
		if (userAccount) {
			return <Commerce accountKey={project.accountKey} />;
		}

		return <Commerce.Skeleton />;
	}

	if (userAccount) {
		return <Home userAccount={userAccount} />;
	}

	return <Home.Skeleton />;
};

export default Pages;
