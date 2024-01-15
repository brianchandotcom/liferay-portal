/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_Channel} from '../models/AnalyticsSettingsRest_v1_0_Channel';
import type {AnalyticsSettingsRest_v1_0_PageChannel} from '../models/AnalyticsSettingsRest_v1_0_PageChannel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10ChannelService {

	/**
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns AnalyticsSettingsRest_v1_0_PageChannel default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetChannelsPage(
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<AnalyticsSettingsRest_v1_0_PageChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/channels',
			query: {
				keywords: keywords,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns AnalyticsSettingsRest_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PostChannel(
		requestBody?: AnalyticsSettingsRest_v1_0_Channel
	): CancelablePromise<AnalyticsSettingsRest_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/analytics-settings-rest/v1.0/channels',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns AnalyticsSettingsRest_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PatchChannel(
		requestBody?: AnalyticsSettingsRest_v1_0_Channel
	): CancelablePromise<AnalyticsSettingsRest_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/analytics-settings-rest/v1.0/channels',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
