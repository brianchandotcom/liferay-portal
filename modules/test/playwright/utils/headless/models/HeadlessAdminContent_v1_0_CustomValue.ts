/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_Geo} from './HeadlessAdminContent_v1_0_Geo';

/**
 * The field's value.
 */
export type HeadlessAdminContent_v1_0_CustomValue = {

	/**
	 * The field's content value for simple types.
	 */
	'data'?: Record<string, any>;

	/**
	 * The localized field's content values for simple types.
	 */
	'data_i18n'?: Record<string, string>;
	'geo'?: HeadlessAdminContent_v1_0_Geo;
	readonly 'x-class-name'?: string;
};
