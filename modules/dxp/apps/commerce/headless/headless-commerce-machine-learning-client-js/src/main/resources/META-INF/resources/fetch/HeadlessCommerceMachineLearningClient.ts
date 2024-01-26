/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { AccountCategoryForecastService } from './services/AccountCategoryForecastService';
import { AccountForecastService } from './services/AccountForecastService';
import { SkuForecastService } from './services/SkuForecastService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceMachineLearningClient {
    public readonly accountCategoryForecast: AccountCategoryForecastService;
    public readonly accountForecast: AccountForecastService;
    public readonly skuForecast: SkuForecastService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = FetchHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://commerce.api.liferay.com/commerce-forecast/v1.0',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.accountCategoryForecast = new AccountCategoryForecastService(this.request);
        this.accountForecast = new AccountForecastService(this.request);
        this.skuForecast = new SkuForecastService(this.request);
    }
}

