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

import {useEffect, useState} from 'react';
import i18n from '../../../../../../../common/I18n';
import {useAppPropertiesContext} from '../../../../../../../common/contexts/AppPropertiesContext';
import {Liferay} from '../../../../../../../common/services/liferay';
import {useGetLiferayExperienceCloudEnvironments} from '../../../../../../../common/services/liferay/graphql/liferay-experience-cloud-environments/queries/useGetLiferayExperienceEnvironments';
import {
	getAnalyticsCloudWorkspace,
	getDXPCloudEnvironment,
} from '../../../../../../../common/services/liferay/graphql/queries';
import {PRODUCT_TYPES} from '../../../../../utils/constants/productTypes';
import {STATUS_TAG_TYPE_NAMES} from '../../../../../utils/constants/statusTag';
import ManageProductButton from './components/ManageProductButton/ManageProductButton';

const ManageProductUsers = ({project, subscriptionGroups}) => {
	const [dxpCloudProjectId, setDxpCloudProjectId] = useState('');
	const [analyctsCloudGroupId, setAnalyctsCloudGroupId] = useState('');
	const {client} = useAppPropertiesContext();

	const activatedLinkDXPC = `https://console.liferay.cloud/projects/${dxpCloudProjectId}/overview`;
	const activatedLinkAC = `https://analytics.liferay.com/workspace/${analyctsCloudGroupId}/sites`;

	useEffect(() => {
		const getDxpCloudEnvimentProjectId = async () => {
			const {data} = await client.query({
				query: getDXPCloudEnvironment,
				variables: {
					filter: `accountKey eq '${project.accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const dxpProjectId =
					data.c?.dXPCloudEnvironments?.items[0]?.projectId;
				setDxpCloudProjectId(dxpProjectId);
			}
		};

		getDxpCloudEnvimentProjectId();

		const getAnalyticsCloudWorkspaces = async () => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${project.accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});
			if (data) {
				const analyticsCloudWorkspacesGroupID =
					data?.c?.analyticsCloudWorkspaces?.items[0]
						?.workspaceGroupId;
				setAnalyctsCloudGroupId(analyticsCloudWorkspacesGroupID);
			}
		};
		getAnalyticsCloudWorkspaces();
	}, [client, project.accountKey]);

	const {data} = useGetLiferayExperienceCloudEnvironments({
		filter: `accountKey eq '${project.accountKey}'`,
	});

	const lxcProjectId =
		data?.c?.liferayExperienceCloudEnvironments?.items[0]?.projectId;

	const activatedLinkLxc = `http://${lxcProjectId}.lxc.liferay.com`;

	const isActiveStatusDXPC =
		subscriptionGroups.find(
			(subscriptionGroup) =>
				subscriptionGroup.name === PRODUCT_TYPES.dxpCloud
		)?.activationStatus === STATUS_TAG_TYPE_NAMES.active;
	const isActiveStatusAC =
		subscriptionGroups.find(
			(subscriptionGroup) =>
				subscriptionGroup.name === PRODUCT_TYPES.analyticsCloud
		)?.activationStatus === STATUS_TAG_TYPE_NAMES.active;

	const isActiveStatusLXC =
		subscriptionGroups.find(
			(subscriptionGroup) =>
				subscriptionGroup.name === PRODUCT_TYPES.liferayExperienceCloud
		)?.activationStatus === STATUS_TAG_TYPE_NAMES.active;

	return (
		<>
			{(isActiveStatusDXPC || isActiveStatusAC || isActiveStatusLXC) && (
				<div className="bg-brand-primary-lighten-6 border-0 card card-flat cp-manager-product-container mt-5">
					<div className="p-4">
						<h4>
							{isActiveStatusLXC
								? i18n.translate(
										'manage-liferay-experience-cloud-users'
								  )
								: i18n.translate('manage-product-users')}
						</h4>

						<p className="mt-2 text-neutral-7 text-paragraph-sm">
							{i18n.translate(
								'manage-roles-and-permissions-of-users-within-each-product'
							)}
						</p>

						<div className="d-flex">
							{isActiveStatusDXPC && (
								<ManageProductButton
									activatedLink={activatedLinkDXPC}
									activatedTitle={i18n.translate(
										'manage-lxc-sm-users'
									)}
								/>
							)}

							{isActiveStatusAC && (
								<ManageProductButton
									activatedLink={activatedLinkAC}
									activatedTitle={i18n.translate(
										'manage-analytics-cloud-users'
									)}
								/>
							)}

							{isActiveStatusLXC && (
								<ManageProductButton
									activatedLink={activatedLinkLxc}
									activatedTitle={i18n.translate(
										'manage-liferay-experience-cloud-users'
									)}
								/>
							)}
						</div>
					</div>
				</div>
			)}
		</>
	);
};
export default ManageProductUsers;
