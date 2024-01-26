/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Image } from '../models/Image';
import type { UserAccount } from '../models/UserAccount';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class UserAccountService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Removes a user by their external reference code from an account by external reference code
     * @returns void
     * @throws ApiError
     */
    public deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode({
        accountExternalReferenceCode,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Gets a user by their external reference code to an account by external reference code
     * @returns UserAccount
     * @throws ApiError
     */
    public getAccountByExternalReferenceCodeUserAccountByExternalReferenceCode({
        accountExternalReferenceCode,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Assigns a user by their external reference code to an account by external reference code
     * @returns void
     * @throws ApiError
     */
    public postAccountByExternalReferenceCodeUserAccountByExternalReferenceCode({
        accountExternalReferenceCode,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Gets the users assigned to an account
     * @returns UserAccount
     * @throws ApiError
     */
    public getAccountUserAccountsByExternalReferenceCodePage({
        externalReferenceCode,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        externalReferenceCode: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Creates a user and assigns them to the account
     * @returns UserAccount
     * @throws ApiError
     */
    public postAccountUserAccountByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: UserAccount,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Removes users from an account by their email addresses
     * @returns void
     * @throws ApiError
     */
    public deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        /**
         * A list of user email addresses to remove from the account
         */
        requestBody: Array<string>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Assigns users to an account by their email addresses
     * @returns void
     * @throws ApiError
     */
    public postAccountUserAccountsByExternalReferenceCodeByEmailAddress({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        /**
         * A list of user email addresses to assign to the account
         */
        requestBody: Array<string>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Removes a user from an account by external reference code by their email address
     * @returns void
     * @throws ApiError
     */
    public deleteAccountUserAccountByExternalReferenceCodeByEmailAddress({
        externalReferenceCode,
        emailAddress,
    }: {
        externalReferenceCode: string,
        emailAddress: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Assigns a user to an account by external reference code by their email address
     * @returns void
     * @throws ApiError
     */
    public postAccountUserAccountByExternalReferenceCodeByEmailAddress({
        externalReferenceCode,
        emailAddress,
    }: {
        externalReferenceCode: string,
        emailAddress: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Gets the users assigned to an account
     * @returns UserAccount
     * @throws ApiError
     */
    public getAccountUserAccountsPage({
        accountId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        accountId: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts',
            path: {
                'accountId': accountId,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Creates a user and assigns them to the account
     * @returns UserAccount
     * @throws ApiError
     */
    public postAccountUserAccount({
        accountId,
        requestBody,
    }: {
        accountId: number,
        requestBody?: UserAccount,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts',
            path: {
                'accountId': accountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Removes users from an account by their email addresses
     * @returns void
     * @throws ApiError
     */
    public deleteAccountUserAccountsByEmailAddress({
        accountId,
        requestBody,
    }: {
        accountId: number,
        /**
         * A list of user email addresses to remove from the account
         */
        requestBody: Array<string>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address',
            path: {
                'accountId': accountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Assigns users to an account by their email addresses
     * @returns UserAccount
     * @throws ApiError
     */
    public postAccountUserAccountsByEmailAddress({
        accountId,
        requestBody,
        accountRoleIds,
    }: {
        accountId: number,
        /**
         * A list of user email addresses to assign to the account
         */
        requestBody: Array<string>,
        accountRoleIds?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address',
            path: {
                'accountId': accountId,
            },
            query: {
                'accountRoleIds': accountRoleIds,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Removes a user from an account by their email address
     * @returns void
     * @throws ApiError
     */
    public deleteAccountUserAccountByEmailAddress({
        accountId,
        emailAddress,
    }: {
        accountId: number,
        emailAddress: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'accountId': accountId,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Assigns a user to an account by their email address
     * @returns UserAccount
     * @throws ApiError
     */
    public postAccountUserAccountByEmailAddress({
        accountId,
        emailAddress,
    }: {
        accountId: number,
        emailAddress: string,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'accountId': accountId,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Removes a user assigned to an account
     * @returns void
     * @throws ApiError
     */
    public deleteAccountUserAccount({
        accountId,
        userAccountId,
    }: {
        accountId: number,
        userAccountId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/{userAccountId}',
            path: {
                'accountId': accountId,
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Gets a user assigned to an account
     * @returns UserAccount
     * @throws ApiError
     */
    public getAccountUserAccount({
        accountId,
        userAccountId,
    }: {
        accountId: number,
        userAccountId: number,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/{userAccountId}',
            path: {
                'accountId': accountId,
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Retrieves information about the user who made the request.
     * @returns UserAccount
     * @throws ApiError
     */
    public getMyUserAccount(): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/my-user-account',
        });
    }
    /**
     * Retrieves the organization's members (users). Results can be paginated, filtered, searched, and sorted.
     * @returns UserAccount
     * @throws ApiError
     */
    public getOrganizationUserAccountsPage({
        organizationId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        organizationId: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts',
            path: {
                'organizationId': organizationId,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Retrieves the site members' user accounts. Results can be paginated, filtered, searched, and sorted.
     * @returns UserAccount
     * @throws ApiError
     */
    public getSiteUserAccountsPage({
        siteId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        siteId: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/sites/{siteId}/user-accounts',
            path: {
                'siteId': siteId,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Retrieves the user accounts. Results can be paginated, filtered, searched, and sorted.
     * @returns UserAccount
     * @throws ApiError
     */
    public getUserAccountsPage({
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Creates a new user account
     * @returns UserAccount
     * @throws ApiError
     */
    public postUserAccount({
        requestBody,
    }: {
        requestBody?: UserAccount,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/user-accounts',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns UserAccount
     * @throws ApiError
     */
    public getUserAccountByEmailAddress({
        emailAddress,
    }: {
        emailAddress: string,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/by-email-address/{emailAddress}',
            path: {
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteUserAccountByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns UserAccount
     * @throws ApiError
     */
    public getUserAccountByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns UserAccount
     * @throws ApiError
     */
    public putUserAccountByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: UserAccount,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns UserAccount
     * @throws ApiError
     */
    public getUserAccountsByStatusPage({
        status,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        status: 'Active' | 'Inactive',
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/by-status/{status}',
            path: {
                'status': status,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Deletes the user account
     * @returns void
     * @throws ApiError
     */
    public deleteUserAccount({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Retrieves the user account.
     * @returns UserAccount
     * @throws ApiError
     */
    public getUserAccount({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Updates the user account with information sent in the request body. Only the provided fields are updated.
     * @returns UserAccount UserAccount successfully updated
     * @throws ApiError
     */
    public patchUserAccount({
        userAccountId,
        requestBody,
    }: {
        userAccountId: number,
        requestBody?: UserAccount,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
            path: {
                'userAccountId': userAccountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the user account with information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns UserAccount UserAccount successfully updated
     * @throws ApiError
     */
    public putUserAccount({
        userAccountId,
        requestBody,
    }: {
        userAccountId: number,
        requestBody?: UserAccount,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
            path: {
                'userAccountId': userAccountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any Created
     * @throws ApiError
     */
    public postHeadlessAdminUserV10UserAccountsImage({
        userAccountId,
        formData,
    }: {
        userAccountId: number,
        formData: Image,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/image',
            path: {
                'userAccountId': userAccountId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
            errors: {
                400: `Invalid input`,
            },
        });
    }
}
