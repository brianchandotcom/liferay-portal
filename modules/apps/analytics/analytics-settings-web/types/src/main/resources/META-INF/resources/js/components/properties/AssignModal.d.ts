/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TProperty} from './Properties';
export declare enum ETabs {
	Channel = 0,
	Sites = 1,
}
interface IAssignModalWrapperProps {
	observer: any;
	onCancel: () => void;
	onSubmit: ({
		commerceChannelIds,
		siteIds,
	}: {
		commerceChannelIds: number[];
		siteIds: number[];
	}) => void;
	property: TProperty | null;
}
declare const AssignModalWrapper: React.FC<IAssignModalWrapperProps>;
export default AssignModalWrapper;
