/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountMember } from '../models/AccountMember';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountMemberService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Members related to an Account.
     * @returns AccountMember Successful operation
     * @throws ApiError
     */
    public getAccountByExternalReferenceCodeAccountMembersPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountMember>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
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
     * Creates an Account Member.
     * @returns AccountMember Created
     * @returns any Async
     * @throws ApiError
     */
    public postAccountByExternalReferenceCodeAccountMember({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountMember,
    }): CancelablePromise<AccountMember | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers',
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
     * Deletes an Account Member by Account external reference code and User ID.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountByExternalReferenceCodeAccountMember({
        externalReferenceCode,
        userId,
    }: {
        externalReferenceCode: string,
        userId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers/{userId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'userId': userId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Account Member by Account external reference code and User ID.
     * @returns AccountMember Successful operation
     * @throws ApiError
     */
    public getAccountByExternalReferenceCodeAccountMember({
        externalReferenceCode,
        userId,
    }: {
        externalReferenceCode: string,
        userId: number,
    }): CancelablePromise<AccountMember> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers/{userId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'userId': userId,
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
     * Updates an Account Member by Account external reference code and User ID.
     * @returns any Created
     * @throws ApiError
     */
    public patchAccountByExternalReferenceCodeAccountMember({
        externalReferenceCode,
        userId,
        requestBody,
    }: {
        externalReferenceCode: string,
        userId: number,
        requestBody: AccountMember,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers/{userId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'userId': userId,
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
     * Gets a list of Members related to an Account.
     * @returns AccountMember Successful operation
     * @throws ApiError
     */
    public getAccountIdAccountMembersPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountMember>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers',
            path: {
                'id': id,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
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
     * Creates an Account Member.
     * @returns AccountMember Created
     * @returns any Async
     * @throws ApiError
     */
    public postAccountIdAccountMember({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountMember,
    }): CancelablePromise<AccountMember | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers',
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
    /**
     * Deletes an Account Member by Account ID and User ID.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountIdAccountMember({
        id,
        userId,
    }: {
        id: number,
        userId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers/{userId}',
            path: {
                'id': id,
                'userId': userId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Account Member by Account ID and User ID.
     * @returns AccountMember Successful operation
     * @throws ApiError
     */
    public getAccountIdAccountMember({
        id,
        userId,
    }: {
        id: number,
        userId: number,
    }): CancelablePromise<AccountMember> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers/{userId}',
            path: {
                'id': id,
                'userId': userId,
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
     * Updates an Account Member by Account ID and User ID.
     * @returns any Created
     * @throws ApiError
     */
    public patchAccountIdAccountMember({
        id,
        userId,
        requestBody,
    }: {
        id: number,
        userId: number,
        requestBody: AccountMember,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers/{userId}',
            path: {
                'id': id,
                'userId': userId,
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
