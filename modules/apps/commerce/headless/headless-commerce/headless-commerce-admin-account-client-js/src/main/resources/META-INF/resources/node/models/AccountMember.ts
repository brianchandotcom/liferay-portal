/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountRole } from './AccountRole';
export type AccountMember = {
    accountId?: number;
    accountRoles?: Array<AccountRole>;
    email: string;
    externalReferenceCode?: string;
    name?: string;
    userExternalReferenceCode?: string;
    userId?: number;
};

