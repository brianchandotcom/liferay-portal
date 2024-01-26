/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { PlacedOrderService } from './services/PlacedOrderService';
import { PlacedOrderAddressService } from './services/PlacedOrderAddressService';
import { PlacedOrderCommentService } from './services/PlacedOrderCommentService';
import { PlacedOrderItemService } from './services/PlacedOrderItemService';
import { PlacedOrderItemShipmentService } from './services/PlacedOrderItemShipmentService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceDeliveryOrderClient {
    public readonly placedOrder: PlacedOrderService;
    public readonly placedOrderAddress: PlacedOrderAddressService;
    public readonly placedOrderComment: PlacedOrderCommentService;
    public readonly placedOrderItem: PlacedOrderItemService;
    public readonly placedOrderItemShipment: PlacedOrderItemShipmentService;
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
        this.placedOrder = new PlacedOrderService(this.request);
        this.placedOrderAddress = new PlacedOrderAddressService(this.request);
        this.placedOrderComment = new PlacedOrderCommentService(this.request);
        this.placedOrderItem = new PlacedOrderItemService(this.request);
        this.placedOrderItemShipment = new PlacedOrderItemShipmentService(this.request);
    }
}

