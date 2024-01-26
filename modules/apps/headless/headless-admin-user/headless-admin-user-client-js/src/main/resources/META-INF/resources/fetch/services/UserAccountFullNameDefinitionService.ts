/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UserAccountFullNameDefinition } from '../models/UserAccountFullNameDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class UserAccountFullNameDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the user account full name definition.
     * @returns UserAccountFullNameDefinition
     * @throws ApiError
     */
    public getHeadlessAdminUserV10UserAccountFullNameDefinition({
        languageId,
    }: {
        languageId?: string,
    }): CancelablePromise<UserAccountFullNameDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-account-full-name-definition',
            query: {
                'languageId': languageId,
            },
        });
    }
}
