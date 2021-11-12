import {useContext} from 'react';
import {AppContext} from '../context';
import {steps} from '../utils/constants';
import Invites from './Invites';
import SetupDXP from './SetupDXP';
import Welcome from './Welcome';

const Pages = () => {
	const [{externalReferenceCode, step}] = useContext(AppContext);

	if (step === steps.invites) {
		return <Invites />;
	}

	if (step === steps.dxp) {
		return <SetupDXP />;
	}

	return <Welcome externalReferenceCode={externalReferenceCode} />;
};

export default Pages;
