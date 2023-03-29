import withAction from './WithAction';
import {fetchCurrentUser} from '../actions/users';

export default withAction(
	({groupId, router}) =>
		fetchCurrentUser(groupId || (router ? router.params.groupId : null)),
	state => {
		const currentUser = state.get('currentUser');

		return currentUser.data
			? state.getIn(['users', currentUser.data])
			: currentUser;
	},
	{propName: 'currentUser'}
);
