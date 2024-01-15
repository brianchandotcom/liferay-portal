/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_Creator} from './HeadlessForm_v1_0_Creator';
import type {HeadlessForm_v1_0_FormRecord} from './HeadlessForm_v1_0_FormRecord';
import type {HeadlessForm_v1_0_FormStructure} from './HeadlessForm_v1_0_FormStructure';
export type HeadlessForm_v1_0_Form = {
	'availableLanguages'?: Array<string>;
	'creator'?: HeadlessForm_v1_0_Creator;
	'dateCreated'?: string;
	'dateModified'?: string;
	'datePublished'?: string;
	'defaultLanguage'?: string;
	'description'?: string;
	'description_i18n'?: Record<string, string>;
	readonly 'formRecords'?: Array<HeadlessForm_v1_0_FormRecord>;
	'formRecordsIds'?: Array<number>;
	'id'?: number;
	'name'?: string;
	'name_i18n'?: Record<string, string>;
	'siteId'?: number;
	'structure'?: HeadlessForm_v1_0_FormStructure;
	'structureId'?: number;
	readonly 'x-class-name'?: string;
};
