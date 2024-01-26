/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Comment } from '../models/Comment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CommentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the blog post's comments in a list. Results can be paginated, filtered, searched, and sorted.
     * @returns Comment
     * @throws ApiError
     */
    public getBlogPostingCommentsPage({
        blogPostingId,
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
        blogPostingId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Comment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/comments',
            path: {
                'blogPostingId': blogPostingId,
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
     * Creates a new comment on the blog post.
     * @returns Comment
     * @throws ApiError
     */
    public postBlogPostingComment({
        blogPostingId,
        requestBody,
    }: {
        blogPostingId: number,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/comments',
            path: {
                'blogPostingId': blogPostingId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the comment and returns a 204 if the operation succeeded.
     * @returns void
     * @throws ApiError
     */
    public deleteComment({
        commentId,
    }: {
        commentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/comments/{commentId}',
            path: {
                'commentId': commentId,
            },
        });
    }
    /**
     * Retrieves the comment.
     * @returns Comment
     * @throws ApiError
     */
    public getComment({
        commentId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        commentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/comments/{commentId}',
            path: {
                'commentId': commentId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Replaces the comment with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns Comment
     * @throws ApiError
     */
    public putComment({
        commentId,
        requestBody,
    }: {
        commentId: number,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/comments/{commentId}',
            path: {
                'commentId': commentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the parent comment's child comments. Results can be paginated, filtered, searched, and sorted.
     * @returns Comment
     * @throws ApiError
     */
    public getCommentCommentsPage({
        parentCommentId,
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
        parentCommentId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Comment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/comments/{parentCommentId}/comments',
            path: {
                'parentCommentId': parentCommentId,
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
     * Creates a new child comment of the existing comment.
     * @returns Comment
     * @throws ApiError
     */
    public postCommentComment({
        parentCommentId,
        requestBody,
    }: {
        parentCommentId: number,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/comments/{parentCommentId}/comments',
            path: {
                'parentCommentId': parentCommentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the document's comments. Results can be paginated, filtered, searched, and sorted.
     * @returns Comment
     * @throws ApiError
     */
    public getDocumentCommentsPage({
        documentId,
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
        documentId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Comment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/documents/{documentId}/comments',
            path: {
                'documentId': documentId,
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
     * Creates a new comment on the document.
     * @returns Comment
     * @throws ApiError
     */
    public postDocumentComment({
        documentId,
        requestBody,
    }: {
        documentId: number,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/documents/{documentId}/comments',
            path: {
                'documentId': documentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the blog posting's comment by blog posting's and comment's external reference codes.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        blogPostingExternalReferenceCode,
        externalReferenceCode,
    }: {
        siteId: number,
        blogPostingExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{blogPostingExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'blogPostingExternalReferenceCode': blogPostingExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the blog posting's comment by blog posting's and comment's external reference codes.
     * @returns Comment
     * @throws ApiError
     */
    public getSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        blogPostingExternalReferenceCode,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        blogPostingExternalReferenceCode: string,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{blogPostingExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'blogPostingExternalReferenceCode': blogPostingExternalReferenceCode,
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
     * Updates the blog posting's comment given the blog posting's and comment's external reference codes, or creates it if it not exists.
     * @returns Comment
     * @throws ApiError
     */
    public putSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        blogPostingExternalReferenceCode,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        blogPostingExternalReferenceCode: string,
        externalReferenceCode: string,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{blogPostingExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'blogPostingExternalReferenceCode': blogPostingExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the parent comment's comment by its parent comment's and comment's external reference codes.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        parentCommentExternalReferenceCode,
        externalReferenceCode,
    }: {
        siteId: number,
        parentCommentExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/comments/by-external-reference-code/{parentCommentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'parentCommentExternalReferenceCode': parentCommentExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the parent comment's comment by its parent comment's and comment's external reference codes.
     * @returns Comment
     * @throws ApiError
     */
    public getSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        parentCommentExternalReferenceCode,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        parentCommentExternalReferenceCode: string,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/comments/by-external-reference-code/{parentCommentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'parentCommentExternalReferenceCode': parentCommentExternalReferenceCode,
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
     * Updates the parent comment's comment given the parent comment's and comment's external reference codes, or creates it if it not exists.
     * @returns Comment
     * @throws ApiError
     */
    public putSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        parentCommentExternalReferenceCode,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        parentCommentExternalReferenceCode: string,
        externalReferenceCode: string,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/comments/by-external-reference-code/{parentCommentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'parentCommentExternalReferenceCode': parentCommentExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the document's comment by document's and comment's external reference codes.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        documentExternalReferenceCode,
        externalReferenceCode,
    }: {
        siteId: number,
        documentExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{documentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'documentExternalReferenceCode': documentExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the document's comment by document's and comment's external reference codes.
     * @returns Comment
     * @throws ApiError
     */
    public getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        documentExternalReferenceCode,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        documentExternalReferenceCode: string,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{documentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'documentExternalReferenceCode': documentExternalReferenceCode,
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
     * Updates the document's comment given the document's and comment's external reference codes, or creates it if it not exists.
     * @returns Comment
     * @throws ApiError
     */
    public putSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        documentExternalReferenceCode,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        documentExternalReferenceCode: string,
        externalReferenceCode: string,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{documentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'documentExternalReferenceCode': documentExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the structured content's comment by structured content's and comment's external reference codes.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        structuredContentExternalReferenceCode,
        externalReferenceCode,
    }: {
        siteId: number,
        structuredContentExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{structuredContentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'structuredContentExternalReferenceCode': structuredContentExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the structured content's comment by structured content's and comment's external reference codes.
     * @returns Comment
     * @throws ApiError
     */
    public getSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        structuredContentExternalReferenceCode,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        structuredContentExternalReferenceCode: string,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{structuredContentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'structuredContentExternalReferenceCode': structuredContentExternalReferenceCode,
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
     * Updates the structured content's comment given the structured content's and comment's external reference codes, or creates it if it not exists.
     * @returns Comment
     * @throws ApiError
     */
    public putSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode({
        siteId,
        structuredContentExternalReferenceCode,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        structuredContentExternalReferenceCode: string,
        externalReferenceCode: string,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{structuredContentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'structuredContentExternalReferenceCode': structuredContentExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the structured content's comments. Results can be paginated, filtered, searched, and sorted.
     * @returns Comment
     * @throws ApiError
     */
    public getStructuredContentCommentsPage({
        structuredContentId,
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
        structuredContentId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Comment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/comments',
            path: {
                'structuredContentId': structuredContentId,
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
     * Creates a new comment on the structured content.
     * @returns Comment
     * @throws ApiError
     */
    public postStructuredContentComment({
        structuredContentId,
        requestBody,
    }: {
        structuredContentId: number,
        requestBody?: Comment,
    }): CancelablePromise<Comment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/comments',
            path: {
                'structuredContentId': structuredContentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
