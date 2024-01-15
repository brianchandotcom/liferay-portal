/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ChangeTrackingRest_v1_0_CTRemote = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'clientId'?: string;
	'clientSecret'?: string;

	/**
	 * The remote's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any of the remote's fields were changed.
	 */
	readonly 'dateModified'?: string;
	'description'?: string;
	readonly 'id'?: number;
	'name'?: string;

	/**
	 * The remote's creator.
	 */
	readonly 'ownerName'?: string;
	'url'?: string;
	readonly 'x-class-name'?: string;
};
