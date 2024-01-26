/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
/**
 * Represents a folder that can be used to organize structured content.
 */
export type StructuredContentFolder = {
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
     * The date the folder was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time any of the folder's fields changed.
     */
    readonly dateModified?: string;
    /**
     * The folder's description.
     */
    description?: string;
    /**
     * The structured content folder's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The folder's ID.
     */
    readonly id?: number;
    /**
     * The folder's name.
     */
    name: string;
    /**
     * The number of structured content folders inside this folder.
     */
    readonly numberOfStructuredContentFolders?: number;
    /**
     * The number of structured content objects inside this folder.
     */
    readonly numberOfStructuredContents?: number;
    /**
     * The ID of the folder's parent, if it exists.
     */
    parentStructuredContentFolderId?: number;
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

