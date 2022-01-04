import {useContext} from 'react';
import {AppContext} from '../context';
import {steps} from '../utils/constants';
import Invites from './Invites';
import SetupDXPCloud from './SetupDXPCloud';
import SuccessDXPCloud from './SuccessDXPCloud';
import Welcome from './Welcome';

const Pages = () => {
	const [{project, step, userAccount}] = useContext(AppContext);

	const StepsLayout = {
		[steps.invites]: {
			Component: <Invites />,
		},
		[steps.dxpCloud]: {
			Component: <SetupDXPCloud />,
		},
		[steps.successDxpCloud]: {
			Component: <SuccessDXPCloud />,
		},
		[steps.welcome]: {
			Component: <Welcome project={project} userAccount={userAccount} />,
			Skeleton: <Welcome.Skeleton />,
		},
	};

	if (userAccount && project) {
		return StepsLayout[step].Component;
	}

	return StepsLayout[steps.welcome].Skeleton;
};

export default Pages;
