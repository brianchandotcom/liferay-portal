/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	BaseLayoutDataItem,
	CommonStyles,
	ResponsiveConfig,
} from './BaseLayoutDataItem';

export type RowLayoutDataItem = BaseLayoutDataItem<
	'row',
	CommonStyles &
		ResponsiveConfig<{
			gutters?: boolean;
			modulesPerRow?: number;
			numberOfColumns?: number;
			reverseOrder?: boolean;
			verticalAlignment?: 'middle';
		}>
>;
