/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import {useModal} from '@clayui/modal';
import {FieldArray, Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import client from '../../../../apolloClient';
import {
	getAnalyticsCloudWorkspace,
	getDXPCloudPageInfo,
	updateAccountSubscriptionGroups,
} from '../../../../common/services/liferay/graphql/queries';
import {
	isLowercaseAndNumbers,
	isValidFriendlyURL,
	maxLength,
} from '../../../../common/utils/validations.form';
import {STATUS_TAG_TYPE_NAMES} from '../../../../routes/customer-portal/utils/constants';
import {Button, Input, Select} from '../../../components';
import getInitialAnalyticsInvite from '../../../utils/getInitialAnalyticsInvite';
import Layout from '../Layout';
import ConfirmationMessageModal from './ConfirmationMessageModal';
import IncidentReportInput from './IncidentReportInput';

const INITIAL_SETUP_ADMIN_COUNT = 1;

const SetupAnalyticsCloudPage = ({
	handlePage,
	leftButton,
	project,
	setFieldValue,
	setFormAlreadySubmitted,
	subscriptionGroupId,
	values,
}) => {
	const {data} = useQuery(getDXPCloudPageInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project?.dXPCDataCenterRegions}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});

	const [isVisibleModal, setIsVisibleModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleModal(false),
	});

	const analyticsDataCenterRegions = useMemo(
		() =>
			data?.c?.dXPCDataCenterRegions?.items.map(({name}) => ({
				label: name,
				value: name,
			})) || [],
		[data]
	);

	const hasDisasterRecovery = !!data?.c?.accountSubscriptions?.items?.length;

	useEffect(() => {
		if (analyticsDataCenterRegions.length) {
			setFieldValue(
				'dxp.dataCenterRegion',
				analyticsDataCenterRegions[0].value
			);

			if (hasDisasterRecovery) {
				setFieldValue(
					'dxp.disasterDataCenterRegion',
					analyticsDataCenterRegions[0].value
				);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [analyticsDataCenterRegions, hasDisasterRecovery]);

	const sendEmail = async () => {
		const analyticsCloud = values?.activations;

		const getAnalyticsCloudActivationSubmitedStatus = async (
			accountKey
		) => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = !!data.c?.analyticsCloudWorkspaces?.items
					?.length;

				return status;
			}

			return false;
		};

		const alreadySubmited = await getAnalyticsCloudActivationSubmitedStatus(
			project.accountKey
		);
		if (alreadySubmited) {
			setFormAlreadySubmitted(true);
		}

		if (!alreadySubmited && analyticsCloud) {
			const {data} = await client.mutate({
				mutation: addAnalyticsCloudEnvironment,
				variables: {
					AnalyticsCloudWorkspace: {
						accountKey: project.accountKey,
						allowedEmailDomains: analyticsCloud.allowedEmailDomains,
						dataCenterLocation: analyticsCloud.dataCenterLocation,
						ownerEmail: analyticsCloud.ownerEmail,
						timeZone: analyticsCloud.timeZone,
						workspaceName: analyticsCloud.workspaceName,
						workspaceURL: analyticsCloud.workspaceURL,
					},
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const analyticsCloudEnvironmentId =
					data.c?.createDXPCloudEnvironment?.dxpCloudEnvironmentId;
				await Promise.all(
					analyticsCloud.incidentReportContact.map(({email}) =>
						client.mutate({
							mutation: addIncidentReportContact,
							variables: {
								AdminDXPCloud: {
									analyticsCloudEnvironmentId,
									emailAddress: email,
								},
								scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
							},
						})
					)
				);

				await client.mutate({
					mutation: updateAccountSubscriptionGroups,
					variables: {
						accountSubscriptionGroup: {
							activationStatus: STATUS_TAG_TYPE_NAMES.inProgress,
						},
						id: subscriptionGroupId,
					},
				});

				setIsVisibleModal(true);
			}
		}
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={() => handlePage()}
					>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button displayType="primary" onClick={sendEmail()}>
						Submit
					</Button>
				),
			}}
			headerProps={{
				helper:
					'We’ll need a few details to finish creating your Analytics Cloud Workspace',
				title: 'Set up Analytics Cloud',
			}}
		>
			<FieldArray
				name="activations.incidentReportContact"
				render={({pop, push}) => (
					<>
						{isVisibleModal && (
							<ConfirmationMessageModal
								handlePage={handlePage}
								observer={observer}
								onClose={onClose}
							/>
						)}

						<ClayForm.Group className="mb-0">
							<ClayForm.Group className="mb-0">
								<Input
									groupStyle="pb-1"
									helper="This user will create and manage the Analytics Cloud Workspace and must have a liferay.com account. The owner Email can be updated vis Support ticket if needed."
									label="Owner Email"
									name="activations.ownerEmail"
									placeholder="user@company.com"
									required
									type="email"
								/>

								<Input
									groupStyle="pb-1"
									helper="Lowercase letters and numbers only. Project IDs cannot be changed."
									label="Workspace Name"
									name="activations.workspaceName"
									placeholder="superbank1"
									required
									type="text"
									validations={[
										(value) => maxLength(value, 255),
										(value) => isLowercaseAndNumbers(value),
									]}
								/>

								<Select
									groupStyle="pb-1"
									helper="Select a server location for your data to be stored."
									key={analyticsDataCenterRegions}
									label="Data Center Location"
									name="activations.dataCenterLocation"
									options={analyticsDataCenterRegions}
									required
								/>

								<Input
									groupStyle="pb-1"
									helper="Please note that the friendly URL cannot be changed once added."
									label="Workspace Friendly URL"
									name="activations.workspaceURL"
									placeholder="/myurl"
									type="text"
									validations={[
										(value) => isValidFriendlyURL(value),
									]}
								/>

								<Input
									groupStyle="pb-1"
									helper="Anyone with an email address at the provided domains can request access to your Workspace. If multiple, separate domains by commas."
									label="Allowed Email Domains"
									name="activations.allowedEmailDomains"
									placeholder="@mycompany.com"
									type="email"
								/>

								<Input
									groupStyle="pb-1"
									helper="Enter the timezone to be used for all data reporting in your Workspace."
									label="Time Zone"
									name="activations.timeZone"
									placeholder="UTC-04:00"
									type="text"
								/>
							</ClayForm.Group>

							{values?.activations?.incidentReportContact?.map(
								(activation, index) => (
									<IncidentReportInput
										activation={activation}
										id={index}
										key={index}
									/>
								)
							)}
						</ClayForm.Group>
						{values?.activations?.incidentReportContact?.length >
							INITIAL_SETUP_ADMIN_COUNT && (
							<Button
								className="ml-3 my-2 text-brandy-secondary"
								displayType="secondary"
								onClick={() => pop()}
								prependIcon="hr"
								small
							>
								Remove Incident Report Contact
							</Button>
						)}

						<Button
							className="btn-outline-primary ml-3 my-2 rounded-xs"
							onClick={() => {
								push(
									getInitialAnalyticsInvite(
										values?.activations
											?.incidentReportContact
									)
								);
							}}
							prependIcon="plus"
							small
						>
							Add Incident Report Contact
						</Button>
					</>
				)}
			/>
		</Layout>
	);
};

const SetupAnalyticsCloudForm = (props) => {
	return (
		<Formik
			initialValues={{
				activations: {
					allowedEmailDomains: '',
					dataCenterLocation: '',
					incidentReportContact: [getInitialAnalyticsInvite()],
					ownerEmail: '',
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
