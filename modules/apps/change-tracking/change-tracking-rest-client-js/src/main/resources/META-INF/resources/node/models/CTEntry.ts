/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Status } from './Status';
export type CTEntry = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly changeType?: string;
    readonly ctCollectionId?: number;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    readonly hideable?: boolean;
    readonly id?: number;
    readonly modelClassNameId?: number;
    readonly modelClassPK?: number;
    readonly ownerId?: number;
    readonly ownerName?: string;
    readonly siteId?: number;
    readonly siteName?: string;
    readonly status?: Status;
    readonly title?: string;
    readonly typeName?: string;
};

