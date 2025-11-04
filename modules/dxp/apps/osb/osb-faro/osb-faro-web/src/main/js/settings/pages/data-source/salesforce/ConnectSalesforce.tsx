import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import WizardPage from 'settings/components/base-page/WizardPage';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {connect} from 'react-redux';
import {ConnectSalesforceAuth} from 'settings/components/salesforce/ConnectSalesforceAuth';
import {Heading, Text} from '@clayui/core';
import {Routes, setUriQueryValue, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {useHistory, useParams} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';

type Step = {
	content: React.FC<{
		addAlert: Alert.AddAlert;
		groupId: string;
		onNext: () => void;
		onPrev: () => void;
	}>;
	description: string;
	title: string;
};

const steps: Step[] = [
	{
		content: props => <ConnectSalesForceStep {...props} />,
		description: Liferay.Language.get(
			'to-connect-your-salesforce-environment-with-liferay-analytics-cloud,-generate-a-token-and-paste-the-code-on-the-input-below.'
		),
		title: Liferay.Language.get('connect-salesforce')
	},
	{
		content: props => <SyncSalesforceDataStep {...props} />,
		description: Liferay.Language.get(
			'select-which-salesforce-data-you-would-like-to-sync-to-analytics-cloud.'
		),
		title: Liferay.Language.get('sync-Salesforce-data')
	},
	{
		content: props => <AssignIndividualsDatatoPropertiesStep {...props} />,
		description: Liferay.Language.get(
			'properties-allow-you-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels.-individuals-data-will-be-available-in-any-property-they-are-assigned-to.'
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

const ConnectSalesforce = ({addAlert}) => {
	const history = useHistory();
	const {groupId} = useParams();
	const params = useQueryParams();

	const [stepIndex, setStepIndex] = useState(
		getSafeStepFromURL(params.stepIndex)
	);

	const currentStep = steps[stepIndex];

	const handleSetStep = (newStepIndex: number) => {
		history.push(
			setUriQueryValue(
				toRoute(Routes.SETTINGS_SALESFORCE_ADD, {
					groupId
				}),
				'stepIndex',
				newStepIndex
			)
		);

		setStepIndex(newStepIndex);
	};

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

				{/* TODO: Add link to the documentation */}

				<a href='/#' target='_blank'>
					<Text size={4} weight='semi-bold'>
						{Liferay.Language.get('learn-more-about-data-sources.')}
					</Text>

					<ClayIcon
						aria-label={Liferay.Language.get(
							'learn-more-about-data-sources.'
						)}
						className='ml-1'
						fontSize={12}
						symbol='shortcut'
					/>
				</a>

				<div className='mt-5'>
					<currentStep.content
						addAlert={addAlert}
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
					/>
				</div>
			</div>
		</WizardPage>
	);
};

interface IButtonGroupProps {
	nextButtonLabel: string;
	onCancel: () => void;
	prevButtonLabel: string;
}

const ButtonGroup: React.FC<IButtonGroupProps> = ({
	nextButtonLabel,
	onCancel,
	prevButtonLabel
}) => (
	<div className='mt-5'>
		<ClayButton block type='submit'>
			{nextButtonLabel}
		</ClayButton>

		<ClayButton block borderless displayType='secondary' onClick={onCancel}>
			{prevButtonLabel}
		</ClayButton>
	</div>
);

const connector = connect(null, {
	addAlert
});

interface IConnectSalesForceStepProps {
	addAlert: Alert.AddAlert;
	groupId: string;
	onNext: () => void;
	onPrev: () => void;
}

const ConnectSalesForceStep: React.FC<IConnectSalesForceStepProps> = ({
	onNext,
	onPrev
}) => (
	<ClayForm
		onSubmit={event => {
			event.preventDefault();

			onNext();
		}}
	>
		{'working in progress...'}

		<ButtonGroup
			nextButtonLabel={Liferay.Language.get('continue')}
			onCancel={onPrev}
			prevButtonLabel={Liferay.Language.get('previous')}
		/>
	</ClayForm>
);

const SyncSalesforceDataStep = ({onNext, onPrev}) => (
	<ClayForm
		onSubmit={event => {
			event.preventDefault();

			onNext();
		}}
	>
		{'working in progress...'}

		<ButtonGroup
			nextButtonLabel={Liferay.Language.get('continue')}
			onCancel={onPrev}
			prevButtonLabel={Liferay.Language.get('previous')}
		/>
	</ClayForm>
);

const AssignIndividualsDatatoPropertiesStep = ({onNext, onPrev}) => (
	<ClayForm
		onSubmit={event => {
			event.preventDefault();

			onNext();
		}}
	>
		<span>{'working in progress...'}</span>

		<ButtonGroup
			nextButtonLabel={Liferay.Language.get('finish-setup')}
			onCancel={onPrev}
			prevButtonLabel={Liferay.Language.get('previous')}
		/>
	</ClayForm>
);

export default connector(ConnectSalesforce);
