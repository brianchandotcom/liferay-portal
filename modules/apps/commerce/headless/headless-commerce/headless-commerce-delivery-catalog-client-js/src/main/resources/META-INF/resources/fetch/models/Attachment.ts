/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
export type Attachment = {
    /**
     * Base64 encoded file
     */
    attachment?: string;
    readonly customFields?: Array<CustomField>;
    displayDate?: string;
    expirationDate?: string;
    readonly galleryEnabled?: boolean;
    readonly id?: number;
    neverExpire?: boolean;
    options?: Record<string, string>;
    priority?: number;
    /**
     * URL of the location
     */
    src?: string;
    readonly tags?: Array<string>;
    title?: string;
    readonly type?: number;
};

