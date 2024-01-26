/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ClientExtension } from './ClientExtension';
import type { ContentDocument } from './ContentDocument';
import type { MasterPage } from './MasterPage';
/**
 * Represents the settings of a page.
 */
export type Settings = {
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
    favIcon?: (ClientExtension | ContentDocument);
    /**
     * The client extensions for global css associated to the page.
     */
    globalCSSClientExtensions?: Array<ClientExtension>;
    /**
     * The client extensions for global js associated to the page.
     */
    globalJSClientExtensions?: Array<ClientExtension>;
    /**
     * The page's JavaScript.
     */
    javascript?: string;
    /**
     * The page's master page.
     */
    masterPage?: MasterPage;
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
    themeCSSClientExtension?: ClientExtension;
    /**
     * The page's theme name.
     */
    themeName?: string;
    /**
     * The page's theme settings.
     */
    themeSettings?: Record<string, any>;
};

