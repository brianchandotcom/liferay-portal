/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { KnowledgeBaseArticle } from '../models/KnowledgeBaseArticle';
import type { Rating } from '../models/Rating';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class KnowledgeBaseArticleService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the knowledge base article and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteKnowledgeBaseArticle({
        knowledgeBaseArticleId,
    }: {
        knowledgeBaseArticleId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
        });
    }
    /**
     * Retrieves the knowledge base article.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public getKnowledgeBaseArticle({
        knowledgeBaseArticleId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        knowledgeBaseArticleId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates only the fields received in the request body, leaving any other fields untouched.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public patchKnowledgeBaseArticle({
        knowledgeBaseArticleId,
        requestBody,
    }: {
        knowledgeBaseArticleId: number,
        requestBody?: KnowledgeBaseArticle,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the knowledge base article with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public putKnowledgeBaseArticle({
        knowledgeBaseArticleId,
        requestBody,
    }: {
        knowledgeBaseArticleId: number,
        requestBody?: KnowledgeBaseArticle,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the knowledge base article's rating and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteKnowledgeBaseArticleMyRating({
        knowledgeBaseArticleId,
    }: {
        knowledgeBaseArticleId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
        });
    }
    /**
     * Retrieves the knowledge base article's rating.
     * @returns Rating
     * @throws ApiError
     */
    public getKnowledgeBaseArticleMyRating({
        knowledgeBaseArticleId,
        fields,
        restrictFields,
    }: {
        knowledgeBaseArticleId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a rating for the knowledge base article.
     * @returns Rating
     * @throws ApiError
     */
    public postKnowledgeBaseArticleMyRating({
        knowledgeBaseArticleId,
        requestBody,
    }: {
        knowledgeBaseArticleId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns Rating
     * @throws ApiError
     */
    public putKnowledgeBaseArticleMyRating({
        knowledgeBaseArticleId,
        requestBody,
    }: {
        knowledgeBaseArticleId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getKnowledgeBaseArticlePermissionsPage({
        knowledgeBaseArticleId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        knowledgeBaseArticleId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/permissions',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putKnowledgeBaseArticlePermissionsPage({
        knowledgeBaseArticleId,
        requestBody,
    }: {
        knowledgeBaseArticleId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/permissions',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putKnowledgeBaseArticleSubscribe({
        knowledgeBaseArticleId,
    }: {
        knowledgeBaseArticleId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/subscribe',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putKnowledgeBaseArticleUnsubscribe({
        knowledgeBaseArticleId,
    }: {
        knowledgeBaseArticleId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/unsubscribe',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
        });
    }
    /**
     * Retrieves the parent knowledge base article's child knowledge base articles. Results can be paginated, filtered, searched, and sorted.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public getKnowledgeBaseArticleKnowledgeBaseArticlesPage({
        parentKnowledgeBaseArticleId,
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
    }: {
        parentKnowledgeBaseArticleId: number,
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
    }): CancelablePromise<Array<KnowledgeBaseArticle>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles',
            path: {
                'parentKnowledgeBaseArticleId': parentKnowledgeBaseArticleId,
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
     * Creates a child knowledge base article of the knowledge base article identified by `parentKnowledgeBaseArticleId`.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public postKnowledgeBaseArticleKnowledgeBaseArticle({
        parentKnowledgeBaseArticleId,
        requestBody,
    }: {
        parentKnowledgeBaseArticleId: number,
        requestBody?: KnowledgeBaseArticle,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles',
            path: {
                'parentKnowledgeBaseArticleId': parentKnowledgeBaseArticleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the folder's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public getKnowledgeBaseFolderKnowledgeBaseArticlesPage({
        knowledgeBaseFolderId,
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
    }: {
        knowledgeBaseFolderId: number,
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
    }): CancelablePromise<Array<KnowledgeBaseArticle>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
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
     * Creates a new knowledge base article in the folder.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public postKnowledgeBaseFolderKnowledgeBaseArticle({
        knowledgeBaseFolderId,
        requestBody,
    }: {
        knowledgeBaseFolderId: number,
        requestBody?: KnowledgeBaseArticle,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the site's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public getSiteKnowledgeBaseArticlesPage({
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
    }): CancelablePromise<Array<KnowledgeBaseArticle>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles',
            path: {
                'siteId': siteId,
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
     * Creates a new knowledge base article.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public postSiteKnowledgeBaseArticle({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: KnowledgeBaseArticle,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the knowledge base article by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteKnowledgeBaseArticleByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's knowledge base article by external reference code.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public getSiteKnowledgeBaseArticleByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates the site's knowledge base article with the given external reference code, or creates it if it not exists.
     * @returns KnowledgeBaseArticle
     * @throws ApiError
     */
    public putSiteKnowledgeBaseArticleByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: KnowledgeBaseArticle,
    }): CancelablePromise<KnowledgeBaseArticle> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getSiteKnowledgeBaseArticlePermissionsPage({
        siteId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        siteId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/permissions',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteKnowledgeBaseArticlePermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteKnowledgeBaseArticleSubscribe({
        siteId,
    }: {
        siteId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/subscribe',
            path: {
                'siteId': siteId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteKnowledgeBaseArticleUnsubscribe({
        siteId,
    }: {
        siteId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/unsubscribe',
            path: {
                'siteId': siteId,
            },
        });
    }
}
