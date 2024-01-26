/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Admin } from './Admin';
export type PortalInstance = {
    readonly active?: boolean;
    /**
     * The portal instance's administrator. This field is optional and is only used in the portal instance creation.
     */
    admin?: Admin;
    /**
     * internal unique key.
     */
    companyId?: number;
    /**
     * domain used for email authentication.
     */
    domain?: string;
    /**
     * public unique key (corresponds to company's webId field)
     */
    portalInstanceId?: string;
    siteInitializerKey?: string;
    virtualHost?: string;
};

