/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Configuration } from './Configuration';
import type { ElementInstance } from './ElementInstance';
export type SXPBlueprint = {
    readonly actions?: Record<string, Record<string, string>>;
    configuration?: Configuration;
    createDate?: string;
    description?: string;
    description_i18n?: Record<string, string>;
    elementInstances?: Array<ElementInstance>;
    externalReferenceCode?: string;
    id?: number;
    modifiedDate?: string;
    schemaVersion?: string;
    title?: string;
    title_i18n?: Record<string, string>;
    userName?: string;
    version?: string;
};

