/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Rendered content, which results from using a template or display page to process the content and return HTML.
 */
export type RenderedContent = {
    /**
     * The ID of the template or display page used to render the content.
     */
    readonly contentTemplateId?: string;
    /**
     * The name of the template or display page used to render the content.
     */
    contentTemplateName?: string;
    /**
     * The localized names of the template or display page used to render the content.
     */
    contentTemplateName_i18n?: Record<string, string>;
    /**
     * Specifies if the template or display page are marked as default to display the content.
     */
    markedAsDefault?: boolean;
    /**
     * An absolute URL to the rendered content.
     */
    renderedContentURL?: string;
    /**
     * Optional field with the rendered content, can be embedded with nestedFields.
     */
    renderedContentValue?: string;
};

