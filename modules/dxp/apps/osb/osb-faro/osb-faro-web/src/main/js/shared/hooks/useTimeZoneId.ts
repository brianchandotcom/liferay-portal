import {useParams} from 'react-router-dom';
import {useStore} from 'react-redux';

export const useTimeZoneId = () => {
	const {groupId} = useParams();
	const store = useStore();
	const timeZoneId = store
		.getState()
		.getIn(['projects', groupId, 'data', 'timeZone', 'timeZoneId']);

	return timeZoneId;
};
