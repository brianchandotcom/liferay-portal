/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
/**
 * Represents a comment. See [Comment](https://www.schema.org/Comment) for more details.
 */
export type Comment = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The comment's author.
     */
    readonly creator?: Creator;
    /**
     * The comment's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The comment's latest modification date.
     */
    readonly dateModified?: string;
    /**
     * The comment's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The comment's ID.
     */
    readonly id?: number;
    /**
     * The number of child comments on this comment.
     */
    readonly numberOfComments?: number;
    /**
     * the ID of the comment's parent, if it exists.
     */
    parentCommentId?: number;
    /**
     * The comment's text content.
     */
    text?: string;
};

