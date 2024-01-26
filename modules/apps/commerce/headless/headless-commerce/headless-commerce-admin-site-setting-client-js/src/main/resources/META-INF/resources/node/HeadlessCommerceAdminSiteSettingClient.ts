/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { AvailabilityEstimateService } from './services/AvailabilityEstimateService';
import { MeasurementUnitService } from './services/MeasurementUnitService';
import { TaxCategoryService } from './services/TaxCategoryService';
import { WarehouseService } from './services/WarehouseService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceAdminSiteSettingClient {
    public readonly availabilityEstimate: AvailabilityEstimateService;
    public readonly measurementUnit: MeasurementUnitService;
    public readonly taxCategory: TaxCategoryService;
    public readonly warehouse: WarehouseService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = NodeHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://commerce.api.liferay.com/commerce-admin-site-setting/v1.0',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.availabilityEstimate = new AvailabilityEstimateService(this.request);
        this.measurementUnit = new MeasurementUnitService(this.request);
        this.taxCategory = new TaxCategoryService(this.request);
        this.warehouse = new WarehouseService(this.request);
    }
}

