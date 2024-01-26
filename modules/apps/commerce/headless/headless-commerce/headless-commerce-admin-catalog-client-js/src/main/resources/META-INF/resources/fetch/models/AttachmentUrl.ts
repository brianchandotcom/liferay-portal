/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
export type AttachmentUrl = {
    /**
     * Content type of attachment
     */
    contentType?: string;
    customFields?: Array<CustomField>;
    displayDate?: string;
    expirationDate?: string;
    externalReferenceCode?: string;
    galleryEnabled?: boolean;
    readonly id?: number;
    neverExpire?: boolean;
    options?: Record<string, string>;
    priority?: number;
    /**
     * URL of the location
     */
    src?: string;
    tags?: Array<string>;
    title?: Record<string, string>;
    readonly type?: number;
};

