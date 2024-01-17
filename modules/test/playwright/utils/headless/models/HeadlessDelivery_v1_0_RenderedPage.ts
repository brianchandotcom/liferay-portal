/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Metadata of the page such as it's master page and template.
 */
export type HeadlessDelivery_v1_0_RenderedPage = {

	/**
	 * The ID of the master page used to render the content.
	 */
	readonly 'masterPageId'?: string;

	/**
	 * The name of the master page used to render the content.
	 */
	'masterPageName'?: string;

	/**
	 * The ID of the template used to render the content.
	 */
	readonly 'pageTemplateId'?: string;

	/**
	 * The name of the template used to render the content.
	 */
	'pageTemplateName'?: string;

	/**
	 * An absolute URL to the rendered page.
	 */
	'renderedPageURL'?: string;
	readonly 'x-class-name'?: string;
};
