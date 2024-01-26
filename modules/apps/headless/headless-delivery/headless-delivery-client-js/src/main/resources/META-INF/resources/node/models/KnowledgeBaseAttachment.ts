/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a binary file attached to a Knowledge Base article.
 */
export type KnowledgeBaseAttachment = {
    /**
     * The file's relative URL.
     */
    readonly contentUrl?: string;
    /**
     * optional field with the content of the document in Base64, can be embedded with nestedFields
     */
    readonly contentValue?: string;
    /**
     * The file's media type (e.g., application/pdf, etc.).
     */
    readonly encodingFormat?: string;
    /**
     * The attachment's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The file's extension.
     */
    readonly fileExtension?: string;
    /**
     * The file's ID.
     */
    readonly id?: number;
    /**
     * The file's size in bytes.
     */
    readonly sizeInBytes?: number;
    /**
     * The file's main title.
     */
    title?: string;
};

