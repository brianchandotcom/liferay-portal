/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { DisplayPageTemplateService } from './services/DisplayPageTemplateService';
import { PageDefinitionService } from './services/PageDefinitionService';
import { StructuredContentService } from './services/StructuredContentService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessAdminContentClient {
    public readonly displayPageTemplate: DisplayPageTemplateService;
    public readonly pageDefinition: PageDefinitionService;
    public readonly structuredContent: StructuredContentService;
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
        this.displayPageTemplate = new DisplayPageTemplateService(this.request);
        this.pageDefinition = new PageDefinitionService(this.request);
        this.structuredContent = new StructuredContentService(this.request);
    }
}

