import {useContext} from 'react';
import {AppContext} from '../context';
import {steps} from '../utils/constants';
import Invites from './invites';
import SetupDXP from './setup.dxp';
import Welcome from './welcome';

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
