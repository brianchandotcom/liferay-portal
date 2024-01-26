/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SamlProvider } from '../models/SamlProvider';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SamlProviderService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the SAML Provider configuration.
     * @returns SamlProvider
     * @throws ApiError
     */
    public getSamlProvider(): CancelablePromise<SamlProvider> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/saml-admin/v1.0/saml-provider',
        });
    }
    /**
     * Patch the SAML Provider configuration.
     * @returns SamlProvider SAML provider configuration was successfully created
     * @throws ApiError
     */
    public patchSamlProvider({
        requestBody,
    }: {
        requestBody?: SamlProvider,
    }): CancelablePromise<SamlProvider> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/saml-admin/v1.0/saml-provider',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Creates a full SAML Provider configuration with peer connections.
     * @returns SamlProvider SAML provider configuration was successfully created
     * @throws ApiError
     */
    public postSamlProvider({
        requestBody,
    }: {
        requestBody?: SamlProvider,
    }): CancelablePromise<SamlProvider> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/saml-admin/v1.0/saml-provider',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
