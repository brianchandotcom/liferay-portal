/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectValidationRule } from '../models/ObjectValidationRule';
import type { PageObjectValidationRule } from '../models/PageObjectValidationRule';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ObjectValidationRuleService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns any
     * @throws ApiError
     */
    public getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage({
        externalReferenceCode,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-validation-rules',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns ObjectValidationRule
     * @throws ApiError
     */
    public postObjectDefinitionByExternalReferenceCodeObjectValidationRule({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectValidationRule,
    }): CancelablePromise<ObjectValidationRule> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-validation-rules',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns PageObjectValidationRule
     * @throws ApiError
     */
    public getObjectDefinitionObjectValidationRulesPage({
        objectDefinitionId,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        objectDefinitionId: number,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<PageObjectValidationRule> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns ObjectValidationRule
     * @throws ApiError
     */
    public postObjectDefinitionObjectValidationRule({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectValidationRule,
    }): CancelablePromise<ObjectValidationRule> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteObjectValidationRule({
        objectValidationRuleId,
    }: {
        objectValidationRuleId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
            path: {
                'objectValidationRuleId': objectValidationRuleId,
            },
        });
    }
    /**
     * @returns ObjectValidationRule
     * @throws ApiError
     */
    public getObjectValidationRule({
        objectValidationRuleId,
    }: {
        objectValidationRuleId: number,
    }): CancelablePromise<ObjectValidationRule> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
            path: {
                'objectValidationRuleId': objectValidationRuleId,
            },
        });
    }
    /**
     * @returns ObjectValidationRule
     * @throws ApiError
     */
    public patchObjectValidationRule({
        objectValidationRuleId,
        requestBody,
    }: {
        objectValidationRuleId: number,
        requestBody?: ObjectValidationRule,
    }): CancelablePromise<ObjectValidationRule> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
            path: {
                'objectValidationRuleId': objectValidationRuleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectValidationRule
     * @throws ApiError
     */
    public putObjectValidationRule({
        objectValidationRuleId,
        requestBody,
    }: {
        objectValidationRuleId: number,
        requestBody?: ObjectValidationRule,
    }): CancelablePromise<ObjectValidationRule> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}',
            path: {
                'objectValidationRuleId': objectValidationRuleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
