/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
/**
 * Represents a section/folder on a message board, used to organize messages.
 */
export type MessageBoardSection = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The section's creator.
     */
    readonly creator?: Creator;
    /**
     * A list of the custom fields associated with the section.
     */
    customFields?: Array<CustomField>;
    /**
     * The date the section was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time the section was changed.
     */
    readonly dateModified?: string;
    /**
     * The section's description.
     */
    description?: string;
    friendlyUrlPath?: string;
    /**
     * The section's ID.
     */
    readonly id?: number;
    /**
     * The number of this section's child sections.
     */
    readonly numberOfMessageBoardSections?: number;
    /**
     * The number of message board threads in this section.
     */
    readonly numberOfMessageBoardThreads?: number;
    /**
     * The ID of the section parent's, if it exists.
     */
    parentMessageBoardSectionId?: number;
    /**
     * The ID of the site to which this section is scoped.
     */
    readonly siteId?: number;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this section.
     */
    readonly subscribed?: boolean;
    /**
     * The section's main title.
     */
    title: string;
    /**
     * A write-only property that specifies the default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

