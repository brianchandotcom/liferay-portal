/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { AccountService } from './services/AccountService';
import { BillingAddressService } from './services/BillingAddressService';
import { ChannelService } from './services/ChannelService';
import { DefaultService } from './services/DefaultService';
import { OrderService } from './services/OrderService';
import { OrderAccountGroupService } from './services/OrderAccountGroupService';
import { OrderItemService } from './services/OrderItemService';
import { OrderNoteService } from './services/OrderNoteService';
import { OrderRuleService } from './services/OrderRuleService';
import { OrderRuleAccountService } from './services/OrderRuleAccountService';
import { OrderRuleAccountGroupService } from './services/OrderRuleAccountGroupService';
import { OrderRuleChannelService } from './services/OrderRuleChannelService';
import { OrderRuleOrderTypeService } from './services/OrderRuleOrderTypeService';
import { OrderTypeService } from './services/OrderTypeService';
import { OrderTypeChannelService } from './services/OrderTypeChannelService';
import { ShippingAddressService } from './services/ShippingAddressService';
import { TermService } from './services/TermService';
import { TermOrderTypeService } from './services/TermOrderTypeService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceAdminOrderClient {
    public readonly account: AccountService;
    public readonly billingAddress: BillingAddressService;
    public readonly channel: ChannelService;
    public readonly default: DefaultService;
    public readonly order: OrderService;
    public readonly orderAccountGroup: OrderAccountGroupService;
    public readonly orderItem: OrderItemService;
    public readonly orderNote: OrderNoteService;
    public readonly orderRule: OrderRuleService;
    public readonly orderRuleAccount: OrderRuleAccountService;
    public readonly orderRuleAccountGroup: OrderRuleAccountGroupService;
    public readonly orderRuleChannel: OrderRuleChannelService;
    public readonly orderRuleOrderType: OrderRuleOrderTypeService;
    public readonly orderType: OrderTypeService;
    public readonly orderTypeChannel: OrderTypeChannelService;
    public readonly shippingAddress: ShippingAddressService;
    public readonly term: TermService;
    public readonly termOrderType: TermOrderTypeService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = FetchHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://commerce.api.liferay.com/commerce-admin-order/v1.0',
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
        this.billingAddress = new BillingAddressService(this.request);
        this.channel = new ChannelService(this.request);
        this.default = new DefaultService(this.request);
        this.order = new OrderService(this.request);
        this.orderAccountGroup = new OrderAccountGroupService(this.request);
        this.orderItem = new OrderItemService(this.request);
        this.orderNote = new OrderNoteService(this.request);
        this.orderRule = new OrderRuleService(this.request);
        this.orderRuleAccount = new OrderRuleAccountService(this.request);
        this.orderRuleAccountGroup = new OrderRuleAccountGroupService(this.request);
        this.orderRuleChannel = new OrderRuleChannelService(this.request);
        this.orderRuleOrderType = new OrderRuleOrderTypeService(this.request);
        this.orderType = new OrderTypeService(this.request);
        this.orderTypeChannel = new OrderTypeChannelService(this.request);
        this.shippingAddress = new ShippingAddressService(this.request);
        this.term = new TermService(this.request);
        this.termOrderType = new TermOrderTypeService(this.request);
    }
}

