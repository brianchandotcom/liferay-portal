/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountAddress } from './AccountAddress';
import type { AccountMember } from './AccountMember';
import type { AccountOrganization } from './AccountOrganization';
export type Account = {
    accountAddresses?: Array<AccountAddress>;
    accountMembers?: Array<AccountMember>;
    accountOrganizations?: Array<AccountOrganization>;
    active?: boolean;
    customFields?: Record<string, any>;
    /**
     * The account's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The account's most recent modification date.
     */
    readonly dateModified?: string;
    defaultBillingAccountAddressId?: number;
    defaultShippingAccountAddressId?: number;
    emailAddresses?: Array<string>;
    externalReferenceCode: string;
    id?: number;
    logoId?: number;
    logoURL?: string;
    name: string;
    root?: boolean;
    taxId?: string;
    type?: number;
};

