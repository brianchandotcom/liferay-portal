/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { ExperimentService } from './services/ExperimentService';
import { ExperimentRunService } from './services/ExperimentRunService';
import { StatusService } from './services/StatusService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class SegmentsAsahRestClient {
    public readonly experiment: ExperimentService;
    public readonly experimentRun: ExperimentRunService;
    public readonly status: StatusService;
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
        this.experiment = new ExperimentService(this.request);
        this.experimentRun = new ExperimentRunService(this.request);
        this.status = new StatusService(this.request);
    }
}

