/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
/**
 * Represents a document folder that can contain documents and other folders.
 */
export type DocumentFolder = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The key of the asset library to which the folder is scoped.
     */
    readonly assetLibraryKey?: string;
    /**
     * The folder's creator.
     */
    readonly creator?: Creator;
    /**
     * A list of the custom fields associated with the folder.
     */
    customFields?: Array<CustomField>;
    /**
     * The folder's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time a field of the folder changed.
     */
    readonly dateModified?: string;
    /**
     * The folder's description.
     */
    description?: string;
    /**
     * The document folder's external reference code.
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
     * The number of this folder's child folders.
     */
    readonly numberOfDocumentFolders?: number;
    /**
     * The number of documents in this folder.
     */
    readonly numberOfDocuments?: number;
    /**
     * The ID of the folder's parent, if it exists.
     */
    parentDocumentFolderId?: number;
    /**
     * The ID of the site to which this folder is scoped.
     */
    readonly siteId?: number;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this folder.
     */
    readonly subscribed?: boolean;
    /**
     * A write-only property that specifies the folder's default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

