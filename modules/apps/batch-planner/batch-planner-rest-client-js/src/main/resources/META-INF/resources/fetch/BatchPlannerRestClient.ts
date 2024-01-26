/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { FieldService } from './services/FieldService';
import { PlanService } from './services/PlanService';
import { SiteScopeService } from './services/SiteScopeService';
import { StrategyService } from './services/StrategyService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class BatchPlannerRestClient {
    public readonly field: FieldService;
    public readonly plan: PlanService;
    public readonly siteScope: SiteScopeService;
    public readonly strategy: StrategyService;
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
        this.field = new FieldService(this.request);
        this.plan = new PlanService(this.request);
        this.siteScope = new SiteScopeService(this.request);
        this.strategy = new StrategyService(this.request);
    }
}

