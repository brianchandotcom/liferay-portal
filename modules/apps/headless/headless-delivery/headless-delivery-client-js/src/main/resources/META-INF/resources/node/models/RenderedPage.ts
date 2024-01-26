/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * A list of rendered pages, which results from using a page template and the appropriate viewport to process the page and return HTML.
 */
export type RenderedPage = {
    /**
     * The ID of the master page used to render the content.
     */
    readonly masterPageId?: string;
    /**
     * The name of the master page used to render the content.
     */
    masterPageName?: string;
    /**
     * The ID of the template used to render the content.
     */
    readonly pageTemplateId?: string;
    /**
     * The name of the template used to render the content.
     */
    pageTemplateName?: string;
    /**
     * An absolute URL to the rendered page.
     */
    renderedPageURL?: string;
};

