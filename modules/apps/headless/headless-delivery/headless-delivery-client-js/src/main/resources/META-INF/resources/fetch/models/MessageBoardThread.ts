/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AggregateRating } from './AggregateRating';
import type { Creator } from './Creator';
import type { CreatorStatistics } from './CreatorStatistics';
import type { CustomField } from './CustomField';
import type { RelatedContent } from './RelatedContent';
import type { TaxonomyCategoryBrief } from './TaxonomyCategoryBrief';
/**
 * Represents a discussion thread in a message board.
 */
export type MessageBoardThread = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The thread's average rating.
     */
    readonly aggregateRating?: AggregateRating;
    /**
     * The thread's main body content (the message written as the thread's description).
     */
    articleBody?: string;
    /**
     * The thread's creator.
     */
    readonly creator?: Creator;
    /**
     * The thread's creator statistics (rank, join date, number of posts, ...)
     */
    creatorStatistics?: CreatorStatistics;
    /**
     * A list of the custom fields associated with the thread.
     */
    customFields?: Array<CustomField>;
    /**
     * The date the thread was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time any field of the thread changed.
     */
    readonly dateModified?: string;
    /**
     * The media format of the thread's content (e.g., HTML, BBCode, etc.).
     */
    encodingFormat?: string;
    friendlyUrlPath?: string;
    /**
     * A flag that indicates whether this thread has a message considered as valid
     */
    hasValidAnswer?: boolean;
    /**
     * The thread's main title.
     */
    headline: string;
    /**
     * The thread's ID.
     */
    readonly id?: number;
    /**
     * A list of keywords describing the thread.
     */
    keywords?: Array<string>;
    readonly lastPostDate?: string;
    /**
     * A flag that indicates whether this thread is locked.
     */
    readonly locked?: boolean;
    /**
     * The ID of the thread's message.
     */
    readonly messageBoardRootMessageId?: number;
    /**
     * The ID of the Message Board Section to which this message is scoped.
     */
    messageBoardSectionId?: number;
    /**
     * The number of the thread's attachments.
     */
    readonly numberOfMessageBoardAttachments?: number;
    /**
     * The number of the thread's messages.
     */
    readonly numberOfMessageBoardMessages?: number;
    /**
     * A list of related contents to this thread.
     */
    readonly relatedContents?: Array<RelatedContent>;
    /**
     * A flag that indicates whether this thread has been seen.
     */
    seen?: boolean;
    /**
     * A flag that indicates whether this thread was posted as a question that can receive approved answers.
     */
    showAsQuestion?: boolean;
    /**
     * The ID of the site to which this thread is scoped.
     */
    readonly siteId?: number;
    /**
     * The thread's status.
     */
    readonly status?: string;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this thread.
     */
    subscribed?: boolean;
    /**
     * The categories associated with this thread.
     */
    readonly taxonomyCategoryBriefs?: Array<TaxonomyCategoryBrief>;
    /**
     * A write-only field that adds `TaxonomyCategory` instances to the thread.
     */
    taxonomyCategoryIds?: Array<number>;
    /**
     * The thread's type.
     */
    threadType?: string;
    /**
     * The number of views of this thread.
     */
    readonly viewCount?: number;
    /**
     * A write-only property that specifies the thread's default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

