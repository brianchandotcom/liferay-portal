/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ContactUserGroup } from '../models/ContactUserGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ContactUserGroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ContactUserGroup
     * @throws ApiError
     */
    public getContactUserGroupsPage({
        keywords,
        page,
        pageSize,
        sort,
    }: {
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<ContactUserGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/contacts/user-groups',
            query: {
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
}
