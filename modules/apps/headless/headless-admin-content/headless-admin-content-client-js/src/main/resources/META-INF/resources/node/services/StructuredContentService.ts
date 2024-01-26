/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema } from '../models/paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class StructuredContentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the site's structured contents latest version. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema
     * @throws ApiError
     */
    public getSiteStructuredContentsPage({
        siteId,
        aggregationTerms,
        fields,
        flatten,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        siteId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        flatten?: boolean,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-content/v1.0/sites/{siteId}/structured-contents',
            path: {
                'siteId': siteId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'flatten': flatten,
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
     * Creates a draft of a structured content
     * @returns paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema
     * @throws ApiError
     */
    public postSiteStructuredContentDraft({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema,
    }): CancelablePromise<paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-content/v1.0/sites/{siteId}/structured-contents/draft',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes a version of a structured content via its ID.
     * @returns void
     * @throws ApiError
     */
    public deleteStructuredContentByVersion({
        structuredContentId,
        version,
    }: {
        structuredContentId: number,
        version: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-content/v1.0/structured-contents/{structuredContentId}/by-version/{version}',
            path: {
                'structuredContentId': structuredContentId,
                'version': version,
            },
        });
    }
    /**
     * Retrieves a version of a structured content
     * @returns paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema
     * @throws ApiError
     */
    public getStructuredContentByVersion({
        structuredContentId,
        version,
        fields,
        nestedFields,
        restrictFields,
    }: {
        structuredContentId: number,
        version: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-content/v1.0/structured-contents/{structuredContentId}/by-version/{version}',
            path: {
                'structuredContentId': structuredContentId,
                'version': version,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves all versions of a structured content via its ID.
     * @returns paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema
     * @throws ApiError
     */
    public getStructuredContentsVersionsPage({
        structuredContentId,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        structuredContentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<paths_1headless_admin_content_1v1_0_1sites_1_siteId_1structured_contents_1draft_post_requestBody_content_application_1xml_schema>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-content/v1.0/structured-contents/{structuredContentId}/versions',
            path: {
                'structuredContentId': structuredContentId,
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
}
