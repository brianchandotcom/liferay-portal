/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessBatchEngine_v1_0_FailedItem = {

	/**
	 * The item which failed to be imported.
	 */
	'item'?: string;

	/**
	 * Position of the item in the import file. For CSV file it will represent a line number, for JSON file it will represent an array index etc.
	 */
	'itemIndex'?: number;

	/**
	 * Message describing the reason of import failure.
	 */
	'message'?: string;
	readonly 'x-class-name'?: string;
};
