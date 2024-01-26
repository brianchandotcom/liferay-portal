/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { AnalyticsDxpEntityBatchExporterService } from './services/AnalyticsDxpEntityBatchExporterService';
import { ChannelService } from './services/ChannelService';
import { CommerceChannelService } from './services/CommerceChannelService';
import { ContactAccountGroupService } from './services/ContactAccountGroupService';
import { ContactConfigurationService } from './services/ContactConfigurationService';
import { ContactOrganizationService } from './services/ContactOrganizationService';
import { ContactUserGroupService } from './services/ContactUserGroupService';
import { DataSourceService } from './services/DataSourceService';
import { FieldService } from './services/FieldService';
import { FieldSummaryService } from './services/FieldSummaryService';
import { SiteService } from './services/SiteService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class AnalyticsSettingsRestClient {
    public readonly analyticsDxpEntityBatchExporter: AnalyticsDxpEntityBatchExporterService;
    public readonly channel: ChannelService;
    public readonly commerceChannel: CommerceChannelService;
    public readonly contactAccountGroup: ContactAccountGroupService;
    public readonly contactConfiguration: ContactConfigurationService;
    public readonly contactOrganization: ContactOrganizationService;
    public readonly contactUserGroup: ContactUserGroupService;
    public readonly dataSource: DataSourceService;
    public readonly field: FieldService;
    public readonly fieldSummary: FieldSummaryService;
    public readonly site: SiteService;
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
        this.analyticsDxpEntityBatchExporter = new AnalyticsDxpEntityBatchExporterService(this.request);
        this.channel = new ChannelService(this.request);
        this.commerceChannel = new CommerceChannelService(this.request);
        this.contactAccountGroup = new ContactAccountGroupService(this.request);
        this.contactConfiguration = new ContactConfigurationService(this.request);
        this.contactOrganization = new ContactOrganizationService(this.request);
        this.contactUserGroup = new ContactUserGroupService(this.request);
        this.dataSource = new DataSourceService(this.request);
        this.field = new FieldService(this.request);
        this.fieldSummary = new FieldSummaryService(this.request);
        this.site = new SiteService(this.request);
    }
}

