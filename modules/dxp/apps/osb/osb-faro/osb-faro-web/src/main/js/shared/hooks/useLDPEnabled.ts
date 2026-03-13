import {Map} from 'immutable';
import {useSelector} from 'react-redux';

type RootState = Map<string, any>;

export const useLDPEnabled = ({groupId}: {groupId: string}) => {
	const project = useSelector((state: RootState) =>
		state.getIn(['projects', groupId, 'data'], null)
	);

	const planName: string = project?.getIn(['faroSubscription', 'name'], null);

	return planName?.includes('Data Platform') ?? false;
};
