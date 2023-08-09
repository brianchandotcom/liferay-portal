/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import {FieldArray, Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import {
	addAnalyticsCloudWorkspace,
	addHighPriorityContact,
	deleteHighPriorityContacts,
	getAnalyticsCloudPageInfo,
	getAnalyticsCloudWorkspace,
	updateAccountSubscriptionGroups,
} from '../../../../common/services/liferay/graphql/queries';

import {
	isLowercaseAndNumbers,
	isValidEmail,
	isValidEmailDomain,
	isValidFriendlyURL,
	maxLength,
} from '../../../../common/utils/validations.form';
import {STATUS_TAG_TYPE_NAMES} from '../../../../routes/customer-portal/utils/constants';
import i18n from '../../../I18n';
import {Button, Input, Select} from '../../../components';
import SetupHighPriorityContactForm, {
	HIGH_PRIORITY_CONTACT_CATEGORIES,
} from '../../../components/HighPriorityContacts/SetupHighPriorityContact';
import useBannedDomains from '../../../hooks/useBannedDomains';
import NotificationQueueService from '../../../services/actions/notificationAction';
import getKebabCase from '../../../utils/getKebabCase';
import Layout from '../Layout';

const BLANK_TEXT = '< none >';
const FETCH_DELAY_AFTER_TYPING = 500;
const MAX_LENGTH = 255;

const SetupAnalyticsCloudPage = ({
	client,
	errors,
	handlePage,
	leftButton,
	project,
	setFieldValue,
	setFormAlreadySubmitted,
	subscriptionGroupId,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);
	const [
		addHighPriorityContactList,
		setAddHighPriorityContactList,
	] = useState([]);
	const [
		removeHighPriorityContactList,
		setRemoveHighPriorityContactList,
	] = useState([]);
	const [step, setStep] = useState(1);

	const handlePreviousStep = () => {
		setStep(step - 1);
	};

	const handleNextStep = () => {
		setStep(step + 1);
	};
	const [isMultiSelectEmpty, setIsMultiSelectEmpty] = useState(false);

	const bannedDomainsOwnerEmail = useBannedDomains(
		values?.activations?.ownerEmailAddress,
		FETCH_DELAY_AFTER_TYPING
	);

	const bannedDomainsAllowedDomains = useBannedDomains(
		values?.activations?.allowedEmailDomains,
		FETCH_DELAY_AFTER_TYPING
	);

	useEffect(() => {}, [values?.activations?.allowedEmailDomains]);

	const {data} = useQuery(getAnalyticsCloudPageInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project?.accountKey}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});

	const {featureFlags} = useAppPropertiesContext();

	const analyticsDataCenterLocations = useMemo(
		() =>
			data?.c?.analyticsCloudDataCenterLocations?.items.map(({name}) => ({
				label: i18n.translate(getKebabCase(name)),
				value: name,
			})) || [],
		[data]
	);

	const hasDisasterRecovery = !!data?.c?.accountSubscriptions?.items?.length;

	useEffect(() => {
		if (analyticsDataCenterLocations.length) {
			setFieldValue(
				'activations.dataCenterLocation',
				analyticsDataCenterLocations[0].value
			);

			if (hasDisasterRecovery) {
				setFieldValue(
					'activations.disasterDataCenterLocation',
					analyticsDataCenterLocations[0].value
				);
			}
		}
	}, [analyticsDataCenterLocations, hasDisasterRecovery, setFieldValue]);

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const handleSubmit = async () => {
		const analyticsCloud = values?.activations;

		const getAnalyticsCloudSubmittedStatus = async (accountKey) => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (data) {
				const status = !!data.c?.analyticsCloudWorkspaces?.items
					?.length;

				return status;
			}

			return false;
		};

		const alreadySubmitted = await getAnalyticsCloudSubmittedStatus(
			project.accountKey
		);
		if (alreadySubmitted) {
			setFormAlreadySubmitted(true);
		}

		if (!alreadySubmitted) {
			const {data} = await client.mutate({
				context: {
					displaySuccess: false,
					type: 'liferay-rest',
				},
				mutation: addAnalyticsCloudWorkspace,
				variables: {
					analyticsCloudWorkspace: {
						accountKey: project.accountKey,
						allowedEmailDomains: analyticsCloud.allowedEmailDomains,
						dataCenterLocation: analyticsCloud.dataCenterLocation,
						ownerEmailAddress: analyticsCloud.ownerEmailAddress,
						r_accountEntryToAnalyticsCloudWorkspace_accountEntryId:
							project?.id,
						timeZone: analyticsCloud.timeZone,
						workspaceFriendlyUrl:
							analyticsCloud.workspaceFriendlyUrl,
						workspaceName: analyticsCloud.workspaceName,
					},
				},
			});

			if (data) {
				await client.mutate({
					context: {
						displaySuccess: false,
						type: 'liferay-rest',
					},
					mutation: updateAccountSubscriptionGroups,
					variables: {
						accountSubscriptionGroup: {
							accountKey: project.accountKey,
							activationStatus: STATUS_TAG_TYPE_NAMES.inProgress,
							r_accountEntryToAccountSubscriptionGroup_accountEntryId:
								project?.id,
						},
						id: subscriptionGroupId,
					},
				});

				await Promise.all(
					removeHighPriorityContactList?.map((item) => {
						return client.mutate({
							context: {
								displaySuccess: false,
								type: 'liferay-rest',
							},
							mutation: deleteHighPriorityContacts,
							variables: {
								highPriorityContactsId: item.objectId,
							},
						});
					})
				);

				await Promise.all(
					addHighPriorityContactList?.map((item) => {
						return client.mutate({
							context: {
								displaySuccess: false,
								type: 'liferay-rest',
							},
							mutation: addHighPriorityContact,
							variables: {
								HighPriorityContacts: {
									contactsCategory: {
										key: item.category.key,
										name: item.category.name,
									},
									r_userToHighPriorityContacts_userId:
										item.id,
								},
							},
						});
					})
				);

				if (featureFlags.includes('LPS-181031')) {
					const emailIncidentReportContact = analyticsCloud?.incidentReportContact
						?.map(({email}) => email)
						.join(', ');

					const notificationTemplateService = new NotificationQueueService(
						client
					);

					await notificationTemplateService.send(
						'SETUP-ANALYTICS-CLOUD-ENVIRONMENT-NOTIFICATION-TEMPLATE',
						{
							'[%AC_DATA_CENTER_LOCATION]':
								analyticsCloud.dataCenterLocation,
							'[%AC_DATA_TIME]': new Date().toUTCString(),
							'[%AC_EMAIL_DOMAINS]':
								analyticsCloud.allowedEmailDomains ||
								BLANK_TEXT,
							'[%AC_INCIDENT_REPORT_CONTACT]': emailIncidentReportContact,
							'[%AC_OWNER_EMAIL]':
								analyticsCloud.ownerEmailAddress,
							'[%AC_TIME_ZONE]':
								analyticsCloud.timeZone || BLANK_TEXT,
							'[%AC_WORKSPACE_FRIENDLY_URL]':
								analyticsCloud.workspaceFriendlyUrl ||
								BLANK_TEXT,
							'[%AC_WORKSPACE_NAME]':
								analyticsCloud.workspaceName,
							'[%PROJECT_ID]': project?.code,
						}
					);
				}
			}

			handlePage(true);
		}
	};
	const handleButtonClick = () => {
		// eslint-disable-next-line no-unused-expressions
		step === 1 ? handlePage(false) : handlePreviousStep();
	};
	const addHighPriorityContacts = (contactList) => {
		const contactsList = contactList.map((item) => item);

		setAddHighPriorityContactList(contactsList);
	};
	const removeHighPriorityContacts = (contactList) => {
		const contactsList = contactList.map(({objectId}) => objectId);
		setRemoveHighPriorityContactList(contactsList);
	};

	const updateMultiSelectEmpty = (error) => {
		setIsMultiSelectEmpty(error);
	};

	return (
		<>
			<Layout
				className="pt-1 px-3"
				footerProps={{
					leftButton: (
						<Button
							borderless
							className="text-neutral-10"
							onClick={() => {
								handleButtonClick();
							}}
						>
							{step === 1
								? leftButton
								: i18n.translate('previous')}
						</Button>
					),
					middleButton: (
						<Button
							disabled={
								step === 1
									? baseButtonDisabled
									: isMultiSelectEmpty
							}
							displayType="primary"
							onClick={step === 1 ? handleNextStep : handleSubmit}
						>
							{step === 1
								? i18n.translate('next')
								: i18n.translate('submit')}
						</Button>
					),
				}}
				headerProps={{
					helper: i18n.translate(
						'we-ll-need-a-few-details-to-finish-creating-your-analytics-cloud-workspace'
					),
					title: i18n.translate('set-up-analytics-cloud'),
				}}
			>
				{step === 1 && (
					<div>
						<FieldArray
							name="activations.incidentReportContact"
							render={() => (
								<>
									<ClayForm.Group className="pb-1">
										<Input
											groupStyle="pb-1"
											helper={i18n.translate(
												'this-user-will-create-and-manage-the-analytics-cloud-workspace-and-must-have-a-liferay-com-account-the-owner-email-can-be-updated-via-a-support-ticket-if-needed'
											)}
											label={i18n.translate(
												'owner-email'
											)}
											name="activations.ownerEmailAddress"
											placeholder="user@company.com"
											required
											type="email"
											validations={[
												(value) =>
													isValidEmail(
														value,
														bannedDomainsOwnerEmail
													),
											]}
										/>

										<Input
											groupStyle="pb-1"
											helper={i18n.translate(
												'lowercase-letters-and-numbers-only-project-ids-cannot-be-changed'
											)}
											label={i18n.translate(
												'workspace-name'
											)}
											name="activations.workspaceName"
											placeholder="superbank1"
											required
											type="text"
											validations={[
												(value) =>
													maxLength(
														value,
														MAX_LENGTH
													),
												(value) =>
													isLowercaseAndNumbers(
														value
													),
											]}
										/>

										<Select
											groupStyle="pb-1"
											helper={i18n.translate(
												'select-a-server-location-for-your-data-to-be-stored'
											)}
											key={analyticsDataCenterLocations}
											label={i18n.translate(
												'data-center-location'
											)}
											name="activations.dataCenterLocation"
											options={
												analyticsDataCenterLocations
											}
											required
										/>

										{hasDisasterRecovery && (
											<Select
												groupStyle="mb-0 pt-2"
												label={i18n.translate(
													'Disaster Recovery Data Center Location'
												)}
												name="activations.disasterDataCenterLocation"
												options={
													analyticsDataCenterLocations
												}
												required
											/>
										)}

										<Input
											groupStyle="pb-1"
											helper={i18n.translate(
												'please-note-that-the-friendly-url-cannot-be-changed-once-added'
											)}
											label={i18n.translate(
												'workspace-friendly-url'
											)}
											name="activations.workspaceFriendlyUrl"
											placeholder="/myurl"
											type="text"
											validations={[
												(value) =>
													isValidFriendlyURL(value),
											]}
										/>

										<Input
											groupStyle="pb-1"
											helper={i18n.translate(
												'anyone-with-an-email-address-at-the-provided-domains-can-request-access-to-your-workspace-if-multiple-separate-domains-by-commas'
											)}
											label={i18n.translate(
												'allowed-email-domains'
											)}
											name="activations.allowedEmailDomains"
											placeholder="@mycompany.com"
											type="text"
											validations={[
												() =>
													isValidEmailDomain(
														bannedDomainsAllowedDomains
													),
											]}
										/>

										<Input
											groupStyle="pb-1"
											helper={i18n.translate(
												'enter-the-timezone-to-be-used-for-all-data-reporting-in-your-workspace'
											)}
											label={i18n.translate('time-zone')}
											name="activations.timeZone"
											placeholder="UTC-04:00"
											type="text"
										/>
									</ClayForm.Group>
								</>
							)}
						/>
					</div>
				)}

				{step === 2 && (
					<div>
						<SetupHighPriorityContactForm
							addContactList={addHighPriorityContacts}
							disableSubmit={updateMultiSelectEmpty}
							filter={
								HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncidentContact
							}
							removedContactList={removeHighPriorityContacts}
						/>
					</div>
				)}
			</Layout>
		</>
	);
};

const SetupAnalyticsCloudForm = (props) => {
	return (
		<Formik
			initialValues={{
				activations: {
					allowedEmailDomains: '',
					dataCenterLocation: '',
					disasterDataCenterLocation: '',
					ownerEmailAddress: '',
					timeZone: '',
					workspaceName: '',
					workspaceURL: '',
				},
			}}
			validateOnChange
		>
			{(formikProps) => (
				<SetupAnalyticsCloudPage {...props} {...formikProps} />
			)}
		</Formik>
	);
};

export default SetupAnalyticsCloudForm;
