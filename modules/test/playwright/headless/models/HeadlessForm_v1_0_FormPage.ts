/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormField} from './HeadlessForm_v1_0_FormField';

/**
 * https://www.schema.org/FormPage
 */
export type HeadlessForm_v1_0_FormPage = {
	'formFields'?: Array<HeadlessForm_v1_0_FormField>;
	'headline'?: string;
	'headline_i18n'?: Record<string, string>;
	'id'?: number;
	'text'?: string;
	'text_i18n'?: Record<string, string>;
	readonly 'x-class-name'?: string;
};
