/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Geo } from './Geo';
/**
 * Represents a custom value.
 */
export type CustomValue = {
    /**
     * The field's content value for simple types.
     */
    data?: Record<string, any>;
    /**
     * The localized field's content values for simple types.
     */
    data_i18n?: Record<string, string>;
    /**
     * A point determined by latitude and longitude.
     */
    geo?: Geo;
};

