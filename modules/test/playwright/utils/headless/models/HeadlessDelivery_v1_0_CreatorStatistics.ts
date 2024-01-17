/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * The thread's creator statistics (rank, join date, number of posts, ...)
 */
export type HeadlessDelivery_v1_0_CreatorStatistics = {

	/**
	 * Join date of the author.
	 */
	readonly 'joinDate'?: string;

	/**
	 * Last post created by the author.
	 */
	readonly 'lastPostDate'?: string;

	/**
	 * Number of posts publicated by the author.
	 */
	readonly 'postsNumber'?: number;

	/**
	 * The rank of the author.
	 */
	readonly 'rank'?: string;
	readonly 'x-class-name'?: string;
};
