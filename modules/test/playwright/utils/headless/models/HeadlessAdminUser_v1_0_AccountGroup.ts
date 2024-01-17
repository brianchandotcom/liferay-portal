/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_CustomField} from './HeadlessAdminUser_v1_0_CustomField';
export type HeadlessAdminUser_v1_0_AccountGroup = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'customFields'?: Array<HeadlessAdminUser_v1_0_CustomField>;
	'description'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'name': string;
	readonly 'x-class-name'?: string;
};
