/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Payment } from '../models/Payment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PaymentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a List of Payments.
     * @returns Payment Successful operation
     * @throws ApiError
     */
    public getPaymentsPage({
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
    }): CancelablePromise<Array<Payment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-payment/v1.0/payments',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
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
     * Creates or updates a Payment.
     * @returns Payment Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postPayment({
        requestBody,
    }: {
        requestBody: Payment,
    }): CancelablePromise<Payment | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-payment/v1.0/payments',
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
     * Deletes an Payment by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deletePaymentByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Payment by external reference code.
     * @returns Payment Successful operation
     * @throws ApiError
     */
    public getPaymentByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Payment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates an Payment by external reference code.
     * @returns Payment Succesfully update the Payment
     * @throws ApiError
     */
    public patchPaymentByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Payment,
    }): CancelablePromise<Payment> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}',
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
     * Makes a refund from an existing payment.
     * @returns Payment Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postPaymentByExternalReferenceCodeRefund({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Payment | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}/refund',
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
     * Deletes an Payment by ID.
     * @returns void
     * @throws ApiError
     */
    public deletePayment({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-payment/v1.0/payments/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Payment by ID.
     * @returns Payment Successful operation
     * @throws ApiError
     */
    public getPayment({
        id,
    }: {
        id: number,
    }): CancelablePromise<Payment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-payment/v1.0/payments/{id}',
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
     * Updates an Payment by ID.
     * @returns Payment Succesfully update the Payment
     * @throws ApiError
     */
    public patchPayment({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Payment,
    }): CancelablePromise<Payment> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-payment/v1.0/payments/{id}',
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
     * Makes a refund from an existing payment.
     * @returns Payment Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postPaymentRefund({
        id,
    }: {
        id: number,
    }): CancelablePromise<Payment | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-payment/v1.0/payments/{id}/refund',
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
}
