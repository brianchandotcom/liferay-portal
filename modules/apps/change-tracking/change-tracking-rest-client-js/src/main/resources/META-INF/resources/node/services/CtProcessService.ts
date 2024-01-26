/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CTProcess } from '../models/CTProcess';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CtProcessService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns CTProcess
     * @throws ApiError
     */
    public getCtProcessesPage({
        status,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        status?: Array<number>,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<CTProcess>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-processes',
            query: {
                'status': status,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns CTProcess
     * @throws ApiError
     */
    public getCtProcess({
        ctProcessId,
    }: {
        ctProcessId: number,
    }): CancelablePromise<CTProcess> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-processes/{ctProcessId}',
            path: {
                'ctProcessId': ctProcessId,
            },
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postCtProcessRevert({
        ctProcessId,
        description,
        name,
    }: {
        ctProcessId: number,
        description?: string,
        name?: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-processes/{ctProcessId}/revert',
            path: {
                'ctProcessId': ctProcessId,
            },
            query: {
                'description': description,
                'name': name,
            },
        });
    }
}
