/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
