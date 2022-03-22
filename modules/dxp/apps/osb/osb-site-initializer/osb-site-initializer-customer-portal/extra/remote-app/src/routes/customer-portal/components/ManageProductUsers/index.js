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
import client from '../../../../apolloClient';
import {
	getAnalyticsCloudWorkspace,
	getDXPCloudEnvironment,
} from '../../../../common/services/liferay/graphql/queries';
import {PRODUCT_TYPES} from '../../utils/constants/productTypes';
import {STATUS_TAG_TYPE_NAMES} from '../../utils/constants/statusTag';
import ManageProductButton from '../ManageProductButton';
const ManageProductUser = ({project, subscriptionGroups}) => {
	const [dxpCloudProjectId, setDxpCloudProjectId] = useState('');
	const [analyctsCloudGroupId, setAnalyctsCloudGroupId] = useState('');
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
					data?.c.analyticsCloudWorkspaces.items[0].workspaceGroupId;
				setAnalyctsCloudGroupId(analyticsCloudWorkspacesGroupID);
			}
		};
		getAnalyticsCloudWorkspaces();
	}, [project.accountKey]);

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

	return (
		<>
			{(isActiveStatusDXPC || isActiveStatusAC) && (
				<div className="bg-brand-primary-lighten-6 border-0 card card-flat cp-manager-product-container mt-5">
					<div className="p-4">
						<p className="h4">Manage Product Users</p>

						<p className="mt-2 text-neutral-7 text-paragraph-sm">
							Manage roles and permissions of users within each
							product.
						</p>

						<div className="d-flex">
							{isActiveStatusDXPC && (
								<ManageProductButton
									activatedLink={activatedLinkDXPC}
									activatedTitle="Manage DXP Cloud Users"
								/>
							)}

							{isActiveStatusAC && (
								<ManageProductButton
									activatedLink={activatedLinkAC}
									activatedTitle="Manage Analytics Cloud Users"
								/>
							)}
						</div>
					</div>
				</div>
			)}
		</>
	);
};
export default ManageProductUser;
