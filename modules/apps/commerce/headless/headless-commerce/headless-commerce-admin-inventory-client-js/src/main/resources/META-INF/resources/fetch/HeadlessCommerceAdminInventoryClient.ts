/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { ChannelService } from './services/ChannelService';
import { OrderTypeService } from './services/OrderTypeService';
import { ReplenishmentItemService } from './services/ReplenishmentItemService';
import { WarehouseService } from './services/WarehouseService';
import { WarehouseChannelService } from './services/WarehouseChannelService';
import { WarehouseItemService } from './services/WarehouseItemService';
import { WarehouseOrderTypeService } from './services/WarehouseOrderTypeService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceAdminInventoryClient {
    public readonly channel: ChannelService;
    public readonly orderType: OrderTypeService;
    public readonly replenishmentItem: ReplenishmentItemService;
    public readonly warehouse: WarehouseService;
    public readonly warehouseChannel: WarehouseChannelService;
    public readonly warehouseItem: WarehouseItemService;
    public readonly warehouseOrderType: WarehouseOrderTypeService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = FetchHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://commerce.api.liferay.com/commerce-admin-inventory/v1.0',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.channel = new ChannelService(this.request);
        this.orderType = new OrderTypeService(this.request);
        this.replenishmentItem = new ReplenishmentItemService(this.request);
        this.warehouse = new WarehouseService(this.request);
        this.warehouseChannel = new WarehouseChannelService(this.request);
        this.warehouseItem = new WarehouseItemService(this.request);
        this.warehouseOrderType = new WarehouseOrderTypeService(this.request);
    }
}

