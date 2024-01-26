/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Node } from '../models/Node';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class NodeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Node
     * @throws ApiError
     */
    public getProcessNodesPage({
        processId,
    }: {
        processId: number,
    }): CancelablePromise<Array<Node>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/nodes',
            path: {
                'processId': processId,
            },
        });
    }
    /**
     * @returns Node
     * @throws ApiError
     */
    public postProcessNode({
        processId,
        requestBody,
    }: {
        processId: number,
        requestBody?: Node,
    }): CancelablePromise<Node> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/nodes',
            path: {
                'processId': processId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteProcessNode({
        processId,
        nodeId,
    }: {
        processId: number,
        nodeId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/nodes/{nodeId}',
            path: {
                'processId': processId,
                'nodeId': nodeId,
            },
        });
    }
}
