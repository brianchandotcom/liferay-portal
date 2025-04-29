/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum Individuals {
	AllIndividuals = 'ALL',
	AnonymousIndividuals = 'UNKNOWN',
	KnownIndividuals = 'KNOWN',
}

export enum RangeSelectors {
	Last24Hours = '0',
	Last7Days = '7',
	Last28Days = '28',
	Last30Days = '30',
	Last90Days = '90',
}

export enum MetricName {
	Comments = 'commentsMetric',
	Downloads = 'downloadsMetric',
	Previews = 'previewsMetric',
	Undefined = 'undefinedMetric',
	Views = 'viewsMetric',
}

export enum AssetTypes {
	Blog = 'blog',
	Document = 'document',
	Undefined = 'undefined',
	WebContent = 'journal',
}

export enum MetricType {
	Comments = 'COMMENTS',
	Downloads = 'DOWNLOADS',
	Previews = 'PREVIEWS',
	Undefined = 'UNDEFINED',
	Views = 'VIEWS',
}

export type IndividualIdentity = {
	createDate: string;
	id: string;
	individualId: string;
};
