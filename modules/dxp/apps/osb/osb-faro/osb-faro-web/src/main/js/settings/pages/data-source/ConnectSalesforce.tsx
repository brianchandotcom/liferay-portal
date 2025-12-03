import React from 'react';
import WizardPage, {Step} from 'settings/components/base-page/WizardPage';
import {AssignIndividualsDatatoPropertiesStep} from 'settings/components/salesforce/steps/AssignIndividualsDataToChannelsStep';
import {ConnectSalesforceStep} from 'settings/components/salesforce/steps/ConnectSalesforceStep';
import {SyncSalesforceDataStep} from 'settings/components/salesforce/steps/SyncSalesforceDataStep';
import {updateSalesforce} from 'shared/api/data-source';

const steps: Step[] = [
	{
		content: props => <ConnectSalesforceStep {...props} />,
		description: Liferay.Language.get(
			'to-connect-your-salesforce-environment-with-liferay-analytics-cloud,-generate-a-token-and-paste-the-code-on-the-input-below'
		),
		title: Liferay.Language.get('connect-salesforce')
	},
	{
		content: props => <SyncSalesforceDataStep {...props} />,
		description: Liferay.Language.get(
			'select-which-salesforce-data-you-would-like-to-sync-to-analytics-cloud'
		),
		title: Liferay.Language.get('sync-Salesforce-data')
	},
	{
		content: props => (
			<AssignIndividualsDatatoPropertiesStep
				{...props}
				updateDataSourceFn={updateSalesforce}
			/>
		),
		description: Liferay.Language.get(
			'properties-allow-you-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels.-individuals-data-will-be-available-in-any-property-they-are-assigned-to'
		),
		title: Liferay.Language.get('assign-individuals-data-to-properties')
	}
];

const ConnectSalesforce = () => <WizardPage steps={steps} />;

export default ConnectSalesforce;
