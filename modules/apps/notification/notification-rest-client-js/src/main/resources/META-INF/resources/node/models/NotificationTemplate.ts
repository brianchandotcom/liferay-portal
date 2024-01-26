/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type NotificationTemplate = {
    readonly actions?: Record<string, Record<string, string>>;
    attachmentObjectFieldExternalReferenceCodes?: Array<string>;
    attachmentObjectFieldIds?: Array<number>;
    body?: Record<string, string>;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    description?: string;
    editorType?: 'freeMarker' | 'richText';
    externalReferenceCode?: string;
    readonly id?: number;
    name?: string;
    name_i18n?: Record<string, string>;
    objectDefinitionExternalReferenceCode?: string;
    objectDefinitionId?: number;
    recipientType?: string;
    recipients?: Array<Record<string, any>>;
    subject?: Record<string, string>;
    system?: boolean;
    type?: string;
    typeLabel?: string;
};

