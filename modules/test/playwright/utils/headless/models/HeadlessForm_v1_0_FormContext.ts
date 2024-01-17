/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormFieldValue} from './HeadlessForm_v1_0_FormFieldValue';
import type {HeadlessForm_v1_0_FormPageContext} from './HeadlessForm_v1_0_FormPageContext';
export type HeadlessForm_v1_0_FormContext = {

	/**
	 * https://www.schema.org/FormFieldValue
	 */
	'formFieldValues'?: Array<HeadlessForm_v1_0_FormFieldValue>;

	/**
	 * https://www.schema.org/FormPageContext
	 */
	'formPageContexts'?: Array<HeadlessForm_v1_0_FormPageContext>;
	'readOnly'?: boolean;
	'showRequiredFieldsWarning'?: boolean;
	'showSubmitButton'?: boolean;
	readonly 'x-class-name'?: string;
};
