/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
/**
 * Represents a wiki node that can be used to organize wiki pages.
 */
export type WikiNode = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The wiki node's creator.
     */
    readonly creator?: Creator;
    /**
     * The date the wiki node was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time any of the wiki node's fields changed.
     */
    readonly dateModified?: string;
    /**
     * The wiki node's description.
     */
    description?: string;
    /**
     * The wiki node's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The wiki node's ID.
     */
    readonly id?: number;
    /**
     * The wiki node's name.
     */
    name?: string;
    /**
     * The number of child wiki page on this wiki node.
     */
    readonly numberOfWikiPages?: number;
    /**
     * The ID of the site to which this wiki node is scoped.
     */
    readonly siteId?: number;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this wiki node.
     */
    readonly subscribed?: boolean;
    /**
     * A write-only property that specifies the default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

