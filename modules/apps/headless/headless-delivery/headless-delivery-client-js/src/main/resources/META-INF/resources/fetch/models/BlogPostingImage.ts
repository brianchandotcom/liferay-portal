/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a blog post's image. Properties follow the [image](https://schema.org/image) specification.
 */
export type BlogPostingImage = {
    /**
     * The image's relative URL.
     */
    readonly contentUrl?: string;
    /**
     * optional field with the content of the image in Base64, can be embedded with nestedFields
     */
    readonly contentValue?: string;
    /**
     * The image's content type (e.g., `application/png`, etc.).
     */
    readonly encodingFormat?: string;
    /**
     * The image's file extension.
     */
    readonly fileExtension?: string;
    /**
     * The image's ID.
     */
    readonly id?: number;
    /**
     * The image's size in bytes.
     */
    readonly sizeInBytes?: number;
    /**
     * The image's title text.
     */
    title?: string;
    /**
     * A write-only property that specifies the default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

