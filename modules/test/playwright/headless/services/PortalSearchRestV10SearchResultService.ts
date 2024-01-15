/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {PortalSearchRest_v1_0_PageSearchResult} from '../models/PortalSearchRest_v1_0_PageSearchResult';
import type {PortalSearchRest_v1_0_SearchRequestBody} from '../models/PortalSearchRest_v1_0_SearchRequestBody';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class PortalSearchRestV10SearchResultService {

	/**
	 * Search the company index for matching content. This endpoint is beta and requires setting the portal property 'feature.flag.LPS-179669' to true or enabling via Instance Settings > Feature Flags: Beta.
	 * @param entryClassNames
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @param requestBody
	 * @returns PortalSearchRest_v1_0_PageSearchResult default response
	 * @throws ApiError
	 */
	public static portalSearchRestV10PostSearchPage(
		entryClassNames?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string,
		requestBody?: PortalSearchRest_v1_0_SearchRequestBody
	): CancelablePromise<PortalSearchRest_v1_0_PageSearchResult> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/portal-search-rest/v1.0/search',
			query: {
				entryClassNames: entryClassNames,
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
