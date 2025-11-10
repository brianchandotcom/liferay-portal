import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React, {useEffect, useState} from 'react';
import SalesforceAccountsAndIndividuals from 'settings/components/salesforce/SalesforceAccountsAndIndividuals';
import URLConstants from 'shared/util/url-constants';
import WizardPage from 'settings/components/base-page/WizardPage';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {ConnectSalesforceAuth} from 'settings/components/salesforce/ConnectSalesforceAuth';
import {DataSource} from 'shared/util/records';
import {disconnect, fetch} from 'shared/api/data-source';
import {Heading, Text} from '@clayui/core';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {useHistory, useParams} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';

type PropsFromRedux = ConnectedProps<typeof connector>;

type Step = {
	content: React.FC<IConnectSalesForceStepProps>;
	description: string;
	title: string;
};

const steps: Step[] = [
	{
		content: props => <ConnectSalesForceStep {...props} />,
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
	const history = useHistory();
	const {groupId} = useParams();
	const params = useQueryParams();

	const [stepIndex, setStepIndex] = useState(
		getSafeStepFromURL(params.stepIndex)
	);

	const currentStep = steps[stepIndex];

	const handleSetStep = (newStepIndex: number) => {
		updateSearchParams(history, 'stepIndex', newStepIndex);

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

				<ClayLink href={URLConstants.HelpConnectDxp} target='_blank'>
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
	addAlert,
	close,
	open
});

interface IConnectSalesForceStepProps extends PropsFromRedux {
	groupId: string;
	onNext: () => void;
	onPrev: () => void;
}

const ConnectSalesForceStep: React.FC<IConnectSalesForceStepProps> = ({
	addAlert,
	close,
	groupId,
	onNext,
	open
}) => {
	const history = useHistory();
	const {id} = useQueryParams();
	const [dataSource, setDataSource] = useState();

	useEffect(() => {
		async function fetchFn() {
			try {
				const dataSource = await fetch({
					groupId,
					id
				});

				setDataSource(dataSource);
			} catch (error) {
				throw new Error(error);
			}
		}

		if (id) {
			fetchFn();
		}
	}, [id]);

	if (!id) {
		return (
			<ConnectSalesforceAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				onCancel={() => {
					history.push(
						toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
							groupId
						})
					);
				}}
				onSubmit={({id}) => {
					updateSearchParams(history, 'id', id);

					onNext();
				}}
			/>
		);
	}

	if (!dataSource) {
		return null;
	}

	return (
		<ClayForm
			onSubmit={event => {
				event.preventDefault();

				onNext();
			}}
		>
			<ClayAlert
				displayType='success'
				title={Liferay.Language.get('success')}
			>
				{Liferay.Language.get('connection-established-successfully')}
			</ClayAlert>

			<ConnectSalesforceAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				dataSource={new DataSource(dataSource)}
				disabled
				onSubmit={onNext}
			/>

			<ButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={() => {
					open(modalTypes.CONFIRMATION_MODAL, {
						message: (
							<Text as='p' size={4}>
								{Liferay.Language.get(
									'this-action-will-stop-syncing-data-from-salesforce-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
								)}
							</Text>
						),
						modalVariant: 'modal-warning',
						onClose: close,
						onSubmit: () =>
							disconnect({
								groupId,
								id
							})
								.then(() => {
									addAlert({
										alertType: Alert.Types.Success,
										message: Liferay.Language.get(
											'data-source-disconnected'
										)
									});

									history.push(
										toRoute(
											Routes.SETTINGS_SALESFORCE_ADD,
											{
												groupId
											}
										)
									);

									close();
								})
								.catch(() => {
									addAlert({
										alertType: Alert.Types.Error,
										message: Liferay.Language.get(
											'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
										),
										timeout: false
									});
								}),
						submitButtonDisplay: 'warning',
						submitMessage: Liferay.Language.get('disconnect'),
						title: Liferay.Language.get('disconnect-data-source'),
						titleIcon: 'warning-full'
					});
				}}
				prevButtonLabel={Liferay.Language.get('disconnect-data-source')}
			/>
		</ClayForm>
	);
};

const SyncSalesforceDataStep = ({onNext, onPrev}) => {
	const [accounts, setAccounts] = useState(false);
	const [individuals, setIndividuals] = useState(false);

	return (
		<ClayForm
			onSubmit={event => {
				event.preventDefault();

				onNext();

				addAlert();
			}}
		>
			<SalesforceAccountsAndIndividuals
				accounts={accounts}
				individuals={individuals}
				onChange={({accounts, individuals}) => {
					setAccounts(accounts);
					setIndividuals(individuals);
				}}
			/>

			<ButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={onPrev}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

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
