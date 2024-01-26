/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectFolderItem } from './ObjectFolderItem';
export type ObjectFolder = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    externalReferenceCode?: string;
    readonly id?: number;
    label?: Record<string, string>;
    name?: string;
    objectFolderItems?: Array<ObjectFolderItem>;
};

