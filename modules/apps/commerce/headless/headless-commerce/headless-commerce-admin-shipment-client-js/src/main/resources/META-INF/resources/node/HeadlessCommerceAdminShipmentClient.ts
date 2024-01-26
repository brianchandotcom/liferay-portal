/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { ShipmentService } from './services/ShipmentService';
import { ShipmentItemService } from './services/ShipmentItemService';
import { ShippingAddressService } from './services/ShippingAddressService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceAdminShipmentClient {
    public readonly shipment: ShipmentService;
    public readonly shipmentItem: ShipmentItemService;
    public readonly shippingAddress: ShippingAddressService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = NodeHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://virtserver.swaggerhub.com/liferayinc/commerce-admin-shipment-api/v1.0',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.shipment = new ShipmentService(this.request);
        this.shipmentItem = new ShipmentItemService(this.request);
        this.shippingAddress = new ShippingAddressService(this.request);
    }
}

