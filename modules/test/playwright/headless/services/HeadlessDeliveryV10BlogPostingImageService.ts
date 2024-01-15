/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_BlogPostingImage} from '../models/HeadlessDelivery_v1_0_BlogPostingImage';
import type {HeadlessDelivery_v1_0_MultipartBody} from '../models/HeadlessDelivery_v1_0_MultipartBody';
import type {HeadlessDelivery_v1_0_PageBlogPostingImage} from '../models/HeadlessDelivery_v1_0_PageBlogPostingImage';
import type {HeadlessDelivery_v1_0_PostSiteBlogPostingImageRequestBody} from '../models/HeadlessDelivery_v1_0_PostSiteBlogPostingImageRequestBody';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10BlogPostingImageService {

	/**
	 * Retrieves the blog post's image. The binary image is returned as a relative URL to the image itself.
	 * @param blogPostingImageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_BlogPostingImage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetBlogPostingImage(
		blogPostingImageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPostingImage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/blog-posting-images/{blogPostingImageId}',
			path: {
				blogPostingImageId: blogPostingImageId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Deletes the blog post's image.
	 * @param blogPostingImageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteBlogPostingImage(
		blogPostingImageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/blog-posting-images/{blogPostingImageId}',
			path: {
				blogPostingImageId: blogPostingImageId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteBlogPostingImageBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/blog-posting-images/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the site's blog post images. Results can be paginated, filtered, searched, and sorted.
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
	 * @returns HeadlessDelivery_v1_0_PageBlogPostingImage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteBlogPostingImagesPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageBlogPostingImage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-posting-images',
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
	 * Creates a blog post image. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`blogPostingImage`) with the metadata.
	 * @param siteId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_BlogPostingImage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteBlogPostingImage(
		siteId: string,
		formData?: HeadlessDelivery_v1_0_PostSiteBlogPostingImageRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_BlogPostingImage> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-posting-images',
			path: {
				siteId: siteId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteBlogPostingImageBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-posting-images/batch',
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
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteBlogPostingImagesPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/blog-posting-images/export-batch',
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
}
