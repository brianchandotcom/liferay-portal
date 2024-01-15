/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_BlogPosting} from '../models/HeadlessDelivery_v1_0_BlogPosting';
import type {HeadlessDelivery_v1_0_PageBlogPosting} from '../models/HeadlessDelivery_v1_0_PageBlogPosting';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_Rating} from '../models/HeadlessDelivery_v1_0_Rating';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10BlogPostingService {

	/**
	 * Retrieves the blog post.
	 * @param blogPostingId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_BlogPosting default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetBlogPosting(
		blogPostingId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPosting> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
			path: {
				blogPostingId: blogPostingId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the blog post with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param blogPostingId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_BlogPosting default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutBlogPosting(
		blogPostingId: string,
		requestBody?: HeadlessDelivery_v1_0_BlogPosting
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPosting> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
			path: {
				blogPostingId: blogPostingId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the blog post and returns a 204 if the operation succeeds.
	 * @param blogPostingId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteBlogPosting(
		blogPostingId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
			path: {
				blogPostingId: blogPostingId,
			},
		});
	}

	/**
	 * Updates the blog post using only the fields received in the request body. Any other fields are left untouched. Returns the updated blog post.
	 * @param blogPostingId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_BlogPosting default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchBlogPosting(
		blogPostingId: string,
		requestBody?: HeadlessDelivery_v1_0_BlogPosting
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPosting> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}',
			path: {
				blogPostingId: blogPostingId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the blog post rating of the user who authenticated the request.
	 * @param blogPostingId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetBlogPostingMyRating(
		blogPostingId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
			path: {
				blogPostingId: blogPostingId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces an existing blog post rating by the user who authenticated the request.
	 * @param blogPostingId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutBlogPostingMyRating(
		blogPostingId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
			path: {
				blogPostingId: blogPostingId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Creates a new blog post rating by the user who authenticated the request.
	 * @param blogPostingId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostBlogPostingMyRating(
		blogPostingId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
			path: {
				blogPostingId: blogPostingId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the blog post rating of the user who authenticated the request.
	 * @param blogPostingId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteBlogPostingMyRating(
		blogPostingId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating',
			path: {
				blogPostingId: blogPostingId,
			},
		});
	}

	/**
	 * Retrieves the site's blog postings. Results can be paginated, filtered, searched, and sorted.
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageBlogPosting default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteBlogPostingsPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageBlogPosting> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings',
			path: {
				siteId: siteId,
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
	 * Creates a new blog post.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_BlogPosting default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteBlogPosting(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_BlogPosting
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPosting> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the blog post's rendered display page
	 * @param blogPostingId
	 * @param displayPageKey
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetBlogPostingRenderedContentByDisplayPageDisplayPageKey(
		blogPostingId: string,
		displayPageKey: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/rendered-content-by-display-page/{displayPageKey}',
			path: {
				blogPostingId: blogPostingId,
				displayPageKey: displayPageKey,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * @param siteId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteBlogPostingSubscribe(
		siteId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/subscribe',
			path: {
				siteId: siteId,
			},
		});
	}

	/**
	 * Retrieves the site's blog post by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_BlogPosting default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteBlogPostingByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPosting> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
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
	 * Updates the site's blog post with the given external reference code, or creates it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_BlogPosting default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteBlogPostingByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_BlogPosting
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPosting> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the blog post by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteBlogPostingByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param siteId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteBlogPostingsPageExportBatch(
		siteId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/export-batch',
			path: {
				siteId: siteId,
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
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteBlogPostingBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteBlogPostingPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/permissions',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteBlogPostingPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteBlogPostingUnsubscribe(
		siteId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-postings/unsubscribe',
			path: {
				siteId: siteId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutBlogPostingBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/blog-postings/batch',
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
	public static headlessDeliveryV10DeleteBlogPostingBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/blog-postings/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param blogPostingId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetBlogPostingPermissionsPage(
		blogPostingId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/permissions',
			path: {
				blogPostingId: blogPostingId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param blogPostingId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutBlogPostingPermissionsPage(
		blogPostingId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/blog-postings/{blogPostingId}/permissions',
			path: {
				blogPostingId: blogPostingId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
