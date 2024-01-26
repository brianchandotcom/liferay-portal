/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SuggestionsContributorConfiguration } from '../models/SuggestionsContributorConfiguration';
import type { SuggestionsContributorResults } from '../models/SuggestionsContributorResults';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SuggestionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SuggestionsContributorResults
     * @throws ApiError
     */
    public postSuggestionsPage({
        search,
        currentUrl,
        destinationFriendlyUrl,
        groupId,
        keywordsParameterName,
        plid,
        scope,
        requestBody,
    }: {
        search: string,
        currentUrl?: string,
        destinationFriendlyUrl?: string,
        groupId?: number,
        keywordsParameterName?: string,
        plid?: number,
        scope?: string,
        requestBody?: Array<SuggestionsContributorConfiguration>,
    }): CancelablePromise<Array<SuggestionsContributorResults>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-search-rest/v1.0/suggestions',
            query: {
                'currentURL': currentUrl,
                'destinationFriendlyURL': destinationFriendlyUrl,
                'groupId': groupId,
                'keywordsParameterName': keywordsParameterName,
                'plid': plid,
                'scope': scope,
                'search': search,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
