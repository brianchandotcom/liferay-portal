import React from 'react';
import WizardPage, {Step} from 'settings/components/base-page/WizardPage';
import {Alert} from 'shared/types';
import {AssignIndividualsDataToPropertiesStep} from 'settings/components/salesforce/steps/AssignIndividualsDataToChannelsStep';
import {ConnectDemandbaseStep} from 'settings/components/demandbase/steps/ConnectDemandbaseStep';
import {updateDemandbase} from 'shared/api/data-source';

const steps: Step[] = [
	{
		content: props => <ConnectDemandbaseStep {...props} />,
		description: Liferay.Language.get(
			'connect-your-demandbase-account-to-analytics-cloud-to-start-importing-company-people-and-engagement-data'
		),
		title: Liferay.Language.get('connect-demandbase')
	},

	{
		content: props => (
			<AssignIndividualsDataToPropertiesStep
				{...props}
				onSubmit={() => {
					props.addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get(
							'the-data-source-setup-has-finished'
						)
					});
				}}
				updateDataSourceFn={updateDemandbase}
			/>
		),
		description: Liferay.Language.get(
			'properties-allow-you-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels.-individuals-data-will-be-available-in-any-property-they-are-assigned-to'
		),
		title: Liferay.Language.get('assign-individuals-data-to-properties')
	}
];

const ConnectDemandbase = () => <WizardPage steps={steps} />;

export default ConnectDemandbase;
