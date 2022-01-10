import {useContext} from 'react';
import {AppContext} from '../context';
import {steps} from '../utils/constants';
import Invites from './Invites';
import SetupDXPCloud from './SetupDXPCloud';
import SuccessDXPCloud from './SuccessDXPCloud';
import Welcome from './Welcome';

const Pages = () => {
	const [{project, step}] = useContext(AppContext);

	const StepsLayout = {
		[steps.invites]: {
			Component: <Invites project={project} />,
		},
		[steps.dxpCloud]: {
			Component: <SetupDXPCloud project={project} />,
		},
		[steps.successDxpCloud]: {
			Component: <SuccessDXPCloud project={project} />,
		},
		[steps.welcome]: {
			Component: <Welcome />,
			Skeleton: <Welcome.Skeleton />,
		},
	};

	if (project) {
		return StepsLayout[step].Component;
	}

	return StepsLayout[steps.welcome].Skeleton;
};

export default Pages;
