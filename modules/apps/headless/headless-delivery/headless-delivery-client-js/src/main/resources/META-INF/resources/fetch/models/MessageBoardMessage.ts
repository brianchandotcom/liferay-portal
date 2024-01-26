/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AggregateRating } from './AggregateRating';
import type { Creator } from './Creator';
import type { CreatorStatistics } from './CreatorStatistics';
import type { CustomField } from './CustomField';
import type { RelatedContent } from './RelatedContent';
/**
 * Represents a message on a message board. Properties follow the [Discussion Forum Posting](https://schema.org/DiscussionForumPosting) specification.
 */
export type MessageBoardMessage = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The message's average rating.
     */
    readonly aggregateRating?: AggregateRating;
    /**
     * A flag that indicates whether the message's author is anonymous.
     */
    anonymous?: boolean;
    /**
     * The message's main content.
     */
    articleBody?: string;
    /**
     * The message's author.
     */
    readonly creator?: Creator;
    /**
     * The message's creator statistics (rank, join date, number of posts, ...)
     */
    creatorStatistics?: CreatorStatistics;
    /**
     * A list of the custom fields associated with the blog post.
     */
    customFields?: Array<CustomField>;
    /**
     * The date the message was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time the content or metadata of the message was changed.
     */
    readonly dateModified?: string;
    /**
     * The message's media format (e.g., HTML, BBCode, etc.).
     */
    encodingFormat?: string;
    /**
     * The message's external reference code.
     */
    externalReferenceCode?: string;
    friendlyUrlPath?: string;
    hasCompanyMx?: boolean;
    /**
     * The message's main title.
     */
    headline?: string;
    /**
     * The message's ID.
     */
    readonly id?: number;
    /**
     * A list of keywords describing the message.
     */
    keywords?: Array<string>;
    /**
     * The ID of the Message Board Section to which this message is scoped.
     */
    messageBoardSectionId?: number;
    /**
     * The ID of the Message Board Thread to which this message is scoped.
     */
    readonly messageBoardThreadId?: number;
    modified?: boolean;
    /**
     * The number of the message's attachments.
     */
    readonly numberOfMessageBoardAttachments?: number;
    /**
     * The number of the message's child messages.
     */
    readonly numberOfMessageBoardMessages?: number;
    /**
     * The ID of the message's parent, if it exists.
     */
    parentMessageBoardMessageId?: number;
    /**
     * A list of related contents to this message.
     */
    readonly relatedContents?: Array<RelatedContent>;
    /**
     * A flag that indicates whether the message is answering a question.
     */
    showAsAnswer?: boolean;
    /**
     * The ID of the site to which this message is scoped.
     */
    readonly siteId?: number;
    /**
     * The message's status.
     */
    readonly status?: string;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this message.
     */
    readonly subscribed?: boolean;
    /**
     * A write-only property that specifies the default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

