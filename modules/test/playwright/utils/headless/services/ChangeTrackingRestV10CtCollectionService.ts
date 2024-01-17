/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTCollection} from '../models/ChangeTrackingRest_v1_0_CTCollection';
import type {ChangeTrackingRest_v1_0_PageCTCollection} from '../models/ChangeTrackingRest_v1_0_PageCTCollection';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ChangeTrackingRestV10CtCollectionService {

	/**
	 * @param ctCollectionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PutCtCollectionBatch(
		ctCollectionId: string,
		callbackUrl?: string,
		requestBody?: ChangeTrackingRest_v1_0_CTCollection
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/batch',
			path: {
				ctCollectionId: ctCollectionId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param ctCollectionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10DeleteCtCollectionBatch(
		ctCollectionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/batch',
			path: {
				ctCollectionId: ctCollectionId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param status
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ChangeTrackingRest_v1_0_PageCTCollection default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtCollectionsPage(
		status?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ChangeTrackingRest_v1_0_PageCTCollection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-collections',
			query: {
				status: status,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns ChangeTrackingRest_v1_0_CTCollection default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollection(
		requestBody?: ChangeTrackingRest_v1_0_CTCollection
	): CancelablePromise<ChangeTrackingRest_v1_0_CTCollection> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param publishDate
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollectionByExternalReferenceCodeSchedulePublish(
		externalReferenceCode: string,
		publishDate?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/schedule-publish',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				publishDate: publishDate,
			},
		});
	}

	/**
	 * @param ctCollectionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollectionPublish(
		ctCollectionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/publish',
			path: {
				ctCollectionId: ctCollectionId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollectionByExternalReferenceCodePublish(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/publish',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param ctCollectionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollectionCheckout(
		ctCollectionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/checkout',
			path: {
				ctCollectionId: ctCollectionId,
			},
		});
	}

	/**
	 * @param ctCollectionId
	 * @returns ChangeTrackingRest_v1_0_CTCollection default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtCollection(
		ctCollectionId: string
	): CancelablePromise<ChangeTrackingRest_v1_0_CTCollection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
			path: {
				ctCollectionId: ctCollectionId,
			},
		});
	}

	/**
	 * @param ctCollectionId
	 * @param requestBody
	 * @returns ChangeTrackingRest_v1_0_CTCollection default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PutCtCollection(
		ctCollectionId: string,
		requestBody?: ChangeTrackingRest_v1_0_CTCollection
	): CancelablePromise<ChangeTrackingRest_v1_0_CTCollection> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
			path: {
				ctCollectionId: ctCollectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param ctCollectionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10DeleteCtCollection(
		ctCollectionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
			path: {
				ctCollectionId: ctCollectionId,
			},
		});
	}

	/**
	 * @param ctCollectionId
	 * @param requestBody
	 * @returns ChangeTrackingRest_v1_0_CTCollection default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PatchCtCollection(
		ctCollectionId: string,
		requestBody?: ChangeTrackingRest_v1_0_CTCollection
	): CancelablePromise<ChangeTrackingRest_v1_0_CTCollection> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
			path: {
				ctCollectionId: ctCollectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param ctCollectionId
	 * @returns string default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtCollectionShareLink(
		ctCollectionId: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-collections/b{ctCollectionId}/share-link',
			path: {
				ctCollectionId: ctCollectionId,
			},
		});
	}

	/**
	 * @param ctCollectionId
	 * @param publishDate
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollectionSchedulePublish(
		ctCollectionId: string,
		publishDate?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/schedule-publish',
			path: {
				ctCollectionId: ctCollectionId,
			},
			query: {
				publishDate: publishDate,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns ChangeTrackingRest_v1_0_CTCollection default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtCollectionByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<ChangeTrackingRest_v1_0_CTCollection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10DeleteCtCollectionByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns ChangeTrackingRest_v1_0_CTCollection default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PatchCtCollectionByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: ChangeTrackingRest_v1_0_CTCollection
	): CancelablePromise<ChangeTrackingRest_v1_0_CTCollection> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns string default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtCollectionByExternalReferenceCodeShareLink(
		externalReferenceCode: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/share-link',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollectionBatch(
		callbackUrl?: string,
		requestBody?: ChangeTrackingRest_v1_0_CTCollection
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param status
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtCollectionsPageExportBatch(
		status?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-collections/export-batch',
			query: {
				status: status,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
