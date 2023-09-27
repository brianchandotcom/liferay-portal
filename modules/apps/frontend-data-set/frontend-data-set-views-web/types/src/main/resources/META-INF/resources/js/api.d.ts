/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSViewType} from './FDSViews';
import {IField, IPickList} from './types';
export declare function getFields(fdsView: FDSViewType): Promise<IField[]>;
export declare function getAllPicklists(
	page?: number,
	items?: IPickList[]
): Promise<IPickList[]>;
