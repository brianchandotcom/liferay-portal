/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminListType_v1_0_ListTypeEntry} from './HeadlessAdminListType_v1_0_ListTypeEntry';
export type HeadlessAdminListType_v1_0_ListTypeDefinition = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'listTypeEntries'?: Array<HeadlessAdminListType_v1_0_ListTypeEntry>;
	'name'?: string;
	'name_i18n'?: Record<string, string>;
	'system'?: boolean;
	readonly 'x-class-name'?: string;
};
