/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export declare type TFeatureFlags = {
	companyId: number;
	dependenciesFulfilled: boolean;
	dependencyKeys: Array<string>;
	description: string;
	enabled: boolean;
	key: string;
	title: string;
};
interface IFeatureFlagListProps {
	featureFlags: Array<TFeatureFlags>;
}
declare const FeatureFlagList: React.FC<IFeatureFlagListProps>;
export default FeatureFlagList;
