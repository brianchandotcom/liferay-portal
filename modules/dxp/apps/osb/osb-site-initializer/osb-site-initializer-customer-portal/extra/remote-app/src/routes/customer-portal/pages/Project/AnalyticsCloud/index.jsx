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
import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import {Liferay} from '../../../../../common/services/liferay';
import {getAnalyticsCloudWorkspace} from '../../../../../common/services/liferay/graphql/queries';
import ActivationStatus from '../../../components/ActivationStatus/index';
import {useCustomerPortal} from '../../../context';
import {PRODUCT_TYPES} from '../../../utils/constants';

const AnalyticsCloud = () => {
	const [analyticsCloudWorkspace, setAnalyticsCloudWorkspace] = useState();
	const [{project, subscriptionGroups, userAccount}] = useCustomerPortal();
	const {client} = useAppPropertiesContext();

	useEffect(() => {
		const getAnalyticsCloudData = async () => {
			const {data} = await client.query({
				fetchPolicy: 'network-only',
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${project.accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const items = data.c?.analyticsCloudWorkspaces?.items;

				if (items.length) {
					setAnalyticsCloudWorkspace(items[0]);
				}
			}
		};

		getAnalyticsCloudData();
	}, [client, project]);

	return (
		<div className="mr-4">
			<ActivationStatus.AnalyticsCloud
				analyticsCloudWorkspace={analyticsCloudWorkspace}
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
