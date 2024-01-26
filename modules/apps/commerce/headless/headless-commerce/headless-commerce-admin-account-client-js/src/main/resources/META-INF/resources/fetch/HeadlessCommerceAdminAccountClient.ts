/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { AccountService } from './services/AccountService';
import { AccountAddressService } from './services/AccountAddressService';
import { AccountChannelEntryService } from './services/AccountChannelEntryService';
import { AccountChannelShippingOptionService } from './services/AccountChannelShippingOptionService';
import { AccountMemberService } from './services/AccountMemberService';
import { AccountOrganizationService } from './services/AccountOrganizationService';
import { AdminAccountGroupService } from './services/AdminAccountGroupService';
import { UserService } from './services/UserService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessCommerceAdminAccountClient {
    public readonly account: AccountService;
    public readonly accountAddress: AccountAddressService;
    public readonly accountChannelEntry: AccountChannelEntryService;
    public readonly accountChannelShippingOption: AccountChannelShippingOptionService;
    public readonly accountMember: AccountMemberService;
    public readonly accountOrganization: AccountOrganizationService;
    public readonly adminAccountGroup: AdminAccountGroupService;
    public readonly user: UserService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = FetchHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? 'https://commerce.api.liferay.com/commerce-admin/v1.0',
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
        this.accountAddress = new AccountAddressService(this.request);
        this.accountChannelEntry = new AccountChannelEntryService(this.request);
        this.accountChannelShippingOption = new AccountChannelShippingOptionService(this.request);
        this.accountMember = new AccountMemberService(this.request);
        this.accountOrganization = new AccountOrganizationService(this.request);
        this.adminAccountGroup = new AdminAccountGroupService(this.request);
        this.user = new UserService(this.request);
    }
}

