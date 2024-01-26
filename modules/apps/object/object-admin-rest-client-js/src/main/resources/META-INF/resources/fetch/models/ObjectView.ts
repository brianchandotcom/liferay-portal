/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectViewColumn } from './ObjectViewColumn';
import type { ObjectViewFilterColumn } from './ObjectViewFilterColumn';
import type { ObjectViewSortColumn } from './ObjectViewSortColumn';
export type ObjectView = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    defaultObjectView?: boolean;
    readonly id?: number;
    name?: Record<string, string>;
    objectDefinitionExternalReferenceCode?: string;
    objectDefinitionId?: number;
    objectViewColumns?: Array<ObjectViewColumn>;
    objectViewFilterColumns?: Array<ObjectViewFilterColumn>;
    objectViewSortColumns?: Array<ObjectViewSortColumn>;
};

