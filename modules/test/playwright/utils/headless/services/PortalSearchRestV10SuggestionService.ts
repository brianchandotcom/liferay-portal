/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {PortalSearchRest_v1_0_PageSuggestionsContributorResults} from '../models/PortalSearchRest_v1_0_PageSuggestionsContributorResults';
import type {PortalSearchRest_v1_0_SuggestionsContributorConfiguration} from '../models/PortalSearchRest_v1_0_SuggestionsContributorConfiguration';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class PortalSearchRestV10SuggestionService {

	/**
	 * @param currentUrl
	 * @param destinationFriendlyUrl
	 * @param groupId
	 * @param keywordsParameterName
	 * @param plid
	 * @param scope
	 * @param search
	 * @param requestBody
	 * @returns PortalSearchRest_v1_0_PageSuggestionsContributorResults default response
	 * @throws ApiError
	 */
	public static portalSearchRestV10PostSuggestionsPage(
		currentUrl?: string,
		destinationFriendlyUrl?: string,
		groupId?: string,
		keywordsParameterName?: string,
		plid?: string,
		scope?: string,
		search?: string,
		requestBody?: Array<PortalSearchRest_v1_0_SuggestionsContributorConfiguration>
	): CancelablePromise<PortalSearchRest_v1_0_PageSuggestionsContributorResults> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/portal-search-rest/v1.0/suggestions',
			query: {
				currentURL: currentUrl,
				destinationFriendlyURL: destinationFriendlyUrl,
				groupId: groupId,
				keywordsParameterName: keywordsParameterName,
				plid: plid,
				scope: scope,
				search: search,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
