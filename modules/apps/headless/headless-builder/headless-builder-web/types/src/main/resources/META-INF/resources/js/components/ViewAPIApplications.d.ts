/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import '../../css/main.scss';
interface APIApplicationsProps {
	apiURLPaths: APIURLPaths;
	basePath: string;
	editURL: string;
	portletId: string;
}
export default function ViewAPIApplications({
	apiURLPaths,
	basePath,
	editURL,
	portletId,
}: APIApplicationsProps): JSX.Element;
export {};
