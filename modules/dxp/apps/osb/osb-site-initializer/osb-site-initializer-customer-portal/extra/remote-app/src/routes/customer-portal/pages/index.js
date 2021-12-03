import {useContext} from 'react';
import {AppContext} from '../context';
import Home from './Home';
import Overview from './Overview';
import CommercePage from './commerce';
import EnterpriseSearch from './enterpriseSearch';

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

		return <div>Overview Skeleton</div>;
	}

	if (page === 'commerce') {
		if (userAccount) {
			return <CommercePage userAccount={userAccount} />;
		}

		return <div>Overview Skeleton</div>;
	}

	if (userAccount) {
		return <Home userAccount={userAccount} />;
	}

	return <Home.Skeleton />;
};

export default Pages;
