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
import {Button} from '../../../../common/components';
import {getDXPCloudEnvironment} from '../../../../common/services/liferay/graphql/queries';
import {PRODUCT_TYPES} from '../../utils/constants/productTypes';
import {STATUS_TAG_TYPE_NAMES} from '../../utils/constants/statusTag';

const ManageProductUser = ({project, subscriptionGroups}) => {
	const [dxpCloudEnvironment, setDxpCloudEnvironment] = useState('');
	const [activationStatusDXPC, setActivatedStatusDXPC] = useState('');
	const [activationStatusAC, setActivatedStatusAC] = useState('Active');
	const groupId = 'groupid';
	const activatedLinkDXPC = `https://console.liferay.cloud/projects/${dxpCloudEnvironment}/overview`;
	const activatedLinkAC = `https://analytics.liferay.com/workspace/${groupId}/sites`;

	useEffect(() => {
		const getOnboardingFormData = async () => {
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

				setDxpCloudEnvironment(dxpProjectId);
			}
		};
		getOnboardingFormData();

		const accountSubscriptionFromDXPC = subscriptionGroups.find(
			(subscriptionGroup) =>
				subscriptionGroup.name === PRODUCT_TYPES.dxpCloud
		);
		const activationStatusDXPC =
			accountSubscriptionFromDXPC?.activationStatus;

		if (activationStatusDXPC === STATUS_TAG_TYPE_NAMES.active) {
			setActivatedStatusDXPC(activationStatusDXPC);
		}
		if (activationStatusAC === STATUS_TAG_TYPE_NAMES.active) {
			setActivatedStatusAC(activationStatusAC);
		}
	}, [
		activationStatusAC,
		dxpCloudEnvironment,
		project.accountKey,
		subscriptionGroups,
	]);

	return (
		<>
			{(activationStatusDXPC || activationStatusAC) && (
				<div className="bg-brand-primary-lighten-6 border-0 card card-flat cp-manager-product-container mt-5">
					<div className="p-4">
						<p className="h4">Manage Product Users</p>

						<p className="mt-2 text-neutral-7 text-paragraph-sm">
							Manage roles and permissions of users within each
							product.
						</p>

						<div className="d-flex">
							{activationStatusDXPC && (
								<a
									href={activatedLinkDXPC}
									rel="noopener noreferrer"
									target="_blank"
								>
									<Button
										appendIcon="shortcut"
										className="align-items-stretch btn cp-manager-product-button d-flex mr-3 p-2 text-neutral-10"
										displayType="secudary"
									>
										<p className="font-weight-semi-bold h6 m-0 pl-1">
											Manage DXP Cloud Users
										</p>
									</Button>
								</a>
							)}

							{activationStatusAC && (
								<a
									href={activatedLinkAC}
									rel="noopener noreferrer"
									target="_blank"
								>
									<Button
										appendIcon="shortcut"
										className="align-items-stretch cp-manager-product-button d-flex p-2 text-neutral-10"
										displayType="secudary"
									>
										<p className="font-weight-semi-bold h6 m-0 pl-1">
											Manage Analytics Cloud Users
										</p>
									</Button>
								</a>
							)}
						</div>
					</div>
				</div>
			)}
		</>
	);
};
export default ManageProductUser;
