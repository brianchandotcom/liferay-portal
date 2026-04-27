import {Languages} from './types';
import {sub} from 'shared/util/lang';

export function buildLanguages(displayName: string): Languages {
	return {
		connectDescription: sub(
			Liferay.Language.get(
				'connect-your-x-account-to-analytics-cloud-to-start-importing-data'
			),
			[displayName]
		) as string,
		connectTitle: sub(Liferay.Language.get('connect-x'), [
			displayName
		]) as string,
		disconnectedAlert: sub(
			Liferay.Language.get(
				'the-data-source-is-disconnected.-data-is-no-longer-being-synced-from-x,-but-you-can-reconnect-to-resume-syncing'
			),
			[displayName]
		) as string,
		endpointHelper: sub(
			Liferay.Language.get(
				'this-is-analytics-cloud-url-x-will-redirect-to-after-a-user-authorizes-the-connection'
			),
			[displayName]
		) as string,
		endpointLabel: sub(
			Liferay.Language.get('copy-this-endpoint-url-to-your-x-instance'),
			[displayName]
		) as string,
		reconnectHelper: sub(
			Liferay.Language.get(
				'to-reestablish-the-connection-between-x-instance-and-liferay-analytics-cloud,-copy-the-token-below-and-go-to-x-instance-settings-to-continue-the-data-source-configuration'
			),
			[displayName]
		) as string,
		successAlert: sub(
			Liferay.Language.get(
				'you-have-successfully-authenticated-your-token-with-liferay-analytics-cloud.-x-data-will-appear-here-once-it-is-sent-and-processed'
			),
			[displayName]
		) as string,
		syncHelper: sub(
			Liferay.Language.get(
				'to-configure-your-x-data-source,-go-to-x-account-connector-settings-and-use-the-token-and-endpoint-url-provided-by-liferay-analytics-cloud'
			),
			[displayName]
		) as string,
		tokenLabel: sub(
			Liferay.Language.get('copy-this-token-to-your-x-instance'),
			[displayName]
		) as string
	};
}
