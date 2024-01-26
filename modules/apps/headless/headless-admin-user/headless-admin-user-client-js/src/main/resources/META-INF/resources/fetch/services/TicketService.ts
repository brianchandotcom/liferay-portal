/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Ticket } from '../models/Ticket';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TicketService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the user's email verification ticket.
     * @returns Ticket
     * @throws ApiError
     */
    public getUserAccountEmailVerificationTicket({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<Ticket> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/email-verification-ticket',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Retrieves the user's password reset ticket.
     * @returns Ticket
     * @throws ApiError
     */
    public getUserAccountPasswordResetTicket({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<Ticket> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/password-reset-ticket',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
}
