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
import {Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import client from '../../../../apolloClient';
import {
	addAdminDXPCloud,
	addDXPCloudEnvironment,
	getDXPCloudPageInfo,
	updateAccountSubscriptionGroups,
} from '../../../../common/services/liferay/graphql/queries';
import {isLowercaseAndNumbers} from '../../../../common/utils/validations.form';
import {STATUS_TAG_TYPES} from '../../../../routes/customer-portal/utils/constants/statusTagTypes';
import {Button, Input, Select} from '../../../components';
import getInitialDXPAdmin from '../../../utils/getInitialDXPAdmin';
import Layout from '../Layout';
import AdminInputs from './AdminInputs';

const SetupDXPCloudPage = ({
	errors,
	handlePage,
	leftButton,
	project,
	setFieldValue,
	subscriptionGroupId,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

	const {data} = useQuery(getDXPCloudPageInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project.accountKey}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});

	const dXPCDataCenterRegions = useMemo(
		() =>
			data?.c?.dXPCDataCenterRegions?.items.map(({name}) => ({
				label: name,
				value: name,
			})) || [],
		[data]
	);

	const hasDisasterRecovery = !!data?.c?.accountSubscriptions?.items?.length;

	useEffect(() => {
		if (dXPCDataCenterRegions.length) {
			setFieldValue(
				'dxp.dataCenterRegion',
				dXPCDataCenterRegions[0].value
			);

			if (hasDisasterRecovery) {
				setFieldValue(
					'dxp.disasterDataCenterRegion',
					dXPCDataCenterRegions[0].value
				);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dXPCDataCenterRegions, hasDisasterRecovery]);

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const sendEmail = async () => {
		const dxp = values?.dxp;

		if (dxp) {
			const {data} = await client.mutate({
				mutation: addDXPCloudEnvironment,
				variables: {
					DXPCloudEnvironment: {
						accountKey: project.accountKey,
						dataCenterRegion: dxp.dataCenterRegion,
						disasterDataCenterRegion: dxp.disasterDataCenterRegion,
						projectId: dxp.projectId,
					},
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const dxpCloudEnvironmentId =
					data.c?.createDXPCloudEnvironment?.dxpCloudEnvironmentId;
				await Promise.all(
					dxp.admins.map(({email, firstName, github, lastName}) =>
						client.mutate({
							mutation: addAdminDXPCloud,
							variables: {
								AdminDXPCloud: {
									dxpCloudEnvironmentId,
									emailAddress: email,
									firstName,
									githubUsername: github,
									lastName,
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
							activationStatus: STATUS_TAG_TYPES.inProgress,
						},
						id: subscriptionGroupId,
					},
				});

				handlePage(true);
			}
		}
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button borderless onClick={() => handlePage()}>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={() => sendEmail()}
					>
						Submit
					</Button>
				),
			}}
			headerProps={{
				helper:
					'We’ll need a few details to finish building your DXP environment(s).',
				title: 'Set up DXP Cloud',
			}}
		>
			<div className="d-flex justify-content-between mb-2 pb-1 pl-3">
				<div className="mr-4 pr-2">
					<label>Project Name</label>

					<p className="dxp-cloud-project-name text-neutral-6 text-paragraph-lg">
						<strong>
							{project.name.length > 71
								? project.name.substring(0, 71) + '...'
								: project.name}
						</strong>
					</p>
				</div>

				<div className="flex-fill">
					<label>Liferay DXP Version</label>

					<p className="text-neutral-6 text-paragraph-lg">
						<strong>{project.dxpVersion}</strong>
					</p>
				</div>
			</div>

			<ClayForm.Group className="mb-0">
				<ClayForm.Group className="mb-0 pb-1">
					<Input
						groupStyle="pb-1"
						helper="Lowercase letters and numbers only. The Project ID cannot be changed."
						label="Project ID"
						name="dxp.projectId"
						required
						type="text"
						validations={[(value) => isLowercaseAndNumbers(value)]}
					/>

					<Select
						groupStyle="mb-0"
						label="Primary Data Center Region"
						name="dxp.dataCenterRegion"
						options={dXPCDataCenterRegions}
						required
					/>

					{!!hasDisasterRecovery && (
						<Select
							groupStyle="mb-0 pt-2"
							label="Disaster Recovery Data Center Region"
							name="dxp.disasterDataCenterRegion"
							options={dXPCDataCenterRegions}
							required
						/>
					)}
				</ClayForm.Group>

				{values.dxp.admins.map((admin, index) => (
					<AdminInputs admin={admin} id={index} key={index} />
				))}
			</ClayForm.Group>

			<Button
				borderless
				className="ml-3 my-2 text-brand-primary"
				onClick={() => {
					setFieldValue('dxp.admins', [
						...values.dxp.admins,
						getInitialDXPAdmin(),
					]);
					setBaseButtonDisabled(true);
				}}
				prependIcon="plus"
				small
			>
				Add Another Admin
			</Button>
		</Layout>
	);
};

const SetupDXPCloudForm = (props) => {
	return (
		<Formik
			initialValues={{
				dxp: {
					admins: [getInitialDXPAdmin()],
					dataCenterRegion: '',
					disasterDataCenterRegion: '',
					projectId: '',
				},
			}}
			validateOnChange
		>
			{(formikProps) => <SetupDXPCloudPage {...props} {...formikProps} />}
		</Formik>
	);
};

export default SetupDXPCloudForm;
