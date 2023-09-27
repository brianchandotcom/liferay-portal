/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TColumn} from './types';
interface IContentProps {
	columns: TColumn[];
	disabled: boolean;
	showCheckbox: boolean;
}
declare const Content: React.FC<IContentProps>;
export default Content;
