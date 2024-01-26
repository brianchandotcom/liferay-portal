/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { DataDefinitionService } from './services/DataDefinitionService';
import { DataDefinitionFieldLinkService } from './services/DataDefinitionFieldLinkService';
import { DataLayoutService } from './services/DataLayoutService';
import { DataListViewService } from './services/DataListViewService';
import { DataRecordService } from './services/DataRecordService';
import { DataRecordCollectionService } from './services/DataRecordCollectionService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class DataEngineRestClient {
    public readonly dataDefinition: DataDefinitionService;
    public readonly dataDefinitionFieldLink: DataDefinitionFieldLinkService;
    public readonly dataLayout: DataLayoutService;
    public readonly dataListView: DataListViewService;
    public readonly dataRecord: DataRecordService;
    public readonly dataRecordCollection: DataRecordCollectionService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = NodeHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? '',
            VERSION: config?.VERSION ?? '2.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.dataDefinition = new DataDefinitionService(this.request);
        this.dataDefinitionFieldLink = new DataDefinitionFieldLinkService(this.request);
        this.dataLayout = new DataLayoutService(this.request);
        this.dataListView = new DataListViewService(this.request);
        this.dataRecord = new DataRecordService(this.request);
        this.dataRecordCollection = new DataRecordCollectionService(this.request);
    }
}

