/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectLayoutTab } from './ObjectLayoutTab';
export type ObjectLayout = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    defaultObjectLayout?: boolean;
    readonly id?: number;
    name?: Record<string, string>;
    objectDefinitionExternalReferenceCode?: string;
    objectDefinitionId?: number;
    objectLayoutTabs?: Array<ObjectLayoutTab>;
};

