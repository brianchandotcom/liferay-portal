/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormFieldContext} from './HeadlessForm_v1_0_FormFieldContext';

/**
 * https://www.schema.org/FormPageContext
 */
export type HeadlessForm_v1_0_FormPageContext = {
	'enabled'?: boolean;

	/**
	 * https://www.schema.org/FormFieldContext
	 */
	'formFieldContexts'?: Array<HeadlessForm_v1_0_FormFieldContext>;
	'showRequiredFieldsWarning'?: boolean;
	readonly 'x-class-name'?: string;
};
