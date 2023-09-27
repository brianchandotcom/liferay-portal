/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface APIApplicationsTableProps {
	apiURLPaths: APIURLPaths;
	currentAPIApplicationId: string | null;
	portletId: string;
	setMainSchemaNav: Dispatch<SetStateAction<MainNav>>;
}
export default function APISchemasTable({
	apiURLPaths,
	currentAPIApplicationId,
	portletId,
	setMainSchemaNav,
}: APIApplicationsTableProps): JSX.Element;
export {};
