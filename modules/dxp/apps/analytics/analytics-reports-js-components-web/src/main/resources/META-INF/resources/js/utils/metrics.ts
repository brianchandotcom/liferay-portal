/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AssetTypes, MetricName, MetricType} from '../types/global';

type AssetMetrics = {
	[key in AssetTypes]: MetricName[];
};

export const assetMetrics: AssetMetrics = {
	[AssetTypes.Blog]: [MetricName.Views, MetricName.Comments],
	[AssetTypes.Document]: [
		MetricName.Downloads,
		MetricName.Previews,
		MetricName.Comments,
	],
	[AssetTypes.WebContent]: [MetricName.Views],
	[AssetTypes.Undefined]: [],
};

export const metricNameByType = {
	[MetricType.Comments]: MetricName.Comments,
	[MetricType.Downloads]: MetricName.Downloads,
	[MetricType.Previews]: MetricName.Previews,
	[MetricType.Views]: MetricName.Views,
	[MetricType.Undefined]: MetricName.Undefined,
};

export type AssetMetricComplement = {
	interactionsByPageTooltipTitle: string;
	metricType: 'percentage' | 'number' | 'long' | 'undefined';
	visitorsBehaviorTooltipTitle: string;
};

export const assetContent: {
	[key in MetricName]: AssetMetricComplement;
} = {
	[MetricName.Comments]: {
		interactionsByPageTooltipTitle: Liferay.Language.get(
			'comments-by-top-pages'
		),
		metricType: 'number',
		visitorsBehaviorTooltipTitle: Liferay.Language.get('total-comments'),
	},
	[MetricName.Downloads]: {
		interactionsByPageTooltipTitle: Liferay.Language.get(
			'downloads-by-top-pages'
		),
		metricType: 'number',
		visitorsBehaviorTooltipTitle: Liferay.Language.get('total-downloads'),
	},
	[MetricName.Previews]: {
		interactionsByPageTooltipTitle: Liferay.Language.get(
			'previews-by-top-pages'
		),
		metricType: 'number',
		visitorsBehaviorTooltipTitle: Liferay.Language.get('total-previews'),
	},
	[MetricName.Views]: {
		interactionsByPageTooltipTitle:
			Liferay.Language.get('views-by-top-pages'),
		metricType: 'number',
		visitorsBehaviorTooltipTitle: Liferay.Language.get('total-views'),
	},
	[MetricName.Undefined]: {
		interactionsByPageTooltipTitle: 'undefined',
		metricType: 'undefined',
		visitorsBehaviorTooltipTitle: 'undefined',
	},
};
