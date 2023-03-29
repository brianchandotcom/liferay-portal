import BasePage from 'settings/components/BasePage';
import React from 'react';
import RequestList from '../hocs/RequestList';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {getDataPrivacy} from 'shared/util/breadcrumbs';
import {RootState} from 'shared/store';
import {Router} from 'shared/types';
import {User} from 'shared/util/records';
import {withCurrentUser} from 'shared/hoc';

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

interface IRequestLogProps extends PropsFromRedux {
	currentUser: User;
	router: Router;
}

export const RequestLog: React.FC<IRequestLogProps> = ({
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
					label: Liferay.Language.get('request-log')
				}
			]}
			className='request-log-page-root'
			documentTitle={Liferay.Language.get('request-log')}
			groupId={groupId}
		>
			<RequestList
				currentUser={currentUser}
				router={router}
				timeZoneId={timeZoneId}
			/>
		</BasePage>
	);
};

export default compose<any>(withCurrentUser)(RequestLog);
