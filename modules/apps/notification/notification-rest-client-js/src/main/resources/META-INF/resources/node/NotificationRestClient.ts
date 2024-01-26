/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { NotificationQueueEntryService } from './services/NotificationQueueEntryService';
import { NotificationTemplateService } from './services/NotificationTemplateService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class NotificationRestClient {
    public readonly notificationQueueEntry: NotificationQueueEntryService;
    public readonly notificationTemplate: NotificationTemplateService;
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
        this.notificationQueueEntry = new NotificationQueueEntryService(this.request);
        this.notificationTemplate = new NotificationTemplateService(this.request);
    }
}

