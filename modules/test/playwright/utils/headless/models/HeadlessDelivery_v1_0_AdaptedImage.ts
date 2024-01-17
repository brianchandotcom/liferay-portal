/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * An array of images in several resolutions and sizes, created by the Adaptive Media framework.
 */
export type HeadlessDelivery_v1_0_AdaptedImage = {

	/**
	 * The image's relative URL.
	 */
	'contentUrl'?: string;

	/**
	 * Optional field with the content of the image in Base64, can be embedded with nestedFields.
	 */
	readonly 'contentValue'?: string;

	/**
	 * The image's height in pixels.
	 */
	'height'?: number;

	/**
	 * The name of the image's Adaptive Media image resolution.
	 */
	'resolutionName'?: string;

	/**
	 * The image's size in bytes.
	 */
	'sizeInBytes'?: number;

	/**
	 * The image's width in pixels.
	 */
	'width'?: number;
	readonly 'x-class-name'?: string;
};
