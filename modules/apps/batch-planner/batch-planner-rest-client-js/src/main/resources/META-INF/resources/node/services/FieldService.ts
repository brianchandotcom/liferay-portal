/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Field } from '../models/Field';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FieldService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Field
     * @throws ApiError
     */
    public getPlanInternalClassNameKeyFieldsPage({
        internalClassNameKey,
        _export,
    }: {
        internalClassNameKey: string,
        _export?: boolean,
    }): CancelablePromise<Array<Field>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/batch-planner/v1.0/plans/{internalClassNameKey}/fields',
            path: {
                'internalClassNameKey': internalClassNameKey,
            },
            query: {
                'export': _export,
            },
        });
    }
}
