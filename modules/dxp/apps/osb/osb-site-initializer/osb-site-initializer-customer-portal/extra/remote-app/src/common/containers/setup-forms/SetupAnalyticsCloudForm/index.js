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
import {FieldArray, Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import client from '../../../../apolloClient';
import {
	addAnalyticsCloudWorkspace,
	addIncidentReportAnalyticsCloud,
	getAnalyticsCloudPageInfo,
} from '../../../../common/services/liferay/graphql/queries';
import {
	isLowercaseAndNumbers,
	isValidEmail,
	isValidEmailDomain,
	isValidFriendlyURL,
	maxLength,
} from '../../../../common/utils/validations.form';
import {Button, Input, Select} from '../../../components';
import useBannedDomains from '../../../hooks/useBannedDomains';
import getInitialAnalyticsInvite from '../../../utils/getInitialAnalyticsInvite';
import Layout from '../Layout';

import IncidentReportInput from './IncidentReportInput';

const SetupAnalyticsCloudPage = ({
	errors,
	handlePage,
	leftButton,
	onClose,
	project,
	setFieldValue,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

	const debouncedOwnerEmail = useBannedDomains(
		values?.activations?.ownerEmailAddress,
		500
	);

	const debouncedAllowedDomains = useBannedDomains(
		values?.activations?.allowedEmailDomains,
		500
	);

	const {data} = useQuery(getAnalyticsCloudPageInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project?.accountKey}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});

	const analyticsDataCenterLocations = useMemo(
		() =>
			data?.c?.analyticsCloudDataCenterLocations?.items.map(({name}) => ({
				label: name,
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

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [analyticsDataCenterLocations, hasDisasterRecovery]);

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const sendEmail = async () => {
		const analyticsCloud = values?.activations;

		const {data} = await client.mutate({
			mutation: addAnalyticsCloudWorkspace,
			variables: {
				analyticsCloudWorkspace: {
					accountKey: project.accountKey,
					dataCenterLocation: analyticsCloud.dataCenterLocation,
					ownerEmailAddress: analyticsCloud.ownerEmailAddress,
					workspaceName: analyticsCloud.workspaceName,
				},
				scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
			},
		});

		if (data) {
			const analyticsCloudWorkspaceId =
				data?.c?.createAnalyticsCloudWorkspace
					?.analyticsCloudWorkspaceId;
			await Promise.all(
				analyticsCloud?.incidentReportContact.map(({email}) => {
					return client.mutate({
						mutation: addIncidentReportAnalyticsCloud,
						variables: {
							IncidentReportContactAnalyticsCloud: {
								analyticsCloudWorkspaceId,
								emailAddress: email,
							},
							scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
						},
					});
				})
			);
		}
		handlePage();
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={() => onClose()}
					>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={sendEmail}
					>
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
				name="activations"
				render={() => (
					<ClayForm.Group className="pb-1">
						<Input
							groupStyle="pb-1"
							helper="This user will create and manage the Analytics Cloud Workspace and must have a liferay.com account. The owner Email can be updated vis Support ticket if needed."
							label="Owner Email"
							name="activations.ownerEmailAddress"
							placeholder="user@company.com"
							required
							type="email"
							validations={[
								(value) =>
									isValidEmail(value, debouncedOwnerEmail),
							]}
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
							key={analyticsDataCenterLocations}
							label="Data Center Location"
							name="activations.dataCenterLocation"
							options={analyticsDataCenterLocations}
							required
						/>

						{!!hasDisasterRecovery && (
							<Select
								groupStyle="mb-0 pt-2"
								label="Disaster Recovery Data Center Location"
								name="activations.disasterDataCenterLocation"
								options={analyticsDataCenterLocations}
								required
							/>
						)}

						<Input
							groupStyle="pb-1"
							helper="Please note that the friendly URL cannot be changed once added."
							label="Workspace Friendly URL"
							name="activations.workspaceURL"
							placeholder="/myurl"
							type="text"
							validations={[(value) => isValidFriendlyURL(value)]}
						/>

						<Input
							groupStyle="pb-1"
							helper="Anyone with an email address at the provided domains can request access to your Workspace. If multiple, separate domains by commas."
							label="Allowed Email Domains"
							name="activations.allowedEmailDomains"
							placeholder="@mycompany.com"
							type="email"
							validations={[
								(value) =>
									isValidEmailDomain(
										value,
										debouncedAllowedDomains
									),
							]}
						/>

						<Input
							groupStyle="pb-1"
							helper="Enter the timezone to be used for all data reporting in your Workspace."
							label="Time Zone"
							name="activations.timeZone"
							placeholder="UTC-04:00"
							type="text"
						/>

						<ClayForm.Group>
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
					</ClayForm.Group>
				)}
			/>

			<Button
				className="btn-outline-primary ml-3 my-2 rounded-xs"
				disabled={baseButtonDisabled}
				onClick={() => setBaseButtonDisabled(true)}
				prependIcon="plus"
				small
			>
				Add Incident Report Contact
			</Button>
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
					disasterDataCenterLocation: '',
					incidentReportContact: [getInitialAnalyticsInvite()],
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
