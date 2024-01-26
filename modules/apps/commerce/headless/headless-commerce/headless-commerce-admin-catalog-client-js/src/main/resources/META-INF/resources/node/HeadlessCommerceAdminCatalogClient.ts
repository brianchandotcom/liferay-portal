/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { AttachmentService } from './services/AttachmentService';
import { CatalogService } from './services/CatalogService';
import { CategoryService } from './services/CategoryService';
import { CurrencyService } from './services/CurrencyService';
import { DiagramService } from './services/DiagramService';
import { GroupedProductService } from './services/GroupedProductService';
import { LinkedProductService } from './services/LinkedProductService';
import { LowStockActionService } from './services/LowStockActionService';
import { MappedProductService } from './services/MappedProductService';
import { OptionService } from './services/OptionService';
import { OptionCategoryService } from './services/OptionCategoryService';
import { OptionValueService } from './services/OptionValueService';
import { PinService } from './services/PinService';
import { ProductService } from './services/ProductService';
import { ProductAccountGroupService } from './services/ProductAccountGroupService';
import { ProductChannelService } from './services/ProductChannelService';
import { ProductConfigurationService } from './services/ProductConfigurationService';
import { ProductGroupService } from './services/ProductGroupService';
import { ProductGroupProductService } from './services/ProductGroupProductService';
import { ProductOptionService } from './services/ProductOptionService';
import { ProductOptionValueService } from './services/ProductOptionValueService';
import { ProductShippingConfigurationService } from './services/ProductShippingConfigurationService';
import { ProductSpecificationService } from './services/ProductSpecificationService';
import { ProductSubscriptionConfigurationService } from './services/ProductSubscriptionConfigurationService';
import { ProductTaxConfigurationService } from './services/ProductTaxConfigurationService';
import { ProductVirtualSettingsService } from './services/ProductVirtualSettingsService';
import { RelatedProductService } from './services/RelatedProductService';
import { SkuService } from './services/SkuService';
import { SkuSubscriptionConfigurationService } from './services/SkuSubscriptionConfigurationService';
import { SkuUnitOfMeasureService } from './services/SkuUnitOfMeasureService';
import { SkuVirtualSettingsService } from './services/SkuVirtualSettingsService';
import { SpecificationService } from './services/SpecificationService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceAdminCatalogClient {
    public readonly attachment: AttachmentService;
    public readonly catalog: CatalogService;
    public readonly category: CategoryService;
    public readonly currency: CurrencyService;
    public readonly diagram: DiagramService;
    public readonly groupedProduct: GroupedProductService;
    public readonly linkedProduct: LinkedProductService;
    public readonly lowStockAction: LowStockActionService;
    public readonly mappedProduct: MappedProductService;
    public readonly option: OptionService;
    public readonly optionCategory: OptionCategoryService;
    public readonly optionValue: OptionValueService;
    public readonly pin: PinService;
    public readonly product: ProductService;
    public readonly productAccountGroup: ProductAccountGroupService;
    public readonly productChannel: ProductChannelService;
    public readonly productConfiguration: ProductConfigurationService;
    public readonly productGroup: ProductGroupService;
    public readonly productGroupProduct: ProductGroupProductService;
    public readonly productOption: ProductOptionService;
    public readonly productOptionValue: ProductOptionValueService;
    public readonly productShippingConfiguration: ProductShippingConfigurationService;
    public readonly productSpecification: ProductSpecificationService;
    public readonly productSubscriptionConfiguration: ProductSubscriptionConfigurationService;
    public readonly productTaxConfiguration: ProductTaxConfigurationService;
    public readonly productVirtualSettings: ProductVirtualSettingsService;
    public readonly relatedProduct: RelatedProductService;
    public readonly sku: SkuService;
    public readonly skuSubscriptionConfiguration: SkuSubscriptionConfigurationService;
    public readonly skuUnitOfMeasure: SkuUnitOfMeasureService;
    public readonly skuVirtualSettings: SkuVirtualSettingsService;
    public readonly specification: SpecificationService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = NodeHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://virtserver.swaggerhub.com/liferayinc/commerce-admin-catalog/v1.0',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.attachment = new AttachmentService(this.request);
        this.catalog = new CatalogService(this.request);
        this.category = new CategoryService(this.request);
        this.currency = new CurrencyService(this.request);
        this.diagram = new DiagramService(this.request);
        this.groupedProduct = new GroupedProductService(this.request);
        this.linkedProduct = new LinkedProductService(this.request);
        this.lowStockAction = new LowStockActionService(this.request);
        this.mappedProduct = new MappedProductService(this.request);
        this.option = new OptionService(this.request);
        this.optionCategory = new OptionCategoryService(this.request);
        this.optionValue = new OptionValueService(this.request);
        this.pin = new PinService(this.request);
        this.product = new ProductService(this.request);
        this.productAccountGroup = new ProductAccountGroupService(this.request);
        this.productChannel = new ProductChannelService(this.request);
        this.productConfiguration = new ProductConfigurationService(this.request);
        this.productGroup = new ProductGroupService(this.request);
        this.productGroupProduct = new ProductGroupProductService(this.request);
        this.productOption = new ProductOptionService(this.request);
        this.productOptionValue = new ProductOptionValueService(this.request);
        this.productShippingConfiguration = new ProductShippingConfigurationService(this.request);
        this.productSpecification = new ProductSpecificationService(this.request);
        this.productSubscriptionConfiguration = new ProductSubscriptionConfigurationService(this.request);
        this.productTaxConfiguration = new ProductTaxConfigurationService(this.request);
        this.productVirtualSettings = new ProductVirtualSettingsService(this.request);
        this.relatedProduct = new RelatedProductService(this.request);
        this.sku = new SkuService(this.request);
        this.skuSubscriptionConfiguration = new SkuSubscriptionConfigurationService(this.request);
        this.skuUnitOfMeasure = new SkuUnitOfMeasureService(this.request);
        this.skuVirtualSettings = new SkuVirtualSettingsService(this.request);
        this.specification = new SpecificationService(this.request);
    }
}

