/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface SchemasContentProps {
	apiURLPaths: APIURLPaths;
	currentAPIApplicationId: string;
	portletId: string;
	setManagementButtonsProps: Dispatch<SetStateAction<ManagementButtonsProps>>;
	setStatus: Dispatch<SetStateAction<ApplicationStatusKeys>>;
	setTitle: Dispatch<SetStateAction<string>>;
}
export default function SchemasContent({
	apiURLPaths,
	currentAPIApplicationId,
	portletId,
	setManagementButtonsProps,
	setStatus,
	setTitle,
}: SchemasContentProps): JSX.Element;
export {};
