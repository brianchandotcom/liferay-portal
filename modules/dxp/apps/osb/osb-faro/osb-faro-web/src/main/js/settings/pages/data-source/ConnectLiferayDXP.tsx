import React from 'react';
import WizardPage, {Step} from 'settings/components/base-page/WizardPage';
import {AssignLiferayIndividualsDataToChannelsStep} from 'settings/components/liferay/steps/AssignLiferayIndividualsDataToChannelsStep';
import {ConnectLiferayDXPStep} from 'settings/components/liferay/steps/ConnectLiferayDXPStep';
import {ReviewSyncedDataStep} from 'settings/components/liferay/steps/ReviewSyncedDataStep';

const steps: Step[] = [
	{
		content: props => <ConnectLiferayDXPStep {...props} />,
		description: Liferay.Language.get(
			'connect-your-dxp-instance-to-analytics-cloud-to-start-tracking-users-visits-and-interactions-within-your-sites'
		),
		title: Liferay.Language.get('connect-your-dxp-analytics')
	},
	{
		content: props => <ReviewSyncedDataStep {...props} />,
		description: Liferay.Language.get(
			'while-your-data-continues-syncing-in-the-background,-you-may-proceed-with-the-setup-process.-to-monitor-the-sync-status-at-any-time,-go-to-settings-in-data-sources'
		),
		title: Liferay.Language.get('review-synced-data')
	},
	{
		content: props => (
			<AssignLiferayIndividualsDataToChannelsStep {...props} />
		),
		description: Liferay.Language.get(
			'properties-allow-you-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels.-individuals-data-will-be-available-in-any-property-they-are-assigned-to'
		),
		title: Liferay.Language.get('assign-individuals-data-to-properties')
	}
];

const ConnectSalesforce = () => <WizardPage steps={steps} />;

export default ConnectSalesforce;
