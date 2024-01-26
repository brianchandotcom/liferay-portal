/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { AccountService } from './services/AccountService';
import { AttachmentService } from './services/AttachmentService';
import { CategoryService } from './services/CategoryService';
import { ChannelService } from './services/ChannelService';
import { LinkedProductService } from './services/LinkedProductService';
import { MappedProductService } from './services/MappedProductService';
import { PinService } from './services/PinService';
import { ProductService } from './services/ProductService';
import { ProductOptionService } from './services/ProductOptionService';
import { ProductOptionValueService } from './services/ProductOptionValueService';
import { ProductSpecificationService } from './services/ProductSpecificationService';
import { RelatedProductService } from './services/RelatedProductService';
import { SkuService } from './services/SkuService';
import { WishListService } from './services/WishListService';
import { WishListItemService } from './services/WishListItemService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceDeliveryCatalogClient {
    public readonly account: AccountService;
    public readonly attachment: AttachmentService;
    public readonly category: CategoryService;
    public readonly channel: ChannelService;
    public readonly linkedProduct: LinkedProductService;
    public readonly mappedProduct: MappedProductService;
    public readonly pin: PinService;
    public readonly product: ProductService;
    public readonly productOption: ProductOptionService;
    public readonly productOptionValue: ProductOptionValueService;
    public readonly productSpecification: ProductSpecificationService;
    public readonly relatedProduct: RelatedProductService;
    public readonly sku: SkuService;
    public readonly wishList: WishListService;
    public readonly wishListItem: WishListItemService;
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
        this.account = new AccountService(this.request);
        this.attachment = new AttachmentService(this.request);
        this.category = new CategoryService(this.request);
        this.channel = new ChannelService(this.request);
        this.linkedProduct = new LinkedProductService(this.request);
        this.mappedProduct = new MappedProductService(this.request);
        this.pin = new PinService(this.request);
        this.product = new ProductService(this.request);
        this.productOption = new ProductOptionService(this.request);
        this.productOptionValue = new ProductOptionValueService(this.request);
        this.productSpecification = new ProductSpecificationService(this.request);
        this.relatedProduct = new RelatedProductService(this.request);
        this.sku = new SkuService(this.request);
        this.wishList = new WishListService(this.request);
        this.wishListItem = new WishListItemService(this.request);
    }
}

