/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminAddress_v1_0_Region} from './HeadlessAdminAddress_v1_0_Region';
export type HeadlessAdminAddress_v1_0_Country = {
	'a2': string;
	'a3': string;
	'active'?: boolean;
	'billingAllowed'?: boolean;
	'groupFilterEnabled'?: boolean;
	readonly 'id'?: number;
	'idd'?: number;
	'name': string;
	'number': number;
	'position'?: number;
	readonly 'regions'?: Array<HeadlessAdminAddress_v1_0_Region>;
	'shippingAllowed'?: boolean;
	'subjectToVAT'?: boolean;
	'title_i18n'?: Record<string, string>;
	'zipRequired'?: boolean;
	readonly 'x-class-name'?: string;
};
