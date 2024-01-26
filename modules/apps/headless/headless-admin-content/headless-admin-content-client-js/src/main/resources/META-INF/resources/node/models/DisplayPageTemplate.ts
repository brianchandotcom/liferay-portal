/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayPageTemplate_properties_pageDefinition_properties_pageElement_properties_pageElements_items } from './DisplayPageTemplate_properties_pageDefinition_properties_pageElement_properties_pageElements_items';
import type { DisplayPageTemplate_properties_pageDefinition_properties_settings_properties_globalJSClientExtensions_items } from './DisplayPageTemplate_properties_pageDefinition_properties_settings_properties_globalJSClientExtensions_items';
import type { DisplayPageTemplateSettings } from './DisplayPageTemplateSettings';
/**
 * Represents a Display Page Template that has fields and is tied to a content type
 */
export type DisplayPageTemplate = {
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The list of languages the Display Page Template has a translation for.
     */
    readonly availableLanguages?: Array<string>;
    /**
     * The Display Page Template's creator.
     */
    readonly creator?: {
        /**
         * The author's additional name (e.g., middle name).
         */
        readonly additionalName?: string;
        /**
         * The type of the content.
         */
        readonly contentType?: string;
        /**
         * The author's surname.
         */
        readonly familyName?: string;
        /**
         * The author's first name.
         */
        readonly givenName?: string;
        /**
         * The author's ID.
         */
        readonly id?: number;
        /**
         * A relative URL to the author's profile image.
         */
        readonly image?: string;
        /**
         * The author's full name.
         */
        readonly name?: string;
        /**
         * A relative URL to the author's user profile. Optional field, can be embedded with nestedFields.
         */
        readonly profileURL?: string;
        /**
         * A list of userGroups information.
         */
        userGroupBriefs?: Array<{
            /**
             * The ID of the user group.
             */
            readonly id?: number;
            /**
             * The name of the user group.
             */
            readonly name?: string;
        }>;
    };
    /**
     * The custom fields associated to the page that renders the Display Page Template.
     */
    customFields?: Array<any>;
    /**
     * The Display Page Template's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time any field of the Display Page Template was changed.
     */
    readonly dateModified?: string;
    /**
     * The Display Page Template's external key.
     */
    displayPageTemplateKey?: string;
    /**
     * The Display Page Template's external key.
     */
    displayPageTemplateSettings?: DisplayPageTemplateSettings;
    /**
     * Specifies if the Display Page Template is the default one for the content type.
     */
    markedAsDefault?: boolean;
    /**
     * Represent a definition of a Page.
     */
    pageDefinition?: {
        /**
         * The page's page element.
         */
        pageElement?: DisplayPageTemplate_properties_pageDefinition_properties_pageElement_properties_pageElements_items;
        /**
         * A list of the page rules this page has.
         */
        pageRules?: Array<{
            /**
             * The custom name of a Page rule.
             */
            conditionType?: 'All' | 'Any';
            /**
             * The page rule ID.
             */
            id?: string;
            /**
             * The custom name of a Page rule.
             */
            name?: string;
            /**
             * A list of actions of a Page rule.
             */
            pageRuleActions?: Array<{
                /**
                 * The page rule action's action.
                 */
                action?: string;
                /**
                 * The page rule action's ID.
                 */
                id?: string;
                /**
                 * The page rule condition's item ID.
                 */
                itemId?: string;
                /**
                 * The page rule action's type.
                 */
                type?: string;
            }>;
            /**
             * A list of conditions of a Page rule.
             */
            pageRuleConditions?: Array<{
                /**
                 * The page rule condition's description.
                 */
                condition?: string;
                /**
                 * The page rule condition's ID.
                 */
                id?: string;
                /**
                 * The page rule condition's type.
                 */
                type?: string;
                /**
                 * The page rule condition's value.
                 */
                value?: string;
            }>;
        }>;
        /**
         * The page's settings.
         */
        settings?: {
            /**
             * The page's color scheme name.
             */
            colorSchemeName?: string;
            /**
             * The page's CSS.
             */
            css?: string;
            /**
             * The FavIcon of the page
             */
            favIcon?: (DisplayPageTemplate_properties_pageDefinition_properties_settings_properties_globalJSClientExtensions_items | {
                /**
                 * Block of actions allowed by the user making the request.
                 */
                readonly actions?: Record<string, Record<string, string>>;
                /**
                 * The type of content.
                 */
                readonly contentType?: string;
                /**
                 * The document's relative URL.
                 */
                readonly contentUrl?: string;
                /**
                 * optional field with the content of the document in Base64, can be embedded with nestedFields
                 */
                readonly contentValue?: string;
                /**
                 * The document's description.
                 */
                description?: string;
                /**
                 * The document's content type (e.g., `application/pdf`, etc.).
                 */
                readonly encodingFormat?: string;
                /**
                 * The document's file extension.
                 */
                readonly fileExtension?: string;
                /**
                 * The document's ID.
                 */
                id?: number;
                /**
                 * The document's file size in bytes.
                 */
                readonly sizeInBytes?: number;
                /**
                 * The document's title.
                 */
                title?: string;
            });
            /**
             * The client extensions for global css associated to the page.
             */
            globalCSSClientExtensions?: Array<DisplayPageTemplate_properties_pageDefinition_properties_settings_properties_globalJSClientExtensions_items>;
            /**
             * The client extensions for global js associated to the page.
             */
            globalJSClientExtensions?: Array<{
                clientExtensionConfig?: Record<string, string>;
                /**
                 * The client extension's external reference code.
                 */
                externalReferenceCode?: string;
                /**
                 * The client extension's name.
                 */
                readonly name?: string;
            }>;
            /**
             * The page's JavaScript.
             */
            javascript?: string;
            /**
             * The page's master page.
             */
            masterPage?: {
                /**
                 * The master page's key.
                 */
                key?: string;
                /**
                 * The master page's name.
                 */
                name?: string;
            };
            /**
             * The StyleBook that is applied to the page.
             */
            styleBook?: {
                /**
                 * The stylebook's key.
                 */
                key?: string;
                /**
                 * The stylebook's name.
                 */
                name?: string;
            };
            /**
             * The Client Extension for the theme css of a page
             */
            themeCSSClientExtension?: DisplayPageTemplate_properties_pageDefinition_properties_settings_properties_globalJSClientExtensions_items;
            /**
             * The page's theme name.
             */
            themeName?: string;
            /**
             * The page's theme settings.
             */
            themeSettings?: Record<string, any>;
        };
        /**
         * The version of the JSON generated by page definition.
         */
        version?: number;
    };
    /**
     * The ID of the site to which this Page Template is scoped.
     */
    readonly siteId?: number;
    /**
     * The title of the Display Page Template
     */
    title: string;
    /**
     * A valid external identifier to reference this Display Page Template.
     */
    readonly uuid?: string;
};

