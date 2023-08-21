/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Issue} from './Issue';

export interface LayoutReportsIssues {
	date: string;
	issues: Issue[];
	timestamp: string;
}

interface LocalizedLayoutReportsIssues {
	[key: string]: LayoutReportsIssues;
}

export interface LayoutReportsData {
	configureGooglePageSpeedURL?: string;
	defaultLanguageId?: string;
	error?: string;
	imagesPath?: string;
	layoutReportsIssues?: LocalizedLayoutReportsIssues;
	pageURLs?: object[];
	privateLayout?: boolean;
	validConnection?: boolean;
}
