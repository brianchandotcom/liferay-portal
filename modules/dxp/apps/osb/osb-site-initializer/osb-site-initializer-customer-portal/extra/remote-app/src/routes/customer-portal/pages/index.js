import {useContext} from 'react';
import {AppContext} from '../context';
import Home from './Home';

const Pages = () => {
	const [{userAccount}] = useContext(AppContext);

	if (userAccount) {
		return <Home userAccount={userAccount} />;
	}
	else {
		return <Home.Skeleton />;
	}
};

export default Pages;
