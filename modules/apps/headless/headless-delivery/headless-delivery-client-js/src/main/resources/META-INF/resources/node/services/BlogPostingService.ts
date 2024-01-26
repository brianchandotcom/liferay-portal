/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BlogPosting } from '../models/BlogPosting';
import type { Rating } from '../models/Rating';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class BlogPostingService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the blog post and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteBlogPosting({
        blogPostingId,
    }: {
        blogPostingId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
            path: {
                'blogPostingId': blogPostingId,
            },
        });
    }
    /**
     * Retrieves the blog post.
     * @returns BlogPosting
     * @throws ApiError
     */
    public getBlogPosting({
        blogPostingId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        blogPostingId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<BlogPosting> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
            path: {
                'blogPostingId': blogPostingId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates the blog post using only the fields received in the request body. Any other fields are left untouched. Returns the updated blog post.
     * @returns BlogPosting
     * @throws ApiError
     */
    public patchBlogPosting({
        blogPostingId,
        requestBody,
    }: {
        blogPostingId: number,
        requestBody?: BlogPosting,
    }): CancelablePromise<BlogPosting> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
            path: {
                'blogPostingId': blogPostingId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the blog post with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns BlogPosting
     * @throws ApiError
     */
    public putBlogPosting({
        blogPostingId,
        requestBody,
    }: {
        blogPostingId: number,
        requestBody?: BlogPosting,
    }): CancelablePromise<BlogPosting> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
            path: {
                'blogPostingId': blogPostingId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the blog post rating of the user who authenticated the request.
     * @returns void
     * @throws ApiError
     */
    public deleteBlogPostingMyRating({
        blogPostingId,
    }: {
        blogPostingId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
            path: {
                'blogPostingId': blogPostingId,
            },
        });
    }
    /**
     * Retrieves the blog post rating of the user who authenticated the request.
     * @returns Rating
     * @throws ApiError
     */
    public getBlogPostingMyRating({
        blogPostingId,
        fields,
        restrictFields,
    }: {
        blogPostingId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
            path: {
                'blogPostingId': blogPostingId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a new blog post rating by the user who authenticated the request.
     * @returns Rating
     * @throws ApiError
     */
    public postBlogPostingMyRating({
        blogPostingId,
        requestBody,
    }: {
        blogPostingId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
            path: {
                'blogPostingId': blogPostingId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces an existing blog post rating by the user who authenticated the request.
     * @returns Rating
     * @throws ApiError
     */
    public putBlogPostingMyRating({
        blogPostingId,
        requestBody,
    }: {
        blogPostingId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
            path: {
                'blogPostingId': blogPostingId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getBlogPostingPermissionsPage({
        blogPostingId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        blogPostingId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/permissions',
            path: {
                'blogPostingId': blogPostingId,
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
    public putBlogPostingPermissionsPage({
        blogPostingId,
        requestBody,
    }: {
        blogPostingId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/permissions',
            path: {
                'blogPostingId': blogPostingId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the blog post's rendered display page
     * @returns string
     * @throws ApiError
     */
    public getBlogPostingRenderedContentByDisplayPageDisplayPageKey({
        blogPostingId,
        displayPageKey,
        fields,
        nestedFields,
        restrictFields,
    }: {
        blogPostingId: number,
        displayPageKey: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/rendered-content-by-display-page/{displayPageKey}',
            path: {
                'blogPostingId': blogPostingId,
                'displayPageKey': displayPageKey,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the site's blog postings. Results can be paginated, filtered, searched, and sorted.
     * @returns BlogPosting
     * @throws ApiError
     */
    public getSiteBlogPostingsPage({
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
    }): CancelablePromise<Array<BlogPosting>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings',
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
     * Creates a new blog post.
     * @returns BlogPosting
     * @throws ApiError
     */
    public postSiteBlogPosting({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: BlogPosting,
    }): CancelablePromise<BlogPosting> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the blog post by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteBlogPostingByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's blog post by external reference code.
     * @returns BlogPosting
     * @throws ApiError
     */
    public getSiteBlogPostingByExternalReferenceCode({
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
    }): CancelablePromise<BlogPosting> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}',
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
     * Updates the site's blog post with the given external reference code, or creates it if it not exists.
     * @returns BlogPosting
     * @throws ApiError
     */
    public putSiteBlogPostingByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: BlogPosting,
    }): CancelablePromise<BlogPosting> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}',
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
    public getSiteBlogPostingPermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/permissions',
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
    public putSiteBlogPostingPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/permissions',
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
    public putSiteBlogPostingSubscribe({
        siteId,
    }: {
        siteId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/subscribe',
            path: {
                'siteId': siteId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteBlogPostingUnsubscribe({
        siteId,
    }: {
        siteId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/unsubscribe',
            path: {
                'siteId': siteId,
            },
        });
    }
}
