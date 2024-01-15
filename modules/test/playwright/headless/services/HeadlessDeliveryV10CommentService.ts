/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Comment} from '../models/HeadlessDelivery_v1_0_Comment';
import type {HeadlessDelivery_v1_0_PageComment} from '../models/HeadlessDelivery_v1_0_PageComment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10CommentService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutCommentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/comments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteCommentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/comments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param blogPostingId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostBlogPostingCommentsPageExportBatch(
		blogPostingId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/comments/export-batch',
			path: {
				blogPostingId: blogPostingId,
			},
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves the comment.
	 * @param commentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetComment(
		commentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/comments/{commentId}',
			path: {
				commentId: commentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the comment with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param commentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutComment(
		commentId: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/comments/{commentId}',
			path: {
				commentId: commentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the comment and returns a 204 if the operation succeeded.
	 * @param commentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteComment(
		commentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/comments/{commentId}',
			path: {
				commentId: commentId,
			},
		});
	}

	/**
	 * Retrieves the document's comments. Results can be paginated, filtered, searched, and sorted.
	 * @param documentId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageComment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentCommentsPage(
		documentId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/documents/{documentId}/comments',
			path: {
				documentId: documentId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Creates a new comment on the document.
	 * @param documentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentComment(
		documentId: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/documents/{documentId}/comments',
			path: {
				documentId: documentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the blog post's comments in a list. Results can be paginated, filtered, searched, and sorted.
	 * @param blogPostingId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageComment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetBlogPostingCommentsPage(
		blogPostingId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/comments',
			path: {
				blogPostingId: blogPostingId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Creates a new comment on the blog post.
	 * @param blogPostingId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostBlogPostingComment(
		blogPostingId: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/comments',
			path: {
				blogPostingId: blogPostingId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the structured content's comment by structured content's and comment's external reference codes.
	 * @param siteId
	 * @param structuredContentExternalReferenceCode
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		structuredContentExternalReferenceCode: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{structuredContentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				structuredContentExternalReferenceCode:
					structuredContentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the structured content's comment given the structured content's and comment's external reference codes, or creates it if it not exists.
	 * @param siteId
	 * @param structuredContentExternalReferenceCode
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		structuredContentExternalReferenceCode: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{structuredContentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				structuredContentExternalReferenceCode:
					structuredContentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the structured content's comment by structured content's and comment's external reference codes.
	 * @param siteId
	 * @param structuredContentExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		structuredContentExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{structuredContentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				structuredContentExternalReferenceCode:
					structuredContentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the structured content's comments. Results can be paginated, filtered, searched, and sorted.
	 * @param structuredContentId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageComment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentCommentsPage(
		structuredContentId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/comments',
			path: {
				structuredContentId: structuredContentId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Creates a new comment on the structured content.
	 * @param structuredContentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentComment(
		structuredContentId: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/comments',
			path: {
				structuredContentId: structuredContentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the parent comment's comment by its parent comment's and comment's external reference codes.
	 * @param siteId
	 * @param parentCommentExternalReferenceCode
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		parentCommentExternalReferenceCode: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/comments/by-external-reference-code/{parentCommentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				parentCommentExternalReferenceCode:
					parentCommentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the parent comment's comment given the parent comment's and comment's external reference codes, or creates it if it not exists.
	 * @param siteId
	 * @param parentCommentExternalReferenceCode
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		parentCommentExternalReferenceCode: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/comments/by-external-reference-code/{parentCommentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				parentCommentExternalReferenceCode:
					parentCommentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the parent comment's comment by its parent comment's and comment's external reference codes.
	 * @param siteId
	 * @param parentCommentExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		parentCommentExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/comments/by-external-reference-code/{parentCommentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				parentCommentExternalReferenceCode:
					parentCommentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param blogPostingId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostBlogPostingCommentBatch(
		blogPostingId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/comments/batch',
			path: {
				blogPostingId: blogPostingId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param documentId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentCommentsPageExportBatch(
		documentId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/documents/{documentId}/comments/export-batch',
			path: {
				documentId: documentId,
			},
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves the document's comment by document's and comment's external reference codes.
	 * @param siteId
	 * @param documentExternalReferenceCode
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		documentExternalReferenceCode: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{documentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				documentExternalReferenceCode: documentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the document's comment given the document's and comment's external reference codes, or creates it if it not exists.
	 * @param siteId
	 * @param documentExternalReferenceCode
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		documentExternalReferenceCode: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{documentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				documentExternalReferenceCode: documentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the document's comment by document's and comment's external reference codes.
	 * @param siteId
	 * @param documentExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		documentExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{documentExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				documentExternalReferenceCode: documentExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the parent comment's child comments. Results can be paginated, filtered, searched, and sorted.
	 * @param parentCommentId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageComment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetCommentCommentsPage(
		parentCommentId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/comments/{parentCommentId}/comments',
			path: {
				parentCommentId: parentCommentId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Creates a new child comment of the existing comment.
	 * @param parentCommentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostCommentComment(
		parentCommentId: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/comments/{parentCommentId}/comments',
			path: {
				parentCommentId: parentCommentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param structuredContentId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentCommentsPageExportBatch(
		structuredContentId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/comments/export-batch',
			path: {
				structuredContentId: structuredContentId,
			},
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves the blog posting's comment by blog posting's and comment's external reference codes.
	 * @param siteId
	 * @param blogPostingExternalReferenceCode
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		blogPostingExternalReferenceCode: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{blogPostingExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				blogPostingExternalReferenceCode:
					blogPostingExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the blog posting's comment given the blog posting's and comment's external reference codes, or creates it if it not exists.
	 * @param siteId
	 * @param blogPostingExternalReferenceCode
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Comment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		blogPostingExternalReferenceCode: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_Comment
	): CancelablePromise<HeadlessDelivery_v1_0_Comment> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{blogPostingExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				blogPostingExternalReferenceCode:
					blogPostingExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the blog posting's comment by blog posting's and comment's external reference codes.
	 * @param siteId
	 * @param blogPostingExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(
		siteId: string,
		blogPostingExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{blogPostingExternalReferenceCode}/comments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				blogPostingExternalReferenceCode:
					blogPostingExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param structuredContentId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentCommentBatch(
		structuredContentId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/comments/batch',
			path: {
				structuredContentId: structuredContentId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param documentId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentCommentBatch(
		documentId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/documents/{documentId}/comments/batch',
			path: {
				documentId: documentId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
