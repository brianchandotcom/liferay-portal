/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * A binary file attached to a message on a message board (`MessageBoardMessage`).
 */
export type MessageBoardAttachment = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The file's relative URL.
     */
    readonly contentUrl?: string;
    /**
     * optional field with the content of the document in Base64, can be embedded with nestedFields
     */
    readonly contentValue?: string;
    /**
     * The file's media format (e.g., application/pdf, etc.).
     */
    readonly encodingFormat?: string;
    /**
     * The message board's external reference code.
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
     * The file's title.
     */
    title?: string;
};

