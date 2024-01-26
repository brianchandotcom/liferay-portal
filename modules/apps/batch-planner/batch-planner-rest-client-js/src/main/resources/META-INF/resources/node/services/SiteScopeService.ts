/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SiteScope } from '../models/SiteScope';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SiteScopeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SiteScope
     * @throws ApiError
     */
    public getPlanInternalClassNameKeySiteScopesPage({
        internalClassNameKey,
        _export,
    }: {
        internalClassNameKey: string,
        _export?: boolean,
    }): CancelablePromise<Array<SiteScope>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/batch-planner/v1.0/plans/{internalClassNameKey}/site-scopes',
            path: {
                'internalClassNameKey': internalClassNameKey,
            },
            query: {
                'export': _export,
            },
        });
    }
}
