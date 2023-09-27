/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getUpdateProjectId(
	projectIdValue,
	lxcEnvironment,
	updateLiferayExperienceCloudEnvironment
) {
	if (lxcEnvironment) {
		updateLiferayExperienceCloudEnvironment({
			variables: {
				LiferayExperienceCloudEnvironment: {
					projectId: projectIdValue,
				},
				liferayExperienceCloudEnvironmentId:
					lxcEnvironment?.liferayExperienceCloudEnvironmentId,
			},
		});
	}
}
