/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TestEntityAddress } from '../models/TestEntityAddress';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TestEntityAddressService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns TestEntityAddress OK
     * @throws ApiError
     */
    public getTestEntityTestEntityAddress({
        testEntityId,
    }: {
        testEntityId: number,
    }): CancelablePromise<TestEntityAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/test/v1.0/test-entities/{testEntityId}/test-entity-address',
            path: {
                'testEntityId': testEntityId,
            },
        });
    }
}
