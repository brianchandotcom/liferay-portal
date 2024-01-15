/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DigitalSignatureRest_v1_0_DSEnvelopeSignatureURL} from '../models/DigitalSignatureRest_v1_0_DSEnvelopeSignatureURL';
import type {DigitalSignatureRest_v1_0_DSRecipientViewDefinition} from '../models/DigitalSignatureRest_v1_0_DSRecipientViewDefinition';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DigitalSignatureRestV10DsRecipientViewDefinitionService {

	/**
	 * @param siteId
	 * @param dsEnvelopeId
	 * @param requestBody
	 * @returns DigitalSignatureRest_v1_0_DSEnvelopeSignatureURL default response
	 * @throws ApiError
	 */
	public static digitalSignatureRestV10PostSiteDsRecipientViewDefinition(
		siteId: string,
		dsEnvelopeId: string,
		requestBody?: DigitalSignatureRest_v1_0_DSRecipientViewDefinition
	): CancelablePromise<DigitalSignatureRest_v1_0_DSEnvelopeSignatureURL> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/digital-signature-rest/v1.0/sites/{siteId}/ds-recipient-view-definition/{dsEnvelopeId}',
			path: {
				siteId: siteId,
				dsEnvelopeId: dsEnvelopeId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
