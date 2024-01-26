/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { AddressService } from './services/AddressService';
import { CartService } from './services/CartService';
import { CartCommentService } from './services/CartCommentService';
import { CartItemService } from './services/CartItemService';
import { PaymentMethodService } from './services/PaymentMethodService';
import { ShippingMethodService } from './services/ShippingMethodService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceDeliveryCartClient {
    public readonly address: AddressService;
    public readonly cart: CartService;
    public readonly cartComment: CartCommentService;
    public readonly cartItem: CartItemService;
    public readonly paymentMethod: PaymentMethodService;
    public readonly shippingMethod: ShippingMethodService;
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
        this.address = new AddressService(this.request);
        this.cart = new CartService(this.request);
        this.cartComment = new CartCommentService(this.request);
        this.cartItem = new CartItemService(this.request);
        this.paymentMethod = new PaymentMethodService(this.request);
        this.shippingMethod = new ShippingMethodService(this.request);
    }
}

