import {useContext} from 'react';
import {AppContext} from '../context';
import {steps} from '../utils/constants';
import Invites from './Invites';
import SetupDXP from './SetupDXP';
import Welcome from './Welcome';

const Pages = () => {
	const [{step, userAccount}] = useContext(AppContext);

	if (step === steps.invites) {
		return <Invites />;
	}

	if (step === steps.dxp) {
		return <SetupDXP />;
	}

	if (userAccount) {
		return <Welcome userAccount={userAccount} />;
	}

	return <Welcome.Skeleton />;
};

export default Pages;
