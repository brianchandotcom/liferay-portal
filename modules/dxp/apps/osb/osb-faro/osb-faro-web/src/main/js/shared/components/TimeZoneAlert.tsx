import Alert, {AlertTypes} from 'shared/components/Alert';
import React from 'react';
import withCurrentUser from 'shared/hoc/WithCurrentUser';
import {applyTimeZone} from 'shared/util/date';
import {compose} from 'redux';
import {connect} from 'react-redux';

import {Map} from 'immutable';
import {RootState} from 'shared/store';
import {sub} from 'shared/util/lang';
import {User} from 'shared/util/records';

const TIME_ZONE_COUNTRY_REGEX = /\([^)]+.*/;

interface ITimeZoneAlertProps {
	currentUser: User;
	displayTimeZone: string;
	modifiedTime: number;
	onClose: () => void;
	stripe: boolean;
	timeZoneId: string;
}

const TimeZoneAlert: React.FC<ITimeZoneAlertProps> = ({
	currentUser,
	displayTimeZone,
	modifiedTime,
	onClose,
	stripe,
	timeZoneId
}) => (
	<Alert
		iconSymbol='exclamation-full'
		onClose={onClose}
		stripe={stripe}
		title={Liferay.Language.get('info')}
		type={AlertTypes.Info}
	>
		{sub(
			Liferay.Language.get(
				'workspace-timezone-has-changed-to-x-as-of-x.-please-allow-1-2-days-for-the-data-to-adjust-to-this-new-setting.'
			),
			[
				displayTimeZone.replace(TIME_ZONE_COUNTRY_REGEX, ''),
				applyTimeZone(
					modifiedTime,
					timeZoneId,
					currentUser.languageId
				).fromNow()
			]
		)}
	</Alert>
);

const connector = connect((state: RootState, {groupId}: {groupId: string}) => {
	const timeZone = state.getIn(
		['projects', groupId, 'data', 'timeZone'],
		Map()
	);

	return {
		displayTimeZone: timeZone.get('displayTimeZone', ''),
		timeZoneId: timeZone.get('timeZoneId', '')
	};
}, null);

export default compose<any>(connector, withCurrentUser)(TimeZoneAlert);
