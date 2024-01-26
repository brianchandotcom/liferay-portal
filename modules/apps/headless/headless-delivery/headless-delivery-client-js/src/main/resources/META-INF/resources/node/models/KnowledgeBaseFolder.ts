/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
/**
 * Represents a folder for organizing Knowledge Base articles.
 */
export type KnowledgeBaseFolder = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The folder's creator.
     */
    readonly creator?: Creator;
    /**
     * A list of the custom fields associated with the folder.
     */
    customFields?: Array<CustomField>;
    /**
     * The date the folder was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time the folder was modified.
     */
    readonly dateModified?: string;
    /**
     * The folder's description.
     */
    description?: string;
    /**
     * The folder's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The folder's ID.
     */
    readonly id?: number;
    /**
     * The folder's main title/name.
     */
    name: string;
    /**
     * The number of Knowledge Base articles in this folder.
     */
    readonly numberOfKnowledgeBaseArticles?: number;
    /**
     * The number of Knowledge Base folders in this folder.
     */
    readonly numberOfKnowledgeBaseFolders?: number;
    /**
     * The folder's parent Knowledge Base folder, if it exists.
     */
    readonly parentKnowledgeBaseFolder?: {
        /**
         * The parent folder's ID.
         */
        folderId?: number;
        /**
         * The parent folder's name.
         */
        folderName?: string;
    };
    /**
     * The ID of the folder's parent Knowledge Base folder, if such a parent folder exists.
     */
    parentKnowledgeBaseFolderId?: number;
    /**
     * The ID of the site to which this folder is scoped.
     */
    readonly siteId?: number;
    /**
     * A write-only property that specifies the folder's default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

