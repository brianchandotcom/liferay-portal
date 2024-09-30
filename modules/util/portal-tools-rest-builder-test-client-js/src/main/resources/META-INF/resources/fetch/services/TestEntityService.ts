/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageTestEntity } from '../models/PageTestEntity';
import type { TestEntity } from '../models/TestEntity';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TestEntityService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns any OK
     * @throws ApiError
     */
    public postReservedWord({
        requestBody,
    }: {
        requestBody: boolean,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/test/v1.0/reserved-word',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns PageTestEntity OK
     * @throws ApiError
     */
    public getTestEntitiesPage(): CancelablePromise<PageTestEntity> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/test/v1.0/test-entities',
        });
    }
    /**
     * @returns TestEntity
     * @throws ApiError
     */
    public postTestEntity({
        requestBody,
    }: {
        requestBody?: TestEntity,
    }): CancelablePromise<TestEntity> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/test/v1.0/test-entities',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the count.
     * @returns number OK
     * @throws ApiError
     */
    public getTestEntityCount(): CancelablePromise<number> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/test/v1.0/test-entities/count',
        });
    }
    /**
     * @returns TestEntity OK
     * @throws ApiError
     */
    public getTestEntity({
        testEntityId,
    }: {
        testEntityId: number,
    }): CancelablePromise<TestEntity> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/test/v1.0/test-entities/{testEntityId}',
            path: {
                'testEntityId': testEntityId,
            },
        });
    }
    /**
     * @returns TestEntity OK
     * @throws ApiError
     */
    public patchTestEntity({
        testEntityId,
        optionalParameter,
        requestBody,
    }: {
        testEntityId: number,
        optionalParameter?: number,
        requestBody?: TestEntity,
    }): CancelablePromise<TestEntity> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/test/v1.0/test-entities/{testEntityId}',
            path: {
                'testEntityId': testEntityId,
            },
            query: {
                'optionalParameter': optionalParameter,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns TestEntity OK
     * @throws ApiError
     */
    public putTestEntity({
        testEntityId,
        optionalParameter,
        requestBody,
    }: {
        testEntityId: number,
        optionalParameter?: number,
        requestBody?: TestEntity,
    }): CancelablePromise<TestEntity> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/test/v1.0/test-entities/{testEntityId}',
            path: {
                'testEntityId': testEntityId,
            },
            query: {
                'optionalParameter': optionalParameter,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
