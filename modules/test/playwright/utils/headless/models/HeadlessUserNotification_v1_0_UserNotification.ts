/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessUserNotification_v1_0_UserNotification = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * The user notification's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The user notification's identifier.
	 */
	readonly 'id'?: number;

	/**
	 * The user notification's message.
	 */
	'message'?: string;

	/**
	 * A flag that indicates whether this user notification has been read.
	 */
	readonly 'read'?: boolean;

	/**
	 * User notification's type.
	 */
	readonly 'type'?: number;
	readonly 'x-class-name'?: string;
};
