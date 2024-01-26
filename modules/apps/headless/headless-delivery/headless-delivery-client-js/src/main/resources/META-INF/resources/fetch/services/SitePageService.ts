/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SitePage } from '../models/SitePage';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SitePageService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the public pages of the site
     * @returns SitePage
     * @throws ApiError
     */
    public getSiteSitePagesPage({
        siteId,
        aggregationTerms,
        fields,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        siteId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<SitePage>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/site-pages',
            path: {
                'siteId': siteId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Adds a new site page
     * @returns SitePage default response
     * @throws ApiError
     */
    public postSiteSitePage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: SitePage,
    }): CancelablePromise<SitePage> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/site-pages',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves a specific public page of a site
     * @returns SitePage
     * @throws ApiError
     */
    public getSiteSitePage({
        siteId,
        friendlyUrlPath,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        siteId: number,
        friendlyUrlPath: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<SitePage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}',
            path: {
                'siteId': siteId,
                'friendlyUrlPath': friendlyUrlPath,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the experiences of a given Page
     * @returns SitePage
     * @throws ApiError
     */
    public getSiteSitePagesExperiencesPage({
        siteId,
        friendlyUrlPath,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        siteId: number,
        friendlyUrlPath: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<SitePage>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/experiences',
            path: {
                'siteId': siteId,
                'friendlyUrlPath': friendlyUrlPath,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves a specific public page of a site for a given experience
     * @returns SitePage
     * @throws ApiError
     */
    public getSiteSitePageExperienceExperienceKey({
        siteId,
        friendlyUrlPath,
        experienceKey,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        siteId: number,
        friendlyUrlPath: string,
        experienceKey: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<SitePage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/experiences/{experienceKey}',
            path: {
                'siteId': siteId,
                'friendlyUrlPath': friendlyUrlPath,
                'experienceKey': experienceKey,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the rendered content of a given public page for a given experience.
     * @returns string
     * @throws ApiError
     */
    public getSiteSitePageExperienceExperienceKeyRenderedPage({
        siteId,
        friendlyUrlPath,
        experienceKey,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        friendlyUrlPath: string,
        experienceKey: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/experiences/{experienceKey}/rendered-page',
            path: {
                'siteId': siteId,
                'friendlyUrlPath': friendlyUrlPath,
                'experienceKey': experienceKey,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the rendered content of a given public page.
     * @returns string
     * @throws ApiError
     */
    public getSiteSitePageRenderedPage({
        siteId,
        friendlyUrlPath,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        friendlyUrlPath: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/rendered-page',
            path: {
                'siteId': siteId,
                'friendlyUrlPath': friendlyUrlPath,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
}
