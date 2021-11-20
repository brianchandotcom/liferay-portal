import {useContext} from 'react';
import {AppContext} from '../context';
import Home from './Home';
import Overview from './Overview';

const Pages = () => {
	const [{page, userAccount}] = useContext(AppContext);

	if (page === 'overview') {
		if (userAccount) {
			return <Overview userAccount={userAccount} />;
		}

		return <div>Overview Skeleton</div>;
	}

	if (userAccount) {
		return <Home userAccount={userAccount} />;
	}

	return <Home.Skeleton />;
};

export default Pages;
