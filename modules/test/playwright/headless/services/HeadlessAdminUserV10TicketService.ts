/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Ticket} from '../models/HeadlessAdminUser_v1_0_Ticket';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10TicketService {

	/**
	 * Retrieves the user's password reset ticket.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_Ticket default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountPasswordResetTicket(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Ticket> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/password-reset-ticket',
			path: {
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Retrieves the user's email verification ticket.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_Ticket default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountEmailVerificationTicket(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Ticket> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/email-verification-ticket',
			path: {
				userAccountId: userAccountId,
			},
		});
	}
}
