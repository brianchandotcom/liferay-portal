/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MeasurementUnit } from '../models/MeasurementUnit';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MeasurementUnitService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a List of Measurement Unit.
     * @returns MeasurementUnit Successful operation
     * @throws ApiError
     */
    public getMeasurementUnitsPage({
        filter,
        page,
        pageSize,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<MeasurementUnit>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a Measurement Unit.
     * @returns MeasurementUnit Created
     * @returns any Async
     * @throws ApiError
     */
    public postMeasurementUnit({
        requestBody,
    }: {
        requestBody: MeasurementUnit,
    }): CancelablePromise<MeasurementUnit | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Deletes a Measurement Unit by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteMeasurementUnitByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Measurement Unit by external reference code.
     * @returns MeasurementUnit Successful operation
     * @throws ApiError
     */
    public getMeasurementUnitByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<MeasurementUnit> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a Measurement Unit by external reference code.
     * @returns any Async
     * @throws ApiError
     */
    public patchMeasurementUnitByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: MeasurementUnit,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Deletes a Measurement Unit by key.
     * @returns void
     * @throws ApiError
     */
    public deleteMeasurementUnitByKey({
        key,
    }: {
        key: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-key/{key}',
            path: {
                'key': key,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Measurement Unit by key.
     * @returns MeasurementUnit Successful operation
     * @throws ApiError
     */
    public getMeasurementUnitByKey({
        key,
    }: {
        key: string,
    }): CancelablePromise<MeasurementUnit> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-key/{key}',
            path: {
                'key': key,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a Measurement Unit by key.
     * @returns any Async
     * @throws ApiError
     */
    public patchMeasurementUnitByKey({
        key,
        requestBody,
    }: {
        key: string,
        requestBody: MeasurementUnit,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-key/{key}',
            path: {
                'key': key,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Gets a List of Measurement Unit by type.
     * @returns MeasurementUnit Successful operation
     * @throws ApiError
     */
    public getMeasurementUnitsByType({
        measurementUnitType,
        page,
        pageSize,
        sort,
    }: {
        measurementUnitType: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<MeasurementUnit>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-type/{measurementUnitType}',
            path: {
                'measurementUnitType': measurementUnitType,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Deletes a Measurement Unit by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteMeasurementUnit({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Measurement Unit by ID.
     * @returns MeasurementUnit Successful operation
     * @throws ApiError
     */
    public getMeasurementUnit({
        id,
    }: {
        id: number,
    }): CancelablePromise<MeasurementUnit> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/{id}',
            path: {
                'id': id,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a Measurement Unit by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchMeasurementUnit({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: MeasurementUnit,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
