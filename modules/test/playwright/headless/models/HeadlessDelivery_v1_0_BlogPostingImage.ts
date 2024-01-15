/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessDelivery_v1_0_BlogPostingImage = {

	/**
	 * The image's relative URL.
	 */
	readonly 'contentUrl'?: string;

	/**
	 * optional field with the content of the image in Base64, can be embedded with nestedFields
	 */
	readonly 'contentValue'?: string;

	/**
	 * The image's content type (e.g., `application/png`, etc.).
	 */
	readonly 'encodingFormat'?: string;

	/**
	 * The image's file extension.
	 */
	readonly 'fileExtension'?: string;

	/**
	 * The image's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The image's size in bytes.
	 */
	readonly 'sizeInBytes'?: number;

	/**
	 * The image's title text.
	 */
	'title'?: string;
	'viewableBy'?: HeadlessDelivery_v1_0_BlogPostingImage.viewableBy;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessDelivery_v1_0_BlogPostingImage {
	export enum viewableBy {
		ANYONE = 'Anyone',
		MEMBERS = 'Members',
		OWNER = 'Owner',
	}
}
