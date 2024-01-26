/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { FormService } from './services/FormService';
import { FormDocumentService } from './services/FormDocumentService';
import { FormRecordService } from './services/FormRecordService';
import { FormStructureService } from './services/FormStructureService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessFormClient {
    public readonly form: FormService;
    public readonly formDocument: FormDocumentService;
    public readonly formRecord: FormRecordService;
    public readonly formStructure: FormStructureService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = NodeHttpRequest) {
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
        this.form = new FormService(this.request);
        this.formDocument = new FormDocumentService(this.request);
        this.formRecord = new FormRecordService(this.request);
        this.formStructure = new FormStructureService(this.request);
    }
}

