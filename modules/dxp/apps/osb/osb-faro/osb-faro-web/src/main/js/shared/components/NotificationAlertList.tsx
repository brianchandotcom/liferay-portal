import * as API from 'shared/api';
import ClayLink from '@clayui/link';
import EmbeddedAlertList from 'shared/components/EmbeddedAlertList';
import React from 'react';
import TimeZoneAlert from './TimeZoneAlert';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {connect, ConnectedProps} from 'react-redux';
import {
	NotificationSubtypes,
	NotificationTypes
} from 'shared/util/records/Notification';
import {Routes, toRoute} from 'shared/util/router';
import {useRequest} from 'shared/hooks';

type PropsFromRedux = ConnectedProps<typeof connector>;

interface INotificationAlertListProps extends PropsFromRedux {
	data: {
		id: string;
		modifiedTime: string;
		subtype: NotificationSubtypes;
	}[];
	groupId: string;
	loading?: boolean;
	refetch?: () => void;
	stripe?: boolean;
	subtypes?: NotificationSubtypes[];
}

type NotificationStrategyParams = {
	groupId: string;
	modifiedTime?: number;
	notificationId: string;
	onClose: (id: string) => void;
	stripe?: boolean;
};

const notificationStrategies = new Map<string, Function>([
	[
		NotificationSubtypes.TimeZoneChanged,
		({
			groupId,
			modifiedTime,
			notificationId,
			onClose,
			stripe
		}: NotificationStrategyParams) => ({
			customComponent: () => (
				<TimeZoneAlert
					groupId={groupId}
					key={notificationId}
					modifiedTime={modifiedTime}
					onClose={() => onClose(notificationId)}
					stripe={stripe}
				/>
			)
		})
	],
	[
		NotificationSubtypes.BlockedEventsLimit,
		({
			groupId,
			notificationId,
			onClose,
			stripe
		}: NotificationStrategyParams) => ({
			alertType: 'warning',
			className: 'd-flex align-items-center',
			id: notificationId,
			key: notificationId,
			message: (
				<>
					<span>
						{Liferay.Language.get(
							'100-event-limit-reached,-resulting-in-blocked-events'
						)}
					</span>

					<ClayLink
						className='button-root py-0'
						href={toRoute(
							Routes.SETTINGS_DEFINITIONS_EVENTS_BLOCK_LIST,
							{groupId}
						)}
						small
					>
						{Liferay.Language.get('view-block-list')}
					</ClayLink>
				</>
			),
			onClose,
			stripe,
			title: Liferay.Language.get('limit-reached')
		})
	]
]);

const connector = connect(null, {addAlert});

export const useNotificationsAPI = (groupId: string) => {
	const response = useRequest({
		dataSourceFn: API.notifications.fetchNotifications,
		variables: {
			groupId,
			type: NotificationTypes.Alert
		}
	});

	return response;
};

const NotificationAlertList: React.FC<INotificationAlertListProps> = ({
	addAlert,
	data = [],
	groupId,
	loading,
	refetch,
	stripe = false,
	subtypes = [NotificationSubtypes.TimeZoneChanged]
}) => {
	if (loading || !data.length) return null;

	const removeNotification = (notificationId: string) => {
		API.notifications
			.readNotification(groupId, notificationId)
			.then(refetch)
			.catch(() => {
				addAlert &&
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-please-try-again'
						),
						timeout: false
					});
			});
	};

	const filterSubtypes = ({subtype}) => subtypes.includes(subtype);

	const transformData = ({id, modifiedTime, subtype}) => {
		const transformer = notificationStrategies.get(subtype);

		if (transformer) {
			return transformer({
				groupId,
				modifiedTime,
				notificationId: id,
				onClose: removeNotification,
				stripe
			});
		}
	};

	return (
		<EmbeddedAlertList
			alerts={data.filter(filterSubtypes).map(transformData)}
			className='notification-alert-list-root'
		/>
	);
};

export default connector(NotificationAlertList);
