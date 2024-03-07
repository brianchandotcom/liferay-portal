/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldTypeName} from 'data-engine-js-components-web';
import React from 'react';
import './SidebarFieldSettings.scss';
declare const SidebarFieldSettings: React.FC<IProps>;
export default SidebarFieldSettings;
interface Field {
	name: string;
	type: FieldTypeName;
}
interface IProps {
	field: Field;
}
