/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a document (binary data and metadata) associated with structured content. Properties follow the [Media Object](https://schema.org/MediaObject) specification.
 */
export type ContentDocument = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The type of content.
     */
    readonly contentType?: string;
    /**
     * The document's relative URL.
     */
    readonly contentUrl?: string;
    /**
     * optional field with the content of the document in Base64, can be embedded with nestedFields
     */
    readonly contentValue?: string;
    /**
     * The document's description.
     */
    description?: string;
    /**
     * The document's content type (e.g., `application/pdf`, etc.).
     */
    readonly encodingFormat?: string;
    /**
     * The document's file extension.
     */
    readonly fileExtension?: string;
    /**
     * The document's ID.
     */
    id?: number;
    /**
     * The document's file size in bytes.
     */
    readonly sizeInBytes?: number;
    /**
     * The document's title.
     */
    title?: string;
};

