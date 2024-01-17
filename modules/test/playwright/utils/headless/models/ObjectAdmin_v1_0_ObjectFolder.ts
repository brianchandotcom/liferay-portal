/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectFolderItem} from './ObjectAdmin_v1_0_ObjectFolderItem';
export type ObjectAdmin_v1_0_ObjectFolder = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'label'?: Record<string, string>;
	'name'?: string;
	'objectFolderItems'?: Array<ObjectAdmin_v1_0_ObjectFolderItem>;
	readonly 'x-class-name'?: string;
};
