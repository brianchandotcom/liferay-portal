/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {Individuals, MetricName, RangeSelectors} from '../types/global';

const API_PREFIX = '/o/analytics-reports-rest/v1.0';

export type AssetMetricProps = {
	assetId: string;
	assetType: string;
	groupId: string;
	individual: Individuals;
	rangeSelector: RangeSelectors;
	selectedMetrics: MetricName[];
};

export function fetchAssetMetric({
	assetId,
	assetType,
	groupId,
	individual,
	rangeSelector,
	selectedMetrics,
}: AssetMetricProps) {
	return fetch(
		`${API_PREFIX}/${groupId}/asset-metrics/${assetType}?assetId=${assetId}&identityType=${individual}&rangeKey=${rangeSelector}&selectedMetrics=${selectedMetrics}`,
		{
			method: 'GET',
		}
	);
}

export type AssetMetricHistogramProps = {
	assetId: string;
	assetType: string;
	groupId: string;
	individual: Individuals;
	rangeSelector: RangeSelectors;
	selectedMetrics: MetricName[];
};

export function fetchAssetMetricHistogram({
	assetId,
	assetType,
	groupId,
	individual,
	rangeSelector,
	selectedMetrics,
}: AssetMetricHistogramProps) {
	return fetch(
		`${API_PREFIX}/${groupId}/asset-metrics/${assetType}/histogram?assetId=${assetId}&identityType=${individual}&rangeKey=${rangeSelector}&selectedMetrics=${selectedMetrics}`,
		{
			method: 'GET',
		}
	);
}

export type AssetAppearsOnHistogramProps = {
	assetId: string;
	assetType: string;
	groupId: string;
	individual: Individuals;
	rangeSelector: RangeSelectors;
};

export function fetchAssetAppearsOnHistogram({
	assetId,
	assetType,
	groupId,
	individual,
	rangeSelector,
}: AssetAppearsOnHistogramProps) {
	return fetch(
		`${API_PREFIX}/${groupId}/asset-metrics/${assetType}/appears-on/histogram?assetId=${assetId}&identityType=${individual}&rangeKey=${rangeSelector}`,
		{
			method: 'GET',
		}
	);
}

export type AssetDeviceMetric = {
	assetId: string;
	assetType: string;
	groupId: string;
	individual: Individuals;
	rangeSelector: RangeSelectors;
};

export function fetchAssetDeviceMetric({
	assetId,
	assetType,
	groupId,
	individual,
	rangeSelector,
}: AssetDeviceMetric) {
	return fetch(
		`${API_PREFIX}/${groupId}/asset-metrics/${assetType}/devices?assetId=${assetId}&identityType=${individual}&rangeKey=${rangeSelector}`,
		{
			method: 'GET',
		}
	);
}
