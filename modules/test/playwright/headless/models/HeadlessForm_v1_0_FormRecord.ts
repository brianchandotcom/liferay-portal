/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_Creator} from './HeadlessForm_v1_0_Creator';
import type {HeadlessForm_v1_0_FormFieldValue} from './HeadlessForm_v1_0_FormFieldValue';
export type HeadlessForm_v1_0_FormRecord = {
	'creator'?: HeadlessForm_v1_0_Creator;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	readonly 'datePublished'?: string;
	'draft'?: boolean;
	'formFieldValues'?: Array<HeadlessForm_v1_0_FormFieldValue>;
	'formId'?: number;
	readonly 'id'?: number;
	readonly 'x-class-name'?: string;
};
