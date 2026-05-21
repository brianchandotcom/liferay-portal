/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Range} from '../components/date_filter';
import {RequestPortletDataHandler} from './portletDataHandler';

export interface ExportRequest {
	endDate?: string;
	fileName: string;
	last?: number;
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

export interface ImportRequest {
	requestPortletDataHandlers?: RequestPortletDataHandler[];
}

export interface ImportProcess {
	dateCreated?: string;
	dateModified?: string;
	id?: number;
	name?: string;
	status?: {code: number; label: string};
}
