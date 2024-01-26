/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { AssigneeService } from './services/AssigneeService';
import { AssigneeMetricService } from './services/AssigneeMetricService';
import { CalendarService } from './services/CalendarService';
import { HistogramMetricService } from './services/HistogramMetricService';
import { IndexService } from './services/IndexService';
import { InstanceService } from './services/InstanceService';
import { NodeService } from './services/NodeService';
import { NodeMetricService } from './services/NodeMetricService';
import { ProcessService } from './services/ProcessService';
import { ProcessMetricService } from './services/ProcessMetricService';
import { ProcessVersionService } from './services/ProcessVersionService';
import { ReindexStatusService } from './services/ReindexStatusService';
import { RoleService } from './services/RoleService';
import { SlaService } from './services/SlaService';
import { SlaResultService } from './services/SlaResultService';
import { TaskService } from './services/TaskService';
import { TimeRangeService } from './services/TimeRangeService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class PortalWorkflowMetricsRestClient {
    public readonly assignee: AssigneeService;
    public readonly assigneeMetric: AssigneeMetricService;
    public readonly calendar: CalendarService;
    public readonly histogramMetric: HistogramMetricService;
    public readonly index: IndexService;
    public readonly instance: InstanceService;
    public readonly node: NodeService;
    public readonly nodeMetric: NodeMetricService;
    public readonly process: ProcessService;
    public readonly processMetric: ProcessMetricService;
    public readonly processVersion: ProcessVersionService;
    public readonly reindexStatus: ReindexStatusService;
    public readonly role: RoleService;
    public readonly sla: SlaService;
    public readonly slaResult: SlaResultService;
    public readonly task: TaskService;
    public readonly timeRange: TimeRangeService;
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
        this.assigneeMetric = new AssigneeMetricService(this.request);
        this.calendar = new CalendarService(this.request);
        this.histogramMetric = new HistogramMetricService(this.request);
        this.index = new IndexService(this.request);
        this.instance = new InstanceService(this.request);
        this.node = new NodeService(this.request);
        this.nodeMetric = new NodeMetricService(this.request);
        this.process = new ProcessService(this.request);
        this.processMetric = new ProcessMetricService(this.request);
        this.processVersion = new ProcessVersionService(this.request);
        this.reindexStatus = new ReindexStatusService(this.request);
        this.role = new RoleService(this.request);
        this.sla = new SlaService(this.request);
        this.slaResult = new SlaResultService(this.request);
        this.task = new TaskService(this.request);
        this.timeRange = new TimeRangeService(this.request);
    }
}

