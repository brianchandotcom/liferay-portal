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

export type DataStrategy = 'MIRROR' | 'MIRROR_OVERWRITE';

export type UserIdStrategy = 'CURRENT_USER_ID' | 'ALWAYS_CURRENT_USER_ID';

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
