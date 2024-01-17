/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_CustomField} from './HeadlessDelivery_v1_0_CustomField';
import type {HeadlessDelivery_v1_0_ParentKnowledgeBaseFolder} from './HeadlessDelivery_v1_0_ParentKnowledgeBaseFolder';

/**
 * Represents a folder for organizing Knowledge Base articles.
 */
export type HeadlessDelivery_v1_0_KnowledgeBaseFolder = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * A list of the custom fields associated with the folder.
	 */
	'customFields'?: Array<HeadlessDelivery_v1_0_CustomField>;

	/**
	 * The date the folder was created.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time the folder was modified.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The folder's description.
	 */
	'description'?: string;

	/**
	 * The folder's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The folder's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The folder's main title/name.
	 */
	'name': string;

	/**
	 * The number of Knowledge Base articles in this folder.
	 */
	readonly 'numberOfKnowledgeBaseArticles'?: number;

	/**
	 * The number of Knowledge Base folders in this folder.
	 */
	readonly 'numberOfKnowledgeBaseFolders'?: number;
	'parentKnowledgeBaseFolder'?: HeadlessDelivery_v1_0_ParentKnowledgeBaseFolder;
	'parentKnowledgeBaseFolderId'?: number;

	/**
	 * The ID of the site to which this folder is scoped.
	 */
	readonly 'siteId'?: number;
	'viewableBy'?: 'Anyone' | 'Members' | 'Owner';
	readonly 'x-class-name'?: string;
};
