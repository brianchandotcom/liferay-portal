/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Phone } from '../models/Phone';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PhoneService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the organization's phone numbers.
     * @returns Phone
     * @throws ApiError
     */
    public getOrganizationPhonesPage({
        organizationId,
    }: {
        organizationId: string,
    }): CancelablePromise<Array<Phone>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/phones',
            path: {
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Retrieves the phone number.
     * @returns Phone
     * @throws ApiError
     */
    public getPhone({
        phoneId,
    }: {
        phoneId: number,
    }): CancelablePromise<Phone> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/phones/{phoneId}',
            path: {
                'phoneId': phoneId,
            },
        });
    }
    /**
     * Retrieves the user's phone numbers.
     * @returns Phone
     * @throws ApiError
     */
    public getUserAccountPhonesPage({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<Array<Phone>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/phones',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
}
