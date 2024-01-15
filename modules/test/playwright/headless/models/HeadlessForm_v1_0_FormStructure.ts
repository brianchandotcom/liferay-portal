/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_Creator} from './HeadlessForm_v1_0_Creator';
import type {HeadlessForm_v1_0_FormPage} from './HeadlessForm_v1_0_FormPage';
import type {HeadlessForm_v1_0_FormSuccessPage} from './HeadlessForm_v1_0_FormSuccessPage';
export type HeadlessForm_v1_0_FormStructure = {
	'availableLanguages'?: Array<string>;
	'creator'?: HeadlessForm_v1_0_Creator;
	'dateCreated'?: string;
	'dateModified'?: string;
	'description'?: string;
	'description_i18n'?: Record<string, string>;

	/**
	 * https://www.schema.org/FormPage
	 */
	'formPages'?: Array<HeadlessForm_v1_0_FormPage>;
	'formSuccessPage'?: HeadlessForm_v1_0_FormSuccessPage;
	'id'?: number;
	'name'?: string;
	'name_i18n'?: Record<string, string>;
	'siteId'?: number;
	readonly 'x-class-name'?: string;
};
