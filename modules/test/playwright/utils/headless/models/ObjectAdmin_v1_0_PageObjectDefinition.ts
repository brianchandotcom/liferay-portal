/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_Facet} from './ObjectAdmin_v1_0_Facet';
import type {ObjectAdmin_v1_0_ObjectDefinition} from './ObjectAdmin_v1_0_ObjectDefinition';
export type ObjectAdmin_v1_0_PageObjectDefinition = {
	items?: Array<ObjectAdmin_v1_0_ObjectDefinition>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<ObjectAdmin_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
