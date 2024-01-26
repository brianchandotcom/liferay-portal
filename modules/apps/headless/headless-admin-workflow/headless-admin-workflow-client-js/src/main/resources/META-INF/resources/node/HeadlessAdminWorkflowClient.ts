/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { AssigneeService } from './services/AssigneeService';
import { TransitionService } from './services/TransitionService';
import { WorkflowDefinitionService } from './services/WorkflowDefinitionService';
import { WorkflowInstanceService } from './services/WorkflowInstanceService';
import { WorkflowLogService } from './services/WorkflowLogService';
import { WorkflowTaskService } from './services/WorkflowTaskService';
import { WorkflowTaskAssignableUsersService } from './services/WorkflowTaskAssignableUsersService';
import { WorkflowTaskTransitionsService } from './services/WorkflowTaskTransitionsService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessAdminWorkflowClient {
    public readonly assignee: AssigneeService;
    public readonly transition: TransitionService;
    public readonly workflowDefinition: WorkflowDefinitionService;
    public readonly workflowInstance: WorkflowInstanceService;
    public readonly workflowLog: WorkflowLogService;
    public readonly workflowTask: WorkflowTaskService;
    public readonly workflowTaskAssignableUsers: WorkflowTaskAssignableUsersService;
    public readonly workflowTaskTransitions: WorkflowTaskTransitionsService;
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
        this.assignee = new AssigneeService(this.request);
        this.transition = new TransitionService(this.request);
        this.workflowDefinition = new WorkflowDefinitionService(this.request);
        this.workflowInstance = new WorkflowInstanceService(this.request);
        this.workflowLog = new WorkflowLogService(this.request);
        this.workflowTask = new WorkflowTaskService(this.request);
        this.workflowTaskAssignableUsers = new WorkflowTaskAssignableUsersService(this.request);
        this.workflowTaskTransitions = new WorkflowTaskTransitionsService(this.request);
    }
}

