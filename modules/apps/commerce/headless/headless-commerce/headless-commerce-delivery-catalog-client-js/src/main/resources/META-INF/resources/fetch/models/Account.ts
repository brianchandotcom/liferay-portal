/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
export type Account = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly customFields?: Array<CustomField>;
    /**
     * The account's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The account's most recent modification date.
     */
    readonly dateModified?: string;
    defaultBillingAddressId?: number;
    defaultShippingAddressId?: number;
    description?: string;
    domains?: Array<string>;
    externalReferenceCode?: string;
    id?: number;
    logoId?: number;
    logoURL?: string;
    name: string;
    organizationIds?: Array<number>;
    status?: number;
    taxId?: string;
    type?: 'business' | 'guest' | 'person' | 'supplier';
};

