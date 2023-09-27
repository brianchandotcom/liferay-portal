/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TColumn} from './types';
interface IManagementToolbarProps {
	addItemTitle?: string;
	columns: TColumn[];
	disabled: boolean;
	onAddItem?: () => void;
	showCheckbox: boolean;
}
declare const ManagementToolbar: React.FC<IManagementToolbarProps>;
export default ManagementToolbar;
