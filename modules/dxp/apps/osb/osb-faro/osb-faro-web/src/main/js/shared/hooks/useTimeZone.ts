import {ITimeZone} from 'shared/util/records/TimeZone';
import {useParams} from 'react-router-dom';
import {useSelector} from 'react-redux';

export const useTimeZone = (): ITimeZone => {
	const {groupId} = useParams();
	const value = useSelector<any, any>(state =>
		state.getIn(['projects', groupId, 'data', 'timeZone'])
	);

	return value.toObject();
};
