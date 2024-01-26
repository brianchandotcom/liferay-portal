/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ListTypeEntry } from './ListTypeEntry';
export type ListTypeDefinition = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    externalReferenceCode?: string;
    readonly id?: number;
    listTypeEntries?: Array<ListTypeEntry>;
    name?: string;
    name_i18n?: Record<string, string>;
    system?: boolean;
};

