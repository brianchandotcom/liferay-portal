import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React, {useEffect, useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import WizardPage from 'settings/components/base-page/WizardPage';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {AssignIndividualsDatatoPropertiesStep} from 'settings/components/salesforce/steps/AssignIndividualsDataToChannelsStep';
import {close, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {
	ConnectSalesforceProvider,
	useConnectSalesforce
} from 'settings/components/salesforce/ConnectSalesforceContext';
import {ConnectSalesforceStep} from 'settings/components/salesforce/steps/ConnectSalesforceStep';
import {DataSource} from 'shared/util/records';
import {fetch} from 'shared/api/data-source';
import {Heading, Text} from '@clayui/core';
import {sub} from 'shared/util/lang';
import {SyncSalesforceDataStep} from 'settings/components/salesforce/steps/SyncSalesforceDataStep';
import {useHistory, useParams} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';

type PropsFromRedux = ConnectedProps<typeof connector>;
interface IConnectSalesForceStepProps extends PropsFromRedux {
	groupId: string;
	onNext: () => void;
	onPrev: () => void;
}

type Step = {
	content: React.FC<IConnectSalesForceStepProps>;
	description: string;
	title: string;
};

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
		content: props => <AssignIndividualsDatatoPropertiesStep {...props} />,
		description: Liferay.Language.get(
			'properties-allow-you-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels.-individuals-data-will-be-available-in-any-property-they-are-assigned-to'
		),
		title: Liferay.Language.get('assign-individuals-data-to-properties')
	}
];

function getSafeStepFromURL(initStep: string | null) {
	const step = Number(initStep);

	if (!step || step < 0) {
		return 0;
	}

	if (step >= steps.length) {
		return steps.length - 1;
	}

	return step;
}

function updateSearchParams(history, key: string, value: any) {
	const params = new URLSearchParams(window.location.search);
	params.set(key, String(value));

	history.push({
		pathname: window.location.pathname,
		search: params.toString()
	});
}

const ConnectSalesforce = ({addAlert, close, open}) => {
	const {dataSource, setDataSource} = useConnectSalesforce();
	const {groupId} = useParams();
	const history = useHistory();
	const params = useQueryParams();

	const [stepIndex, setStepIndex] = useState(
		getSafeStepFromURL(params.stepIndex)
	);

	const currentStep = steps[stepIndex];

	const handleSetStep = (newStepIndex: number) => {
		updateSearchParams(history, 'stepIndex', newStepIndex);

		setStepIndex(newStepIndex);
	};

	useEffect(() => {
		async function fetchFn() {
			try {
				const dataSource = await fetch({
					groupId,
					id: params.dataSourceId
				});

				setDataSource(new DataSource(dataSource));
			} catch (error) {
				addAlert({
					alertType: Alert.Types.Error,
					message: error.message,
					timeout: false
				});
			}
		}

		if (params.dataSourceId && !dataSource) {
			fetchFn();
		}
	}, [groupId, params.dataSourceId]);

	return (
		<WizardPage>
			<div className='w-100'>
				<Text color='secondary' size={3}>
					{sub(Liferay.Language.get('step-x-of-x'), [
						stepIndex + 1,
						steps.length
					])}
				</Text>

				<div className='mb-3 mt-2'>
					<Heading level={4} weight='bold'>
						{currentStep.title}
					</Heading>
				</div>

				<div className='mb-1'>
					<Text color='secondary' size={4}>
						{currentStep.description}
					</Text>
				</div>

				<ClayLink
					decoration='underline'
					href={URLConstants.HelpConnectDxp}
					target='_blank'
				>
					<Text size={4} weight='semi-bold'>
						{Liferay.Language.get('learn-more-about-data-sources')}
					</Text>

					<ClayIcon
						aria-label={Liferay.Language.get(
							'learn-more-about-data-sources'
						)}
						className='ml-1'
						fontSize={12}
						symbol='shortcut'
					/>
				</ClayLink>

				<div className='mt-5'>
					<currentStep.content
						addAlert={addAlert}
						close={close}
						groupId={groupId}
						onNext={() => {
							if (stepIndex < steps.length - 1) {
								handleSetStep(stepIndex + 1);
							}
						}}
						onPrev={() => {
							if (stepIndex > 0) {
								handleSetStep(stepIndex - 1);
							}
						}}
						open={open}
					/>
				</div>
			</div>
		</WizardPage>
	);
};

const connector = connect(null, {
	addAlert,
	close,
	open
});

export default connector((props: any) => (
	<ConnectSalesforceProvider>
		<ConnectSalesforce {...props} />
	</ConnectSalesforceProvider>
));
