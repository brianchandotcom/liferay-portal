/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectViewColumn} from './ObjectAdmin_v1_0_ObjectViewColumn';
import type {ObjectAdmin_v1_0_ObjectViewFilterColumn} from './ObjectAdmin_v1_0_ObjectViewFilterColumn';
import type {ObjectAdmin_v1_0_ObjectViewSortColumn} from './ObjectAdmin_v1_0_ObjectViewSortColumn';
export type ObjectAdmin_v1_0_ObjectView = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'defaultObjectView'?: boolean;
	readonly 'id'?: number;
	'name'?: Record<string, string>;
	'objectDefinitionExternalReferenceCode'?: string;
	'objectDefinitionId'?: number;
	'objectViewColumns'?: Array<ObjectAdmin_v1_0_ObjectViewColumn>;
	'objectViewFilterColumns'?: Array<ObjectAdmin_v1_0_ObjectViewFilterColumn>;
	'objectViewSortColumns'?: Array<ObjectAdmin_v1_0_ObjectViewSortColumn>;
	readonly 'x-class-name'?: string;
};
