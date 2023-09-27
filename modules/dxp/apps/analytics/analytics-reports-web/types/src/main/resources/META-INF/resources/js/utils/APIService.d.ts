/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Parameters {
	namespace: string;
	plid: number;
	timeSpanKey?: 'last-7-days' | 'last-30-days';
	timeSpanOffset?: number;
}
declare const _default: {
	getAnalyticsReportsData(
		analyticsReportsURL: string,
		body: object
	): Promise<any>;
	getHistoricalReads(
		analyticsReportsHistoricalReadsURL: string,
		{namespace, plid, timeSpanKey, timeSpanOffset}: Parameters
	): Promise<any>;
	getHistoricalViews(
		analyticsReportsHistoricalViewsURL: string,
		{namespace, plid, timeSpanKey, timeSpanOffset}: Parameters
	): Promise<any>;
	getTotalReads(
		analyticsReportsTotalReadsURL: string,
		{namespace, plid}: Parameters
	): Promise<any>;
	getTotalViews(
		analyticsReportsTotalViewsURL: string,
		{namespace, plid}: Parameters
	): Promise<any>;
	getTrafficSources(
		analyticsReportsTrafficSourcesURL: string,
		{namespace, plid, timeSpanKey, timeSpanOffset}: Parameters
	): Promise<any>;
};
export default _default;

/**
 *
 *
 * @export
 * @param {Object} body
 * @param {string} prefix
 * @param {FormData} [formData=new FormData()]
 * @returns {FormData}
 */
export declare function _getFormDataRequest(
	body: object,
	prefix: string,
	formData?: FormData
): FormData;
