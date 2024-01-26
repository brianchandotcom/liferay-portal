/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { AccountService } from './services/AccountService';
import { AccountGroupService } from './services/AccountGroupService';
import { AccountRoleService } from './services/AccountRoleService';
import { EmailAddressService } from './services/EmailAddressService';
import { OrganizationService } from './services/OrganizationService';
import { PhoneService } from './services/PhoneService';
import { PostalAddressService } from './services/PostalAddressService';
import { RoleService } from './services/RoleService';
import { SegmentService } from './services/SegmentService';
import { SegmentUserService } from './services/SegmentUserService';
import { SiteService } from './services/SiteService';
import { SubscriptionService } from './services/SubscriptionService';
import { TicketService } from './services/TicketService';
import { UserAccountService } from './services/UserAccountService';
import { UserAccountFullNameDefinitionService } from './services/UserAccountFullNameDefinitionService';
import { UserGroupService } from './services/UserGroupService';
import { WebUrlService } from './services/WebUrlService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessAdminUserClient {
    public readonly account: AccountService;
    public readonly accountGroup: AccountGroupService;
    public readonly accountRole: AccountRoleService;
    public readonly emailAddress: EmailAddressService;
    public readonly organization: OrganizationService;
    public readonly phone: PhoneService;
    public readonly postalAddress: PostalAddressService;
    public readonly role: RoleService;
    public readonly segment: SegmentService;
    public readonly segmentUser: SegmentUserService;
    public readonly site: SiteService;
    public readonly subscription: SubscriptionService;
    public readonly ticket: TicketService;
    public readonly userAccount: UserAccountService;
    public readonly userAccountFullNameDefinition: UserAccountFullNameDefinitionService;
    public readonly userGroup: UserGroupService;
    public readonly webUrl: WebUrlService;
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
        this.accountGroup = new AccountGroupService(this.request);
        this.accountRole = new AccountRoleService(this.request);
        this.emailAddress = new EmailAddressService(this.request);
        this.organization = new OrganizationService(this.request);
        this.phone = new PhoneService(this.request);
        this.postalAddress = new PostalAddressService(this.request);
        this.role = new RoleService(this.request);
        this.segment = new SegmentService(this.request);
        this.segmentUser = new SegmentUserService(this.request);
        this.site = new SiteService(this.request);
        this.subscription = new SubscriptionService(this.request);
        this.ticket = new TicketService(this.request);
        this.userAccount = new UserAccountService(this.request);
        this.userAccountFullNameDefinition = new UserAccountFullNameDefinitionService(this.request);
        this.userGroup = new UserGroupService(this.request);
        this.webUrl = new WebUrlService(this.request);
    }
}

