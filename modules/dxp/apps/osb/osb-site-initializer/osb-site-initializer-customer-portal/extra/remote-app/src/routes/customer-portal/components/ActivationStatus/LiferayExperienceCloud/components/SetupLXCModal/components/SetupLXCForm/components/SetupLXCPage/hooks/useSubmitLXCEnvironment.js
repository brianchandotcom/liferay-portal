/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import SearchBuilder from '~/common/core/SearchBuilder';
import {
	addHighPriorityContactsList,
	associateContactRole,
	removeContactRole,
	removeHighPriorityContactsList,
} from '~/routes/customer-portal/utils/getHighPriorityContacts';
import {useOnboarding} from '~/routes/onboarding/context';
import NotificationQueueService from '../../../../../../../../../../../../../src/common/services/actions/notificationAction';
import {useAppPropertiesContext} from '../../../../../../../../../../../../common/contexts/AppPropertiesContext';
import {
	useCreateAdminLiferayExperienceCloud,
	useCreateLiferayExperienceCloudEnvironments,
} from '../../../../../../../../../../../../common/services/liferay/graphql/liferay-experience-cloud-environments';
import {
	getLiferayExperienceCloudEnvironments,
	updateAccountSubscriptionGroups,
} from '../../../../../../../../../../../../common/services/liferay/graphql/queries';
import {useCustomerPortal} from '../../../../../../../../../../../../routes/customer-portal/context';
import {STATUS_TAG_TYPE_NAMES} from '../../../../../../../../../../utils/constants';

export default function useSubmitLXCEnvironment(
	handleChangeForm,
	project,
	setFormAlreadySubmitted,
	addHighPriorityContactList,
	removeHighPriorityContactList,
	subscriptionGroupLxcId,
	handleLoadingSubmitButton,
	values
) {
	const {client} = useAppPropertiesContext();

	const {featureFlags, provisioningServerAPI} = useAppPropertiesContext();

	const customerPortalContext = useCustomerPortal();

	const onboardingContext = useOnboarding();

	const sessionId =
		customerPortalContext?.[0].sessionId ||
		onboardingContext?.[0].sessionId;

	const [
		createLiferayExperienceCloudEnvironment,
	] = useCreateLiferayExperienceCloudEnvironments();

	const [updateAccountSubscriptionGroupsInfo] = useMutation(
		updateAccountSubscriptionGroups
	);
	const [
		createAdminLiferayExperienceCloud,
	] = useCreateAdminLiferayExperienceCloud();

	const handleSubmitLxcEnvironment = async () => {
		const lxcActivationFields = values?.lxc;

		const liferayExperienceCloudStatus = async () => {
			const {data} = await client.query({
				query: getLiferayExperienceCloudEnvironments,
				variables: {
					filter: SearchBuilder.eq('accountKey', project.accountKey),
				},
			});
			if (data) {
				const status = !!data?.c?.liferayExperienceCloudEnvironments
					?.items?.length;

				return status;
			}

			return false;
		};

		const alreadySubmitted = await liferayExperienceCloudStatus();

		if (alreadySubmitted) {
			setFormAlreadySubmitted(true);
		}

		if (!alreadySubmitted) {
			try {
				handleLoadingSubmitButton(true);
				if (featureFlags.includes('LPS-159127')) {
					await Promise.all(
						removeHighPriorityContactList?.map(async (item) => {
							removeContactRole(
								item,
								project,
								sessionId,
								provisioningServerAPI
							);
						})
					);
					await Promise.all(
						addHighPriorityContactList?.map(async (item) => {
							return associateContactRole(
								item,
								project,
								sessionId,
								provisioningServerAPI
							);
						})
					);
				}
				const {data} = await createLiferayExperienceCloudEnvironment({
					variables: {
						LiferayExperienceCloudEnvironment: {
							accountKey: project.accountKey,
							incidentManagementEmailAddress:
								lxcActivationFields.incidentManagementEmail,
							incidentManagementFullName:
								lxcActivationFields.incidentManagementFullName,
							primaryRegion: lxcActivationFields.primaryRegion,
							projectId: lxcActivationFields.projectId,
						},
					},
				});

				if (data) {
					const liferayExperienceCloudEnvironmentId =
						data.createLiferayExperienceCloudEnvironment?.id;

					await updateAccountSubscriptionGroupsInfo({
						context: {
							displaySuccess: false,
							type: 'liferay-rest',
						},
						variables: {
							accountSubscriptionGroup: {
								accountKey: project.accountKey,
								activationStatus:
									STATUS_TAG_TYPE_NAMES.inProgress,
								r_accountEntryToAccountSubscriptionGroup_accountEntryId:
									project.id,
							},
							id: subscriptionGroupLxcId,
						},
					});

					await Promise.all(
						lxcActivationFields?.admins?.map(
							({email, fullName}) => {
								return createAdminLiferayExperienceCloud({
									variables: {
										AdminLiferayExperienceCloud: {
											emailAddress: email,
											fullName,
											githubUsername: '...',
											liferayExperienceCloudEnvironmentId,
										},
									},
								});
							}
						)
					);
					if (featureFlags.includes('LPS-159127')) {
						await Promise.all(
							removeHighPriorityContactList?.map((item) => {
								return removeHighPriorityContactsList(
									client,
									item,
									project
								);
							})
						);
						await Promise.all(
							addHighPriorityContactList?.map((item) => {
								return addHighPriorityContactsList(
									client,
									item,
									project
								);
							})
						);
					}
					if (featureFlags.includes('LPS-181031')) {
						const adminInfo = lxcActivationFields?.admins?.map(
							({email, fullName}) => {
								const [
									firstName,
									...lastNames
								] = fullName.split(' ');
								const lastName = lastNames.join(' ');
								const projectAdminEmailBody = `
							<strong>First Name -</strong> ${firstName}<br>
							<strong>Last Name - </strong>${lastName}<br>
							<strong>Email Address - </strong>${email}
							<br><br>`;

								return projectAdminEmailBody;
							}
						);
						const notificationTemplateService = new NotificationQueueService(
							client
						);

						await notificationTemplateService.send(
							'SETUP-LXC-ENVIRONMENT-NOTIFICATION-TEMPLATE',
							{
								'[%DATE_AND_TIME_SUBMITTED%]': new Date().toUTCString(),
								'[%PROJECT_ADMIN%]': adminInfo.join(''),
								'[%PROJECT_CODE%]': project.code,
								'[%PROJECT_DATA_CENTER_REGION%]':
									lxcActivationFields.primaryRegion,
								'[%PROJECT_ID%]': lxcActivationFields.projectId,
							}
						);
					}
				}
				handleLoadingSubmitButton(false);
				handleChangeForm(true);
			}
			catch {
				handleLoadingSubmitButton(false);
			}
		}
	};

	return handleSubmitLxcEnvironment;
}
