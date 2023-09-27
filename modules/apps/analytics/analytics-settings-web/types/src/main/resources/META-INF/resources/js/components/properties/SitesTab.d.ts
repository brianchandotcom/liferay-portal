/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TProperty} from './Properties';
interface ISiteTabProps {
	initialIds: number[];
	onSitesChange: (ids: number[]) => void;
	property: TProperty;
}
declare const SitesTab: React.FC<ISiteTabProps>;
export default SitesTab;
