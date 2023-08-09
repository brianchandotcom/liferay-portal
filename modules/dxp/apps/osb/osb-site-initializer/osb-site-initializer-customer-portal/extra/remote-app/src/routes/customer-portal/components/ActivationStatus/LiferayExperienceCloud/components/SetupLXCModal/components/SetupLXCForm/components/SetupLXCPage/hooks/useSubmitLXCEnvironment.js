/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import SearchBuilder from '~/common/core/SearchBuilder';
import NotificationQueueService from '../../../../../../../../../../../../../src/common/services/actions/notificationAction';
import {useAppPropertiesContext} from '../../../../../../../../../../../../common/contexts/AppPropertiesContext';
import {
	useCreateAdminLiferayExperienceCloud,
	useCreateLiferayExperienceCloudEnvironments,
} from '../../../../../../../../../../../../common/services/liferay/graphql/liferay-experience-cloud-environments';
import {
	addHighPriorityContact,
	deleteHighPriorityContacts,
	getLiferayExperienceCloudEnvironments,
	updateAccountSubscriptionGroups,
} from '../../../../../../../../../../../../common/services/liferay/graphql/queries';
import {STATUS_TAG_TYPE_NAMES} from '../../../../../../../../../../utils/constants';

export default function useSubmitLXCEnvironment(
	handleChangeForm,
	project,
	setFormAlreadySubmitted,
	addHighPriorityContactList,
	removeHighPriorityContactList,
	subscriptionGroupLxcId,
	values
) {
	const {client} = useAppPropertiesContext();

	const {featureFlags} = useAppPropertiesContext();

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
							activationStatus: STATUS_TAG_TYPE_NAMES.inProgress,
							r_accountEntryToAccountSubscriptionGroup_accountEntryId:
								project.id,
						},
						id: subscriptionGroupLxcId,
					},
				});

				await Promise.all(
					lxcActivationFields?.admins?.map(({email, fullName}) => {
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
					})
				);

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
					const adminInfo = lxcActivationFields?.admins?.map(
						({email, fullName}) => {
							const [firstName, ...lastNames] = fullName.split(
								' '
							);
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

			handleChangeForm(true);
		}
	};

	return handleSubmitLxcEnvironment;
}
