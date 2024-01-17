/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Creator} from './HeadlessAdminUser_v1_0_Creator';
export type HeadlessAdminUser_v1_0_Site = {
	'availableLanguages'?: Array<string>;
	'creator'?: HeadlessAdminUser_v1_0_Creator;
	'description'?: string;
	'description_i18n'?: Record<string, string>;
	'descriptiveName'?: string;
	'descriptiveName_i18n'?: Record<string, string>;
	'friendlyUrlPath'?: string;
	'id'?: number;
	'key'?: string;
	'membershipType'?: string;
	'name'?: string;
	'name_i18n'?: Record<string, string>;
	'parentSiteId'?: number;
	'sites'?: Array<HeadlessAdminUser_v1_0_Site>;
	readonly 'x-class-name'?: string;
};
