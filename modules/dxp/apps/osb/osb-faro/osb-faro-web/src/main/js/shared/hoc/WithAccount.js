import withAction from './WithAction';
import {ACCOUNTS, Routes, toRoute} from 'shared/util/router';
import {fetchAccount} from 'shared/actions/accounts';

export default withAction(
	({groupId, id}) => fetchAccount({accountId: id, groupId}),
	(state, {id}) => state.getIn(['accounts', id]),
	{
		errorPageProps: ({channelId, groupId}) => ({
			href: toRoute(Routes.CONTACTS_LIST_ENTITY, {
				channelId,
				groupId,
				type: ACCOUNTS
			}),
			linkLabel: Liferay.Language.get('go-to-accounts'),
			message: Liferay.Language.get(
				'the-account-you-are-looking-for-does-not-exist'
			),
			subtitle: Liferay.Language.get('account-not-found')
		}),
		propName: 'account'
	}
);
