/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_AggregateRating} from './HeadlessDelivery_v1_0_AggregateRating';
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_CreatorStatistics} from './HeadlessDelivery_v1_0_CreatorStatistics';
import type {HeadlessDelivery_v1_0_CustomField} from './HeadlessDelivery_v1_0_CustomField';
import type {HeadlessDelivery_v1_0_RelatedContent} from './HeadlessDelivery_v1_0_RelatedContent';
import type {HeadlessDelivery_v1_0_TaxonomyCategoryBrief} from './HeadlessDelivery_v1_0_TaxonomyCategoryBrief';

/**
 * Represents a discussion thread in a message board.
 */
export type HeadlessDelivery_v1_0_MessageBoardThread = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'aggregateRating'?: HeadlessDelivery_v1_0_AggregateRating;

	/**
	 * The thread's main body content (the message written as the thread's description).
	 */
	'articleBody'?: string;
	'creator'?: HeadlessDelivery_v1_0_Creator;
	'creatorStatistics'?: HeadlessDelivery_v1_0_CreatorStatistics;

	/**
	 * A list of the custom fields associated with the thread.
	 */
	'customFields'?: Array<HeadlessDelivery_v1_0_CustomField>;

	/**
	 * The date the thread was created.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any field of the thread changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The media format of the thread's content (e.g., HTML, BBCode, etc.).
	 */
	'encodingFormat'?: string;
	'friendlyUrlPath'?: string;

	/**
	 * A flag that indicates whether this thread has a message considered as valid
	 */
	'hasValidAnswer'?: boolean;

	/**
	 * The thread's main title.
	 */
	'headline': string;

	/**
	 * The thread's ID.
	 */
	readonly 'id'?: number;

	/**
	 * A list of keywords describing the thread.
	 */
	'keywords'?: Array<string>;
	readonly 'lastPostDate'?: string;

	/**
	 * A flag that indicates whether this thread is locked.
	 */
	readonly 'locked'?: boolean;

	/**
	 * The ID of the thread's message.
	 */
	readonly 'messageBoardRootMessageId'?: number;

	/**
	 * The ID of the Message Board Section to which this message is scoped.
	 */
	'messageBoardSectionId'?: number;

	/**
	 * The number of the thread's attachments.
	 */
	readonly 'numberOfMessageBoardAttachments'?: number;

	/**
	 * The number of the thread's messages.
	 */
	readonly 'numberOfMessageBoardMessages'?: number;

	/**
	 * A list of related contents to this thread.
	 */
	readonly 'relatedContents'?: Array<HeadlessDelivery_v1_0_RelatedContent>;

	/**
	 * A flag that indicates whether this thread has been seen.
	 */
	'seen'?: boolean;

	/**
	 * A flag that indicates whether this thread was posted as a question that can receive approved answers.
	 */
	'showAsQuestion'?: boolean;

	/**
	 * The ID of the site to which this thread is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The thread's status.
	 */
	readonly 'status'?: string;

	/**
	 * A flag that indicates whether the user making the requests is subscribed to this thread.
	 */
	'subscribed'?: boolean;

	/**
	 * The categories associated with this thread.
	 */
	readonly 'taxonomyCategoryBriefs'?: Array<HeadlessDelivery_v1_0_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;

	/**
	 * The thread's type.
	 */
	'threadType'?: string;

	/**
	 * The number of views of this thread.
	 */
	readonly 'viewCount'?: number;
	'viewableBy'?: 'Anyone' | 'Members' | 'Owner';
	readonly 'x-class-name'?: string;
};
