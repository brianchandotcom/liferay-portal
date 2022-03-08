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
import client from '../../../../../apolloClient';
import {Liferay} from '../../../../../common/services/liferay';
import {getAnalyticsCloudWorkspace} from '../../../../../common/services/liferay/graphql/queries';
import ActivationStatus from '../../../components/ActivationStatus/index';
import {PRODUCT_TYPES} from '../../../utils/constants';

const AnalyticsCloud = ({project, subscriptionGroups, userAccount}) => {
	const [
		analyticsCloudEnvironment,
		setAnalyticsCloudEnvironment,
	] = useState();

	useEffect(() => {
		const getAnalyticsCloudData = async () => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${project.accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const items = data.c?.AnalyticsCloudWorkspaces?.items;

				if (items.length) {
					setAnalyticsCloudEnvironment(items[0]);
				}
			}
		};

		getAnalyticsCloudData();
	}, [project]);

	return (
		<div className="mr-4">
			<ActivationStatus.AnalyticsCloud
				analyticsCloudEnvironment={analyticsCloudEnvironment}
				project={project}
				subscriptionGroupAnalyticsCloud={subscriptionGroups.find(
					(subscriptionGroup) =>
						subscriptionGroup.name === PRODUCT_TYPES.analyticsCloud
				)}
				userAccount={userAccount}
			/>
		</div>
	);
};

export default AnalyticsCloud;
