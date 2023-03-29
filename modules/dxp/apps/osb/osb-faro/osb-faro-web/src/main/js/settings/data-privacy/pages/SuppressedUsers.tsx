import BasePage from 'settings/components/BasePage';
import React from 'react';
import SuppressedUserList from '../hocs/SuppressedUserList';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {getDataPrivacy} from 'shared/util/breadcrumbs';
import {RootState} from 'shared/store';
import {Router} from 'shared/types';
import {User} from 'shared/util/records';
import {withAdminPermission} from 'shared/hoc';

const connector = connect((store: RootState, {groupId}: {groupId: string}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId'
	])
}));

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ISuppressedUsersProps extends PropsFromRedux {
	currentUser: User;
	router: Router;
}

export const SuppressedUsers: React.FC<ISuppressedUsersProps> = ({
	currentUser,
	router,
	timeZoneId
}) => {
	const {
		params: {groupId}
	} = router;

	return (
		<BasePage
			breadcrumbItems={[
				getDataPrivacy({groupId}),
				{
					active: true,
					label: Liferay.Language.get('suppressed-user-list')
				}
			]}
			className='suppressed-users-page-root'
			documentTitle={Liferay.Language.get('suppressed-user-list')}
			groupId={groupId}
		>
			<SuppressedUserList
				currentUser={currentUser}
				router={router}
				timeZoneId={timeZoneId}
			/>
		</BasePage>
	);
};

export default compose<any>(withAdminPermission, connector)(SuppressedUsers);
