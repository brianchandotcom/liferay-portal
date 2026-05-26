/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Range} from '../components/date_filter';
import {RequestPortletDataHandler} from './portletDataHandler';

export interface ExportRequest {
	deletions?: boolean;
	endDate?: string;
	fileName: string;
	last?: number;
	permissions?: boolean;
	range?: Range;
	requestPortletDataHandlers?: RequestPortletDataHandler[];
	startDate?: string;
}

export interface ExportProcess {
	dateCreated?: string;
	dateModified?: string;
	id?: number;
	name?: string;
	status?: {code: number; label: string};
}

export type DataStrategy = 'MIRROR' | 'MIRROR_OVERWRITE' | 'COPY_AS_NEW';

export type UserIdStrategy = 'CURRENT_USER_ID' | 'ALWAYS_CURRENT_USER_ID';

export interface ImportRequest {
	dataStrategy?: DataStrategy;
	deletions?: boolean;
	permissions?: boolean;
	requestPortletDataHandlers?: RequestPortletDataHandler[];
	userIdStrategy?: UserIdStrategy;
}

export interface ImportProcess {
	dateCreated?: string;
	dateModified?: string;
	id?: number;
	name?: string;
	status?: {code: number; label: string};
}
