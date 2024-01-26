/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Meta } from './Meta';
/**
 * Each SCIM resource (Users, Groups, etc.) includes the following common attributes.
 */
export type BaseScim = {
    /**
     * A String that is an identifier for the resource as defined by the provisioning client.
     */
    externalId?: string;
    /**
     * A unique identifier for a SCIM resource as defined by the service provider.
     */
    readonly id?: string;
    meta?: Meta;
    /**
     * A multi-valued list of strings indicating the namespaces of the SCIM schemas that define the attributes present in the current JSON structure.
     */
    schemas?: Array<string>;
};

