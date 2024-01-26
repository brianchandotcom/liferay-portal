/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NotificationTemplate } from '../models/NotificationTemplate';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class NotificationTemplateService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public getNotificationTemplatesPage({
        aggregationTerms,
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        aggregationTerms?: Array<string>,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<NotificationTemplate>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/notification/v1.0/notification-templates',
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public postNotificationTemplate({
        requestBody,
    }: {
        requestBody?: NotificationTemplate,
    }): CancelablePromise<NotificationTemplate> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/notification/v1.0/notification-templates',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public getNotificationTemplateByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<NotificationTemplate> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/notification/v1.0/notification-templates/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public putNotificationTemplateByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: NotificationTemplate,
    }): CancelablePromise<NotificationTemplate> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/notification/v1.0/notification-templates/by-external-reference-code/{externalReferenceCode}',
            path: {
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
    public deleteNotificationTemplate({
        notificationTemplateId,
    }: {
        notificationTemplateId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
            path: {
                'notificationTemplateId': notificationTemplateId,
            },
        });
    }
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public getNotificationTemplate({
        notificationTemplateId,
    }: {
        notificationTemplateId: number,
    }): CancelablePromise<NotificationTemplate> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
            path: {
                'notificationTemplateId': notificationTemplateId,
            },
        });
    }
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public patchNotificationTemplate({
        notificationTemplateId,
        requestBody,
    }: {
        notificationTemplateId: number,
        requestBody?: NotificationTemplate,
    }): CancelablePromise<NotificationTemplate> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
            path: {
                'notificationTemplateId': notificationTemplateId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public putNotificationTemplate({
        notificationTemplateId,
        requestBody,
    }: {
        notificationTemplateId: number,
        requestBody?: NotificationTemplate,
    }): CancelablePromise<NotificationTemplate> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
            path: {
                'notificationTemplateId': notificationTemplateId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns NotificationTemplate
     * @throws ApiError
     */
    public postNotificationTemplateCopy({
        notificationTemplateId,
    }: {
        notificationTemplateId: number,
    }): CancelablePromise<NotificationTemplate> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/notification/v1.0/notification-templates/{notificationTemplateId}/copy',
            path: {
                'notificationTemplateId': notificationTemplateId,
            },
        });
    }
}
