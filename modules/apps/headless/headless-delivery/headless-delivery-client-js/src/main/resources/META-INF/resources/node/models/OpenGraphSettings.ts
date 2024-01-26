/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ContentDocument } from './ContentDocument';
/**
 * Represents settings related with Open Graph protocol.
 */
export type OpenGraphSettings = {
    /**
     * The Open Graph's description.
     */
    description?: string;
    /**
     * The localized Open Graph's descriptions.
     */
    description_i18n?: Record<string, string>;
    /**
     * The Open Graph's image.
     */
    image?: ContentDocument;
    /**
     * The Open Graph's image alt.
     */
    imageAlt?: string;
    /**
     * The localized Open Graph's image alts.
     */
    imageAlt_i18n?: Record<string, string>;
    /**
     * The Open Graph's title.
     */
    title?: string;
    /**
     * The localized Open Graph's titles.
     */
    title_i18n?: Record<string, string>;
};

