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

import {gql, useMutation} from '@apollo/client';

export const CREATE_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENT = gql`
	mutation createLiferayExperienceCloudEnvironment(
		$LiferayExperienceCloudEnvironment: InputC_LiferayExperienceCloudEnvironment!
	) {
		createLiferayExperienceCloudEnvironment(
			input: $LiferayExperienceCloudEnvironment
		)
			@rest(
				method: "POST"
				type: "C_LiferayExperienceCloudEnvironment"
				path: "/c/liferayexperiencecloudenvironments"
			) {
			accountKey
			id
			incidentManagementEmailAddress
			incidentManagementFullName
			primaryRegion
			projectId
		}
	}
`;

export function useCreateLiferayExperienceCloudEnvironments(
	variables,
	options = {displaySuccess: false}
) {
	return useMutation(
		CREATE_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENT,
		{
			context: {
				displaySuccess: options.displaySuccess,
				type: 'liferay-rest',
			},
		},
		variables
	);
}
