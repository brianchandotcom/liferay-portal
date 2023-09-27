/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TWorkflowStatus} from './types';
interface ICustomViewWrapperProps extends React.HTMLAttributes<HTMLElement> {
	filterOperators: TFilterOperators;
	isViewOnly: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectViewId: string;
	workflowStatusJSONArray: TWorkflowStatus[];
}
declare const CustomViewWrapper: React.FC<ICustomViewWrapperProps>;
export default CustomViewWrapper;
