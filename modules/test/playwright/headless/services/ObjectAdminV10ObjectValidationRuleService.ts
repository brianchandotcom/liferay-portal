/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectValidationRule} from '../models/ObjectAdmin_v1_0_ObjectValidationRule';
import type {ObjectAdmin_v1_0_PageObjectValidationRule} from '../models/ObjectAdmin_v1_0_PageObjectValidationRule';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectValidationRuleService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectValidationRule default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectValidationRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-validation-rules',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectValidationRule default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionByExternalReferenceCodeObjectValidationRule(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectValidationRule
	): CancelablePromise<ObjectAdmin_v1_0_ObjectValidationRule> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-validation-rules',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	public static objectAdminV10PutObjectValidationRuleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-validation-rules/batch',
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
	public static objectAdminV10DeleteObjectValidationRuleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-validation-rules/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectValidationRuleId
	 * @returns ObjectAdmin_v1_0_ObjectValidationRule default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectValidationRule(
		objectValidationRuleId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectValidationRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
			path: {
				objectValidationRuleId: objectValidationRuleId,
			},
		});
	}

	/**
	 * @param objectValidationRuleId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectValidationRule default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectValidationRule(
		objectValidationRuleId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectValidationRule
	): CancelablePromise<ObjectAdmin_v1_0_ObjectValidationRule> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
			path: {
				objectValidationRuleId: objectValidationRuleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectValidationRuleId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectValidationRule(
		objectValidationRuleId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
			path: {
				objectValidationRuleId: objectValidationRuleId,
			},
		});
	}

	/**
	 * @param objectValidationRuleId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectValidationRule default response
	 * @throws ApiError
	 */
	public static objectAdminV10PatchObjectValidationRule(
		objectValidationRuleId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectValidationRule
	): CancelablePromise<ObjectAdmin_v1_0_ObjectValidationRule> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
			path: {
				objectValidationRuleId: objectValidationRuleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectValidationRulesPageExportBatch(
		objectDefinitionId: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules/export-batch',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			query: {
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectValidationRule default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionObjectValidationRulesPage(
		objectDefinitionId: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectValidationRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectValidationRule default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectValidationRule(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectValidationRule
	): CancelablePromise<ObjectAdmin_v1_0_ObjectValidationRule> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectValidationRuleBatch(
		objectDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules/batch',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
