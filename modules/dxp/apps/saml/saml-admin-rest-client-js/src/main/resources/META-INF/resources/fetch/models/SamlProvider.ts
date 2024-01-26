/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Idp } from './Idp';
import type { Sp } from './Sp';
/**
 * The complete configuration of this SAML Provider.
 */
export type SamlProvider = {
    enabled?: boolean;
    entityId?: string;
    idp?: Idp;
    keyStoreCredentialPassword?: string;
    role?: 'idp' | 'sp';
    signMetadata?: boolean;
    sp?: Sp;
    sslRequired?: boolean;
};

