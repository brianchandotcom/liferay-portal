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
import ClayForm, {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {FieldArray, Formik} from 'formik';

import {useEffect, useMemo, useState} from 'react';
import {
	addAdminDXPCloud,
	addDXPCloudEnvironment,
	getDXPCloudEnvironment,
	getDXPCloudPageInfo,
	getListTypeDefinitions,
	updateAccountSubscriptionGroups,
} from '../../../../common/services/liferay/graphql/queries';
import {isLowercaseAndNumbers} from '../../../../common/utils/validations.form';
import {STATUS_TAG_TYPE_NAMES} from '../../../../routes/customer-portal/utils/constants';
import i18n from '../../../I18n';
import {Button, Input, Select} from '../../../components';
import getInitialDXPAdmin from '../../../utils/getInitialDXPAdmin';
import getKebabCase from '../../../utils/getKebabCase';
import Layout from '../Layout';
import AdminInputs from './AdminInputs';

const INITIAL_SETUP_ADMIN_COUNT = 1;
const MAXIMUM_NUMBER_OF_CHARACTERS = 77;

const SetupDXPCloudPage = ({
	client,
	dxpVersion,
	errors,
	handlePage,
	leftButton,
	listType,
	project,
	setFieldValue,
	setFormAlreadySubmitted,
	subscriptionGroupId,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);
	const [dxpVersions, setDxpVersions] = useState([]);
	const [selectedVersion, setSelectedVersion] = useState(dxpVersion || '');
	const {data} = useQuery(getDXPCloudPageInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project.accountKey}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});
	useEffect(() => {
		const fetchListTypeDefinitions = async () => {
			const {data} = await client.query({
				query: getListTypeDefinitions,
				variables: {
					filter: `name eq '${listType}'`,
				},
			});

			const items = data?.listTypeDefinitions?.items[0]?.listTypeEntries;

			if (items?.length) {
				const sortedItems = [...items].sort();
				setDxpVersions(sortedItems);

				setSelectedVersion(
					sortedItems.find((item) => item.name === dxpVersion)
						?.name || sortedItems[0].name
				);
			}
		};

		fetchListTypeDefinitions();
	}, [client, dxpVersion, listType]);

	const dXPCDataCenterRegions = useMemo(
		() =>
			data?.c?.dXPCDataCenterRegions?.items.map(({name}) => ({
				label: i18n.translate(getKebabCase(name)),
				value: getKebabCase(name),
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

		const getDXPCloudActivationSubmitedStatus = async (accountKey) => {
			const {data} = await client.query({
				query: getDXPCloudEnvironment,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = !!data.c?.dXPCloudEnvironments?.items?.length;

				return status;
			}

			return false;
		};

		const alreadySubmitted = await getDXPCloudActivationSubmitedStatus(
			project.accountKey
		);
		if (alreadySubmitted) {
			setFormAlreadySubmitted(true);
		}

		if (!alreadySubmitted && dxp) {
			const {data} = await client.mutate({
				context: {
					displaySuccess: false,
				},
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
							context: {
								displaySuccess: false,
							},
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
							accountKey: project.accountKey,
							activationStatus: STATUS_TAG_TYPE_NAMES.inProgress,
							manageContactsURL: `https://console.liferay.cloud/projects/${dxpCloudEnvironmentId}/overview`,
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
						{i18n.translate('submit')}
					</Button>
				),
			}}
			headerProps={{
				helper: i18n.translate(
					'we-ll-need-a-few-details-to-finish-building-your-lxc-sm-environment'
				),
				title: i18n.translate('set-up-lxc-sm'),
			}}
		>
			<FieldArray
				name="dxp.admins"
				render={({pop, push}) => (
					<>
						<div className="d-flex justify-content-between mb-2 pb-1 pl-3">
							<div className="mr-4 pr-2">
								<label>{i18n.translate('project-name')}</label>

								<p className="lxc-sm-project-name text-neutral-6 text-paragraph-lg">
									<strong>
										{project.name.length >
										MAXIMUM_NUMBER_OF_CHARACTERS
											? project.name.substring(
													0,
													MAXIMUM_NUMBER_OF_CHARACTERS
											  ) + '...'
											: project.name}
									</strong>
								</p>
							</div>

							<div className="flex-fill">
								<label>
									{i18n.translate('liferay-dxp-version')}
								</label>

								<div className="position-relative">
									<ClayIcon
										className="select-icon"
										symbol="caret-bottom"
									/>

									<ClaySelect
										className="bg-neutral-1 border-0 font-weight-bold mr-2 pr-6"
										onChange={({target}) => {
											setSelectedVersion(target.value);
										}}
										value={selectedVersion}
									>
										{dxpVersions.map((version) => (
											<ClaySelect.Option
												className="font-weight-bold options"
												key={version.key}
												label={version.name}
											/>
										))}
									</ClaySelect>
								</div>
							</div>
						</div>
						<ClayForm.Group className="mb-0">
							<ClayForm.Group className="mb-0 pb-1">
								<Input
									groupStyle="pb-1"
									helper={i18n.translate(
										'lowercase-letters-and-numbers-only-the-project-id-cannot-be-changed'
									)}
									label={i18n.translate('project-id')}
									name="dxp.projectId"
									required
									type="text"
									validations={[
										(value) => isLowercaseAndNumbers(value),
									]}
								/>

								<Select
									groupStyle="mb-0"
									label={i18n.translate(
										'primary-data-center-region'
									)}
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
								<AdminInputs
									admin={admin}
									id={index}
									key={index}
								/>
							))}
						</ClayForm.Group>
						{values?.dxp?.admins?.length >
							INITIAL_SETUP_ADMIN_COUNT && (
							<Button
								className="ml-3 my-2 text-brandy-secondary"
								displayType="secondary"
								onClick={() => {
									pop();
									setBaseButtonDisabled(false);
								}}
								prependIcon="hr"
								small
							>
								{i18n.translate('remove-this-admin')}
							</Button>
						)}
						<Button
							className="btn-outline-primary cp-btn-add-lxc-sm ml-3 my-2 rounded-xs"
							disabled={baseButtonDisabled}
							onClick={() => {
								push(getInitialDXPAdmin(values?.dxp?.admins));
								setBaseButtonDisabled(true);
							}}
							prependIcon="plus"
							small
						>
							{i18n.translate('add-another-admin')}
						</Button>
					</>
				)}
			/>
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
