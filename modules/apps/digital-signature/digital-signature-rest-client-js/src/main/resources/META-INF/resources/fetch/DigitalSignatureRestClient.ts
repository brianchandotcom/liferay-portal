/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { DsEnvelopeService } from './services/DsEnvelopeService';
import { DsRecipientViewDefinitionService } from './services/DsRecipientViewDefinitionService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class DigitalSignatureRestClient {
    public readonly dsEnvelope: DsEnvelopeService;
    public readonly dsRecipientViewDefinition: DsRecipientViewDefinitionService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = FetchHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? '',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.dsEnvelope = new DsEnvelopeService(this.request);
        this.dsRecipientViewDefinition = new DsRecipientViewDefinitionService(this.request);
    }
}

