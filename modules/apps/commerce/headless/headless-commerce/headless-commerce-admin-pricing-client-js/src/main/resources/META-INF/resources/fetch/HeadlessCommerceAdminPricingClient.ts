/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { DiscountService } from './services/DiscountService';
import { DiscountAccountGroupService } from './services/DiscountAccountGroupService';
import { DiscountCategoryService } from './services/DiscountCategoryService';
import { DiscountProductService } from './services/DiscountProductService';
import { DiscountRuleService } from './services/DiscountRuleService';
import { PriceEntryService } from './services/PriceEntryService';
import { PriceListService } from './services/PriceListService';
import { PriceListAccountGroupService } from './services/PriceListAccountGroupService';
import { TierPriceService } from './services/TierPriceService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceAdminPricingClient {
    public readonly discount: DiscountService;
    public readonly discountAccountGroup: DiscountAccountGroupService;
    public readonly discountCategory: DiscountCategoryService;
    public readonly discountProduct: DiscountProductService;
    public readonly discountRule: DiscountRuleService;
    public readonly priceEntry: PriceEntryService;
    public readonly priceList: PriceListService;
    public readonly priceListAccountGroup: PriceListAccountGroupService;
    public readonly tierPrice: TierPriceService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = FetchHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://commerce.api.liferay.com/commerce-admin-pricing/v1.0',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.discount = new DiscountService(this.request);
        this.discountAccountGroup = new DiscountAccountGroupService(this.request);
        this.discountCategory = new DiscountCategoryService(this.request);
        this.discountProduct = new DiscountProductService(this.request);
        this.discountRule = new DiscountRuleService(this.request);
        this.priceEntry = new PriceEntryService(this.request);
        this.priceList = new PriceListService(this.request);
        this.priceListAccountGroup = new PriceListAccountGroupService(this.request);
        this.tierPrice = new TierPriceService(this.request);
    }
}

