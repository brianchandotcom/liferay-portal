/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * The blog post's cover image.
 */
export type HeadlessDelivery_v1_0_Image = {

	/**
	 * The text describing the image.
	 */
	'caption'?: string;

	/**
	 * The image's relative URL.
	 */
	readonly 'contentUrl'?: string;

	/**
	 * optional field with the content of the image in Base64, can be embedded with nestedFields
	 */
	readonly 'contentValue'?: string;

	/**
	 * The image's ID. This can be used to retrieve more information in the `Document` API.
	 */
	'imageId'?: number;
	readonly 'x-class-name'?: string;
};
