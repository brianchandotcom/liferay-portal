/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { ObjectActionService } from './services/ObjectActionService';
import { ObjectDefinitionService } from './services/ObjectDefinitionService';
import { ObjectFieldService } from './services/ObjectFieldService';
import { ObjectFolderService } from './services/ObjectFolderService';
import { ObjectLayoutService } from './services/ObjectLayoutService';
import { ObjectRelationshipService } from './services/ObjectRelationshipService';
import { ObjectValidationRuleService } from './services/ObjectValidationRuleService';
import { ObjectViewService } from './services/ObjectViewService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class ObjectAdminRestClient {
    public readonly objectAction: ObjectActionService;
    public readonly objectDefinition: ObjectDefinitionService;
    public readonly objectField: ObjectFieldService;
    public readonly objectFolder: ObjectFolderService;
    public readonly objectLayout: ObjectLayoutService;
    public readonly objectRelationship: ObjectRelationshipService;
    public readonly objectValidationRule: ObjectValidationRuleService;
    public readonly objectView: ObjectViewService;
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
        this.objectAction = new ObjectActionService(this.request);
        this.objectDefinition = new ObjectDefinitionService(this.request);
        this.objectField = new ObjectFieldService(this.request);
        this.objectFolder = new ObjectFolderService(this.request);
        this.objectLayout = new ObjectLayoutService(this.request);
        this.objectRelationship = new ObjectRelationshipService(this.request);
        this.objectValidationRule = new ObjectValidationRuleService(this.request);
        this.objectView = new ObjectViewService(this.request);
    }
}

