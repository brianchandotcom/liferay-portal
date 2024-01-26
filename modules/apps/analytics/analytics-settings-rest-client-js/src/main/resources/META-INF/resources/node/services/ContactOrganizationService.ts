/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ContactOrganization } from '../models/ContactOrganization';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ContactOrganizationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ContactOrganization
     * @throws ApiError
     */
    public getContactOrganizationsPage({
        keywords,
        page,
        pageSize,
        sort,
    }: {
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<ContactOrganization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/contacts/organizations',
            query: {
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
}
