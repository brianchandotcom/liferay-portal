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

import {useMemo} from 'react';
import {Liferay} from '../services/liferay';

export default function useRouterPath() {
	return useMemo(() => {
		const relativeSiteURL = Liferay.ThemeDisplay.getLayoutRelativeURL();
		const siteURL = relativeSiteURL.substring(
			1,
			relativeSiteURL.lastIndexOf('/')
		);

		return {
			home: () => `${Liferay.ThemeDisplay.getPortalURL()}/${siteURL}`,
			onboarding: (externalReferenceCode) =>
				`${Liferay.ThemeDisplay.getPortalURL()}/${siteURL}/onboarding/#/${externalReferenceCode}`,
			project: (externalReferenceCode) =>
				`${Liferay.ThemeDisplay.getPortalURL()}/${siteURL}/project/#/${externalReferenceCode}`,
		};
	}, []);
}
