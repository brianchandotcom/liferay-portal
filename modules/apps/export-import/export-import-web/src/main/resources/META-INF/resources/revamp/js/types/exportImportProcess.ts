/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Range} from '../components/date_filter';
import {RequestPortletDataHandler} from './portletDataHandler';

export type ExportImportProcess = 'export' | 'import';

export interface ExportProcess {
	dateCreated?: string;
	dateModified?: string;
	id?: number;
	name?: string;
	status?: {code: number; label: string};
}

export interface ExportProcessRequest {
	comments?: boolean;
	deletions?: boolean;
	endDate?: string;
	last?: number;
	logo?: boolean;
	name: string;
	permissions?: boolean;
	range?: Range;
	ratings?: boolean;
	requestPortletDataHandlers?: RequestPortletDataHandler[];
	sitePagesSettings?: boolean;
	siteTemplateSettings?: boolean;
	startDate?: string;
	themeSettings?: boolean;
}

export const DATA_STRATEGIES = {
	MIRROR: 'MIRROR',
	MIRROR_OVERWRITE: 'MIRROR_OVERWRITE',
} as const;

export type DataStrategy =
	(typeof DATA_STRATEGIES)[keyof typeof DATA_STRATEGIES];

export const USER_ID_STRATEGIES = {
	ALWAYS_CURRENT_USER_ID: 'ALWAYS_CURRENT_USER_ID',
	CURRENT_USER_ID: 'CURRENT_USER_ID',
} as const;

export type UserIdStrategy =
	(typeof USER_ID_STRATEGIES)[keyof typeof USER_ID_STRATEGIES];

export interface ImportProcess {
	dateCreated?: string;
	dateModified?: string;
	id?: number;
	name?: string;
	status?: {code: number; label: string};
}

export interface ImportProcessRequest {
	comments?: boolean;
	dataStrategy?: DataStrategy;
	deletions?: boolean;
	logo?: boolean;
	name?: string;
	permissions?: boolean;
	ratings?: boolean;
	requestPortletDataHandlers?: RequestPortletDataHandler[];
	sitePagesSettings?: boolean;
	siteTemplateSettings?: boolean;
	themeSettings?: boolean;
	userIdStrategy?: UserIdStrategy;
}
