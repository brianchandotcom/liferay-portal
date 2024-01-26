/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DSEnvelopeSignatureURL } from '../models/DSEnvelopeSignatureURL';
import type { DSRecipientViewDefinition } from '../models/DSRecipientViewDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DsRecipientViewDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns DSEnvelopeSignatureURL
     * @throws ApiError
     */
    public postSiteDsRecipientViewDefinition({
        siteId,
        dsEnvelopeId,
        requestBody,
    }: {
        siteId: number,
        dsEnvelopeId: string,
        requestBody?: DSRecipientViewDefinition,
    }): CancelablePromise<DSEnvelopeSignatureURL> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/digital-signature-rest/v1.0/sites/{siteId}/ds-recipient-view-definition/{dsEnvelopeId}',
            path: {
                'siteId': siteId,
                'dsEnvelopeId': dsEnvelopeId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
