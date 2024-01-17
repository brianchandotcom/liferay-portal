/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormFieldOption} from './HeadlessForm_v1_0_FormFieldOption';

/**
 * https://www.schema.org/FormFieldContext
 */
export type HeadlessForm_v1_0_FormFieldContext = {
	'evaluable'?: boolean;
	'formFieldOptions'?: Array<HeadlessForm_v1_0_FormFieldOption>;
	'name'?: string;
	'readOnly'?: boolean;
	'required'?: boolean;
	'valid'?: boolean;
	'value'?: string;
	'valueChanged'?: boolean;
	'visible'?: boolean;
	readonly 'x-class-name'?: string;
};
