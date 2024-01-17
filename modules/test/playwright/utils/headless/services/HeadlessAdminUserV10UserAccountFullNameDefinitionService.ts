/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_UserAccountFullNameDefinition} from '../models/HeadlessAdminUser_v1_0_UserAccountFullNameDefinition';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10UserAccountFullNameDefinitionService {

	/**
	 * Retrieves the user account full name definition.
	 * @param languageId
	 * @returns HeadlessAdminUser_v1_0_UserAccountFullNameDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountFullNameDefinition(
		languageId?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccountFullNameDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-account-full-name-definition',
			query: {
				languageId: languageId,
			},
		});
	}
}
