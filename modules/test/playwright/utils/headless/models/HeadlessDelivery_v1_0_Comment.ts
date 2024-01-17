/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
export type HeadlessDelivery_v1_0_Comment = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * The comment's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The comment's latest modification date.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The comment's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The comment's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The number of child comments on this comment.
	 */
	readonly 'numberOfComments'?: number;

	/**
	 * the ID of the comment's parent, if it exists.
	 */
	'parentCommentId'?: number;

	/**
	 * The comment's text content.
	 */
	'text'?: string;
	readonly 'x-class-name'?: string;
};
